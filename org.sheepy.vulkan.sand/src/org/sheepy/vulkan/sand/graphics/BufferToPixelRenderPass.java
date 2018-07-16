package org.sheepy.vulkan.sand.graphics;

import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.*;

import java.util.List;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkImageBlit;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSubpassDependency;
import org.lwjgl.vulkan.VkSubpassDescription;
import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.command.AbstractCommandBuffer;
import org.sheepy.vulkan.command.graphic.RenderCommandBuffer;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.imgui.ImGuiPipeline;
import org.sheepy.vulkan.pipeline.swap.IRenderPass;
import org.sheepy.vulkan.swapchain.SwapChainManager.Extent2D;
import org.sheepy.vulkan.view.ImageView;

public class BufferToPixelRenderPass implements IRenderPass
{
	private LogicalDevice logicalDevice;
	private Image srcImage;
	private BufferedSwapConfiguration configuration;

	private long renderPass;

	private ImGuiPipeline myImGui;
	private VkImageBlit.Buffer region;

	public BufferToPixelRenderPass(BufferedSwapConfiguration configuration)
	{
		this.logicalDevice = configuration.logicalDevice;
		this.srcImage = configuration.pixelImage;
		this.configuration = configuration;

		this.myImGui = configuration.imGui;
		
		region = VkImageBlit.calloc(1);
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
	}

	@Override
	public void buildRenderPass(List<RenderCommandBuffer> commandBuffers)
	{
		myImGui.newFrame(false);
		myImGui.updateBuffers();

		for (int i = 0; i < commandBuffers.size(); i++)
		{
			RenderCommandBuffer commandBuffer = commandBuffers.get(i);
			ImageView imageView = configuration.imageViewManager.getImageViews().get(i);

			commandBuffer.startCommand();

			buildSwapCommand(commandBuffer, imageView);

			commandBuffer.startRenderPass();

			myImGui.drawFrame(commandBuffer.getVkCommandBuffer());

			commandBuffer.endRenderPass();
			commandBuffer.endCommand();
		}
	}

	public void buildSwapCommand(AbstractCommandBuffer commandBuffer, ImageView dstImageView)
	{
		Extent2D extent = configuration.swapChainManager.getExtent();

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

		region.dstOffsets(1).x(extent.getWidth());
		region.dstOffsets(1).y(extent.getHeight());
		region.dstOffsets(1).z(1);

		vkCmdBlitImage(commandBuffer.getVkCommandBuffer(), bltSrcImage,
				VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL, bltDstImage,
				VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, region, VK_FILTER_LINEAR);

		srcImage.transitionImageLayout(commandBuffer.getVkCommandBuffer(),
				VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL, VK_IMAGE_LAYOUT_GENERAL, 1,
				VK_PIPELINE_STAGE_TRANSFER_BIT, VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT,
				VK_ACCESS_TRANSFER_READ_BIT, VK_ACCESS_SHADER_WRITE_BIT);

		dstImageView.transitionImageLayout(commandBuffer.getVkCommandBuffer(),
				VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL, 1,
				VK_PIPELINE_STAGE_TRANSFER_BIT, VK_PIPELINE_STAGE_VERTEX_SHADER_BIT,
				VK_ACCESS_TRANSFER_WRITE_BIT, VK_ACCESS_SHADER_WRITE_BIT);
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		VkAttachmentDescription colorAttachment = VkAttachmentDescription.calloc();
		colorAttachment.format(configuration.swapChainManager.getColorDomain().getColorFormat());
		colorAttachment.samples(VK_SAMPLE_COUNT_1_BIT);
		colorAttachment.loadOp(VK_ATTACHMENT_LOAD_OP_LOAD);
		colorAttachment.storeOp(VK_ATTACHMENT_STORE_OP_STORE);
		colorAttachment.stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE);
		colorAttachment.stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE);
		colorAttachment.initialLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);
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
		dependency.srcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
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

		myImGui.allocate(stack);

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
		region.free();
		myImGui.free();
		vkDestroyRenderPass(logicalDevice.getVkDevice(), renderPass, null);
	}
}
