package org.sheepy.vsand.analysis;

import java.util.List;

public record ColumnProfile(int materialId,
							int bottomY,
							List<ColumnMetrics> columns,
							int leftExtent,
							int rightExtent,
							int minBottomHeight,
							int maxBottomHeight)
{
	public ColumnProfile
	{
		columns = List.copyOf(columns);
	}

	public int bottomHeightRange()
	{
		return maxBottomHeight - minBottomHeight;
	}

	public boolean hasLiquid()
	{
		return leftExtent >= 0 && rightExtent >= leftExtent;
	}

	public List<ColumnMetrics> liquidColumns()
	{
		return columns.stream().filter(ColumnMetrics::hasLiquid).toList();
	}
}
