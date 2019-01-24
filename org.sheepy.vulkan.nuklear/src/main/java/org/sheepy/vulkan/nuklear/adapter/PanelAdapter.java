package org.sheepy.vulkan.nuklear.adapter;

import static org.lwjgl.nuklear.Nuklear.*;

import java.nio.ByteBuffer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.impl.AbstractStatefullAdapter;
import org.sheepy.lily.core.model.presentation.IUIElement;
import org.sheepy.lily.core.model.ui.IControl;
import org.sheepy.lily.core.model.ui.Panel;
import org.sheepy.lily.core.model.ui.UiPackage;
import org.sheepy.lily.vulkan.api.window.IWindowListener;
import org.sheepy.lily.vulkan.api.window.Surface;
import org.sheepy.lily.vulkan.common.ui.UIUtil;
import org.sheepy.lily.vulkan.common.window.Window;

public class PanelAdapter extends AbstractStatefullAdapter implements IUIElementAdapter
{
	private final IWindowListener listener = new IWindowListener()
	{
		@Override
		public void onWindowResize(Surface surface)
		{
			updateLocation(surface);
		}
	};

	private NkRect rect = NkRect.create();

	private Panel panel;
	private ByteBuffer textBuffer;
	private Window window = null;

	private int style;

	@Override
	public void setTarget(Notifier newTarget)
	{
		panel = (Panel) newTarget;
		String name = panel.getName();

		if (name == null)
		{
			name = "";
		}

		style = NK_WINDOW_BORDER | NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_BACKGROUND;
		if (panel.isMinimizable())
		{
			style |= NK_WINDOW_MINIMIZABLE;
		}
		if (panel.isMovable())
		{
			style |= NK_WINDOW_MOVABLE;
		}
		if (panel.isShowTitle())
		{
			style |= NK_WINDOW_TITLE;
		}
		if (panel.isMinimized())
		{
			style |= NK_WINDOW_MINIMIZED;
		}

		textBuffer = MemoryUtil.memUTF8(name);
	}

	@Override
	public void unsetTarget(Notifier oldTarget)
	{
		window.removeListener(listener);

		super.unsetTarget(oldTarget);
	}

	private void updateLocation(Surface surface)
	{
		int width = panel.getWidth();
		int height = panel.getHeight();
		int x = UIUtil.computeXRelative(surface, panel);
		int y = UIUtil.computeYRelative(surface, panel);

		rect.set(x, y, width, height);
	}

	@Override
	public boolean layout(UIContext context, IUIElement control)
	{
		boolean res = false;
		var nkContext = context.nkContext;

		if (window == null)
		{
			window = context.window;
			updateLocation(window.getSurface());
			window.addListener(listener);
		}

		try (MemoryStack stack = MemoryStack.stackPush())
		{
			rect = NkRect.mallocStack(stack);
			int width = panel.getWidth();
			int height = panel.getHeight();
			int x = UIUtil.computeXRelative(window.getSurface(), panel);
			int y = UIUtil.computeYRelative(window.getSurface(), panel);

			if (nk_begin(nkContext, panel.getName(), nk_rect(x, y, width, height, rect), style))
			{
				if (nk_window_is_collapsed(nkContext, textBuffer)
						&& (style & NK_WINDOW_MINIMIZED) != 0)
				{
					style ^= NK_WINDOW_MINIMIZED;
				}

				for (IControl child : panel.getControls())
				{
					var adapter = IUIElementAdapter.adapt(child);
					res |= adapter.layout(context, child);
				}
			}
			nk_end(nkContext);
		}

		return res;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return UiPackage.Literals.PANEL == eClass;
	}
}
