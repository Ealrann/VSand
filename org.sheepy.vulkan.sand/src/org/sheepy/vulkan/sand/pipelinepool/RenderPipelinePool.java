package org.sheepy.vulkan.sand.pipelinepool;

import static org.lwjgl.vulkan.KHRSurface.VK_PRESENT_MODE_FIFO_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.vkQueuePresentKHR;
import static org.lwjgl.vulkan.VK10.*;

import java.util.Collection;

import org.lwjgl.system.MemoryStack;
import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.concurrent.ISignalEmitter;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.SurfacePipelinePool;
import org.sheepy.vulkan.pipeline.swap.SwapPipeline;
import org.sheepy.vulkan.sand.graphics.BufferToPixelRenderPass;
import org.sheepy.vulkan.sand.graphics.BufferedSwapConfiguration;
import org.sheepy.vulkan.sand.graphics.SandUIDescriptor;
import org.sheepy.vulkan.sand.util.LoadCounter;
import org.sheepy.vulkan.window.Surface;

public class RenderPipelinePool extends SurfacePipelinePool
{

	private LogicalDevice logicalDevice;
	private Image image;
	private Collection<ISignalEmitter> waitForEmitters;
	private SandUIDescriptor uiDescriptor;

	private LoadCounter loadCounterTotal = new LoadCounter("Total", 120);
	private LoadCounter loadCounterRender = new LoadCounter("Render", 120);
	private LoadCounter loadCounterUI = new LoadCounter("UI", 120);

	private SwapPipeline renderPipeline;
	private BufferedSwapConfiguration configuration;

	public RenderPipelinePool(LogicalDevice logicalDevice, Image image,
			SandUIDescriptor uiDescriptor, Collection<ISignalEmitter> waitForEmitters)
	{
		super(logicalDevice, logicalDevice.getQueueManager().getGraphicQueueIndex(), true);

		this.logicalDevice = logicalDevice;
		this.uiDescriptor = uiDescriptor;
		this.image = image;
		this.waitForEmitters = waitForEmitters;

		buildPipelines();
	}

	public void buildPipelines()
	{
		configuration = new BufferedSwapConfiguration(logicalDevice, commandPool, image,
				uiDescriptor);
		// enable VSync
		configuration.presentationMode = VK_PRESENT_MODE_FIFO_KHR;

		configuration.renderPass = new BufferToPixelRenderPass(configuration);

		// We will use the swap image as a target transfer
		configuration.swapImageUsages |= VK_IMAGE_USAGE_TRANSFER_DST_BIT;

		renderPipeline = new SwapPipeline(configuration, waitForEmitters);

		subAllocationObjects.add(configuration);
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
		loadCounterRender.start();
		loadCounterTotal.start();

		vkQueueWaitIdle(logicalDevice.getQueueManager().getGraphicQueue());
		Integer imageIndex = renderPipeline.acquireNextImage();

		loadCounterTotal.countTime();

		loadCounterUI.start();

		if (configuration.imGui.newFrame())
		{
			configuration.imGui.updateBuffers();
			configuration.renderPass
					.buildRenderPass(configuration.commandBuffers.getCommandBuffers());

			System.out.println("Rebuild");
		}

		loadCounterUI.countTime();

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

		loadCounterRender.countTime();
	}

	@Override
	public void resize(MemoryStack stack, Surface surface)
	{
		renderPipeline.freeNode();
		configure(surface);
		renderPipeline.allocateNode(stack);
	}
}
