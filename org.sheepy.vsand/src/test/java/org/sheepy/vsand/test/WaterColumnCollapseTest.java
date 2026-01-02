package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.BoardAssertions;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class WaterColumnCollapseTest
{
	@Test
	void waterColumnTipDisappearsAfterSettling() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(64, 160);
		harness.frameBoard("Wall", 1);

		final int floorY = 150;
		harness.drawLine("Wall", 1, floorY, 62, floorY, 1);

		final int water = harness.materialIndex("Water");
		final int empty = harness.materialIndex("Void");

		final int columnX = 32;
		final int squareSize = 10;
		for (int y = 55; y <= 135; y += 10)
		{
			harness.drawSquare("Water", columnX, y, squareSize);
		}
		harness.drawSquare("Water", columnX, 144, squareSize);

		final var board = harness.fetchBoardAfterIterations(120, 200);
		final int totalWater = BoardAssertions.countInRect(board, 0, 0, board.width(), board.height(), water);
		assertTrue(totalWater > 0, "Expected some water to remain after settling");

		final int halfSize = squareSize >> 1;
		final int columnX0 = columnX - halfSize;
		final int columnX1Exclusive = columnX + halfSize + 1;
		final int initialTopY = 50;
		final int tipHeight = 15;

		final int tipWater = BoardAssertions.countInRect(board, columnX0, initialTopY, columnX1Exclusive, initialTopY + tipHeight, water);
		assertEquals(0,
					 tipWater,
					 "Expected the top %d cells of the initial column to clear after 120 iterations (tipWater=%d)"
							 .formatted(tipHeight, tipWater));
		BoardAssertions.assertAllInRect(board, columnX0, initialTopY, columnX1Exclusive, initialTopY + tipHeight, empty);
	}
}

