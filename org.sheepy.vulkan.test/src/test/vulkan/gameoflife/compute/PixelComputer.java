package test.vulkan.gameoflife.compute;

import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_COMPUTE_BIT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sheepy.lily.game.vulkan.descriptor.IDescriptor;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.compute.IComputer;
import org.sheepy.lily.game.vulkan.shader.Shader;

public class PixelComputer implements IComputer
{
	private static final String SHADER_LOCATION = "test/vulkan/gameoflife/life2pixel.comp.spv";

	private LogicalDevice logicalDevice;

	private int width;
	private int height;

	private Shader boardToImageShader;

	private List<IDescriptor> descriptors;

	public PixelComputer(LogicalDevice logicalDevice, BoardBuffer board, BoardImage image)
	{
		this.logicalDevice = logicalDevice;

		descriptors = new ArrayList<>();
		descriptors.add(board);
		descriptors.add(image);

		descriptors = Collections.unmodifiableList(descriptors);

		this.width = board.getWidth();
		this.height = board.getHeight();
	}

	@Override
	public void load()
	{
		boardToImageShader = new Shader(logicalDevice, SHADER_LOCATION,
				VK_SHADER_STAGE_COMPUTE_BIT);
	}

	@Override
	public void free()
	{
		boardToImageShader.destroy();
	}

	@Override
	public Shader getShader()
	{
		return boardToImageShader;
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

	@Override
	public List<IDescriptor> getDescriptors()
	{
		return descriptors;
	}

}
