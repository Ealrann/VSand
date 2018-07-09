package org.sheepy.vulkan.sand.graphics;

import static org.lwjgl.vulkan.VK10.VK_PIPELINE_STAGE_TRANSFER_BIT;

import java.util.Collection;

import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.AbstractCommandBuffer;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.concurrent.ISignalEmitter;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.swap.AbstractSwapPipeline;
import org.sheepy.lily.game.vulkan.pipeline.swap.FrameSubmission;
import org.sheepy.lily.game.vulkan.pipeline.swap.IGraphicsPipeline;
import org.sheepy.lily.game.vulkan.pipeline.swap.IRenderPass;
import org.sheepy.lily.game.vulkan.pipeline.swap.SwapConfiguration;

public class BufferedSwapPipeline extends AbstractSwapPipeline
{
	private Image pixelImage;

	public BufferedSwapPipeline(LogicalDevice logicalDevice, SwapConfiguration configuration,
			CommandPool commandPool, Image image, Collection<ISignalEmitter> waitForSignals)
	{
		super(logicalDevice, configuration, commandPool, waitForSignals);

		this.pixelImage = image;
	}

	@Override
	protected IRenderPass buildRenderPass()
	{
		return new BufferToPixelRenderPass(logicalDevice, pixelImage, this);
	}

	@Override
	protected FrameSubmission buildFrameSubmission()
	{
		return new FrameSubmission(logicalDevice, commandBuffers, swapChainManager, waitForSignals,
				VK_PIPELINE_STAGE_TRANSFER_BIT);
	}

	public void bind(AbstractCommandBuffer commandBuffer)
	{}

	@Override
	public void destroy(boolean full)
	{
		super.destroy(full);
	}

	@Override
	protected IGraphicsPipeline buildGraphicsPipeline()
	{
		return null;
	}
}
