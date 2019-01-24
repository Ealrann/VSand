package org.sheepy.vsand.adapter.drawer;

import static org.lwjgl.nuklear.Nuklear.*;

import java.nio.ByteBuffer;

import org.lwjgl.nuklear.NkColor;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkRect;
import org.sheepy.vsand.adapter.MaterialSelectorPanelAdapter.LineData;
import org.sheepy.vsand.model.Material;

public class MaterialDrawer
{
	private final int lineHeight;

	private byte defaultBorderColorR;
	private byte defaultBorderColorG;
	private byte defaultBorderColorB;
	private byte defaultBorderColorA;
	private NkColor borderColor;

	private float defaultPaddingX;
	private float defaultPaddingY;

	private final byte r;
	private final byte g;
	private final byte b;

	public MaterialDrawer(int lineHeight, int r, int g, int b)
	{
		this.lineHeight = lineHeight;
		this.r = (byte) r;
		this.g = (byte) g;
		this.b = (byte) b;
	}

	public void prepare(NkContext nkContext)
	{
		borderColor = nkContext.style().window().border_color();
		defaultBorderColorR = borderColor.r();
		defaultBorderColorG = borderColor.g();
		defaultBorderColorB = borderColor.b();
		defaultBorderColorA = borderColor.a();

		defaultPaddingX = nkContext.style().window().padding().x();
		defaultPaddingY = nkContext.style().window().padding().y();
	}

	public boolean draw(NkContext nkContext,
						LineData data,
						Material main,
						ByteBuffer id,
						NkRect rect)
	{
		boolean res = false;
		nkContext.style().window().padding().x(0);
		nkContext.style().window().padding().y(0);

		int style = NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_BORDER;

		if (main == data.material)
		{
			borderColor.r(r);
			borderColor.g(g);
			borderColor.b(b);
			borderColor.a((byte) 255);
		}
		else
		{
			borderColor.a((byte) 0);
		}

		if (nk_begin(nkContext, id, rect, style))
		{
			nk_layout_row_dynamic(nkContext, lineHeight - 5, 1);
			if (nk_button_color(nkContext, data.color))
			{
				res = true;
			}
		}
		nk_end(nkContext);

		if (main == data.material)
		{
			borderColor.r(defaultBorderColorR);
			borderColor.g(defaultBorderColorG);
			borderColor.b(defaultBorderColorB);
			borderColor.a((byte) 0);
		}

		nkContext.style().window().padding().x(defaultPaddingX);
		nkContext.style().window().padding().y(defaultPaddingY);

		return res;
	}

	public void finish()
	{
		borderColor.a(defaultBorderColorA);
	}
}
