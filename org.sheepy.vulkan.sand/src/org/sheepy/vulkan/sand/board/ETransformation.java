package org.sheepy.vulkan.sand.board;

public enum ETransformation
{
	WATER__PLANT(EMaterial.Water, EMaterial.Plant, EMaterial.Plant, 800, false),
	VOID__FIRE(EMaterial.Void, EMaterial.Fire, EMaterial.Fire, 40, false),
	FIRE__ALL(EMaterial.Fire, null, EMaterial.Void, 300, false),
	FIRE__WATER(EMaterial.Fire, EMaterial.Water, EMaterial.Void, 1000, false),
	FIRESTATIC__WATER(EMaterial.FireStatic, EMaterial.Water, EMaterial.Void, 1000, false),

	CONCRETE__ALL(EMaterial.Concrete, null, EMaterial.Wall, 10, true),
	
	WATER__FIRE(EMaterial.Water, EMaterial.Fire, EMaterial.Vapor, 20, false),
	VAPOR__ALL(EMaterial.Vapor, null, EMaterial.Water, 1, false),
	
	FIRESTATIC__FINALFIRE(EMaterial.FireStatic, null, EMaterial.FireFinal, 100, false),
	FIREFINAL__ALL(EMaterial.FireFinal, null, EMaterial.Void, 500, false),
	FIREFINAL__VOID(EMaterial.FireFinal, EMaterial.Void, EMaterial.Fire, 500, false),
	
	PLANT__FIRE(EMaterial.Plant, EMaterial.Fire, EMaterial.FireStatic, 240, false),
	PLANT__FIRESTATIC(EMaterial.Plant, EMaterial.FireStatic, EMaterial.FireStatic, 240, false),
	PLANT__FIREFINAL(EMaterial.Plant, EMaterial.FireFinal, EMaterial.FireStatic, 1000, false),
	
	WAX__FIRE(EMaterial.Wax, EMaterial.Fire, EMaterial.FireStatic, 200, false),
	WAX__WAXFIRE(EMaterial.Wax, EMaterial.FireStatic, EMaterial.FireStatic, 15, false),
	WAX__LIQUIDWAX(EMaterial.FireFinal, EMaterial.Wax, EMaterial.LiquidWax, 650, false),
	WAX__FINALFIRE(EMaterial.Wax, EMaterial.FireFinal, EMaterial.FireStatic, 1000, false),
	LIQUIDWAX__ALL(EMaterial.LiquidWax, null, EMaterial.Wax, 100, true),
	

	LAVA__LAVA(EMaterial.Lava, null, EMaterial.LavaBoiling, 5, false),
	LAVABOILING__ALL(EMaterial.LavaBoiling, null, EMaterial.Lava, 50, false),
	VOID__LAVABOILING(EMaterial.Void, EMaterial.LavaBoiling, EMaterial.Fire, 40, false),
	LAVABOILING__LAVA(EMaterial.LavaBoiling, EMaterial.Lava, EMaterial.Lava, 40, false),

	WAX__LAVA(EMaterial.Wax, EMaterial.Lava, EMaterial.Fire, 250, false),
	LIQUIDWAX__LAVA(EMaterial.LiquidWax, EMaterial.Lava, EMaterial.Fire, 250, false),
	PLANT__LAVA(EMaterial.Plant, EMaterial.Lava, EMaterial.Fire, 400, false),
	SAND__LAVA(EMaterial.Sand, EMaterial.Lava, EMaterial.Fire, 200, false),
	SAND__LAVABOILING(EMaterial.Sand, EMaterial.LavaBoiling, EMaterial.Lava, 500, false),
	DIRT__LAVA(EMaterial.Dirt, EMaterial.Lava, EMaterial.Void, 200, false),
	DIRT__LAVABOILING(EMaterial.Dirt, EMaterial.LavaBoiling, EMaterial.Lava, 200, false),
	WALL__LAVA(EMaterial.Wall, EMaterial.Lava, EMaterial.Fire, 4, false),
	CONCRETE__LAVA(EMaterial.Concrete, EMaterial.Lava, EMaterial.Fire, 5, false),
	VAPOR__LAVA(EMaterial.Vapor, EMaterial.Lava, EMaterial.Fire, 100, false),

	WATER__LAVA(EMaterial.Water, EMaterial.Lava, EMaterial.Vapor, 400, false),
	WATER__LAVABOILING(EMaterial.Water, EMaterial.LavaBoiling, EMaterial.Vapor, 800, false),
	LAVA_BOILING__WATER(EMaterial.LavaBoiling, EMaterial.Water, EMaterial.Void, 5, false),
	LAVA__WATER(EMaterial.Lava, EMaterial.Water, EMaterial.Vapor, 25, false),
	;

	public final EMaterial reactant;
	public final EMaterial catalyst;
	public final EMaterial target;

	// in percent [0-1000]
	public final int probability;
	public final boolean staticTransformation;

	private ETransformation(EMaterial reactant, EMaterial catalyst, EMaterial target,
			int probability, boolean staticTransformation)
	{
		this.reactant = reactant;
		this.catalyst = catalyst;
		this.target = target;
		this.probability = probability;
		this.staticTransformation = staticTransformation;
	}

	public static int[] toArray()
	{
		int[] res = new int[EMaterial.MAX_MATERIAL_NUMBER * EMaterial.MAX_MATERIAL_NUMBER];
		for (int i = 0; i < res.length; i++)
		{
			res[i] = -1;
		}

		for (ETransformation transfo : values())
		{
			int value = transfo.target.ordinal();
			value |= transfo.probability << 16;
			value |= (transfo.staticTransformation ? 1 : 0) << 30;

			if (transfo.catalyst != null)
			{
				res[transfo.catalyst.ordinal() * EMaterial.MAX_MATERIAL_NUMBER
						+ transfo.reactant.ordinal()] = value;
			}
			else
			{
				// null catalyst means: react with everything.
				for (int i = 0; i < EMaterial.MAX_MATERIAL_NUMBER; i++)
				{
					res[i * EMaterial.MAX_MATERIAL_NUMBER + transfo.reactant.ordinal()] = value;
				}
			}
		}

		return res;
	}
}
