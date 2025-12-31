package org.sheepy.vsand.dump;

import org.joml.Vector2i;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record DumpConfig(Vector2i size,
						 Path scenarioPath,
						 int frames,
						 int speed,
						 Path outDir,
						 boolean dumpRawBuffers,
						 Long deterministicSeed)
{
	private static final DateTimeFormatter RUN_DIR_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

	public static DumpConfig parse(final String[] args)
	{
		Vector2i size = null;
		Path scenario = null;
		Integer frames = null;
		int speed = 1;
		Path outDir = null;
		boolean dumpRaw = true;
		Long seed = null;

		for (final String arg : args)
		{
			if (arg == null || arg.isBlank()) continue;

			if ("--help".equals(arg) || "-h".equals(arg))
			{
				throw usage(null);
			}
			else if (arg.startsWith("--size="))
			{
				size = parseSize(arg.substring("--size=".length()));
			}
			else if (arg.startsWith("--scenario="))
			{
				scenario = Path.of(arg.substring("--scenario=".length()));
			}
			else if (arg.startsWith("--frames="))
			{
				frames = parsePositiveInt("--frames", arg.substring("--frames=".length()));
			}
			else if (arg.startsWith("--speed="))
			{
				speed = parsePositiveInt("--speed", arg.substring("--speed=".length()));
			}
			else if (arg.startsWith("--out="))
			{
				outDir = Path.of(arg.substring("--out=".length()));
			}
			else if ("--no-raw".equals(arg))
			{
				dumpRaw = false;
			}
			else if (arg.startsWith("--seed="))
			{
				seed = Long.parseLong(arg.substring("--seed=".length()));
			}
		}

		if (size == null) throw usage("Missing --size=WxH");
		if (scenario == null) throw usage("Missing --scenario=/path/to/scenario.txt");
		if (frames == null) throw usage("Missing --frames=N");
		if ((size.x() & 1) != 0 || (size.y() & 1) != 0) throw usage("Size must be even (swizzled storage)");

		final var effectiveOutDir = outDir != null ? outDir : defaultOutDir();
		return new DumpConfig(new Vector2i(size), scenario, frames, speed, effectiveOutDir, dumpRaw, seed);
	}

	public void applyTo(final VSandApplication application)
	{
		application.speed(speed);

		if (deterministicSeed != null)
		{
			final var buffer = findBoardConstantBuffer(application);
			if (buffer != null)
			{
				buffer.deterministicRandom(true);
				buffer.randomSeed(deterministicSeed);
			}
		}
	}

	private static BoardConstantBuffer findBoardConstantBuffer(final VSandApplication application)
	{
		return application.streamTree()
						  .filter(BoardConstantBuffer.class::isInstance)
						  .map(BoardConstantBuffer.class::cast)
						  .findFirst()
						  .orElse(null);
	}

	private static Vector2i parseSize(final String value)
	{
		final int sep = value.indexOf('x');
		if (sep <= 0 || sep + 1 >= value.length()) throw usage("Invalid --size value: " + value);

		final int w = parsePositiveInt("width", value.substring(0, sep));
		final int h = parsePositiveInt("height", value.substring(sep + 1));
		return new Vector2i(w, h);
	}

	private static int parsePositiveInt(final String name, final String value)
	{
		final int parsed;
		try
		{
			parsed = Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			throw usage("Invalid %s value: %s".formatted(name, value));
		}

		if (parsed <= 0) throw usage("%s must be > 0".formatted(name));
		return parsed;
	}

	private static Path defaultOutDir()
	{
		final var timestamp = RUN_DIR_FORMAT.format(LocalDateTime.now());
		return Path.of("build", "state-dumps", "run-" + timestamp);
	}

	private static IllegalArgumentException usage(final String error)
	{
		final var message = """
				%s
				Usage: VSandStateDumpLauncher --size=WxH --scenario=PATH --frames=N [--speed=N] [--seed=LONG] [--out=DIR] [--no-raw]
				
				Scenario format (one command per line, '#' comments allowed):
				  - fill <MaterialName>
				  - frame <MaterialName> <thickness>
				  - line <MaterialName> <x1> <y1> <x2> <y2> <thickness>
				  - circle <MaterialName> <x> <y> <size>
				  - square <MaterialName> <x> <y> <size>
				"""
				.formatted(error == null ? "" : "Error: " + error + "\n");
		return new IllegalArgumentException(message);
	}
}

