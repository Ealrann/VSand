package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class WaterFreeFallExpansionTest
{
	private static final int M_FULL = 4096;

	@Test
	void fallingWaterBlobShouldNotExpandIntoLowMassCloud() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(128, 128, 1234L);
		harness.drawCircle("Water", 64, 20, 40);

		final var state = harness.fetchBoardAndMassAfterIterations(60, 80);
		final int water = harness.materialIndex("Water");

		long totalMass = 0;
		int cellCount = 0;
		for (int y = 0; y < state.board().height(); y++)
		{
			for (int x = 0; x < state.board().width(); x++)
			{
				if (state.board().cell(x, y) == water)
				{
					cellCount++;
					totalMass += state.mass().mass(x, y);
				}
			}
		}

		assertTrue(cellCount > 0, "Expected some water to exist after 60 iterations");

		final double averageMassPerCell = (double) totalMass / cellCount;
		final double minExpectedAverage = M_FULL * 0.75;
		assertTrue(averageMassPerCell >= minExpectedAverage,
				   "Expected falling water to remain fairly compact (cells=%d, totalMass=%d, average=%.1f)"
						   .formatted(cellCount, totalMass, averageMassPerCell));
	}
}

