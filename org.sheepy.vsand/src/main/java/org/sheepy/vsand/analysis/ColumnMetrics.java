package org.sheepy.vsand.analysis;

public record ColumnMetrics(int x,
							int cellCount,
							long mass,
							int nonZeroMassCells,
							int zeroMassCells,
							int partialCells,
							int fullCells,
							int overfullCells,
							int minY,
							int maxY,
							int bottomHeight)
{
	public boolean hasLiquid()
	{
		return cellCount > 0;
	}
}
