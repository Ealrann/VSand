package org.sheepy.vulkan.sand.pipelinepool;

import java.util.Collection;

import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.command.ECommandStage;
import org.sheepy.vulkan.concurrent.ISignalEmitter;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.imgui.ImGuiGraphicPipeline;
import org.sheepy.vulkan.pipeline.graphic.GraphicProcess;
import org.sheepy.vulkan.pipeline.graphic.GraphicProcessPool;
import org.sheepy.vulkan.sand.graphics.BufferedGraphicPipeline;
import org.sheepy.vulkan.sand.graphics.BufferedSwapConfiguration;
import org.sheepy.vulkan.sand.graphics.SandUIDescriptor;

public class RenderPipelinePool extends GraphicProcessPool
{

	public RenderPipelinePool(LogicalDevice logicalDevice, Image image,
			SandUIDescriptor uiDescriptor, Collection<ISignalEmitter> waitForEmitters)
	{
		super(new BufferedSwapConfiguration(logicalDevice, image, uiDescriptor),
				true);

		GraphicProcess process = new GraphicProcess(context);
		process.addProcessUnit(new BufferedGraphicPipeline(context, image), ECommandStage.PreRender);
		process.addProcessUnit(new ImGuiGraphicPipeline(context, uiDescriptor), ECommandStage.Render);

		addProcess(process);
	}
}
