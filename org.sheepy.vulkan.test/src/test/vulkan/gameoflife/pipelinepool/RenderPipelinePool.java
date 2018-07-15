package test.vulkan.gameoflife.pipelinepool;

import static org.lwjgl.vulkan.KHRSwapchain.vkQueuePresentKHR;
import static org.lwjgl.vulkan.VK10.*;

import java.util.Collection;

import org.lwjgl.system.MemoryStack;
import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.concurrent.ISignalEmitter;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.SurfacePipelinePool;
import org.sheepy.vulkan.pipeline.swap.SwapPipeline;
import org.sheepy.vulkan.window.Surface;

import test.vulkan.gameoflife.graphics.BufferToPixelRenderPass;
import test.vulkan.gameoflife.graphics.BufferedSwapConfiguration;

public class RenderPipelinePool extends SurfacePipelinePool
{
	private LogicalDevice logicalDevice;
	private Image image;
	private Collection<ISignalEmitter> waitForEmitters;

	private SwapPipeline swapPipeline;
	private BufferedSwapConfiguration configuration;

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
		configuration = new BufferedSwapConfiguration(logicalDevice, commandPool, image);

		// We will use the swap image as a target transfer
		configuration.swapImageUsages |= VK_IMAGE_USAGE_TRANSFER_DST_BIT;

		configuration.renderPass = new BufferToPixelRenderPass(configuration);

		swapPipeline = new SwapPipeline(configuration, waitForEmitters);
		subAllocationObjects.add(configuration);
		subAllocationObjects.add(swapPipeline);
	}

	@Override
	public void configure(Surface surface)
	{
		swapPipeline.configure(surface);
	}

	@Override
	public void execute()
	{
		Integer imageIndex = swapPipeline.acquireNextImage();

		if (imageIndex != null)
		{
			if (vkQueueSubmit(logicalDevice.getQueueManager().getGraphicQueue(),
					configuration.frameSubmission.getSubmitInfo(imageIndex),
					VK_NULL_HANDLE) != VK_SUCCESS)
			{
				throw new AssertionError("failed to submit draw command buffer!");
			}

			vkQueuePresentKHR(logicalDevice.getQueueManager().getGraphicQueue(),
					configuration.frameSubmission.getPresentInfo(imageIndex));
		}
	}

	@Override
	public void resize(MemoryStack stack, Surface surface)
	{
		swapPipeline.freeNode();
		configure(surface);
		swapPipeline.allocateNode(stack);
	}
}
