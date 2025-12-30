package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.BoardAssertions;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class WaterPropagationTest
{
	@Test
	void waterPropagatesLeft() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(32, 16);
		harness.frameBoard("Wall", 2);
		harness.drawSquare("Water", 26, 8, 12);

		final var boards = harness.fetchBoardsAtIterations(160, 1, 120);
		final var after1 = boards.getFirst();
		final var after120 = boards.get(1);

		final int water = harness.materialIndex("Water");

		final int leftAfter1 = BoardAssertions.countInRect(after1, 2, 2, 16, 14, water);
		final int leftAfter120 = BoardAssertions.countInRect(after120, 2, 2, 16, 14, water);
		final int movedToLeft = leftAfter120 - leftAfter1;
		assertTrue(movedToLeft >= 25,
				   "Expected at least 25 water to move to the left after 120 iterations (leftAfter1=%d, leftAfter120=%d)"
						   .formatted(leftAfter1, leftAfter120));
	}
}
