package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.BoardAssertions;
import org.sheepy.vsand.testutil.FetchedBoard;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class FireLifetimeTest
{
	@Test
	void fireEventuallyDiesOut() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(16, 16);
		harness.fillBoard("Fire");

		final var boards = harness.fetchBoardsAtIterations(90, 3, 60);
		final var after3 = boards.getFirst();
		final var after60 = boards.get(1);

		final int fire = harness.materialIndex("Fire");
		final int fireStatic = harness.materialIndex("FireStatic");
		final int fireFinal = harness.materialIndex("FireFinal");
		final int empty = harness.materialIndex("Void");

		final int totalFireAfter3 = countAnyFire(after3, fire, fireStatic, fireFinal);
		assertTrue(totalFireAfter3 > 0, "Expected some fire after 3 iterations (count=%d)".formatted(totalFireAfter3));

		final int totalFireAfter60 = countAnyFire(after60, fire, fireStatic, fireFinal);
		assertEquals(0, totalFireAfter60, "Expected no fire after 60 iterations");
		BoardAssertions.assertFilledWith(after60, empty);
	}

	private static int countAnyFire(final FetchedBoard board,
									final int fire,
									final int fireStatic,
									final int fireFinal)
	{
		return BoardAssertions.countInRect(board, 0, 0, board.width(), board.height(), fire)
			   + BoardAssertions.countInRect(board, 0, 0, board.width(), board.height(), fireStatic)
			   + BoardAssertions.countInRect(board, 0, 0, board.width(), board.height(), fireFinal);
	}
}
