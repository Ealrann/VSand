package org.sheepy.vulkan.sand.board;

public enum ETransformation
{
	WATER__PLANT(EMaterial.Water, EMaterial.Plant, EMaterial.Plant, 400, false),
	VOID__FIRE(EMaterial.Void, EMaterial.Fire, EMaterial.Fire, 40, false),
	FIRE__ALL(EMaterial.Fire, null, EMaterial.Void, 240, false),

	CONCRETE__ALL(EMaterial.Concrete, null, EMaterial.Wall, 10, true),
	
	FIRESTATIC__VOID(EMaterial.FireStatic, null, EMaterial.Void, 20, false),
	FIRESTATIC__FIRE(EMaterial.FireStatic, EMaterial.Void, EMaterial.Fire, 120, false),
	
	PLANT__FIRE(EMaterial.Plant, EMaterial.Fire, EMaterial.FireStatic, 240, false),
	PLANT__FIRESTATIC(EMaterial.Plant, EMaterial.FireStatic, EMaterial.FireStatic, 240, false),
	
	WAX__FIRE(EMaterial.Wax, EMaterial.Fire, EMaterial.WaxFire, 200, false),
	WAX__WAXFIRE(EMaterial.Wax, EMaterial.WaxFire, EMaterial.WaxFire, 40, false),
	WAX__FINALFIRE(EMaterial.Wax, EMaterial.FinalWaxFire, EMaterial.WaxFire, 1000, false),
	WAXFIRE__FINALFIRE(EMaterial.WaxFire, null, EMaterial.FinalWaxFire, 10, false),
	WAXFIRE__VOID(EMaterial.WaxFire, EMaterial.Void, EMaterial.Fire, 40, false),
	FINALFIRE__LIQUIDWAX(EMaterial.FinalWaxFire, null, EMaterial.LiquidWax, 50, false),
	LIQUIDWAX__WAX(EMaterial.LiquidWax, null, EMaterial.Wax, 100, true)
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
			value = value | (transfo.probability << 16);
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
