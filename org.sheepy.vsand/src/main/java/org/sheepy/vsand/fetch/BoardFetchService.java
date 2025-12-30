package org.sheepy.vsand.fetch;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.cadence.Tick;
import org.sheepy.lily.vulkan.model.process.CompositeTask;
import org.sheepy.lily.vulkan.model.process.FetchBuffer;
import org.sheepy.lily.vulkan.model.process.IPipelineTask;
import org.sheepy.lily.vulkan.model.process.PipelineBarrier;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = VSandApplication.class)
@Adapter
@AutoLoad
public final class BoardFetchService implements IAdapter
{
	private final VSandApplication application;

	private CompositeTask fetchTask;
	private PipelineBarrier fetchBarrier;
	private FetchBuffer fetchBoard1;
	private FetchBuffer fetchBoard2;
	private BoardConstantBuffer boardConstantBuffer;
	private boolean pendingRequest = false;
	private int pendingBuffers = 0;

	private byte[] lastBoard1;
	private byte[] lastBoard2;
	private byte[] lastBoard;
	private int lastBoardIndex = -1;

	private BoardFetchService(VSandApplication application)
	{
		this.application = application;
	}

	@Load
	private void load()
	{
		fetchTask = application.fetchBoardTask();
		fetchBarrier = findBarrier(fetchTask);
		fetchBoard1 = findFetchBuffer(fetchTask, "Fetch Board 1");
		fetchBoard2 = findFetchBuffer(fetchTask, "Fetch Board 2");
		boardConstantBuffer = findBoardConstantBuffer(application);
	}

	@Tick
	private void tick()
	{
		if (application.fetchRequested() && pendingRequest == false)
		{
			application.fetchRequested(false);
			triggerFetch();
		}
	}

	public void triggerFetch()
	{
		if (fetchTask == null) return;

		final int boardIndex = resolveTargetBoardIndex();
		final int enabledBuffers = enableFetchBuffers(boardIndex);
		if (enabledBuffers == 0) return;

		pendingRequest = true;
		pendingBuffers = enabledBuffers;
		if (fetchBarrier != null) fetchBarrier.enabled(true);
		fetchTask.enabled(true);
	}

	public void onBufferFetched(int bufferIndex, ByteBuffer data)
	{
		final byte[] copy = new byte[data.remaining()];
		data.get(copy);
		if (bufferIndex == 0) lastBoard1 = copy;
		else if (bufferIndex == 1) lastBoard2 = copy;
		lastBoard = copy;
		lastBoardIndex = bufferIndex;

		if (pendingRequest)
		{
			pendingBuffers--;
			if (pendingBuffers <= 0)
			{
				pendingRequest = false;
				fetchTask.enabled(false);
				if (fetchBarrier != null) fetchBarrier.enabled(false);
				disableFetchBuffers();
			}
		}
	}

	public byte[] lastBoard()
	{
		return lastBoard;
	}

	public int lastBoardIndex()
	{
		return lastBoardIndex;
	}

	public byte[] lastBoard1()
	{
		return lastBoard1;
	}

	public byte[] lastBoard2()
	{
		return lastBoard2;
	}

	private int resolveTargetBoardIndex()
	{
		if (boardConstantBuffer == null) return 0;
		return nextBoardIndex(boardConstantBuffer.currentBoardBuffer());
	}

	private int enableFetchBuffers(int boardIndex)
	{
		int enabledCount = 0;
		if (fetchBoard1 != null)
		{
			final boolean enabled = boardIndex == 0;
			fetchBoard1.enabled(enabled);
			if (enabled) enabledCount++;
		}
		if (fetchBoard2 != null)
		{
			final boolean enabled = boardIndex == 1;
			fetchBoard2.enabled(enabled);
			if (enabled) enabledCount++;
		}
		return enabledCount;
	}

	private void disableFetchBuffers()
	{
		if (fetchBoard1 != null) fetchBoard1.enabled(false);
		if (fetchBoard2 != null) fetchBoard2.enabled(false);
	}

	private static int nextBoardIndex(int currentIndex)
	{
		return (currentIndex + 1) % 2;
	}

	private static PipelineBarrier findBarrier(CompositeTask task)
	{
		if (task == null) return null;
		for (IPipelineTask subTask : task.tasks())
		{
			if (subTask instanceof PipelineBarrier barrier)
			{
				return barrier;
			}
		}
		return null;
	}

	private static FetchBuffer findFetchBuffer(CompositeTask task, String name)
	{
		if (task == null) return null;
		for (IPipelineTask subTask : task.tasks())
		{
			if (subTask instanceof FetchBuffer fetchBuffer && name.equals(fetchBuffer.name()))
			{
				return fetchBuffer;
			}
		}
		return null;
	}

	private static BoardConstantBuffer findBoardConstantBuffer(VSandApplication application)
	{
		return application.streamTree()
						  .filter(BoardConstantBuffer.class::isInstance)
						  .map(BoardConstantBuffer.class::cast)
						  .findFirst()
						  .orElse(null);
	}
}
