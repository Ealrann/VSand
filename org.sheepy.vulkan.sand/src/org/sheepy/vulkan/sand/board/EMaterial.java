package org.sheepy.vulkan.sand.board;

public enum EMaterial
{
	Void(0, false, 0, 0, 0f, 0f, 0f),
	Sand(1, false, 2, 2, 1f, 1f, 0.255f);

	public final int id;
	public final boolean isStatic;
	public final int density;
	public final int runoff;
	public final float r;
	public final float g;
	public final float b;

	private EMaterial(int id, boolean isStatic, int density, int runoff, float r, float g, float b)
	{
		this.id = id;
		this.isStatic = isStatic;
		this.density = density;
		this.runoff = runoff;
		this.r = r;
		this.g = g;
		this.b = b;
	}
}
