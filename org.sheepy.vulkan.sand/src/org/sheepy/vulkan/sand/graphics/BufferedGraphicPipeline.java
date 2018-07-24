package org.sheepy.vulkan.sand.graphics;

import static org.lwjgl.vulkan.VK10.*;

import java.util.Collections;
import java.util.List;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageBlit;
import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.buffer.ImageBarrier;
import org.sheepy.vulkan.command.graphic.RenderCommandBuffer;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.descriptor.DescriptorPool;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.imgui.ImGuiPipeline;
import org.sheepy.vulkan.pipeline.graphic.GraphicContext;
import org.sheepy.vulkan.pipeline.graphic.IGraphicProcessUnit;
import org.sheepy.vulkan.swapchain.SwapChainManager.Extent2D;
import org.sheepy.vulkan.view.ImageView;

public class BufferedGraphicPipeline implements IGraphicProcessUnit, IAllocable
{
	private GraphicContext context;
	private ImGuiPipeline imGui;
	private Image srcImage;

	public BufferedGraphicPipeline(Image srcImage)
	{
		this.srcImage = srcImage;
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		imGui.allocate(stack);

		imGui.newFrame();
		imGui.updateBuffers();
	}

	@Override
	public void free()
	{
		imGui.free();
	}

	@Override
	public void bindContext(GraphicContext context, DescriptorPool descriptorPool)
	{
		this.context = context;

		if (context != null)
		{
			this.imGui = ((BufferedSwapConfiguration) context.configuration).imGui;
		}
	}

	@Override
	public void execute(RenderCommandBuffer commandBuffer)
	{
		imGui.drawFrame(commandBuffer.getVkCommandBuffer());
	}

	@Override
	public void executePreRender(RenderCommandBuffer commandBuffer)
	{
		Extent2D extent = context.swapChainManager.getExtent();
		ImageView dstImageView = context.imageViewManager.getImageViews().get(commandBuffer.id);

		// Intend to blit from this image, set the layout accordingly

		ImageBarrier barrier = new ImageBarrier(VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT,
				VK_PIPELINE_STAGE_TRANSFER_BIT);
		barrier.addImageBarrier(srcImage, VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL,
				VK_ACCESS_TRANSFER_READ_BIT);
		barrier.addImageBarrier(dstImageView.getImageId(), dstImageView.getImageFormat(), 1, 0,
				VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, 0, VK_ACCESS_TRANSFER_WRITE_BIT);

		barrier.execute(commandBuffer.getVkCommandBuffer());

		long bltSrcImage = srcImage.getId();
		long bltDstImage = dstImageView.getImageId();

		// Do a 32x32 blit to all of the dst image - should get big squares
		VkImageBlit.Buffer region = VkImageBlit.calloc(1);
		region.srcSubresource().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
		region.srcSubresource().mipLevel(0);
		region.srcSubresource().baseArrayLayer(0);
		region.srcSubresource().layerCount(1);
		region.srcOffsets(0).x(0);
		region.srcOffsets(0).y(0);
		region.srcOffsets(0).z(0);
		region.srcOffsets(1).x(srcImage.getWidth());
		region.srcOffsets(1).y(srcImage.getHeight());
		region.srcOffsets(1).z(1);
		region.dstSubresource().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
		region.dstSubresource().mipLevel(0);
		region.dstSubresource().baseArrayLayer(0);
		region.dstSubresource().layerCount(1);
		region.dstOffsets(0).x(0);
		region.dstOffsets(0).y(0);
		region.dstOffsets(0).z(0);
		region.dstOffsets(1).x(extent.getWidth());
		region.dstOffsets(1).y(extent.getHeight());
		region.dstOffsets(1).z(1);

		vkCmdBlitImage(commandBuffer.getVkCommandBuffer(), bltSrcImage,
				VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL, bltDstImage,
				VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, region, VK_FILTER_NEAREST);

		ImageBarrier barrierEnd = new ImageBarrier(VK_PIPELINE_STAGE_TRANSFER_BIT,
				VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT);
		barrierEnd.addImageBarrier(srcImage, VK_IMAGE_LAYOUT_GENERAL, VK_ACCESS_SHADER_WRITE_BIT);
		barrierEnd.addImageBarrier(dstImageView.getImageId(), dstImageView.getImageFormat(), 1,
				VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL,
				VK_ACCESS_TRANSFER_WRITE_BIT, VK_ACCESS_SHADER_WRITE_BIT);

		barrierEnd.execute(commandBuffer.getVkCommandBuffer());
	}

	@Override
	public void executePostRender(RenderCommandBuffer commandBuffer)
	{}

	@Override
	public List<IDescriptor> getDescriptors()
	{
		return Collections.emptyList();
	}
}
