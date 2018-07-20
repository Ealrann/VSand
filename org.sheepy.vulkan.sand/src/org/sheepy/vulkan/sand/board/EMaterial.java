package org.sheepy.vulkan.sand.board;

public enum EMaterial
{
	Void(0, false, 0, 0, 0, 0, 0, 0),
	Sand(1, false, 2, 1, 2, 244, 244, 67),
	Wall(2, true, 20, 0, 1, 153, 153, 143),
	Water(3, false, 1, 30, 1, 67, 67, 244),
	Plant(4, true, 15, 0, 1, 67, 244, 67);

	public final int id;
	public final boolean isStatic;
	public final int density;
	public final int runoff;
	public final int viscosity;
	public final float r;
	public final float g;
	public final float b;

	private EMaterial(int id, boolean isStatic, int density, int runoff, int viscosity, int r,
			int g, int b)
	{
		this.id = id;
		this.isStatic = isStatic;
		this.density = density;
		this.runoff = runoff;
		this.viscosity = viscosity;
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
	}
}
