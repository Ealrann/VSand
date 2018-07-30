package org.sheepy.vulkan.sand.board;

public enum EMaterial
{

	Void(false, 0, 60, 0, 0, 0, 0),
	Sand(false, 5, 1, 2, 244, 244, 67),
	Wall(true, 20, 0, 1, 153, 153, 143),
	Concrete(false, 20, 4, 1, 94, 94, 85),
	Water(false, 4, 30, 1, 67, 67, 244),
	Vapor(false, -2, 1, 1, 159, 159, 249),

	Fire(false, -1, 0, 1, 244, 67, 67),
	FireStatic(true, 0, 0, 1, 244, 38, 38, false),
	FireFinal(true, 0, 0, 1, 255, 0, 0, false),

	Plant(true, 15, 0, 1, 67, 244, 67),

	Wax(true, 20, 0, 1, 254, 254, 231),
	LiquidWax(false, 1, 1, 1, 231, 254, 254, false),

	Lava(false, 3, 5, 1, 244, 111, 67),
	LavaBoiling(false, 2, 6, 1, 244, 244, 67, false),

	;

	public static final int MAX_MATERIAL_NUMBER = 32;

	public final boolean isStatic;
	public final int density;
	public final int runoff;
	public final int viscosity; // not yet implemented
	public final float r;
	public final float g;
	public final float b;

	public final boolean userFriendly;

	private EMaterial(boolean isStatic, int density, int runoff, int viscosity, int r, int g, int b,
			boolean userFriendly)
	{
		this.isStatic = isStatic;
		this.density = density;
		this.runoff = runoff;
		this.viscosity = viscosity;
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.userFriendly = userFriendly;
	}

	private EMaterial(boolean isStatic, int density, int runoff, int viscosity, int r, int g, int b)
	{
		this(isStatic, density, runoff, viscosity, r, g, b, true);
	}
}
