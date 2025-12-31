package org.sheepy.vsand.dump;

import org.sheepy.vsand.model.vsand.DrawCircle;
import org.sheepy.vsand.model.vsand.DrawCommand;
import org.sheepy.vsand.model.vsand.DrawLine;
import org.sheepy.vsand.model.vsand.DrawSquare;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record Scenario(List<ScenarioCommand> commands)
{
	public static Scenario parse(final Path path) throws IOException
	{
		final var content = Files.readString(path, StandardCharsets.UTF_8);
		final var lines = content.split("\\R", -1);

		final var commands = new ArrayList<ScenarioCommand>();
		for (int i = 0; i < lines.length; i++)
		{
			final var rawLine = lines[i];
			final var line = stripComment(rawLine).trim();
			if (line.isBlank()) continue;

			final var parts = line.split("\\s+");
			try
			{
				commands.add(parseCommand(parts));
			}
			catch (RuntimeException e)
			{
				throw new IllegalArgumentException("Invalid scenario command at %s:%d: %s"
														   .formatted(path, i + 1, rawLine.trim()), e);
			}
		}

		return new Scenario(List.copyOf(commands));
	}

	public void applyTo(final VSandApplication application)
	{
		final var drawQueue = application.drawQueue();
		for (final var command : commands)
		{
			switch (command)
			{
				case Fill fill -> drawQueue.add(buildFill(application, fill.materialName()));
				case Frame frame -> addFrame(drawQueue, application, frame.materialName(), frame.thickness());
				case Line line -> drawQueue.add(buildLine(application, line));
				case Circle circle -> drawQueue.add(buildCircle(application, circle));
				case Square square -> drawQueue.add(buildSquare(application, square));
			}
		}
	}

	private static ScenarioCommand parseCommand(final String[] parts)
	{
		return switch (parts[0])
		{
			case "fill" -> new Fill(require(parts, 1, "material"));
			case "frame" -> new Frame(require(parts, 1, "material"), parseInt(require(parts, 2, "thickness")));
			case "line" -> new Line(require(parts, 1, "material"),
									parseInt(require(parts, 2, "x1")),
									parseInt(require(parts, 3, "y1")),
									parseInt(require(parts, 4, "x2")),
									parseInt(require(parts, 5, "y2")),
									parseInt(require(parts, 6, "size")));
			case "circle" -> new Circle(require(parts, 1, "material"),
										parseInt(require(parts, 2, "x")),
										parseInt(require(parts, 3, "y")),
										parseInt(require(parts, 4, "size")));
			case "square" -> new Square(require(parts, 1, "material"),
										parseInt(require(parts, 2, "x")),
										parseInt(require(parts, 3, "y")),
										parseInt(require(parts, 4, "size")));
			default -> throw new IllegalArgumentException("Unknown command: " + parts[0]);
		};
	}

	private static DrawCommand buildFill(final VSandApplication application, final String materialName)
	{
		final var material = findMaterial(application, materialName);
		final int size = Math.max(application.size().x(), application.size().y());
		final var draw = DrawSquare.builder().build();
		draw.material(material);
		draw.size(size);
		draw.x(application.size().x() / 2);
		draw.y(application.size().y() / 2);
		return draw;
	}

	private static void addFrame(final List<DrawCommand> drawQueue,
								 final VSandApplication application,
								 final String materialName,
								 final int thickness)
	{
		final var material = findMaterial(application, materialName);
		final int w = application.size().x();
		final int h = application.size().y();

		drawQueue.add(buildLine(material, 0, 0, w - 1, 0, thickness));
		drawQueue.add(buildLine(material, 0, h - 1, w - 1, h - 1, thickness));
		drawQueue.add(buildLine(material, 0, 0, 0, h - 1, thickness));
		drawQueue.add(buildLine(material, w - 1, 0, w - 1, h - 1, thickness));
	}

	private static DrawCommand buildLine(final VSandApplication application, final Line line)
	{
		final var material = findMaterial(application, line.materialName());
		return buildLine(material, line.x1(), line.y1(), line.x2(), line.y2(), line.thickness());
	}

	private static DrawCommand buildLine(final Material material,
										 final int x1,
										 final int y1,
										 final int x2,
										 final int y2,
										 final int thickness)
	{
		final var draw = DrawLine.builder().build();
		draw.material(material);
		draw.x1(x1);
		draw.y1(y1);
		draw.x2(x2);
		draw.y2(y2);
		draw.size(thickness);
		return draw;
	}

	private static DrawCommand buildCircle(final VSandApplication application, final Circle circle)
	{
		final var material = findMaterial(application, circle.materialName());
		final var draw = DrawCircle.builder().build();
		draw.material(material);
		draw.x(circle.x());
		draw.y(circle.y());
		draw.size(circle.size());
		return draw;
	}

	private static DrawCommand buildSquare(final VSandApplication application, final Square square)
	{
		final var material = findMaterial(application, square.materialName());
		final var draw = DrawSquare.builder().build();
		draw.material(material);
		draw.x(square.x());
		draw.y(square.y());
		draw.size(square.size());
		return draw;
	}

	private static Material findMaterial(final VSandApplication application, final String name)
	{
		return application.materials().materials().stream()
						  .filter(m -> name.equals(m.name()))
						  .findFirst()
						  .orElseThrow(() -> new IllegalArgumentException("Unknown material: " + name));
	}

	private static String stripComment(final String line)
	{
		final int hashIndex = line.indexOf('#');
		return hashIndex >= 0 ? line.substring(0, hashIndex) : line;
	}

	private static String require(final String[] parts, final int index, final String what)
	{
		if (index >= parts.length) throw new IllegalArgumentException("Missing " + what);
		return parts[index];
	}

	private static int parseInt(final String value)
	{
		return Integer.parseInt(value);
	}

	public sealed interface ScenarioCommand permits Fill, Frame, Line, Circle, Square
	{
	}

	public record Fill(String materialName) implements ScenarioCommand
	{
	}

	public record Frame(String materialName, int thickness) implements ScenarioCommand
	{
	}

	public record Line(String materialName, int x1, int y1, int x2, int y2, int thickness) implements ScenarioCommand
	{
	}

	public record Circle(String materialName, int x, int y, int size) implements ScenarioCommand
	{
	}

	public record Square(String materialName, int x, int y, int size) implements ScenarioCommand
	{
	}
}

