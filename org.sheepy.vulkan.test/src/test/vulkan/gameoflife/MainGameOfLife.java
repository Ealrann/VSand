package test.vulkan.gameoflife;

import org.sheepy.vulkan.VulkanApplication;

public class MainGameOfLife
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static void main(String[] args)
	{
		VulkanApplication app = new GameOfLifeApplication(WIDTH, HEIGHT);

		try
		{
			app.run();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
