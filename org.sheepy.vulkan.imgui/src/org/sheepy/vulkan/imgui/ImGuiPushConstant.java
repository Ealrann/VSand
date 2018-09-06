package org.sheepy.vulkan.imgui;

import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_VERTEX_BIT;

import org.sheepy.vulkan.buffer.PushConstant;
import org.sheepy.vulkan.device.LogicalDevice;

import glm_.vec2.Vec2;
import imgui.IO;

public class ImGuiPushConstant extends PushConstant
{
	public final Vec2 scale = new Vec2();
	public final Vec2 translate = new Vec2(-1.0f);
	
	private final IO io;

	public ImGuiPushConstant(LogicalDevice logicalDevice, IO io)
	{
		super(logicalDevice, VK_SHADER_STAGE_VERTEX_BIT, 16);
		
		this.io = io;
	}

	@Override
	protected void updateBuffer()
	{
		scale.setX(2.0f / io.getDisplaySize().getX());
		scale.setY(2.0f / io.getDisplaySize().getY());
		
		buffer.putFloat(scale.getX());
		buffer.putFloat(scale.getY());
		buffer.putFloat(translate.getX());
		buffer.putFloat(translate.getY());

		buffer.flip();
	}
}
