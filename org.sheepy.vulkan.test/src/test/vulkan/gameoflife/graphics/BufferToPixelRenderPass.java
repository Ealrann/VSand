package test.vulkan.gameoflife.graphics;

import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.*;

import java.util.List;

import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkImageBlit;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSubpassDependency;
import org.lwjgl.vulkan.VkSubpassDescription;
import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.AbstractCommandBuffer;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.swap.IRenderPass;
import org.sheepy.lily.game.vulkan.swapchain.SwapChainManager;
import org.sheepy.lily.game.vulkan.swapchain.SwapChainManager.Extent2D;
import org.sheepy.lily.game.vulkan.view.ImageView;

public class BufferToPixelRenderPass implements IRenderPass
{
	private LogicalDevice logicalDevice;
	private Image srcImage;
	private BufferedSwapPipeline pipeline;

	private long renderPass;

	public BufferToPixelRenderPass(LogicalDevice logicalDevice, Image srcImage,
			BufferedSwapPipeline pipeline)
	{
		this.logicalDevice = logicalDevice;
		this.srcImage = srcImage;
		this.pipeline = pipeline;
	}

	@Override
	public void buildRenderPass(List<? extends AbstractCommandBuffer> commandBuffers)
	{
		for (int i = 0; i < commandBuffers.size(); i++)
		{
			AbstractCommandBuffer commandBuffer = commandBuffers.get(i);
			ImageView imageView = pipeline.getImageView().getImageViews().get(i);

			commandBuffer.start();

			pipeline.bind(commandBuffer);

			buildSwapCommand(pipeline, commandBuffer, imageView);

			commandBuffer.end();
		}
	}

	public void buildSwapCommand(BufferedSwapPipeline pipeline,
			AbstractCommandBuffer commandBuffer,
			ImageView dstImageView)
	{
		Extent2D extent = pipeline.getSwapChain().getExtent();

		// Intend to blit from this image, set the layout accordingly

		srcImage.transitionImageLayout(commandBuffer.getVkCommandBuffer(), VK_IMAGE_LAYOUT_GENERAL,
				VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL, 1, VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT,
				VK_PIPELINE_STAGE_TRANSFER_BIT, VK_ACCESS_SHADER_WRITE_BIT,
				VK_ACCESS_TRANSFER_READ_BIT);

		dstImageView.transitionImageLayout(commandBuffer.getVkCommandBuffer(),
				VK_IMAGE_LAYOUT_UNDEFINED, VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, 1,
				VK_PIPELINE_STAGE_TRANSFER_BIT, VK_PIPELINE_STAGE_TRANSFER_BIT, 0,
				VK_ACCESS_TRANSFER_WRITE_BIT);

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

		srcImage.transitionImageLayout(commandBuffer.getVkCommandBuffer(),
				VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL, VK_IMAGE_LAYOUT_GENERAL, 1,
				VK_PIPELINE_STAGE_TRANSFER_BIT, VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT,
				VK_ACCESS_TRANSFER_READ_BIT, VK_ACCESS_SHADER_WRITE_BIT);

		dstImageView.transitionImageLayout(commandBuffer.getVkCommandBuffer(),
				VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, VK_IMAGE_LAYOUT_PRESENT_SRC_KHR, 1,
				VK_PIPELINE_STAGE_TRANSFER_BIT, VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT,
				VK_ACCESS_TRANSFER_WRITE_BIT, VK_ACCESS_COLOR_ATTACHMENT_READ_BIT);

		// // Use a barrier to make sure the blit is finished before the copy
		// // starts
		// VkImageMemoryBarrier.Buffer memBarrier =
		// VkImageMemoryBarrier.calloc(1);
		// memBarrier.sType(VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER);
		// memBarrier.srcAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT);
		// memBarrier.dstAccessMask(VK_ACCESS_MEMORY_READ_BIT);
		// memBarrier.oldLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL);
		// memBarrier.newLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL);
		// memBarrier.srcQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
		// memBarrier.dstQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
		// memBarrier.subresourceRange().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
		// memBarrier.subresourceRange().baseMipLevel(0);
		// memBarrier.subresourceRange().levelCount(1);
		// memBarrier.subresourceRange().baseArrayLayer(0);
		// memBarrier.subresourceRange().layerCount(1);
		// memBarrier.image(bltDstImage);
		// vkCmdPipelineBarrier(commandBuffer.getVkCommandBuffer(),
		// VK_PIPELINE_STAGE_TRANSFER_BIT,
		// VK_PIPELINE_STAGE_TRANSFER_BIT, 0, null, null, memBarrier);

		// // Do a image copy to part of the dst image - checks should stay
		// small
		// VkImageCopy.Buffer cregion = VkImageCopy.calloc(1);
		// cregion.srcSubresource().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
		// cregion.srcSubresource().mipLevel(0);
		// cregion.srcSubresource().baseArrayLayer(0);
		// cregion.srcSubresource().layerCount(1);
		// cregion.srcOffset().x(0);
		// cregion.srcOffset().y(0);
		// cregion.srcOffset().z(0);
		// cregion.dstSubresource().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
		// cregion.dstSubresource().mipLevel(0);
		// cregion.dstSubresource().baseArrayLayer(0);
		// cregion.dstSubresource().layerCount(1);
		// cregion.dstOffset().x(256);
		// cregion.dstOffset().y(256);
		// cregion.dstOffset().z(0);
		// cregion.extent().width(128);
		// cregion.extent().height(128);
		// cregion.extent().depth(1);
		//
		// vkCmdCopyImage(commandBuffer.getVkCommandBuffer(), bltSrcImage,
		// VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL, bltDstImage,
		// VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, cregion);
		//
		// VkImageMemoryBarrier.Buffer prePresentBarrier =
		// VkImageMemoryBarrier.calloc(1);
		// prePresentBarrier.sType(VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER);
		// prePresentBarrier.srcAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT);
		// prePresentBarrier.dstAccessMask(VK_ACCESS_MEMORY_READ_BIT);
		// prePresentBarrier.oldLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL);
		// prePresentBarrier.newLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR);
		// prePresentBarrier.srcQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
		// prePresentBarrier.dstQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
		// prePresentBarrier.subresourceRange().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
		// prePresentBarrier.subresourceRange().baseMipLevel(0);
		// prePresentBarrier.subresourceRange().levelCount(1);
		// prePresentBarrier.subresourceRange().baseArrayLayer(0);
		// prePresentBarrier.subresourceRange().layerCount(1);
		// prePresentBarrier.image(bltDstImage);
		// vkCmdPipelineBarrier(commandBuffer.getVkCommandBuffer(),
		// VK_PIPELINE_STAGE_TRANSFER_BIT,
		// VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT, 0, null, null,
		// prePresentBarrier);

		// cregion.free();
		// prePresentBarrier.free();
		// memBarrier.free();
		// region.free();
	}

	@Override
	public void load(SwapChainManager swapChain)
	{
		VkAttachmentDescription colorAttachment = VkAttachmentDescription.calloc();
		colorAttachment.format(swapChain.getColorDomain().getColorFormat());
		colorAttachment.samples(VK_SAMPLE_COUNT_1_BIT);
		colorAttachment.loadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE);
		colorAttachment.storeOp(VK_ATTACHMENT_STORE_OP_STORE);
		colorAttachment.stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE);
		colorAttachment.stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE);
		colorAttachment.initialLayout(VK_IMAGE_LAYOUT_UNDEFINED);
		colorAttachment.finalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR);

		VkAttachmentReference.Buffer colorAttachmentRef = VkAttachmentReference.calloc(1);
		colorAttachmentRef.attachment(0);
		colorAttachmentRef.layout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);

		VkSubpassDescription.Buffer subpass = VkSubpassDescription.calloc(1);
		subpass.pipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS);
		subpass.colorAttachmentCount(1);
		subpass.pColorAttachments(colorAttachmentRef);

		VkSubpassDependency.Buffer dependency = VkSubpassDependency.calloc(1);
		dependency.srcSubpass(VK_SUBPASS_EXTERNAL);
		dependency.dstSubpass(0);
		dependency.srcStageMask(VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT);
		dependency.srcAccessMask(0);
		dependency.dstStageMask(VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT);
		dependency.dstAccessMask(
				VK_ACCESS_COLOR_ATTACHMENT_READ_BIT | VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT);

		int attachmentCount = 1;
		VkAttachmentDescription.Buffer attachments = VkAttachmentDescription
				.calloc(attachmentCount);
		attachments.put(colorAttachment);
		attachments.flip();

		VkRenderPassCreateInfo renderPassInfo = VkRenderPassCreateInfo.calloc();
		renderPassInfo.sType(VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO);
		renderPassInfo.pAttachments(attachments);
		renderPassInfo.pSubpasses(subpass);
		renderPassInfo.pDependencies(dependency);

		long[] aRenderPass = new long[1];
		if (vkCreateRenderPass(logicalDevice.getVkDevice(), renderPassInfo, null,
				aRenderPass) != VK_SUCCESS)
		{
			throw new AssertionError("Failed to create render pass!");
		}
		renderPass = aRenderPass[0];

		attachments.free();
		dependency.free();
		renderPassInfo.free();
		subpass.free();
		colorAttachmentRef.free();
		colorAttachment.free();
	}

	@Override
	public long getId()
	{
		return renderPass;
	}

	@Override
	public void free()
	{
		vkDestroyRenderPass(logicalDevice.getVkDevice(), renderPass, null);
	}
}
