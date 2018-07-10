package org.sheepy.vulkan.sand;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_R8G8B8A8_UNORM;

import java.util.Collections;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.sheepy.vulkan.VulkanApplication;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.board.EMaterial;
import org.sheepy.vulkan.sand.board.EShape;
import org.sheepy.vulkan.sand.board.EShapeSize;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.pipelinepool.BoardPipelinePool;
import org.sheepy.vulkan.sand.pipelinepool.RenderPipelinePool;

public class SandApplication extends VulkanApplication
{
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 576;
	// private static final int WIDTH = 2048;
	// private static final int HEIGHT = 1152;

	private static final float ZOOM = 1f;

	// in update per frame
	private int speed = 1;
	private boolean pause = false;
	private boolean next = false;
	private int indexMaterial = 1;

	private BoardPipelinePool boardPool;
	private RenderPipelinePool renderPool;
	private BoardModifications boardModifications;

	// private static final int TARGET_FPS = 60;
	// private static final float FRAME_TIME_STEP_MS = (1f / TARGET_FPS) * 1000;

	private boolean drawEnabled = false;

	public SandApplication()
	{
		super(WIDTH, HEIGHT);

		LogicalDevice logicalDevice = initLogicalDevice();

		window.setMouseButtonCallback(new GLFWMouseButtonCallback()
		{
			@Override
			public void invoke(long window, int button, int action, int mods)
			{
				if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS)
				{
					drawEnabled = true;
				}

				if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE)
				{
					drawEnabled = false;
				}
			}
		});

		window.setKeyCallback(new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				if (key == GLFW_KEY_P && action == GLFW_PRESS)
				{
					pause = !pause;
				}
				if (key == GLFW_KEY_T && action == GLFW_PRESS)
				{
					indexMaterial++;
					if (indexMaterial >= EMaterial.values().length)
					{
						indexMaterial = 0;
					}
				}
				if (key == GLFW_KEY_N && action == GLFW_PRESS)
				{
					pause = false;
					speed = 1;
					next = true;
				}
			}
		});

		boardModifications = new BoardModifications(logicalDevice, ZOOM);

		BoardImage image = new BoardImage(logicalDevice);
		image.load((int) (WIDTH * ZOOM), (int) (HEIGHT * ZOOM), VK_FORMAT_R8G8B8A8_UNORM);

		boardPool = new BoardPipelinePool(logicalDevice, boardModifications, image);

		renderPool = new RenderPipelinePool(logicalDevice, image.getImage(),
				Collections.emptyList());

		attachPipelinePool(boardPool);
		attachPipelinePool(renderPool);
	}

	@Override
	public void mainLoop()
	{
		super.mainLoop();
	}

	private int count = 0;

	@Override
	public void drawFrame()
	{

		if (drawEnabled)
		{
			double[] cursorPosition = window.getCursorPosition();
			boardModifications.pushModification(EShape.Circle, EShapeSize.ES6,
					(int) cursorPosition[0], (int) cursorPosition[1],
					EMaterial.values()[indexMaterial]);
		}

		if (count++ == 90)
		{
			boardModifications.pushModification(EShape.Circle, EShapeSize.ES6, 300, 200,
					EMaterial.Sand);
			boardModifications.pushModification(EShape.Square, EShapeSize.ES6, 320, 350,
					EMaterial.Wall);
			System.out.println("Put sand");
		}

		if (pause != true)
		{
			for (int i = 0; i < speed * ZOOM; i++)
			{
				boardPool.execute();
			}

			if (next == true)
			{
				next = false;
				pause = true;
			}
		}

		renderPool.execute();
	}

	public static void main(String[] args)
	{
		VulkanApplication app = new SandApplication();

		try
		{
			app.run();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
