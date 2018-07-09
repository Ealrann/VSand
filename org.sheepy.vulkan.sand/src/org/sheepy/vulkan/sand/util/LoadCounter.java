package org.sheepy.vulkan.sand.util;

public class LoadCounter
{
	private final String name;
	private final int maxCount;

	private int count = 0;
	private long dateStart = -1;
	private long dateBeforeAcquire;

	private float[] history;

	public LoadCounter(String name, int numberOfStep)
	{
		this.name = name;
		this.maxCount = numberOfStep;

		history = new float[maxCount];
	}

	public void start()
	{
		dateBeforeAcquire = System.currentTimeMillis();
	}

	public void countTime()
	{
		long acquireDuration = System.currentTimeMillis() - dateBeforeAcquire;

		if (dateStart != -1)
		{
			long totalDuration = System.currentTimeMillis() - dateStart;
			history[count] = 1 - (acquireDuration / totalDuration);
			count++;
		}

		if (count == maxCount)
		{
			float averageLoad = 0;
			for (float f : history)
			{
				averageLoad += f;
			}
			averageLoad /= maxCount;

			System.out.println(String.format("Load [%s] : %f", name, averageLoad));
			count = 0;
		}

		dateStart = System.currentTimeMillis();
	}
}
