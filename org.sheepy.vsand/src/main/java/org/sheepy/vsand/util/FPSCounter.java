package org.sheepy.vsand.util;

public class FPSCounter
{
	public static final int COUNT_DURATION_NS = 2 * 1000000000;
	
	private int count = 0;
	private long lastTime = -1;

	public void step()
	{
		if (lastTime == -1) lastTime = System.nanoTime();

		count++;

		if (System.nanoTime() - lastTime > COUNT_DURATION_NS)
		{
			long totalDelta = System.nanoTime() - lastTime;

			long averageDelta = totalDelta / count;

			float fps = 1000000000f / averageDelta;

			System.out.println("FPS: " + fps);

			lastTime = System.nanoTime();
			count = 0;
		}
	}
}
