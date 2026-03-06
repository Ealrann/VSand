package org.sheepy.vsand.dump;

import org.sheepy.vsand.model.vsand.Materials;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class StateDumpWriter
{
	private final DumpConfig config;
	private final VSandApplication application;
	private final Path outDir;
	private final int nameWidth;

	public StateDumpWriter(final VSandApplication application, final DumpConfig config) throws IOException
	{
		this.config = config;
		this.application = application;
		this.outDir = config.outDir();
		this.nameWidth = Math.max(4, String.valueOf(config.frames()).length());

		Files.createDirectories(outDir);
		writeMetadata();
	}

	public Path outDir()
	{
		return outDir;
	}

	public void writeFrame(final int frameIndex,
						   final int simulatedTicks,
						   final byte[] swizzledBoard,
						   final byte[] swizzledMass) throws IOException
	{
		final String frameName = ("frame_%0" + nameWidth + "d").formatted(frameIndex);

		if (config.dumpRawBuffers())
		{
			Files.write(outDir.resolve(frameName + ".board.swz.bin"), swizzledBoard);
			Files.write(outDir.resolve(frameName + ".mass.swz.bin"), swizzledMass);
		}

		final var board = SwizzledBoard.decode(config.size().x(), config.size().y(), swizzledBoard);
		final var mass = SwizzledMass.decode(config.size().x(), config.size().y(), swizzledMass);

		final var stats = computeStats(board, mass);

		final var builder = new StringBuilder(64 * 1024);
		builder.append("frame=").append(frameIndex)
			   .append(" ticks=").append(simulatedTicks)
			   .append(" size=").append(config.size().x()).append('x').append(config.size().y())
			   .append(" speed=").append(config.speed())
			   .append('\n');

		builder.append("cellsNonEmpty=").append(stats.nonEmptyCells)
			   .append(" cellsNonZeroMass=").append(stats.nonZeroMassCells)
			   .append(" totalMass=").append(stats.totalMass)
			   .append('\n');

		for (final var entry : stats.perMaterial)
		{
			if (entry.cellCount == 0 && entry.massSum == 0) continue;
			final String materialName = materialName(entry.materialId);
			builder.append("mat=").append(hex8(entry.materialId))
				   .append(" name=").append(materialName)
				   .append(" cells=").append(entry.cellCount)
				   .append(" mass=").append(entry.massSum);
			if (entry.cellCount > 0)
			{
				builder.append(" bbox=")
					   .append(entry.minX).append(',').append(entry.minY)
					   .append("..")
					   .append(entry.maxX).append(',').append(entry.maxY)
					   .append(" span=").append(entry.maxX - entry.minX + 1).append('x').append(entry.maxY - entry.minY + 1)
					   .append(" avgX=").append(formatAverage(entry.sumX, entry.cellCount))
					   .append(" avgY=").append(formatAverage(entry.sumY, entry.cellCount));
			}
			if (entry.massSum > 0)
			{
				builder.append(" minMass=").append(entry.minMass)
					   .append(" maxMass=").append(entry.maxMass)
					   .append(" avgMass=").append(formatAverage(entry.massSum, entry.cellCount));
			}
			builder.append('\n');
		}

		builder.append('\n');
		builder.append("matHex:\n");
		appendMaterialHexGrid(builder, board);
		builder.append('\n');
		builder.append("massHex:\n");
		appendMassHexGrid(builder, mass);

		Files.writeString(outDir.resolve(frameName + ".txt"), builder, StandardCharsets.UTF_8);
	}

	private void writeMetadata() throws IOException
	{
		final var builder = new StringBuilder(4096);
		builder.append("size=").append(config.size().x()).append('x').append(config.size().y()).append('\n');
		builder.append("scenario=").append(config.scenarioPath()).append('\n');
		builder.append("frames=").append(config.frames()).append('\n');
		builder.append("speed=").append(config.speed()).append('\n');
		builder.append("dumpRaw=").append(config.dumpRawBuffers()).append('\n');
		builder.append("seed=").append(config.deterministicSeed() == null ? "random" : config.deterministicSeed()).append('\n');
		builder.append('\n');
		builder.append("materials:\n");
		final var materials = application.materials().materials();
		for (int i = 0; i < materials.size(); i++)
		{
			final var material = materials.get(i);
			builder.append(hex8(i)).append(' ').append(material.name()).append('\n');
		}
		Files.writeString(outDir.resolve("metadata.txt"), builder, StandardCharsets.UTF_8);
	}

	private String materialName(final int materialId)
	{
		final Materials materials = application.materials();
		if (materials == null) return "?";
		if (materialId < 0 || materialId >= materials.materials().size()) return "?";
		final var material = materials.materials().get(materialId);
		return material != null ? material.name() : "?";
	}

	private static void appendMaterialHexGrid(final StringBuilder builder, final SwizzledBoard board)
	{
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				if (x > 0) builder.append(' ');
				builder.append(hex8(board.materialAt(x, y)));
			}
			builder.append('\n');
		}
	}

	private static void appendMassHexGrid(final StringBuilder builder, final SwizzledMass mass)
	{
		for (int y = 0; y < mass.height(); y++)
		{
			for (int x = 0; x < mass.width(); x++)
			{
				if (x > 0) builder.append(' ');
				builder.append(hex16(mass.massAt(x, y)));
			}
			builder.append('\n');
		}
	}

	private static Stats computeStats(final SwizzledBoard board, final SwizzledMass mass)
	{
		final var perMaterial = new StatsEntry[256];
		for (int i = 0; i < perMaterial.length; i++)
		{
			perMaterial[i] = new StatsEntry(i);
		}

		long totalMass = 0;
		int nonEmpty = 0;
		int nonZeroMassCells = 0;

		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				final int materialId = board.materialAt(x, y);
				if (materialId != 0) nonEmpty++;

				final int m = mass.massAt(x, y);
				if (m != 0) nonZeroMassCells++;
				totalMass += m;

				final var entry = perMaterial[materialId];
				entry.cellCount++;
				entry.sumX += x;
				entry.sumY += y;
				entry.minX = Math.min(entry.minX, x);
				entry.maxX = Math.max(entry.maxX, x);
				entry.minY = Math.min(entry.minY, y);
				entry.maxY = Math.max(entry.maxY, y);
				entry.massSum += m;
				if (m != 0)
				{
					entry.minMass = Math.min(entry.minMass, m);
					entry.maxMass = Math.max(entry.maxMass, m);
				}
			}
		}

		return new Stats(totalMass, nonEmpty, nonZeroMassCells, Arrays.asList(perMaterial));
	}

	private static String hex8(final int value)
	{
		return "%02X".formatted(value & 0xFF);
	}

	private static String hex16(final int value)
	{
		return "%04X".formatted(value & 0xFFFF);
	}

	private static String formatAverage(final long sum, final int count)
	{
		if (count == 0) return "n/a";

		return "%.2f".formatted((double) sum / count);
	}

	private record Stats(long totalMass, int nonEmptyCells, int nonZeroMassCells, java.util.List<StatsEntry> perMaterial)
	{
	}

	private static final class StatsEntry
	{
		private final int materialId;
		private int cellCount = 0;
		private long massSum = 0;
		private long sumX = 0;
		private long sumY = 0;
		private int minX = Integer.MAX_VALUE;
		private int maxX = Integer.MIN_VALUE;
		private int minY = Integer.MAX_VALUE;
		private int maxY = Integer.MIN_VALUE;
		private int minMass = Integer.MAX_VALUE;
		private int maxMass = Integer.MIN_VALUE;

		private StatsEntry(final int materialId)
		{
			this.materialId = materialId;
		}
	}

	public record SwizzledBoard(int width, int height, int swizzledHeight, int[] packed)
	{
		public static SwizzledBoard decode(final int width, final int height, final byte[] swizzledBytes)
		{
			final int swizzledWidth = width / 2;
			final int swizzledHeight = height / 2;
			final int expectedByteLength = swizzledWidth * swizzledHeight * Integer.BYTES;
			if (swizzledBytes.length != expectedByteLength)
			{
				throw new IllegalArgumentException("Board buffer size mismatch: expected %d, got %d"
														   .formatted(expectedByteLength, swizzledBytes.length));
			}

			final var buffer = ByteBuffer.wrap(swizzledBytes).order(ByteOrder.LITTLE_ENDIAN);
			final int packedLength = swizzledWidth * swizzledHeight;
			final var packed = new int[packedLength];
			for (int i = 0; i < packedLength; i++)
			{
				packed[i] = buffer.getInt(i * Integer.BYTES);
			}

			return new SwizzledBoard(width, height, swizzledHeight, packed);
		}

		public int materialAt(final int x, final int y)
		{
			final int swizzledX = x >> 1;
			final int swizzledY = y >> 1;
			final int swizzledLoc = swizzledX * swizzledHeight + swizzledY;
			final int value = packed[swizzledLoc];
			final int offset = ((x & 1) | ((y & 1) << 1)) << 3;
			return (value >> offset) & 0xFF;
		}
	}

	public record SwizzledMass(int width, int height, int swizzledHeight, int[] packed)
	{
		public static SwizzledMass decode(final int width, final int height, final byte[] swizzledBytes)
		{
			final int swizzledWidth = width / 2;
			final int swizzledHeight = height / 2;
			final int expectedByteLength = swizzledWidth * swizzledHeight * 2 * Integer.BYTES;
			if (swizzledBytes.length != expectedByteLength)
			{
				throw new IllegalArgumentException("Mass buffer size mismatch: expected %d, got %d"
														   .formatted(expectedByteLength, swizzledBytes.length));
			}

			final var buffer = ByteBuffer.wrap(swizzledBytes).order(ByteOrder.LITTLE_ENDIAN);
			final int packedLength = swizzledWidth * swizzledHeight * 2;
			final var packed = new int[packedLength];
			for (int i = 0; i < packedLength; i++)
			{
				packed[i] = buffer.getInt(i * Integer.BYTES);
			}

			return new SwizzledMass(width, height, swizzledHeight, packed);
		}

		public int massAt(final int x, final int y)
		{
			final int swizzledX = x >> 1;
			final int swizzledY = y >> 1;
			final int swizzledLoc = swizzledX * swizzledHeight + swizzledY;

			final int wordIndex = swizzledLoc * 2 + (y & 1);
			final int word = packed[wordIndex];
			final int shift = (x & 1) << 4;

			return (word >> shift) & 0xFFFF;
		}
	}
}
