package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class WaterBoilingInvariantTest
{
	@Test
	void waterPoolShouldNotCreateInternalVoidsNearBottom() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(128, 128, 1234L);
		harness.setBoardUpdateRepeatCount(5);
		harness.frameBoard("Wall", 2);
		harness.drawSquare("Water", 64, 83, 84);

		final var state = harness.fetchBoardAfterIterations(600, 700);
		final int water = harness.materialIndex("Water");
		final int empty = harness.materialIndex("Void");

		final int thickness = 2;
		final int width = state.width();
		final int height = state.height();
		final int yStart = (height * 2) / 3;

		int internalVoidCount = 0;
		int waterCount = 0;
		for (int y = Math.max(thickness + 1, yStart); y <= height - thickness - 2; y++)
		{
			for (int x = thickness; x <= width - thickness - 1; x++)
			{
				final int cell = state.cell(x, y);
				if (cell == water) waterCount++;
				if (cell != empty) continue;

				if (state.cell(x, y - 1) == water && state.cell(x, y + 1) == water)
				{
					internalVoidCount++;
				}
			}
		}

		assertTrue(waterCount > 0, "Expected some water to exist in the bottom third");
		assertTrue(internalVoidCount == 0,
				   "Expected no internal void pockets near the bottom of a resting pool (count=%d)"
						   .formatted(internalVoidCount));
	}
}

