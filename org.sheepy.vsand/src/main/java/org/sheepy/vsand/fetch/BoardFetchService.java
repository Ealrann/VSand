package org.sheepy.vsand.fetch;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.logoce.lmf.core.api.notification.Notification;
import org.sheepy.lily.core.api.adapter.NotifyChanged;
import org.sheepy.lily.core.api.application.IApplicationAdapter;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.cadence.EditingCommand;
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

	private BoardConstantBuffer boardConstantBuffer;
	private FetchPipeline fetchPipeline;

	private boolean fetchInProgress = false;
	private boolean fetchQueued = false;
	private int expectedBoardIndex = -1;
	private int remainingFetchCount = 0;

	private byte[] lastBoard;
	private byte[] lastMass;
	private byte[] pendingBoard;
	private byte[] pendingMass;

	private BoardFetchService(final VSandApplication application)
	{
		this.application = application;
	}

	@Load
	private void load()
	{
		fetchPipeline = FetchPipeline.of(application.fetchBoardTask());
		boardConstantBuffer = findBoardConstantBuffer(application);
	}

	@NotifyChanged(featureIds = VSandApplication.FeatureIDs.FETCH_REQUESTED)
	private void fetchRequestedChanged(final Notification notification)
	{
		if (notification.booleanValue() == false) return;
		if (fetchInProgress || fetchQueued) return;

		if (scheduleFetch())
		{
			application.fetchRequested(false);
		}
	}

	private boolean scheduleFetch()
	{
		if (fetchPipeline == null) return false;
		if (fetchInProgress || fetchQueued) return false;

		final var applicationAdapter = application.adapt(IApplicationAdapter.class);
		final var cadenceManager = applicationAdapter != null ? applicationAdapter.getCadenceManager() : null;
		final var commandStack = cadenceManager != null ? cadenceManager.getCommandStack() : null;
		if (commandStack == null)
		{
			triggerFetchNow();
			return true;
		}

		fetchQueued = true;
		commandStack.add(new EditingCommand("org.sheepy.vsand.fetch.BoardFetchService")
		{
			@Override
			public void execute()
			{
				fetchQueued = false;
				triggerFetchNow();
			}
		});

		return true;
	}

	private void triggerFetchNow()
	{
		if (fetchPipeline == null) return;

		final int boardIndex = resolveTargetBoardIndex();
		final int enabledCount = fetchPipeline.enableFetchBuffersForBoardIndex(boardIndex);
		if (enabledCount == 0) return;

		expectedBoardIndex = boardIndex;
		remainingFetchCount = enabledCount;
		pendingBoard = null;
		pendingMass = null;
		fetchInProgress = true;
		fetchPipeline.enable();
	}

	public void onBoardFetched(int bufferIndex, ByteBuffer data)
	{
		onBufferFetched(bufferIndex, data, FetchedBufferType.BOARD);
	}

	public void onMassFetched(int bufferIndex, ByteBuffer data)
	{
		onBufferFetched(bufferIndex, data, FetchedBufferType.MASS);
	}

	private void onBufferFetched(final int bufferIndex, final ByteBuffer data, final FetchedBufferType bufferType)
	{
		if (fetchInProgress == false) return;
		if (bufferIndex != expectedBoardIndex) return;

		final byte[] copy = new byte[data.remaining()];
		data.get(copy);

		switch (bufferType)
		{
			case BOARD -> pendingBoard = copy;
			case MASS -> pendingMass = copy;
		}

		remainingFetchCount--;
		if (remainingFetchCount > 0) return;

		lastBoard = pendingBoard;
		lastMass = pendingMass;

		fetchInProgress = false;
		fetchPipeline.disable();

		if (application.fetchRequested() && fetchQueued == false && scheduleFetch())
		{
			application.fetchRequested(false);
		}
	}

	public byte[] lastBoard()
	{
		return lastBoard;
	}

	public byte[] lastMass()
	{
		return lastMass;
	}

	/**
	 * Triggers a fetch immediately (no cadence command), targeting the same board index as {@link #fetchRequestedChanged(Notification)}.
	 * Intended for tooling (tests, headless runners).
	 */
	public boolean requestFetchNow()
	{
		if (fetchPipeline == null) return false;
		if (fetchInProgress || fetchQueued) return false;

		triggerFetchNow();
		return true;
	}

	private int resolveTargetBoardIndex()
	{
		if (boardConstantBuffer == null) return 0;

		final int current = boardConstantBuffer.currentBoardBuffer();
		final int speed = Math.max(1, application.speed());
		return (speed & 1) == 0 ? current : nextBoardIndex(current);
	}

	private static int nextBoardIndex(int currentIndex)
	{
		return (currentIndex + 1) % 2;
	}

	private enum FetchedBufferType
	{
		BOARD,
		MASS
	}

	private record FetchPipeline(CompositeTask task,
								 PipelineBarrier barrier,
								 FetchBuffer board1,
								 FetchBuffer board2,
								 FetchBuffer mass1,
								 FetchBuffer mass2)
	{
		private static FetchPipeline of(final CompositeTask task)
		{
			if (task == null) return null;

			PipelineBarrier barrier = null;
			FetchBuffer board1 = null;
			FetchBuffer board2 = null;
			FetchBuffer mass1 = null;
			FetchBuffer mass2 = null;
			for (final IPipelineTask subTask : task.tasks())
			{
				if (subTask instanceof PipelineBarrier pipelineBarrier)
				{
					barrier = pipelineBarrier;
				}
				else if (subTask instanceof FetchBuffer fetchBuffer)
				{
					if ("Fetch Board 1".equals(fetchBuffer.name()))
					{
						board1 = fetchBuffer;
					}
					else if ("Fetch Board 2".equals(fetchBuffer.name()))
					{
						board2 = fetchBuffer;
					}
					else if ("Fetch Mass 1".equals(fetchBuffer.name()))
					{
						mass1 = fetchBuffer;
					}
					else if ("Fetch Mass 2".equals(fetchBuffer.name()))
					{
						mass2 = fetchBuffer;
					}
				}
			}

			return new FetchPipeline(task, barrier, board1, board2, mass1, mass2);
		}

		private void enable()
		{
			if (barrier != null) barrier.enabled(true);
			task.enabled(true);
		}

		private void disable()
		{
			task.enabled(false);
			if (barrier != null) barrier.enabled(false);

			if (board1 != null) board1.enabled(false);
			if (board2 != null) board2.enabled(false);
			if (mass1 != null) mass1.enabled(false);
			if (mass2 != null) mass2.enabled(false);
		}

		private int enableFetchBuffersForBoardIndex(final int boardIndex)
		{
			int enabledCount = 0;
			if (board1 != null)
			{
				final boolean shouldEnable = boardIndex == 0;
				board1.enabled(shouldEnable);
				if (shouldEnable) enabledCount++;
			}
			if (board2 != null)
			{
				final boolean shouldEnable = boardIndex == 1;
				board2.enabled(shouldEnable);
				if (shouldEnable) enabledCount++;
			}
			if (mass1 != null)
			{
				final boolean shouldEnable = boardIndex == 0;
				mass1.enabled(shouldEnable);
				if (shouldEnable) enabledCount++;
			}
			if (mass2 != null)
			{
				final boolean shouldEnable = boardIndex == 1;
				mass2.enabled(shouldEnable);
				if (shouldEnable) enabledCount++;
			}
			return enabledCount;
		}
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
