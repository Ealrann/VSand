package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.BoardAssertions;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class LavaWaterSteamTest
{
	@Test
	void lavaHeatsWaterToSteamThenDisappears() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(16, 32);
		harness.frameBoard("Wall", 1);
		harness.drawSquare("Lava", 8, 9, 12);
		harness.drawSquare("Water", 8, 22, 12);

		final var boards = harness.fetchBoardsAtIterations(140, 10, 30, 120);
		final var after10 = boards.getFirst();
		final var after30 = boards.get(1);
		final var after120 = boards.get(2);

		final int water = harness.materialIndex("Water");
		final int steam = harness.materialIndex("Steam");
		final int lava = harness.materialIndex("Lava");
		final int lavaBoiling = harness.materialIndex("LavaBoiling");

		final int waterAfter10 = BoardAssertions.countInRect(after10, 1, 16, 15, 31, water);
		assertTrue(waterAfter10 > 0, "Expected some water in the lower chunk after 10 iterations");

		final int steamAfter30 = BoardAssertions.countInRect(after30, 1, 1, 15, 31, steam);
		final int lavaAfter30 = countLava(after30, lava, lavaBoiling);
		assertTrue(steamAfter30 > 10, "Expected >10 steam after 30 iterations (steam=%d, lava=%d)".formatted(steamAfter30, lavaAfter30));
		assertTrue(lavaAfter30 > 0, "Expected some lava after 30 iterations (steam=%d, lava=%d)".formatted(steamAfter30, lavaAfter30));

		final int steamAfter120 = BoardAssertions.countInRect(after120, 1, 1, 15, 31, steam);
		final int lavaAfter120 = countLava(after120, lava, lavaBoiling);
		assertEquals(0, lavaAfter120, "Expected lava to disappear after 120 iterations (steam=%d)".formatted(steamAfter120));
		assertTrue(steamAfter120 > 0, "Expected steam to remain after 120 iterations");
	}

	private static int countLava(final org.sheepy.vsand.testutil.FetchedBoard board, final int lava, final int lavaBoiling)
	{
		return BoardAssertions.countInRect(board, 1, 1, 15, 31, lava)
			   + BoardAssertions.countInRect(board, 1, 1, 15, 31, lavaBoiling);
	}
}

