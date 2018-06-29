package test.vulkan.gameoflife.compute;

import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_COMPUTE_BIT;

import java.util.ArrayList;
import java.util.List;

import org.sheepy.lily.game.vulkan.descriptor.IDescriptor;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.compute.IComputer;
import org.sheepy.lily.game.vulkan.shader.Shader;

import test.vulkan.gameoflife.Board;

public class LifeComputer implements IComputer
{
	private static final String SHADER_LOCATION = "test/vulkan/gameoflife/life.comp.spv";

	private LogicalDevice logicalDevice;

	private int width;
	private int height;

	private BoardBuffer buffer;
	private BoardBuffer sourceBuffer;

	private Shader shader;

	public static LifeComputer alloc(LogicalDevice logicalDevice, Board board)
	{
		LifeComputer res = new LifeComputer(logicalDevice, board);
		res.load();
		return res;
	}

	public LifeComputer(LogicalDevice logicalDevice, Board board)
	{
		this.logicalDevice = logicalDevice;
		buffer = new BoardBuffer(logicalDevice, board);
	}

	public void load()
	{
		shader = new Shader(logicalDevice, SHADER_LOCATION, VK_SHADER_STAGE_COMPUTE_BIT);
	}

	public void attachSourceBuffer(BoardBuffer boardBuffer)
	{
		this.sourceBuffer = boardBuffer;
	}

	@Override
	public void free()
	{
		buffer.free();
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
		return width;
	}

	@Override
	public int getDataHeight()
	{
		return height;
	}

	@Override
	public int getDataDepth()
	{
		return 1;
	}

	public BoardBuffer getBuffer()
	{
		return buffer;
	}

	@Override
	public List<IDescriptor> getDescriptors()
	{
		List<IDescriptor> res = new ArrayList<>();

		res.add(sourceBuffer);
		res.add(buffer);

		return res;
	}
}
