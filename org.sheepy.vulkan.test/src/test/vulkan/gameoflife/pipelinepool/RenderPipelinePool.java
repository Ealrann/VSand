package test.vulkan.gameoflife.pipelinepool;

import static org.lwjgl.vulkan.KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.vkQueuePresentKHR;
import static org.lwjgl.vulkan.VK10.*;

import java.util.Collection;

import org.lwjgl.system.MemoryStack;
import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.concurrent.ISignalEmitter;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.SurfacePipelinePool;
import org.sheepy.vulkan.pipeline.swap.SwapConfiguration;
import org.sheepy.vulkan.window.Surface;

import test.vulkan.gameoflife.graphics.BufferedSwapPipeline;

public class RenderPipelinePool extends SurfacePipelinePool
{
	private LogicalDevice logicalDevice;
	private Image image;
	private Collection<ISignalEmitter> waitForEmitters;

	private BufferedSwapPipeline renderPipeline;

	public RenderPipelinePool(LogicalDevice logicalDevice, Image image,
			Collection<ISignalEmitter> waitForEmitters)
	{
		super(logicalDevice, logicalDevice.getQueueManager().getGraphicQueueIndex());

		this.logicalDevice = logicalDevice;
		this.image = image;
		this.waitForEmitters = waitForEmitters;

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
		renderPipeline = new BufferedSwapPipeline(logicalDevice, configuration, commandPool, image,
				waitForEmitters);
		subAllocationObjects.add(renderPipeline);
	}

	@Override
	public void configure(Surface surface)
	{
		renderPipeline.configure(surface);
	}

	@Override
	public void execute()
	{
		Integer imageIndex = renderPipeline.acquireNextImage();

		if (imageIndex != null)
		{
			if (vkQueueSubmit(logicalDevice.getQueueManager().getGraphicQueue(),
					renderPipeline.getFrameSubmission().getSubmitInfo(imageIndex),
					VK_NULL_HANDLE) != VK_SUCCESS)
			{
				throw new AssertionError("failed to submit draw command buffer!");
			}

			vkQueuePresentKHR(logicalDevice.getQueueManager().getGraphicQueue(),
					renderPipeline.getFrameSubmission().getPresentInfo(imageIndex));
		}
	}

	@Override
	public void resize(MemoryStack stack, Surface surface)
	{
		renderPipeline.free(false);
		configure(surface);
		renderPipeline.allocate(stack);
	}
}
