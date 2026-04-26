package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.analysis.LiquidStateAnalyzer;
import org.sheepy.vsand.analysis.MaterialGrid;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class PressureChunkBoundaryRegressionTest
{
	private static final long SEED = 1234L;
	private static final int SIMULATION_SPEED = 4;
	private static final int EMPTY_MATERIAL_ID = 0;
	private static final int SLOPE_NEIGHBORHOOD_RADIUS = 6;
	private static final int SLOPE_CELL_COUNT_TOLERANCE = 2;

	@Test
	void pressureUpKeepsChunkSeamPocketsBounded() throws IOException
	{
		final var harness = waterUPipeHarness();
		final var water = harness.materialIndex("Water");
		final var states = harness.fetchBoardsAndMassAtIterations(150, 20, 120);
		final var initialMetrics = LiquidStateAnalyzer.analyze(states.getFirst().board(), states.getFirst().mass(), water, "Water");
		final var frame120Metrics = LiquidStateAnalyzer.analyze(states.getLast().board(), states.getLast().mass(), water, "Water");

		assertEquals(initialMetrics.cellCount(), frame120Metrics.cellCount(), "Water cell count drifted across chunk seam scenario");
		assertTrue(frame120Metrics.horizontalChunkSeamVoidPockets() <= 1,
				   "Pressure movement opened too many void pockets on horizontal chunk seams: %d"
						   .formatted(frame120Metrics.horizontalChunkSeamVoidPockets()));
		assertTrue(frame120Metrics.verticalChunkSeamVoidPockets() <= 1,
				   "Pressure movement opened too many void pockets on vertical chunk seams: %d"
						   .formatted(frame120Metrics.verticalChunkSeamVoidPockets()));
	}

	@Test
	void slopedFlowDoesNotKeepBoilingOnVerticalChunkSeams() throws IOException
	{
		final var harness = waterSlopeHarness();
		final var water = harness.materialIndex("Water");
		final var states = harness.fetchBoardsAndMassAtIterations(260, 20, 240);
		final var initialMetrics = LiquidStateAnalyzer.analyze(states.getFirst().board(), states.getFirst().mass(), water, "Water");
		final var frame240Metrics = LiquidStateAnalyzer.analyze(states.getLast().board(), states.getLast().mass(), water, "Water");

		final var cellCountDrift = Math.abs(initialMetrics.cellCount() - frame240Metrics.cellCount());
		assertTrue(cellCountDrift <= SLOPE_CELL_COUNT_TOLERANCE,
				   "Water cell count drifted too much across slope chunk seam scenario: %d -> %d, drift=%d, tolerance=%d"
						   .formatted(initialMetrics.cellCount(),
									  frame240Metrics.cellCount(),
									  cellCountDrift,
									  SLOPE_CELL_COUNT_TOLERANCE));
		assertTrue(frame240Metrics.horizontalChunkSeamVoidPockets() <= 1,
				   "Slope flow kept too many void pockets on horizontal chunk seams: %d"
						   .formatted(frame240Metrics.horizontalChunkSeamVoidPockets()));
		assertTrue(frame240Metrics.verticalChunkSeamVoidPockets() <= 3,
				   "Slope flow kept too many void pockets on vertical chunk seams: %d"
						   .formatted(frame240Metrics.verticalChunkSeamVoidPockets()));
	}

	@Test
	void slopedFlowDoesNotCreateBottomAirOnVerticalChunkSeams() throws IOException
	{
		final var harness = waterSlopeHarness();
		final var water = harness.materialIndex("Water");
		final var wall = harness.materialIndex("Wall");
		final long[] checkpoints = {80, 100, 120};
		final var boards = harness.fetchBoardsAtIterations(140, checkpoints);
		final var bottomAirFrames = collectBottomAirFrames(boards, checkpoints, water, wall);
		final var persistentBottomAirCells = bottomAirFrames.values()
															.stream()
															.filter(frames -> frames.size() > 1)
															.count();

		assertTrue(persistentBottomAirCells <= 1,
				   "Slope flow kept persistent bottom air under water on vertical chunk seams: %s"
						   .formatted(describePersistentBottomAirCells(bottomAirFrames)));
	}

	private static Map<BottomAirCell, List<Long>> collectBottomAirFrames(final List<? extends MaterialGrid> boards,
																		 final long[] checkpoints,
																		 final int liquidMaterialId,
																		 final int wallMaterialId)
	{
		final Map<BottomAirCell, List<Long>> framesByCell = new HashMap<>();
		for (int i = 0; i < boards.size(); i++)
		{
			final var board = boards.get(i);
			for (int y = 0; y < board.height() - 2; y++)
			{
				for (int x = 0; x < board.width(); x++)
				{
					if (isBottomAirCell(board, x, y, liquidMaterialId, wallMaterialId))
					{
						framesByCell.computeIfAbsent(new BottomAirCell(x, y), ignored -> new ArrayList<>()).add(checkpoints[i]);
					}
				}
			}
		}
		return framesByCell;
	}

	private static String describePersistentBottomAirCells(final Map<BottomAirCell, List<Long>> framesByCell)
	{
		final var builder = new StringBuilder();
		for (final var entry : framesByCell.entrySet())
		{
			if (entry.getValue().size() > 1)
			{
				if (builder.isEmpty() == false)
				{
					builder.append("; ");
				}
				builder.append("x=").append(entry.getKey().x())
					   .append(" y=").append(entry.getKey().y())
					   .append(" frames=").append(entry.getValue());
			}
		}
		return builder.toString();
	}

	private static boolean isBottomAirCell(final MaterialGrid board,
										   final int x,
										   final int y,
										   final int liquidMaterialId,
										   final int wallMaterialId)
	{
		return isVerticalChunkBoundaryNeighbor(x)
				&& board.materialAt(x, y) == liquidMaterialId
				&& board.materialAt(x, y + 1) == EMPTY_MATERIAL_ID
				&& board.materialAt(x, y + 2) == liquidMaterialId
				&& hasNearbyMaterial(board, x, y, wallMaterialId, SLOPE_NEIGHBORHOOD_RADIUS);
	}

	private static boolean hasNearbyMaterial(final MaterialGrid board,
											 final int centerX,
											 final int centerY,
											 final int materialId,
											 final int radius)
	{
		for (int y = Math.max(0, centerY - radius); y <= Math.min(board.height() - 1, centerY + radius); y++)
		{
			for (int x = Math.max(0, centerX - radius); x <= Math.min(board.width() - 1, centerX + radius); x++)
			{
				if (board.materialAt(x, y) == materialId)
				{
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isVerticalChunkBoundaryNeighbor(final int x)
	{
		return x > 0 && (x % 16 == 0 || (x + 1) % 16 == 0);
	}

	private record BottomAirCell(int x, int y)
	{
	}

	private static VSandTestHarness waterUPipeHarness() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(64, 64, SEED);
		harness.setBoardUpdateRepeatCount(SIMULATION_SPEED);
		harness.frameBoard("Wall", 1);
		harness.drawLine("Wall", 16, 10, 16, 54, 2);
		harness.drawSquare("Wall", 32, 17, 14);
		harness.drawSquare("Wall", 32, 31, 14);
		harness.drawSquare("Wall", 32, 43, 14);
		harness.drawLine("Wall", 48, 10, 48, 54, 2);
		harness.drawLine("Wall", 16, 54, 48, 54, 2);
		harness.drawLine("Water", 20, 16, 20, 50, 5);
		return harness;
	}

	private static VSandTestHarness waterSlopeHarness() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(96, 64, SEED);
		harness.setBoardUpdateRepeatCount(SIMULATION_SPEED);
		harness.frameBoard("Wall", 1);
		harness.drawLine("Wall", 8, 22, 88, 54, 3);
		harness.drawLine("Water", 18, 4, 38, 4, 26);
		return harness;
	}
}
