package test.vulkan.gameoflife.pipelinepool;

import static org.lwjgl.vulkan.KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.vkQueuePresentKHR;
import static org.lwjgl.vulkan.VK10.*;

import java.util.Collection;

import org.lwjgl.system.MemoryStack;
import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.concurrent.ISignalEmitter;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.IPipelinePool;
import org.sheepy.lily.game.vulkan.pipeline.swap.SwapConfiguration;

import test.vulkan.gameoflife.graphics.BufferedSwapPipeline;

public class RenderPipelinePool implements IPipelinePool
{

	private LogicalDevice logicalDevice;
	private Image image;
	private Collection<ISignalEmitter> waitForEmitters;
	private CommandPool commandPool;

	private BufferedSwapPipeline renderPipeline;

	public RenderPipelinePool(LogicalDevice logicalDevice, Image image,
			Collection<ISignalEmitter> waitForEmitters)
	{
		this.logicalDevice = logicalDevice;
		this.image = image;
		this.waitForEmitters = waitForEmitters;

		try (MemoryStack stack = MemoryStack.stackPush())
		{
			commandPool = CommandPool.alloc(stack, logicalDevice,
					logicalDevice.getQueueManager().getGraphicQueueIndex());
		}

		buildPipelines();
	}

	public void buildPipelines()
	{
		SwapConfiguration configuration = new SwapConfiguration(VK_FORMAT_B8G8R8A8_UNORM,
				VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);
		// We will fill the framebuffer manually.
		configuration.renderPipeline = false;

		// We will use the swap image as a target transfer
		configuration.swapImageUsages |= VK_IMAGE_USAGE_TRANSFER_DST_BIT;
		renderPipeline = new BufferedSwapPipeline(logicalDevice, configuration, commandPool,
				image, waitForEmitters);
	}

	@Override
	public void load(MemoryStack stack, long surface, int width, int height)
	{
		renderPipeline.load(surface, width, height);
	}

	@Override
	public void execute()
	{
		int imageIndex = renderPipeline.acquireNextImage();

		if (vkQueueSubmit(logicalDevice.getQueueManager().getGraphicQueue(),
				renderPipeline.getFrameSubmission().getSubmitInfo(imageIndex),
				VK_NULL_HANDLE) != VK_SUCCESS)
		{
			throw new AssertionError("failed to submit draw command buffer!");
		}

		vkQueuePresentKHR(logicalDevice.getQueueManager().getGraphicQueue(),
				renderPipeline.getFrameSubmission().getPresentInfo(imageIndex));
	}

	@Override
	public void resize(long surface, int width, int height)
	{
		renderPipeline.destroy(false);
		renderPipeline.load(surface, width, height);
	}

	@Override
	public void free()
	{
		renderPipeline.destroy(true);
		commandPool.free();
	}

	@Override
	public CommandPool getCommandPool()
	{
		return commandPool;
	}
}
