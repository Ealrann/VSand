package test.vulkan.mesh;

import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.*;

import java.nio.LongBuffer;
import java.util.List;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSubpassDependency;
import org.lwjgl.vulkan.VkSubpassDescription;
import org.sheepy.vulkan.command.graphic.RenderCommandBuffer;
import org.sheepy.vulkan.descriptor.DescriptorSet;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.swap.IRenderPass;

public class MeshRenderPass implements IRenderPass
{
	private LogicalDevice logicalDevice;
	private MeshSwapConfiguration configuration;
	private Mesh mesh;

	private long renderPass;

	public MeshRenderPass(MeshSwapConfiguration configuration)
	{
		this.logicalDevice = configuration.logicalDevice;
		this.configuration = configuration;
		this.mesh = ((MeshSwapConfiguration) configuration).mesh;
	}

	@Override
	public void buildRenderPass(List<RenderCommandBuffer> commandBuffers)
	{
		for (RenderCommandBuffer commandBuffer : commandBuffers)
		{
			commandBuffer.start();

			vkCmdBindPipeline(commandBuffer.getVkCommandBuffer(), VK_PIPELINE_BIND_POINT_GRAPHICS,
					configuration.graphicsPipeline.getId());

			mesh.bindBufferForRender(commandBuffer);

			if (configuration.descriptorPool != null)
			{
				LongBuffer bDescriptorSet = MemoryUtil
						.memAllocLong(configuration.descriptorPool.size());
				for (DescriptorSet descriptorSet : configuration.descriptorPool)
				{
					bDescriptorSet.put(descriptorSet.getId());
				}
				bDescriptorSet.flip();
				vkCmdBindDescriptorSets(commandBuffer.getVkCommandBuffer(),
						VK_PIPELINE_BIND_POINT_GRAPHICS, configuration.graphicsPipeline.getLayoutId(), 0,
						bDescriptorSet, null);
			}

			mesh.draw(commandBuffer);

			commandBuffer.end();
		}
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		VkAttachmentDescription colorAttachment = VkAttachmentDescription.callocStack(stack);
		colorAttachment.format(configuration.swapChainManager.getColorDomain().getColorFormat());
		colorAttachment.samples(VK_SAMPLE_COUNT_1_BIT);
		colorAttachment.loadOp(VK_ATTACHMENT_LOAD_OP_CLEAR);
		colorAttachment.storeOp(VK_ATTACHMENT_STORE_OP_STORE);
		colorAttachment.stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE);
		colorAttachment.stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE);
		colorAttachment.initialLayout(VK_IMAGE_LAYOUT_UNDEFINED);
		colorAttachment.finalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR);

		VkAttachmentReference.Buffer colorAttachmentRef = VkAttachmentReference.callocStack(1,
				stack);
		colorAttachmentRef.attachment(0);
		colorAttachmentRef.layout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);

		VkAttachmentDescription depthAttachment = null;
		VkAttachmentReference depthAttachmentRef = null;
		if (configuration.depthResource != null)
		{
			depthAttachment = VkAttachmentDescription.callocStack(stack);
			depthAttachment.format(configuration.depthResource.getDepthFormat());
			depthAttachment.samples(VK_SAMPLE_COUNT_1_BIT);
			depthAttachment.loadOp(VK_ATTACHMENT_LOAD_OP_CLEAR);
			depthAttachment.storeOp(VK_ATTACHMENT_STORE_OP_DONT_CARE);
			depthAttachment.stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE);
			depthAttachment.stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE);
			depthAttachment.initialLayout(VK_IMAGE_LAYOUT_UNDEFINED);
			depthAttachment.finalLayout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL);

			depthAttachmentRef = VkAttachmentReference.callocStack(stack);
			depthAttachmentRef.attachment(1);
			depthAttachmentRef.layout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL);
		}

		VkSubpassDescription.Buffer subpass = VkSubpassDescription.callocStack(1, stack);
		subpass.pipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS);
		subpass.colorAttachmentCount(1);
		subpass.pColorAttachments(colorAttachmentRef);
		subpass.pDepthStencilAttachment(depthAttachmentRef);

		VkSubpassDependency.Buffer dependency = VkSubpassDependency.callocStack(1, stack);
		dependency.srcSubpass(VK_SUBPASS_EXTERNAL);
		dependency.dstSubpass(0);
		dependency.srcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
		dependency.srcAccessMask(0);
		dependency.dstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
		dependency.dstAccessMask(
				VK_ACCESS_COLOR_ATTACHMENT_READ_BIT | VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT);

		int attachmentCount = 1;
		attachmentCount += depthAttachment != null ? 1 : 0;
		VkAttachmentDescription.Buffer attachments = VkAttachmentDescription
				.callocStack(attachmentCount, stack);
		attachments.put(colorAttachment);
		if (depthAttachment != null)
		{
			attachments.put(depthAttachment);
		}
		attachments.flip();

		VkRenderPassCreateInfo renderPassInfo = VkRenderPassCreateInfo.callocStack(stack);
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
