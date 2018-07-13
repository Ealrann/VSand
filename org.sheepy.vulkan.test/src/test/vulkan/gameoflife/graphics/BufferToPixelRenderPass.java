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
import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.command.AbstractCommandBuffer;
import org.sheepy.vulkan.command.graphic.RenderCommandBuffer;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.swap.IRenderPass;
import org.sheepy.vulkan.swapchain.SwapChainManager;
import org.sheepy.vulkan.swapchain.SwapChainManager.Extent2D;
import org.sheepy.vulkan.view.ImageView;

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
	public void buildRenderPass(List<RenderCommandBuffer> commandBuffers)
	{
		for (int i = 0; i < commandBuffers.size(); i++)
		{
			RenderCommandBuffer commandBuffer = commandBuffers.get(i);
			ImageView imageView = pipeline.getImageView().getImageViews().get(i);

			commandBuffer.startCommand();

			pipeline.bind(commandBuffer);

			buildSwapCommand(pipeline, commandBuffer, imageView);

			commandBuffer.startRenderPass();
			
			commandBuffer.endRenderPass();
			commandBuffer.endCommand();
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
				VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL, 1,
				VK_PIPELINE_STAGE_TRANSFER_BIT, VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT,
				VK_ACCESS_TRANSFER_WRITE_BIT, VK_ACCESS_COLOR_ATTACHMENT_READ_BIT);
	}

	@Override
	public void load(SwapChainManager swapChain)
	{
		VkAttachmentDescription colorAttachment = VkAttachmentDescription.calloc();
		colorAttachment.format(swapChain.getColorDomain().getColorFormat());
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
