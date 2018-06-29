package test.vulkan.gameoflife.compute;

import java.util.Collections;

import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputePipeline;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputerPool;

public class PixelComputerPipeline extends ComputePipeline
{
	private PixelComputer computer;

	public PixelComputerPipeline(LogicalDevice logicalDevice, CommandPool commandPool, Board board)
	{
		super(logicalDevice, commandPool, Collections.emptyList());

		computer = new PixelComputer(logicalDevice, commandPool, board);
	}

	@Override
	public void load()
	{
		computer.load();

		ComputerPool pool = new ComputerPool(Collections.singletonList(computer));
		attachComputerPool(pool);

		super.load();
	}

	public Image getImage()
	{
		return computer.getImage();
	}
}
