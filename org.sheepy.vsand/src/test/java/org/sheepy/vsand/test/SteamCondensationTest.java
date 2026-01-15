package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.BoardAssertions;
import org.sheepy.vsand.testutil.FetchedBoard;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class SteamCondensationTest
{
	@Test
	void steamCondensesIntoWaterWithoutCreatingVoid() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(16, 16);
		harness.fillBoard("Wall");
		harness.drawCircle("Steam", 8, 8, 8);

		final var checkpoints = LongStream.rangeClosed(1, 12).map(i -> i * 15).toArray();
		final var boards = harness.fetchBoardsAtIterations(200, checkpoints);

		final int water = harness.materialIndex("Water");
		final int steam = harness.materialIndex("Steam");
		final int empty = harness.materialIndex("Void");

		final int waterStart = countAll(boards.getFirst(), water);
		final int steamStart = countAll(boards.getFirst(), steam);

		for (int i = 0; i < boards.size(); i++)
		{
			final var board = boards.get(i);
			final int emptyCount = countAll(board, empty);
			if (emptyCount != 0)
			{
				final var first = findFirst(board, empty);
				final int x = first.x();
				final int y = first.y();
				final int up = y > 0 ? board.cell(x, y - 1) : -1;
				final int down = y + 1 < board.height() ? board.cell(x, y + 1) : -1;
				final int left = x > 0 ? board.cell(x - 1, y) : -1;
				final int right = x + 1 < board.width() ? board.cell(x + 1, y) : -1;
				assertEquals(0,
							 emptyCount,
							 "Expected no void to appear at iter %d (found %d at (%d, %d), up=%d, down=%d, left=%d, right=%d)"
									 .formatted(checkpoints[i], emptyCount, x, y, up, down, left, right));
			}
		}

		for (int i = 1; i < boards.size(); i++)
		{
			final var previous = boards.get(i - 1);
			final var current = boards.get(i);

			final int waterPrevious = countAll(previous, water);
			final int waterCurrent = countAll(current, water);
			final int steamPrevious = countAll(previous, steam);
			final int steamCurrent = countAll(current, steam);

			assertTrue(waterCurrent >= waterPrevious,
					   "Expected water to be increasing every 15 iterations (prev=%d, current=%d)"
							   .formatted(waterPrevious, waterCurrent));
			assertTrue(steamCurrent <= steamPrevious,
					   "Expected steam to be decreasing every 15 iterations (prev=%d, current=%d)"
							   .formatted(steamPrevious, steamCurrent));
		}

		final int waterEnd = countAll(boards.getLast(), water);
		final int steamEnd = countAll(boards.getLast(), steam);
		assertTrue(waterEnd > waterStart, "Expected water to increase over the test (start=%d, end=%d)".formatted(waterStart, waterEnd));
		assertTrue(steamEnd < steamStart, "Expected steam to decrease over the test (start=%d, end=%d)".formatted(steamStart, steamEnd));
	}

	private static int countAll(final FetchedBoard board, final int materialIndex)
	{
		return BoardAssertions.countInRect(board, 0, 0, board.width(), board.height(), materialIndex);
	}

	private static CellPos findFirst(final FetchedBoard board, final int materialIndex)
	{
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				if (board.cell(x, y) == materialIndex)
				{
					return new CellPos(x, y);
				}
			}
		}
		return new CellPos(-1, -1);
	}

	private record CellPos(int x, int y)
	{
	}
}
