package test.vulkan.gameoflife.graphics;

import static org.lwjgl.vulkan.VK10.VK_PIPELINE_STAGE_TRANSFER_BIT;

import java.util.Collections;

import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.AbstractCommandBuffer;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.swap.AbstractSwapPipeline;
import org.sheepy.lily.game.vulkan.pipeline.swap.FrameSubmission;
import org.sheepy.lily.game.vulkan.pipeline.swap.IGraphicsPipeline;
import org.sheepy.lily.game.vulkan.pipeline.swap.IRenderPass;
import org.sheepy.lily.game.vulkan.pipeline.swap.SwapConfiguration;

import test.vulkan.gameoflife.compute.PixelComputerPipeline;

public class BufferedSwapPipeline extends AbstractSwapPipeline
{
	private Image pixelImage;

	public BufferedSwapPipeline(LogicalDevice logicalDevice, SwapConfiguration configuration,
			CommandPool commandPool, PixelComputerPipeline pixelComputerPipeline)
	{
		super(logicalDevice, configuration, commandPool,
				Collections.singletonList(pixelComputerPipeline));

		this.pixelImage = pixelComputerPipeline.getImage();
	}

	@Override
	protected IRenderPass buildRenderPass()
	{
		return new BufferToPixelRenderPass(logicalDevice, pixelImage, this);
	}

	@Override
	protected FrameSubmission buildFrameSubmission()
	{
		return new FrameSubmission(logicalDevice, swapChainManager, waitForSignals,
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
