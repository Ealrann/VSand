package org.sheepy.vsand.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class BoardAssertions
{
	private BoardAssertions()
	{
	}

	public static void assertFilledWith(final FetchedBoard board, final int expected)
	{
		assertTrue(expected >= 0 && expected <= 0xFF, "Expected material index must fit in uint8");
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				final int cell = board.cell(x, y);
				if (cell != expected)
				{
					assertEquals(expected, cell, "Unexpected material at (%d, %d)".formatted(x, y));
				}
			}
		}
	}

	public static int countInRect(final FetchedBoard board,
								  final int x0,
								  final int y0,
								  final int x1Exclusive,
								  final int y1Exclusive,
								  final int expected)
	{
		int count = 0;
		for (int y = Math.max(0, y0); y < Math.min(board.height(), y1Exclusive); y++)
		{
			for (int x = Math.max(0, x0); x < Math.min(board.width(), x1Exclusive); x++)
			{
				if (board.cell(x, y) == expected) count++;
			}
		}
		return count;
	}

	public static void assertAnyInRect(final FetchedBoard board,
									   final int x0,
									   final int y0,
									   final int x1Exclusive,
									   final int y1Exclusive,
									   final int expected,
									   final String what)
	{
		final int count = countInRect(board, x0, y0, x1Exclusive, y1Exclusive, expected);
		assertTrue(count > 0, what);
	}

	public static void assertAllInRect(final FetchedBoard board,
									   final int x0,
									   final int y0,
									   final int x1Exclusive,
									   final int y1Exclusive,
									   final int expected)
	{
		for (int y = Math.max(0, y0); y < Math.min(board.height(), y1Exclusive); y++)
		{
			for (int x = Math.max(0, x0); x < Math.min(board.width(), x1Exclusive); x++)
			{
				final int cell = board.cell(x, y);
				if (cell != expected)
				{
					assertEquals(expected, cell, "Unexpected material at (%d, %d)".formatted(x, y));
				}
			}
		}
	}
}
