package org.sheepy.vulkan.sand.pipelinepool;

import org.sheepy.lily.game.vulkan.buffer.Buffer;
import org.sheepy.lily.game.vulkan.command.compute.ComputeCommandBuffer;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputeProcess;

public class BoardComputeProcess extends ComputeProcess
{
	private Buffer clearBuffer;
	private Buffer movedBuffer;

	public BoardComputeProcess(LogicalDevice logicalDevice, Buffer clearBuffer, Buffer movedBuffer)
	{
		super(logicalDevice);
		this.clearBuffer = clearBuffer;
		this.movedBuffer = movedBuffer;
	}

	@Override
	public void recordCommand(ComputeCommandBuffer commandBuffer)
	{
		Buffer.copyBuffer(commandBuffer.getVkCommandBuffer(), clearBuffer.getId(),
				movedBuffer.getId(), (int) clearBuffer.getSize());

		super.recordCommand(commandBuffer);
	}
}
