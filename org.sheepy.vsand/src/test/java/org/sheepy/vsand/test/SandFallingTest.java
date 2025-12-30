package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.BoardAssertions;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public final class SandFallingTest
{
	@Test
	void sandFallsToBottomLeavingVoidAtTop() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(16, 32);
		harness.setBoardUpdateRepeatCount(32);

		harness.frameBoard("Wall", 2);
		harness.drawCircle("Sand", 8, 5, 6);

		final var board = harness.fetchBoardAfterIterations(100, 140);

		final int sand = harness.materialIndex("Sand");
		final int wall = harness.materialIndex("Wall");
		final int empty = harness.materialIndex("Void");

		final int bottomSandCount = BoardAssertions.countInRect(board, 2, 22, 14, 30, sand);
		assertNotEquals(0, bottomSandCount, "Expected sand to reach the bottom area");

		BoardAssertions.assertAllInRect(board, 6, 3, 11, 8, empty);
		BoardAssertions.assertAnyInRect(board, 2, 0, 14, 3, wall, "Expected top border to be wall");
	}
}
