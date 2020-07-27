package org.sheepy.vsand.util;

public class PressureSpreadTest
{
	public static void main(String[] args)
	{
		final int height = 100;
		int pressure = 1000;

		for (int i = height; i > 0; i--)
		{
			final int currentPressure = computePressure(i, pressure);
			System.out.println("h=" + i + ", p=" + currentPressure);
			pressure -= currentPressure;
		}
	}


	private record Cell(float mass){};
	private record Material(float defaultMass, float massDelta){};

	public static void test(String[] args)
	{

		Cell cell = null;
		Cell neigh = null;
		Material material = null;
		float Flow;
		boolean isNeighboorAbove = true;
		boolean isNeighboorBelow = true;
		if ( isNeighboorAbove )
		{
			if ( ( cell.mass < material.defaultMass ) || ( neigh.mass < material.defaultMass ) )
			{
				Flow = cell.mass - material.defaultMass;
			}
			else
			{
				Flow = (cell.mass + neigh.mass - material.massDelta) / 2;
			}
		}
		else if (isNeighboorBelow)
		{
			if ( ( cell.mass < material.defaultMass ) || ( neigh.mass < material.defaultMass ) )
			{
				Flow = material.defaultMass - neigh.mass;
			}
			else
			{
				Flow = (cell.mass + neigh.mass + material.massDelta) / 2;
			}
		}
		else // neighbour is on same level
		{
			Flow = ( cell.mass + neigh.mass ) / 2;
		}

	}

	private static int computePressure(int height, int pressure)
	{
		if (height == 1 || pressure <= 1)
		{
			return pressure;
		}

		final int expectedPressure = triangluarSum(height);
		if (expectedPressure == pressure)
		{
			return height;
		}
		else
		{
			if (pressure < expectedPressure)
			{
				return (int) invTriangular(pressure - 1);
			}
			else
			{
				return (int) Math.ceil((float) pressure / height);
			}
		}
	}

	private static int triangluarSum(int n)
	{
		return (n * (n + 1)) >> 1;
	}

	private static float invTriangular(int sum)
	{
		return (float) ((Math.sqrt(8. * sum + 1.) - 1) / 2.);
	}
}
