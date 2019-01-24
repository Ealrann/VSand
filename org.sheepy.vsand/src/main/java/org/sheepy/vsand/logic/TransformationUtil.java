package org.sheepy.vsand.logic;

import org.eclipse.emf.common.util.EList;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.Transformation;
import org.sheepy.vsand.model.VSandApplication;

public class TransformationUtil
{
	// WATER__PLANT(EMaterial.Water, EMaterial.Plant, EMaterial.Plant, 800, false),
	// VOID__FIRE(EMaterial.Void, EMaterial.Fire, EMaterial.Fire, 40, false),
	// FIRE__ALL(EMaterial.Fire, null, EMaterial.Void, 300, false),
	// FIRE__WATER(EMaterial.Fire, EMaterial.Water, EMaterial.Void, 1000, false),
	// FIRESTATIC__WATER(EMaterial.FireStatic, EMaterial.Water, EMaterial.Void, 1000, false),
	//
	// CONCRETE__ALL(EMaterial.Concrete, null, EMaterial.Wall, 10, true),
	//
	// WATER__FIRE(EMaterial.Water, EMaterial.Fire, EMaterial.Vapor, 20, false),
	// VAPOR__ALL(EMaterial.Vapor, null, EMaterial.Water, 1, false),
	//
	// WATER__DIRT(EMaterial.Dirt, EMaterial.Water, EMaterial.WetDirt, 20, false),
	// WETDIRT_DIRT(EMaterial.WetDirt, null, EMaterial.Dirt, 20, true),
	// WETDIRT_VOID(EMaterial.WetDirt, EMaterial.Void, EMaterial.Grass, 20, true),
	// WETDIRT_WATER(EMaterial.WetDirt, EMaterial.Water, EMaterial.Grass, 15, true),
	// DIRT_WETDIRT(EMaterial.Dirt, EMaterial.WetDirt, EMaterial.WetDirt, 10, false),
	//
	// FIRESTATIC__FINALFIRE(EMaterial.FireStatic, null, EMaterial.FireFinal, 100, false),
	// FIREFINAL__ALL(EMaterial.FireFinal, null, EMaterial.Void, 500, false),
	// FIREFINAL__VOID(EMaterial.FireFinal, EMaterial.Void, EMaterial.Fire, 500, false),
	//
	// PLANT__FIRE(EMaterial.Plant, EMaterial.Fire, EMaterial.FireStatic, 240, false),
	// PLANT__FIRESTATIC(EMaterial.Plant, EMaterial.FireStatic, EMaterial.FireStatic, 240, false),
	// PLANT__FIREFINAL(EMaterial.Plant, EMaterial.FireFinal, EMaterial.FireStatic, 1000, false),
	//
	// GRASS__FIRE(EMaterial.Grass, EMaterial.Fire, EMaterial.FireStatic, 240, false),
	// GRASS__FIRESTATIC(EMaterial.Grass, EMaterial.FireStatic, EMaterial.FireStatic, 240, false),
	// GRASS__FIREFINAL(EMaterial.Grass, EMaterial.FireFinal, EMaterial.FireStatic, 1000, false),
	// GRASS__WATER(EMaterial.Water, EMaterial.Grass, EMaterial.Grass, 1, false),
	//
	// WAX__FIRE(EMaterial.Wax, EMaterial.Fire, EMaterial.FireStatic, 200, false),
	// WAX__WAXFIRE(EMaterial.Wax, EMaterial.FireStatic, EMaterial.FireStatic, 15, false),
	// WAX__LIQUIDWAX(EMaterial.FireFinal, EMaterial.Wax, EMaterial.LiquidWax, 650, false),
	// WAX__FINALFIRE(EMaterial.Wax, EMaterial.FireFinal, EMaterial.FireStatic, 1000, false),
	// LIQUIDWAX__ALL(EMaterial.LiquidWax, null, EMaterial.Wax, 100, true),
	// LIQUIDWAX__FIRE(EMaterial.LiquidWax, EMaterial.Fire, EMaterial.FireStatic, 1000, true),
	// LIQUIDWAX__WAXFIRE(EMaterial.LiquidWax, EMaterial.FireStatic, EMaterial.FireStatic, 1000,
	// true),
	// LIQUIDWAX__FINALFIRE(EMaterial.LiquidWax, EMaterial.FireFinal, EMaterial.FireStatic, 1000,
	// true),
	//
	// LAVA__LAVA(EMaterial.Lava, null, EMaterial.LavaBoiling, 8, false),
	// LAVABOILING__ALL(EMaterial.LavaBoiling, null, EMaterial.Lava, 50, false),
	// VOID__LAVABOILING(EMaterial.Void, EMaterial.LavaBoiling, EMaterial.Fire, 40, false),
	// LAVABOILING__LAVA(EMaterial.LavaBoiling, EMaterial.Lava, EMaterial.Lava, 40, false),
	//
	// WAX__LAVA(EMaterial.Wax, EMaterial.Lava, EMaterial.Fire, 250, false),
	// LIQUIDWAX__LAVA(EMaterial.LiquidWax, EMaterial.Lava, EMaterial.Fire, 250, false),
	// PLANT__LAVA(EMaterial.Plant, EMaterial.Lava, EMaterial.Fire, 400, false),
	// SAND__LAVA(EMaterial.Sand, EMaterial.Lava, EMaterial.Fire, 200, false),
	// SAND__LAVABOILING(EMaterial.Sand, EMaterial.LavaBoiling, EMaterial.Lava, 500, false),
	// DIRT__LAVA(EMaterial.Dirt, EMaterial.Lava, EMaterial.Void, 200, false),
	// WETDIRT__LAVA(EMaterial.WetDirt, EMaterial.Lava, EMaterial.Vapor, 200, false),
	// GRASS__LAVA(EMaterial.Grass, EMaterial.Lava, EMaterial.Fire, 200, false),
	// DIRT__LAVABOILING(EMaterial.Dirt, EMaterial.LavaBoiling, EMaterial.Lava, 200, false),
	// WALL__LAVA(EMaterial.Wall, EMaterial.Lava, EMaterial.Fire, 2, false),
	// CONCRETE__LAVA(EMaterial.Concrete, EMaterial.Lava, EMaterial.Fire, 5, false),
	// VAPOR__LAVA(EMaterial.Vapor, EMaterial.Lava, EMaterial.Fire, 100, false),
	//
	// WATER__LAVA(EMaterial.Water, EMaterial.Lava, EMaterial.Vapor, 400, false),
	// WATER__LAVABOILING(EMaterial.Water, EMaterial.LavaBoiling, EMaterial.Vapor, 800, false),
	// LAVA_BOILING__WATER(EMaterial.LavaBoiling, EMaterial.Water, EMaterial.Void, 5, false),
	// LAVA__WATER(EMaterial.Lava, EMaterial.Water, EMaterial.Vapor, 25, false),
	// ;

	private static final int PROBABILITY_LOCATION = 16;
	private static final int STATIC_FLAG = 1 << 30;

	public static int[] toArray(VSandApplication application)
	{
		var materials = application.getMaterials();
		var transformations = application.getTransformations();

		int[] res = new int[MaterialUtil.MAX_MATERIAL_NUMBER * MaterialUtil.MAX_MATERIAL_NUMBER];
		for (int i = 0; i < res.length; i++)
		{
			res[i] = -1;
		}

		var materialList = materials.getMaterials();

		for (Transformation transfo : transformations.getTransformations())
		{
			fillTransformation(res, materialList, transfo);
		}

		return res;
	}

	private static void fillTransformation(	int[] res,
											EList<Material> materialList,
											Transformation transfo)
	{
		int value = materialList.indexOf(transfo.getTarget());
		var catalyst = transfo.getCatalyst();
		var reactant = transfo.getReactant();
		int reactantIndex = materialList.indexOf(reactant);

		value |= transfo.getProbability() << PROBABILITY_LOCATION;
		value |= transfo.isIsStaticTransformation() ? STATIC_FLAG : 0;

		if (catalyst != null)
		{
			int catalystIndex = materialList.indexOf(catalyst);
			res[catalystIndex * MaterialUtil.MAX_MATERIAL_NUMBER + reactantIndex] = value;
		}
		else
		{
			// null catalyst means: react with everything.
			for (int i = 0; i < MaterialUtil.MAX_MATERIAL_NUMBER; i++)
			{
				res[i * MaterialUtil.MAX_MATERIAL_NUMBER + reactantIndex] = value;
			}
		}
	}
}
