package org.sheepy.vulkan.sand.graphics;

import org.lwjgl.system.MemoryStack;
import org.sheepy.vulkan.command.graphic.RenderCommandBuffer;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.descriptor.DescriptorPool;
import org.sheepy.vulkan.imgui.ImGuiPipeline;
import org.sheepy.vulkan.imgui.UIDescriptor;
import org.sheepy.vulkan.pipeline.graphic.GraphicContext;
import org.sheepy.vulkan.pipeline.graphic.IGraphicExecutable;
import org.sheepy.vulkan.pipeline.graphic.IGraphicProcessUnit;

public class ImGuiGraphicPipeline implements IGraphicExecutable, IGraphicProcessUnit, IAllocable
{
	public ImGuiPipeline imGui;
	public GraphicContext context;

	public ImGuiGraphicPipeline(UIDescriptor uiConfiguration)
	{
		imGui = new ImGuiPipeline(uiConfiguration);
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		imGui.allocate(stack);

		imGui.newFrame();
		imGui.updateBuffers();
	}

	@Override
	public void bindContext(DescriptorPool descriptorPool, GraphicContext context)
	{
		imGui.bindContext(descriptorPool, context);
	}

	@Override
	public void free()
	{
		imGui.free();
	}

	@Override
	public void execute(RenderCommandBuffer commandBuffer)
	{
		imGui.drawFrame(commandBuffer.getVkCommandBuffer());
	}

	public boolean update()
	{
		if (imGui.newFrame())
		{
			imGui.updateBuffers();
			return true;
		}
		return false;
	}

}
