package org.sheepy.vsand.analysis;

public interface MassGrid
{
	int width();

	int height();

	int massAt(int x, int y);
}
