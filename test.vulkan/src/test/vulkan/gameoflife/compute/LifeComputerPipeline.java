package test.vulkan.gameoflife.compute;

import java.util.Collections;

import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputePipeline;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputerPool;

public class LifeComputerPipeline extends ComputePipeline
{
	private LifeComputer computer;

	public LifeComputerPipeline(LogicalDevice logicalDevice, CommandPool commandPool, Board board)
	{
		super(logicalDevice, commandPool, Collections.emptyList());

		computer = new LifeComputer(logicalDevice, board);
	}

	@Override
	public void load()
	{
		computer.load();

		ComputerPool pool = new ComputerPool(Collections.singletonList(computer));
		attachComputerPool(pool);

		super.load();
	}
}