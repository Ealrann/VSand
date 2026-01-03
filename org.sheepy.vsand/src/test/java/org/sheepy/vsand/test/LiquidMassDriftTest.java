package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.FetchedMass;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class LiquidMassDriftTest
{
	@Test
	void waterPoolMassShouldBeConservedInLargeTank() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(128, 128, 1234L);
		harness.setBoardUpdateRepeatCount(5);
		harness.frameBoard("Wall", 2);
		harness.drawSquare("Water", 64, 83, 84);

		final var states = harness.fetchBoardsAndMassAtIterations(220, 5, 200);
		final int water = harness.materialIndex("Water");

		final var early = states.getFirst();
		final var late = states.get(1);

		final long earlyMass = totalMass(early.board(), early.mass(), water);
		final long lateMass = totalMass(late.board(), late.mass(), water);

		assertEquals(earlyMass,
					 lateMass,
					 "Expected water mass to be conserved in a large resting pool (early=%d, late=%d)"
							 .formatted(earlyMass, lateMass));
	}

	private static long totalMass(final org.sheepy.vsand.testutil.FetchedBoard board,
								  final FetchedMass mass,
								  final int water)
	{
		long total = 0;
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				if (board.cell(x, y) == water)
				{
					total += mass.mass(x, y);
				}
			}
		}
		return total;
	}
}
