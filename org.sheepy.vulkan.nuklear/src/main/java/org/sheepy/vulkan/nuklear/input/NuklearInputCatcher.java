package org.sheepy.vulkan.nuklear.input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nuklear.Nuklear.*;

import java.util.List;

import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkMouse;
import org.lwjgl.nuklear.NkVec2;
import org.sheepy.lily.core.api.input.event.CharEvent;
import org.sheepy.lily.core.api.input.event.IInputEvent;
import org.sheepy.lily.core.api.input.event.KeyEvent;
import org.sheepy.lily.core.api.input.event.MouseButtonEvent;
import org.sheepy.lily.core.api.input.event.MouseLocationEvent;
import org.sheepy.lily.core.api.input.event.ScrollEvent;
import org.sheepy.lily.core.api.types.SVector2f;
import org.sheepy.lily.core.model.types.EKeyState;
import org.sheepy.lily.vulkan.common.input.IInputCatcher;
import org.sheepy.vulkan.nuklear.pipeline.NuklearPipelineAdapter;

public class NuklearInputCatcher implements IInputCatcher
{
	private static final NkVec2 scroll = NkVec2.create();

	private NkContext nkContext;
	private long window;
	private NuklearPipelineAdapter pipelineAdapter;

	public void configure(	NkContext nkContext,
							long windowId,
							NuklearPipelineAdapter pipelineAdapter)
	{
		this.nkContext = nkContext;
		this.window = windowId;
		this.pipelineAdapter = pipelineAdapter;
	}

	@Override
	public void onCharEvent(CharEvent event)
	{
		nk_input_unicode(nkContext, event.codepoint);
	}

	@Override
	public void onKeyEvent(KeyEvent event)
	{
		boolean press = event.state == EKeyState.PRESSED;
		switch (event.key)
		{
		case GLFW_KEY_ESCAPE:
			break;
		case GLFW_KEY_DELETE:
			nk_input_key(nkContext, NK_KEY_DEL, press);
			break;
		case GLFW_KEY_ENTER:
			nk_input_key(nkContext, NK_KEY_ENTER, press);
			break;
		case GLFW_KEY_TAB:
			nk_input_key(nkContext, NK_KEY_TAB, press);
			break;
		case GLFW_KEY_BACKSPACE:
			nk_input_key(nkContext, NK_KEY_BACKSPACE, press);
			break;
		case GLFW_KEY_UP:
			nk_input_key(nkContext, NK_KEY_UP, press);
			break;
		case GLFW_KEY_DOWN:
			nk_input_key(nkContext, NK_KEY_DOWN, press);
			break;
		case GLFW_KEY_HOME:
			nk_input_key(nkContext, NK_KEY_TEXT_START, press);
			nk_input_key(nkContext, NK_KEY_SCROLL_START, press);
			break;
		case GLFW_KEY_END:
			nk_input_key(nkContext, NK_KEY_TEXT_END, press);
			nk_input_key(nkContext, NK_KEY_SCROLL_END, press);
			break;
		case GLFW_KEY_PAGE_DOWN:
			nk_input_key(nkContext, NK_KEY_SCROLL_DOWN, press);
			break;
		case GLFW_KEY_PAGE_UP:
			nk_input_key(nkContext, NK_KEY_SCROLL_UP, press);
			break;
		case GLFW_KEY_LEFT_SHIFT:
		case GLFW_KEY_RIGHT_SHIFT:
			nk_input_key(nkContext, NK_KEY_SHIFT, press);
			break;
		case GLFW_KEY_LEFT_CONTROL:
		case GLFW_KEY_RIGHT_CONTROL:
			if (press)
			{
				nk_input_key(nkContext, NK_KEY_COPY, glfwGetKey(window, GLFW_KEY_C) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_PASTE, glfwGetKey(window, GLFW_KEY_P) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_CUT, glfwGetKey(window, GLFW_KEY_X) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_TEXT_UNDO,
						glfwGetKey(window, GLFW_KEY_Z) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_TEXT_REDO,
						glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_TEXT_WORD_LEFT,
						glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_TEXT_WORD_RIGHT,
						glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_TEXT_LINE_START,
						glfwGetKey(window, GLFW_KEY_B) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_TEXT_LINE_END,
						glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS);
			}
			else
			{
				nk_input_key(nkContext, NK_KEY_LEFT,
						glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_RIGHT,
						glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS);
				nk_input_key(nkContext, NK_KEY_COPY, false);
				nk_input_key(nkContext, NK_KEY_PASTE, false);
				nk_input_key(nkContext, NK_KEY_CUT, false);
				nk_input_key(nkContext, NK_KEY_SHIFT, false);
			}
			break;
		}
	}

	@Override
	public void onMouseClickEvent(SVector2f location, MouseButtonEvent event)
	{
		int x = (int) location.getX();
		int y = (int) location.getY();

		int nkButton = -1;
		switch (event.button)
		{
		case RIGHT:
			nkButton = NK_BUTTON_RIGHT;
			break;
		case MIDDLE:
			nkButton = NK_BUTTON_MIDDLE;
			break;
		case LEFT:
			nkButton = NK_BUTTON_LEFT;
			break;
		default:
			nkButton = -1;
		}

		if (nkButton != -1)
		{
			nk_input_button(nkContext, nkButton, x, y, event.pressed);
		}
	}

	@Override
	public void onMouseLocationEvent(MouseLocationEvent event)
	{
		nk_input_motion(nkContext, (int) event.x, (int) event.y);
	}

	@Override
	public void onScrollEvent(ScrollEvent event)
	{
		scroll.x(event.xOffset);
		scroll.y(event.yOffset);
		nk_input_scroll(nkContext, scroll);
	}

	@Override
	public void startCatch()
	{
		nk_input_begin(nkContext);
	}

	@Override
	public boolean hasCaughtInputs(List<IInputEvent> events)
	{
		NkMouse mouse = nkContext.input().mouse();
		if (mouse.grab())
		{
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		}
		else if (mouse.grabbed())
		{
			float prevX = mouse.prev().x();
			float prevY = mouse.prev().y();
			glfwSetCursorPos(window, prevX, prevY);
			mouse.pos().x(prevX);
			mouse.pos().y(prevY);
		}
		else if (mouse.ungrab())
		{
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		}

		nk_input_end(nkContext);

		pipelineAdapter.layout(events);

		boolean res = false;

		if (nk_item_is_any_active(nkContext))
		{
			for (IInputEvent event : events)
			{
				if (event instanceof MouseButtonEvent && ((MouseButtonEvent) event).pressed == true)
				{
					res = true;
				}
			}
		}

		return res;
	}
}
