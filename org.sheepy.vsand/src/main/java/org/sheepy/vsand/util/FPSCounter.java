package org.sheepy.vsand.util;

public class FPSCounter
{
	private static final double NS = 1000000000;

	public static final int COUNT_DURATION_NS = 2 * 1000000000;

	private int count = 0;
	private long lastTime = -1;

	public void step()
	{
		if (lastTime == -1) lastTime = System.nanoTime();

		count++;

		if (System.nanoTime() - lastTime > COUNT_DURATION_NS)
		{
			final long totalDelta = System.nanoTime() - lastTime;
			final long averageDelta = totalDelta / count;
			final float fps = (float) (NS / averageDelta);

			System.out.println("FPS: " + fps);

			lastTime = System.nanoTime();
			count = 0;
		}
	}
}
