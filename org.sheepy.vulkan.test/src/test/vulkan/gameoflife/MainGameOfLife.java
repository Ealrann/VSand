package test.vulkan.gameoflife;

import org.sheepy.lily.game.vulkan.VulkanApplication;

public class MainGameOfLife
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

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
