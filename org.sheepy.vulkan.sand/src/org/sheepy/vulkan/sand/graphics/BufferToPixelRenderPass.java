package org.sheepy.vulkan.sand.graphics;

import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.*;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSubpassDependency;
import org.lwjgl.vulkan.VkSubpassDescription;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.graphic.GraphicContext;
import org.sheepy.vulkan.pipeline.graphic.IRenderPass;

public class BufferToPixelRenderPass implements IRenderPass
{
	private LogicalDevice logicalDevice;
	private GraphicContext context;

	private long renderPass;

	public BufferToPixelRenderPass()
	{}

	@Override
	public void bindContext(GraphicContext context)
	{
		this.context = context;
		this.logicalDevice = context.logicalDevice;
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		VkAttachmentDescription colorAttachment = VkAttachmentDescription.calloc();
		colorAttachment.format(context.swapChainManager.getColorDomain().getColorFormat());
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
		dependency.dstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
		dependency.srcAccessMask(0);
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
