package org.sheepy.vulkan.sand.graphics;

import static org.lwjgl.vulkan.KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
import static org.lwjgl.vulkan.KHRSurface.VK_PRESENT_MODE_FIFO_KHR;
import static org.lwjgl.vulkan.VK10.*;

import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.imgui.ImGuiPipeline;
import org.sheepy.vulkan.pipeline.graphic.GraphicConfiguration;

public class BufferedSwapConfiguration extends GraphicConfiguration
{
	public Image pixelImage;
	public ImGuiPipeline imGui;

	public BufferedSwapConfiguration(LogicalDevice logicalDevice, Image pixelImage,
			SandUIDescriptor uiDescriptor)
	{
		super(logicalDevice, VK_FORMAT_B8G8R8A8_UNORM, VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);

		this.pixelImage = pixelImage;

		this.frameWaitStage = VK_PIPELINE_STAGE_TRANSFER_BIT;

		// We will use the swap image as a target transfer
		swapImageUsages |= VK_IMAGE_USAGE_TRANSFER_DST_BIT;
		renderPass = new BufferToPixelRenderPass();
		
		// enable VSync
		presentationMode = VK_PRESENT_MODE_FIFO_KHR;

	}
}
