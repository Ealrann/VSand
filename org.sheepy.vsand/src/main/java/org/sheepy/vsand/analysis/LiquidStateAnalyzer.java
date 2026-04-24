package org.sheepy.vsand.analysis;

import java.util.ArrayList;
import java.util.List;

public final class LiquidStateAnalyzer
{
	public static final int M_FULL = 4096;

	private LiquidStateAnalyzer()
	{
	}

	public static LiquidMetrics analyze(final MaterialGrid board, final MassGrid mass, final int materialId)
	{
		return analyze(board, mass, materialId, "material-" + materialId);
	}

	public static LiquidMetrics analyze(final MaterialGrid board,
										final MassGrid mass,
										final int materialId,
										final String materialName)
	{
		final var accumulator = accumulate(board, mass, materialId);
		final int profileBottomY = accumulator.hasLiquid() ? accumulator.maxY : board.height() - 1;
		return analyze(board, mass, materialId, materialName, profileBottomY);
	}

	public static LiquidMetrics analyze(final MaterialGrid board,
										final MassGrid mass,
										final int materialId,
										final String materialName,
										final int profileBottomY)
	{
		checkSameSize(board, mass);

		final var accumulator = accumulate(board, mass, materialId);
		final var profile = buildColumnProfile(board, mass, materialId, clamp(profileBottomY, 0, board.height() - 1));

		if (accumulator.hasLiquid() == false)
		{
			return new LiquidMetrics(materialId,
									 materialName,
									 board.width(),
									 board.height(),
									 0,
									 0,
									 0,
									 0,
									 0,
									 0,
									 0,
										 0,
										 0,
										 0,
										 -1,
										 -1,
									 -1,
									 -1,
									 Double.NaN,
									 Double.NaN,
									 profile);
		}

		return new LiquidMetrics(materialId,
								 materialName,
								 board.width(),
								 board.height(),
								 accumulator.totalMass,
								 accumulator.cellCount,
								 accumulator.nonZeroMassCells,
								 accumulator.zeroMassCells,
								 accumulator.partialCells,
								 accumulator.fullCells,
								 accumulator.overfullCells,
								 accumulator.surfaceCells,
								 accumulator.voidBelowCells,
								 accumulator.isolatedCells,
								 accumulator.minX,
								 accumulator.minY,
								 accumulator.maxX,
								 accumulator.maxY,
								 (double) accumulator.sumX / accumulator.cellCount,
								 (double) accumulator.sumY / accumulator.cellCount,
								 profile);
	}

	private static Accumulator accumulate(final MaterialGrid board, final MassGrid mass, final int materialId)
	{
		checkSameSize(board, mass);

		final var accumulator = new Accumulator();
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				if (board.materialAt(x, y) == materialId)
				{
					accumulator.add(board, mass, materialId, x, y);
				}
			}
		}
		return accumulator;
	}

	private static ColumnProfile buildColumnProfile(final MaterialGrid board,
													final MassGrid mass,
													final int materialId,
													final int bottomY)
	{
		final var columns = new ArrayList<ColumnMetrics>(board.width());
		int leftExtent = -1;
		int rightExtent = -1;
		int minBottomHeight = Integer.MAX_VALUE;
		int maxBottomHeight = 0;

		for (int x = 0; x < board.width(); x++)
		{
			int cellCount = 0;
			long massSum = 0;
			int nonZeroMassCells = 0;
			int zeroMassCells = 0;
			int partialCells = 0;
			int fullCells = 0;
			int overfullCells = 0;
			int minY = Integer.MAX_VALUE;
			int maxY = Integer.MIN_VALUE;

			for (int y = 0; y < board.height(); y++)
			{
				if (board.materialAt(x, y) == materialId)
				{
					final int cellMass = mass.massAt(x, y);
					cellCount++;
					massSum += cellMass;
					minY = Math.min(minY, y);
					maxY = Math.max(maxY, y);
					if (cellMass == 0) zeroMassCells++;
					if (cellMass > 0) nonZeroMassCells++;
					if (cellMass > 0 && cellMass < M_FULL) partialCells++;
					if (cellMass >= M_FULL) fullCells++;
					if (cellMass > M_FULL) overfullCells++;
				}
			}

			final int bottomHeight = countBottomConnectedHeight(board, materialId, x, bottomY);
			if (cellCount > 0)
			{
				if (leftExtent == -1) leftExtent = x;
				rightExtent = x;
			}
			if (bottomHeight > 0)
			{
				minBottomHeight = Math.min(minBottomHeight, bottomHeight);
				maxBottomHeight = Math.max(maxBottomHeight, bottomHeight);
			}

			columns.add(new ColumnMetrics(x,
										  cellCount,
										  massSum,
										  nonZeroMassCells,
										  zeroMassCells,
										  partialCells,
										  fullCells,
										  overfullCells,
										  cellCount > 0 ? minY : -1,
										  cellCount > 0 ? maxY : -1,
										  bottomHeight));
		}

		if (minBottomHeight == Integer.MAX_VALUE)
		{
			minBottomHeight = 0;
		}

		return new ColumnProfile(materialId, bottomY, columns, leftExtent, rightExtent, minBottomHeight, maxBottomHeight);
	}

	private static int countBottomConnectedHeight(final MaterialGrid board,
												  final int materialId,
												  final int x,
												  final int bottomY)
	{
		int res = 0;
		for (int y = bottomY; y >= 0; y--)
		{
			if (board.materialAt(x, y) != materialId) break;
			res++;
		}
		return res;
	}

	private static void checkSameSize(final MaterialGrid board, final MassGrid mass)
	{
		if (board.width() != mass.width() || board.height() != mass.height())
		{
			throw new IllegalArgumentException("Board and mass sizes differ: %dx%d vs %dx%d"
													   .formatted(board.width(), board.height(), mass.width(), mass.height()));
		}
	}

	private static int clamp(final int value, final int min, final int max)
	{
		return Math.max(min, Math.min(max, value));
	}

	private static final class Accumulator
	{
		private long totalMass = 0;
		private int cellCount = 0;
		private int nonZeroMassCells = 0;
		private int zeroMassCells = 0;
		private int partialCells = 0;
		private int fullCells = 0;
		private int overfullCells = 0;
		private int surfaceCells = 0;
		private int voidBelowCells = 0;
		private int isolatedCells = 0;
		private long sumX = 0;
		private long sumY = 0;
		private int minX = Integer.MAX_VALUE;
		private int minY = Integer.MAX_VALUE;
		private int maxX = Integer.MIN_VALUE;
		private int maxY = Integer.MIN_VALUE;

		private void add(final MaterialGrid board, final MassGrid mass, final int materialId, final int x, final int y)
		{
			final int cellMass = mass.massAt(x, y);
			cellCount++;
			totalMass += cellMass;
			sumX += x;
			sumY += y;
			minX = Math.min(minX, x);
			minY = Math.min(minY, y);
			maxX = Math.max(maxX, x);
			maxY = Math.max(maxY, y);

			if (cellMass == 0) zeroMassCells++;
			if (cellMass > 0) nonZeroMassCells++;
			if (cellMass > 0 && cellMass < M_FULL) partialCells++;
			if (cellMass >= M_FULL) fullCells++;
			if (cellMass > M_FULL) overfullCells++;

			if (y == 0 || board.materialAt(x, y - 1) != materialId) surfaceCells++;
			if (y + 1 < board.height() && board.materialAt(x, y + 1) == 0) voidBelowCells++;
			if (hasSameMaterialNeighbor(board, materialId, x, y) == false) isolatedCells++;
		}

		private boolean hasLiquid()
		{
			return cellCount > 0;
		}

		private static boolean hasSameMaterialNeighbor(final MaterialGrid board,
													   final int materialId,
													   final int x,
													   final int y)
		{
			return (x > 0 && board.materialAt(x - 1, y) == materialId)
					|| (x + 1 < board.width() && board.materialAt(x + 1, y) == materialId)
					|| (y > 0 && board.materialAt(x, y - 1) == materialId)
					|| (y + 1 < board.height() && board.materialAt(x, y + 1) == materialId);
		}
	}
}
