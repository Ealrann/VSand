package org.sheepy.vulkan.imgui;

import org.sheepy.vulkan.window.Window;

import glm_.vec4.Vec4;
import imgui.Col;
import imgui.ImGui;
import imgui.Style;

public abstract class UIDescriptor
{
	private Window window;
	
	protected boolean dirty = false;

	public UIDescriptor(Window window)
	{
		this.window = window;
	}

	public void configureStyle(Style style)
	{
		style.getColors();

		style.getColors().set(Col.TitleBg.getI(), new Vec4(1.0f, 0.0f, 0.0f, 0.6f));
		style.getColors().set(Col.TitleBgActive.getI(), new Vec4(1.0f, 0.0f, 0.0f, 0.8f));
		style.getColors().set(Col.MenuBarBg.getI(), new Vec4(1.0f, 0.0f, 0.0f, 0.4f));
		style.getColors().set(Col.Header.getI(), new Vec4(1.0f, 0.0f, 0.0f, 0.4f));
		style.getColors().set(Col.CheckMark.getI(), new Vec4(0.0f, 1.0f, 0.0f, 1.0f));
	}
	
	public abstract boolean newFrame(ImGui imgui);
	
	public int[] getSize()
	{
		return window.getSurface().size;
	}
	
	public boolean isDirty()
	{
		return dirty;
	}

	public void setDirty(boolean dirty)
	{
		this.dirty = dirty;
	}
}
