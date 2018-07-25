package org.sheepy.vulkan.sand;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_R8G8B8A8_UNORM;

import java.util.Collections;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.sheepy.vulkan.VulkanApplication;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.board.EShape;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.graphics.SandUIDescriptor;
import org.sheepy.vulkan.sand.pipelinepool.BoardPipelinePool;
import org.sheepy.vulkan.sand.pipelinepool.RenderPipelinePool;
import org.sheepy.vulkan.sand.util.FPSCounter;

import imgui.IO;
import imgui.ImGui;

public class SandApplication extends VulkanApplication
{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	// private static final int WIDTH = 2048;
	// private static final int HEIGHT = 1152;

	private static final float ZOOM = 1f;

	// in update per frame
	private boolean pause = false;
	private boolean next = false;

	private BoardPipelinePool boardPool;
	private RenderPipelinePool renderPool;
	private BoardModifications boardModifications;

	private boolean firstDraw = false;
	private boolean drawEnabled = false;
	private SandUIDescriptor uiDescriptor;

	private BoardImage image;

	private FPSCounter fpsCounter = new FPSCounter();

	public SandApplication()
	{
		super(WIDTH, HEIGHT);

		LogicalDevice logicalDevice = initLogicalDevice();

		uiDescriptor = new SandUIDescriptor(window);

		window.setMouseButtonCallback(new GLFWMouseButtonCallback()
		{
			@Override
			public void invoke(long window, int button, int action, int mods)
			{
				// Update imGui
				IO io = ImGui.INSTANCE.getIo();

				switch (button)
				{
				case GLFW_MOUSE_BUTTON_LEFT:
					io.getMouseDown()[0] = action == GLFW_PRESS;
					if (io.getWantCaptureMouse() == false && action == GLFW_PRESS)
					{
						if (drawEnabled == false)
						{
							firstDraw = true;
						}
						drawEnabled = true;
					}
					else
					{
						drawEnabled = false;
					}
					break;
				case GLFW_MOUSE_BUTTON_RIGHT:
					io.getMouseDown()[1] = action == GLFW_PRESS;
					break;
				case GLFW_MOUSE_BUTTON_MIDDLE:
					io.getMouseDown()[2] = action == GLFW_PRESS;
					break;
				}

			}
		});

		window.setKeyCallback(new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				if ((key == GLFW_KEY_P || key == GLFW_KEY_SPACE) && action == GLFW_PRESS)
				{
					pause = !pause;
				}
				if (key == GLFW_KEY_N && action == GLFW_PRESS)
				{
					pause = false;
					next = true;
				}
			}
		});

		boardModifications = new BoardModifications(logicalDevice, ZOOM);

		image = new BoardImage(logicalDevice);
		image.load((int) (WIDTH * ZOOM), (int) (HEIGHT * ZOOM), VK_FORMAT_R8G8B8A8_UNORM);

		boardPool = new BoardPipelinePool(logicalDevice, boardModifications, image);

		renderPool = new RenderPipelinePool(logicalDevice, image.getImage(), uiDescriptor,
				Collections.emptyList());

		attachPipelinePool(boardPool);
		attachPipelinePool(renderPool);

	}

	@Override
	public void cleanup()
	{
		image.free();
		boardModifications.free();

		super.cleanup();
	}

	@Override
	public void mainLoop()
	{
		super.mainLoop();
	}

	@Override
	public void drawFrame()
	{
		if (DEBUG) fpsCounter.step();

		double[] cursorPosition = SandApplication.this.window.getCursorPosition();
		IO io = ImGui.INSTANCE.getIo();
		io.getMousePos().setX((int) cursorPosition[0]);
		io.getMousePos().setY((int) cursorPosition[1]);

		if (pause != true) boardPool.setSpeed(uiDescriptor.speed);
		else boardPool.setSpeed(0);

		if (drawEnabled)
		{
			if (firstDraw)
			{
				boardModifications.pushModification(EShape.Circle, uiDescriptor.getBrushSize(),
						(int) cursorPosition[0], (int) cursorPosition[1],
						uiDescriptor.getMaterial());
				firstDraw = false;
			}
			else
			{
				boardModifications.pushModification(EShape.Line, uiDescriptor.getBrushSize(),
						(int) cursorPosition[0], (int) cursorPosition[1],
						uiDescriptor.getMaterial());
			}
		}

		boardPool.execute();

		if (next == true)
		{
			next = false;
			boardPool.setSpeed(0);
			pause = true;
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
