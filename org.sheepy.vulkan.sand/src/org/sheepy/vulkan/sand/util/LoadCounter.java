package org.sheepy.vulkan.sand.util;

public class LoadCounter
{
	private final String name;
	private final int maxCount;

	private int count = 0;
	private long dateStart = -1;
	private long dateBeforeAcquire;

	private double[] history;

	public LoadCounter(String name, int numberOfStep)
	{
		this.name = name;
		this.maxCount = numberOfStep;

		history = new double[maxCount];
	}

	public void start()
	{
		dateBeforeAcquire = getTime();
	}

	private static final long getTime()
	{
		return System.nanoTime();
	}

	public void countTime()
	{
		long acquireDuration = getTime() - dateBeforeAcquire;

		if (dateStart != -1)
		{
			long totalDuration = getTime() - dateStart;

			if (acquireDuration == 0) history[count] = 1;
			else history[count] = 1 - ((double) acquireDuration / totalDuration);
			count++;
		}

		if (count == maxCount)
		{
			double averageLoad = 0;
			for (double f : history)
			{
				averageLoad += f;
			}
			averageLoad /= maxCount;

			System.out.println(String.format("Load [%s] : %f", name, averageLoad));
			count = 0;
		}

		dateStart = getTime();
	}
}
