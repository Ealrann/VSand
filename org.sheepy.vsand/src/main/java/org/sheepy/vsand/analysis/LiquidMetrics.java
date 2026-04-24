package org.sheepy.vsand.analysis;

public record LiquidMetrics(int materialId,
							String materialName,
							int width,
							int height,
							long totalMass,
							int cellCount,
							int nonZeroMassCells,
							int zeroMassCells,
							int partialCells,
							int fullCells,
							int overfullCells,
							int surfaceCells,
							int voidBelowCells,
							int isolatedCells,
							int minX,
							int minY,
							int maxX,
							int maxY,
							double averageX,
							double averageY,
							ColumnProfile columnProfile)
{
	public boolean hasLiquid()
	{
		return cellCount > 0;
	}

	public int bboxWidth()
	{
		return hasLiquid() ? maxX - minX + 1 : 0;
	}

	public int bboxHeight()
	{
		return hasLiquid() ? maxY - minY + 1 : 0;
	}

	public int surfaceRange()
	{
		return columnProfile.bottomHeightRange();
	}
}
