package org.sheepy.vulkan.nuklear.pipeline.draw;

import static org.lwjgl.nuklear.Nuklear.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.nuklear.NkBuffer;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkDrawCommand;

public class DrawRecorder
{
	private final NkContext nkContext;

	private int previousDrawedIndexes = 0;
	private final boolean debug;
	private final List<DrawCommandData> datas = new ArrayList<>();

	public DrawRecorder(NkContext nkContext, boolean debug)
	{
		this.nkContext = nkContext;
		this.debug = debug;
	}

	public void prepare(NkBuffer cmds)
	{
		int drawedIndexes = 0;

		datas.clear();
		for (NkDrawCommand cmd = nk__draw_begin(nkContext, cmds); cmd != null; cmd = nk__draw_next(
				cmd, cmds, nkContext))
		{
			int elemCount = cmd.elem_count();
			if (elemCount <= 0)
			{
				continue;
			}
			else
			{
				drawedIndexes += elemCount;
				datas.add(new DrawCommandData(cmd));
			}
		}

		if (debug && previousDrawedIndexes != drawedIndexes)
		{
			System.out.println("Index count:" + drawedIndexes);
			previousDrawedIndexes = drawedIndexes;
		}

		clear();
	}

	public void clear()
	{
		nk_clear(nkContext);
	}

	public List<DrawCommandData> getDrawCommands()
	{
		return datas;
	}
}
