package org.sheepy.vsand.analysis;

public interface MaterialGrid
{
	int width();

	int height();

	int materialAt(int x, int y);
}
