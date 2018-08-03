package org.sheepy.vulkan.sand;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_R8G8B8A8_UNORM;

import java.util.Collections;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.sheepy.vulkan.VulkanApplication;
import org.sheepy.vulkan.device.LogicalDevice;
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
	private static final String TITLE = "VSand";
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;

	private static final float ZOOM = 1f;

	// in update per frame
	private boolean pause = false;
	private boolean next = false;

	private BoardPipelinePool boardPool;
	private RenderPipelinePool renderPool;

	private boolean firstDrawLeft = false;
	private boolean drawEnabledLeft = false;
	private SandUIDescriptor uiDescriptor;

	private BoardImage image;

	private FPSCounter fpsCounter = new FPSCounter();

	public SandApplication()
	{
		super(TITLE, WIDTH, HEIGHT);

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
						if (drawEnabledLeft == false)
						{
							firstDrawLeft = true;
						}
						drawEnabledLeft = true;
					}
					else
					{
						drawEnabledLeft = false;
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

		image = new BoardImage(logicalDevice);
		image.load((int) (WIDTH * ZOOM), (int) (HEIGHT * ZOOM), VK_FORMAT_R8G8B8A8_UNORM);

		boardPool = new BoardPipelinePool(logicalDevice, image, ZOOM);

		renderPool = new RenderPipelinePool(logicalDevice, image.getImage(), uiDescriptor,
				Collections.emptyList());

		attachPipelinePool(boardPool);
		attachPipelinePool(renderPool);

	}

	@Override
	public void cleanup()
	{
		image.free();

		super.cleanup();
	}

	private void computeBoardMousePosition(double[] mousePos)
	{
		if (window.getSurface().width != WIDTH && window.getSurface().height != HEIGHT)
		{
			mousePos[0] *= (float) WIDTH / window.getSurface().width;
			mousePos[1] *= (float) HEIGHT / window.getSurface().height;
		}
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

		// Main draw
		if (drawEnabledLeft)
		{
			draw(cursorPosition, uiDescriptor.getMaterial(), firstDrawLeft);
			firstDrawLeft = false;
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

	private void draw(double[] cursorPosition, EMaterial material, boolean firstDraw)
	{
		computeBoardMousePosition(cursorPosition);
		if (firstDraw)
		{
			boardPool.pushModification(EShape.Circle, uiDescriptor.getBrushSize(),
					(int) cursorPosition[0], (int) cursorPosition[1], material);
		}
		else
		{
			boardPool.pushModification(EShape.Line, uiDescriptor.getBrushSize(),
					(int) cursorPosition[0], (int) cursorPosition[1], material);
		}
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
