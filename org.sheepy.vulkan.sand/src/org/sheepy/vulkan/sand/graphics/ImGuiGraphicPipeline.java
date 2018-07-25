package org.sheepy.vulkan.sand.graphics;

import org.lwjgl.system.MemoryStack;
import org.sheepy.vulkan.command.graphic.RenderCommandBuffer;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.descriptor.DescriptorPool;
import org.sheepy.vulkan.imgui.ImGuiPipeline;
import org.sheepy.vulkan.pipeline.graphic.GraphicContext;
import org.sheepy.vulkan.pipeline.graphic.IGraphicExecutable;
import org.sheepy.vulkan.pipeline.graphic.IGraphicProcessUnit;

public class ImGuiGraphicPipeline implements IGraphicExecutable, IGraphicProcessUnit, IAllocable
{
	private ImGuiPipeline imGui;

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
	public void bindContext(DescriptorPool descriptorPool, GraphicContext context)
	{
		this.imGui = ((BufferedSwapConfiguration) context.configuration).imGui;
	}

	@Override
	public void execute(RenderCommandBuffer commandBuffer)
	{
		imGui.drawFrame(commandBuffer.getVkCommandBuffer());
	}

}
