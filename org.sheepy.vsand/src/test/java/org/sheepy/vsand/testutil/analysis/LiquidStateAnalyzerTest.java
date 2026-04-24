package org.sheepy.vsand.testutil.analysis;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.analysis.LiquidStateAnalyzer;
import org.sheepy.vsand.analysis.MassGrid;
import org.sheepy.vsand.analysis.MaterialGrid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class LiquidStateAnalyzerTest
{
	@Test
	void computesMassAndBottomConnectedSurfaceProfile()
	{
		final int water = 5;
		final var board = new ArrayMaterialGrid(6, 6);
		final var mass = new ArrayMassGrid(6, 6);

		putLiquid(board, mass, water, 1, 4, LiquidStateAnalyzer.M_FULL);
		putLiquid(board, mass, water, 1, 5, LiquidStateAnalyzer.M_FULL + 12);
		putLiquid(board, mass, water, 2, 5, LiquidStateAnalyzer.M_FULL / 2);
		putLiquid(board, mass, water, 4, 1, LiquidStateAnalyzer.M_FULL);

		final var metrics = LiquidStateAnalyzer.analyze(board, mass, water, "Water", 5);

		assertTrue(metrics.hasLiquid());
		assertEquals(4, metrics.cellCount());
		assertEquals(4, metrics.nonZeroMassCells());
		assertEquals(1, metrics.partialCells());
		assertEquals(3, metrics.fullCells());
		assertEquals(1, metrics.overfullCells());
		assertEquals(2, metrics.columnProfile().maxBottomHeight());
		assertEquals(1, metrics.columnProfile().minBottomHeight());
		assertEquals(1, metrics.columnProfile().bottomHeightRange());
		assertEquals(1, metrics.columnProfile().leftExtent());
		assertEquals(4, metrics.columnProfile().rightExtent());
	}

	private static void putLiquid(final ArrayMaterialGrid board,
								  final ArrayMassGrid mass,
								  final int material,
								  final int x,
								  final int y,
								  final int cellMass)
	{
		board.values[y][x] = material;
		mass.values[y][x] = cellMass;
	}

	private static final class ArrayMaterialGrid implements MaterialGrid
	{
		private final int width;
		private final int height;
		private final int[][] values;

		private ArrayMaterialGrid(final int width, final int height)
		{
			this.width = width;
			this.height = height;
			values = new int[height][width];
		}

		@Override
		public int width()
		{
			return width;
		}

		@Override
		public int height()
		{
			return height;
		}

		@Override
		public int materialAt(final int x, final int y)
		{
			return values[y][x];
		}
	}

	private static final class ArrayMassGrid implements MassGrid
	{
		private final int width;
		private final int height;
		private final int[][] values;

		private ArrayMassGrid(final int width, final int height)
		{
			this.width = width;
			this.height = height;
			values = new int[height][width];
		}

		@Override
		public int width()
		{
			return width;
		}

		@Override
		public int height()
		{
			return height;
		}

		@Override
		public int massAt(final int x, final int y)
		{
			return values[y][x];
		}
	}
}
