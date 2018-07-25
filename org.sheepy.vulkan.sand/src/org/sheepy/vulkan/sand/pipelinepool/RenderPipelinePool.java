package org.sheepy.vulkan.sand.pipelinepool;

import java.util.Collection;

import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.command.ECommandStage;
import org.sheepy.vulkan.concurrent.ISignalEmitter;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.imgui.ImGuiPipeline;
import org.sheepy.vulkan.pipeline.graphic.GraphicProcess;
import org.sheepy.vulkan.pipeline.graphic.GraphicProcessPool;
import org.sheepy.vulkan.sand.graphics.BufferedGraphicPipeline;
import org.sheepy.vulkan.sand.graphics.BufferedSwapConfiguration;
import org.sheepy.vulkan.sand.graphics.ImGuiGraphicPipeline;
import org.sheepy.vulkan.sand.graphics.SandUIDescriptor;

public class RenderPipelinePool extends GraphicProcessPool
{
	public RenderPipelinePool(LogicalDevice logicalDevice, Image image,
			SandUIDescriptor uiDescriptor, Collection<ISignalEmitter> waitForEmitters)
	{
		super(logicalDevice, new BufferedSwapConfiguration(logicalDevice, image, uiDescriptor),
				true);

		((BufferedSwapConfiguration) configuration).imGui = new ImGuiPipeline(commandPool,
				configuration, uiDescriptor);

		ImGuiGraphicPipeline imguiPipeline = new ImGuiGraphicPipeline();

		GraphicProcess process = new GraphicProcess(configuration);
		process.addProcessUnit(new BufferedGraphicPipeline(image), ECommandStage.PreRender);
		process.addProcessUnit(imguiPipeline, ECommandStage.Render);

		addProcess(process);
	}

	@Override
	public void execute()
	{
		if (((BufferedSwapConfiguration) configuration).imGui.newFrame())
		{
			((BufferedSwapConfiguration) configuration).imGui.updateBuffers();
			recordCommands();
		}

		super.execute();
	}

}
