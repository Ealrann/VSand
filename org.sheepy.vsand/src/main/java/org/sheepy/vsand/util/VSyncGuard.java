package org.sheepy.vsand.util;

import java.util.concurrent.TimeUnit;

// The Vulkan spec doesn't impose any vsync, even with Fifo (even if generally, drivers waits
// VSync when Fifo
// is enabled).
// We ensure "VSync" here, in order to not consume all CPU/GPU resources.
public class VSyncGuard
{
	private final long refreshTimeAvailableNs;

	private long nextFrameEndDateNs = 0;

	public VSyncGuard(long refreshTimeAvailableNs)
	{
		this.refreshTimeAvailableNs = refreshTimeAvailableNs;
	}

	public void start()
	{
		nextFrameEndDateNs = System.nanoTime() + refreshTimeAvailableNs;
	}

	public void step()
	{
		long remainingUntilDeadlineNs = nextFrameEndDateNs - System.nanoTime();
		if (remainingUntilDeadlineNs > 0)
		{
			try
			{
				TimeUnit.NANOSECONDS.sleep(remainingUntilDeadlineNs);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			// We are late, so let's recalibrate the clock
			nextFrameEndDateNs = System.nanoTime();
		}

		nextFrameEndDateNs += refreshTimeAvailableNs;
	}
}
