package org.sheepy.vulkan.sand.graphics;

import java.util.ArrayList;
import java.util.List;

import org.sheepy.vulkan.imgui.UIDescriptor;
import org.sheepy.vulkan.sand.board.EMaterial;
import org.sheepy.vulkan.sand.board.EShapeSize;
import org.sheepy.vulkan.window.Window;

import glm_.vec2.Vec2;
import glm_.vec4.Vec4;
import imgui.Cond;
import imgui.IO;
import imgui.ImGui;
import imgui.WindowFlag;

public class SandUIDescriptor extends UIDescriptor
{
	private final static int CONFIG_WINDOW_WIDTH = 200;
	private final static int CONFIG_WINDOW_HEIGHT = 400;
	
	private EMaterial material = EMaterial.Sand;
	private int[] currentSize = {EShapeSize.ES6.ordinal()};
	private static final List<String> sizeList = new ArrayList<>();
	
	public SandUIDescriptor(Window window)
	{
		super(window);
		
		for(EShapeSize size : EShapeSize.values())
		{
			sizeList.add(String.valueOf(size.getSize()));
		}
	}
	
	@Override
	public void newFrame(ImGui imgui)
	{
		IO io = imgui.getIo();
		
		imgui.setNextWindowPos(new Vec2(io.getDisplaySize().getX() - CONFIG_WINDOW_WIDTH, 0), Cond.Always, new Vec2(0, 0));
		imgui.setNextWindowSize(new Vec2(CONFIG_WINDOW_WIDTH, CONFIG_WINDOW_HEIGHT), Cond.Always);
		imgui.begin("Configuration", new boolean[1],
				WindowFlag.NoMove.getI() | WindowFlag.NoResize.getI());


		imgui.textUnformatted("Brush Size:", 0);
		imgui.combo("", currentSize, sizeList, 16);
		imgui.verticalSeparator();
		imgui.textUnformatted("Materials:", 0);
		for (EMaterial m : EMaterial.values())
		{
			if (imgui.colorButton(m.name(), new Vec4(m.r, m.g, m.b, 1f), 0,
					new Vec2(100, 25)))
			{
				material = m;
			}
		}


		imgui.end();
	}

	public EMaterial getMaterial()
	{
		return material;
	}

	public EShapeSize getBrushSize()
	{
		return EShapeSize.values()[currentSize[0]];
	}
}
