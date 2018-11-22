package org.sheepy.vulkan.nuklear.pipeline.draw;

import org.lwjgl.nuklear.NkDrawCommand;

public class DrawCommandData
{
	private static final float fb_scale_x = 1f;
	private static final float fb_scale_y = 1f;

	public final int xOffset;
	public final int yOffset;
	public final int xExtent;
	public final int yExtent;

	public final long descriptorSetId;
	public final int elemCount;

	public DrawCommandData(NkDrawCommand cmd)
	{
		int elemCount = cmd.elem_count();

		xOffset = (int) (cmd.clip_rect().x() * fb_scale_x);
		yOffset = (int) (cmd.clip_rect().y() * fb_scale_y);
		xExtent = (int) (cmd.clip_rect().w() * fb_scale_x);
		yExtent = (int) (cmd.clip_rect().h() * fb_scale_y);

		long textureId = cmd.texture().ptr();
		descriptorSetId = textureId;
		this.elemCount = elemCount;
	}
}