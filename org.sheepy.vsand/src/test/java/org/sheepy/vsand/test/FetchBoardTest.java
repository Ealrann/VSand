package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.BoardAssertions;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public final class FetchBoardTest
{
	@Test
	void fetchCopiesBoardData() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(16, 16);
		harness.fillBoard("Wall");
		final var board = harness.fetchBoardAfterIterations(3, 24);

		final int wallIndex = harness.materialIndex("Wall");
		BoardAssertions.assertFilledWith(board, wallIndex);
	}
}
