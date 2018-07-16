package org.sheepy.vulkan.sand.graphics;

import static org.lwjgl.vulkan.KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_B8G8R8A8_UNORM;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_STAGE_TRANSFER_BIT;

import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.imgui.ImGuiPipeline;
import org.sheepy.vulkan.pipeline.swap.SwapConfiguration;

public class BufferedSwapConfiguration extends SwapConfiguration
{
	public Image pixelImage;
	public ImGuiPipeline imGui;

	public BufferedSwapConfiguration(LogicalDevice logicalDevice, CommandPool commandPool,
			Image pixelImage, SandUIDescriptor uiDescriptor)
	{
		super(logicalDevice, commandPool, VK_FORMAT_B8G8R8A8_UNORM,
				VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);

		this.pixelImage = pixelImage;

		this.frameWaitStage = VK_PIPELINE_STAGE_TRANSFER_BIT;

		imGui = new ImGuiPipeline(commandPool, this, uiDescriptor);
	}

	
}
