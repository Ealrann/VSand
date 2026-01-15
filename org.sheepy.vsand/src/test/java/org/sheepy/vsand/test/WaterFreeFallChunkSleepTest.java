package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class WaterFreeFallChunkSleepTest
{
	@Test
	void fallingTallWaterColumnShouldNotLeaveStuckResidueInUpperChunks() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(64, 256, 1234L);
		harness.frameBoard("Wall", 1);
		harness.drawLine("Water", 32, 10, 32, 200, 10);

		final var board = harness.fetchBoardAfterIterations(40, 60);
		final int water = harness.materialIndex("Water");

		int minY = Integer.MAX_VALUE;
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				if (board.cell(x, y) == water)
				{
					minY = Math.min(minY, y);
				}
			}
		}

		assertTrue(minY != Integer.MAX_VALUE, "Expected some water to remain after 40 iterations");
		assertTrue(minY > 10, "Expected the top of the falling column to move down (minY=%d)".formatted(minY));
	}
}

