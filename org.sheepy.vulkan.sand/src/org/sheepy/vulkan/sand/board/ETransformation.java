package org.sheepy.vulkan.sand.board;

public enum ETransformation
{
	WATER_TO_PLANT(EMaterial.Water, EMaterial.Plant, EMaterial.Plant, 1f);

	public final EMaterial reactant;
	public final EMaterial catalyst;
	public final EMaterial target;
	public final float probability;

	private ETransformation(EMaterial reactant, EMaterial catalyst, EMaterial target,
			float probability)
	{
		this.reactant = reactant;
		this.catalyst = catalyst;
		this.target = target;
		this.probability = probability;
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
			res[transfo.catalyst.id * EMaterial.MAX_MATERIAL_NUMBER
					+ transfo.reactant.id] = transfo.target.id;
		}

		return res;
	}
}
