package test.vulkan.mesh;

import static org.lwjgl.vulkan.KHRSwapchain.vkQueuePresentKHR;
import static org.lwjgl.vulkan.VK10.*;

import org.lwjgl.system.MemoryStack;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.SurfacePipelinePool;
import org.sheepy.vulkan.pipeline.swap.SwapPipeline;
import org.sheepy.vulkan.window.Surface;

public class MeshRenderPipelinePool extends SurfacePipelinePool
{
	private LogicalDevice logicalDevice;

	private SwapPipeline swapPipeline;

	public MeshRenderPipelinePool(LogicalDevice logicalDevice)
	{
		super(logicalDevice, logicalDevice.getQueueManager().getGraphicQueueIndex());
		this.logicalDevice = logicalDevice;
	}

	@Override
	public void execute()
	{
		Integer imageIndex = swapPipeline.acquireNextImage();

		if (imageIndex != null)
		{
			if (vkQueueSubmit(logicalDevice.getQueueManager().getGraphicQueue(),
					swapPipeline.getFrameSubmission().getSubmitInfo(imageIndex),
					VK_NULL_HANDLE) != VK_SUCCESS)
			{
				throw new AssertionError("failed to submit draw command buffer!");
			}

			vkQueuePresentKHR(logicalDevice.getQueueManager().getGraphicQueue(),
					swapPipeline.getFrameSubmission().getPresentInfo(imageIndex));
		}
	}

	@Override
	public void configure(Surface surface)
	{
		swapPipeline.configure(surface);
	}

	@Override
	public void resize(MemoryStack stack, Surface surface)
	{
		swapPipeline.freeNode();
		configure(surface);
		swapPipeline.allocateNode(stack);
	}

	public void setSwapPipeline(MeshSwapPipeline swapPipeline, MeshSwapConfiguration configuration)
	{
		this.swapPipeline = swapPipeline;
		subAllocationObjects.add(configuration);
		subAllocationObjects.add(swapPipeline);
	}
}
