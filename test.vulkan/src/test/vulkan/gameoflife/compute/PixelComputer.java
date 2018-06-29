package test.vulkan.gameoflife.compute;

import static org.lwjgl.vulkan.VK10.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.CommandPool;
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

	private BoardImage image;
	private Shader boardToImageShader;

	private List<IDescriptor> descriptors;

	public PixelComputer(LogicalDevice logicalDevice, CommandPool commandPool, Board board)
	{
		this.logicalDevice = logicalDevice;

		image = new BoardImage(logicalDevice);

		descriptors = new ArrayList<>();
		descriptors.add(board);
		descriptors.add(image);

		descriptors = Collections.unmodifiableList(descriptors);

		this.width = board.getWidth();
		this.height = board.getHeight();

		image.load(commandPool, logicalDevice.getQueueManager().getComputeQueue(), width, height,
				VK_FORMAT_R8G8B8A8_UNORM);
	}

	public void load()
	{
		boardToImageShader = new Shader(logicalDevice, SHADER_LOCATION,
				VK_SHADER_STAGE_COMPUTE_BIT);
	}

	@Override
	public void free()
	{
		boardToImageShader.destroy();
		image.free();
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

	public Image getImage()
	{
		return image.getImage();
	}

	@Override
	public List<IDescriptor> getDescriptors()
	{
		return descriptors;
	}

}
