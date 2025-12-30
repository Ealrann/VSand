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

	private byte[] lastBoard;

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
		final boolean enabled = fetchPipeline.enableFetchBufferForBoardIndex(boardIndex);
		if (enabled == false) return;

		fetchInProgress = true;
		fetchPipeline.enable();
	}

	public void onBufferFetched(int bufferIndex, ByteBuffer data)
	{
		final byte[] copy = new byte[data.remaining()];
		data.get(copy);
		lastBoard = copy;

		if (fetchInProgress == false) return;

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

	private int resolveTargetBoardIndex()
	{
		if (boardConstantBuffer == null) return 0;
		return nextBoardIndex(boardConstantBuffer.currentBoardBuffer());
	}

	private static int nextBoardIndex(int currentIndex)
	{
		return (currentIndex + 1) % 2;
	}

	private record FetchPipeline(CompositeTask task, PipelineBarrier barrier, FetchBuffer board1, FetchBuffer board2)
	{
		private static FetchPipeline of(final CompositeTask task)
		{
			if (task == null) return null;

			PipelineBarrier barrier = null;
			FetchBuffer board1 = null;
			FetchBuffer board2 = null;
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
				}
			}

			return new FetchPipeline(task, barrier, board1, board2);
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
		}

		private boolean enableFetchBufferForBoardIndex(final int boardIndex)
		{
			boolean enabled = false;
			if (board1 != null)
			{
				final boolean shouldEnable = boardIndex == 0;
				board1.enabled(shouldEnable);
				enabled |= shouldEnable;
			}
			if (board2 != null)
			{
				final boolean shouldEnable = boardIndex == 1;
				board2.enabled(shouldEnable);
				enabled |= shouldEnable;
			}
			return enabled;
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
