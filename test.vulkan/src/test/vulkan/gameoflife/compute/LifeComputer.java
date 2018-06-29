package test.vulkan.gameoflife.compute;

import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_COMPUTE_BIT;

import java.util.Collections;
import java.util.List;

import org.sheepy.lily.game.vulkan.buffer.Buffer;
import org.sheepy.lily.game.vulkan.descriptor.IDescriptor;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.compute.IComputer;
import org.sheepy.lily.game.vulkan.shader.Shader;

public class LifeComputer implements IComputer
{
	private static final String SHADER_LOCATION = "test/vulkan/gameoflife/life.comp.spv";
	private Board board;

	private Shader shader;

	public static LifeComputer alloc(LogicalDevice logicalDevice, Board board)
	{
		LifeComputer res = new LifeComputer(logicalDevice, board);
		res.load();
		return res;
	}

	public LifeComputer(LogicalDevice logicalDevice, Board board)
	{
		this.board = board;

		shader = new Shader(logicalDevice, SHADER_LOCATION, VK_SHADER_STAGE_COMPUTE_BIT);
	}

	public void load()
	{
		board.load();
	}

	@Override
	public void free()
	{
		board.free();
		shader.destroy();
	}

	@Override
	public Shader getShader()
	{
		return shader;
	}

	@Override
	public int getDataWidth()
	{
		return board.getWidth();
	}

	@Override
	public int getDataHeight()
	{
		return board.getHeight();
	}

	@Override
	public int getDataDepth()
	{
		return 1;
	}

	public Buffer getBuffer()
	{
		return board.getBuffer();
	}

	@Override
	public List<IDescriptor> getDescriptors()
	{
		return Collections.singletonList(board);
	}
}
