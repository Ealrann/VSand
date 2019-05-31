package org.sheepy.vsand.logic;

import org.eclipse.emf.common.util.EList;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.Transformation;
import org.sheepy.vsand.model.VSandApplication;

public final class TransformationUtil
{
	private static final int PROPAGATION_LOCATION = 8;
	private static final int PROBABILITY_LOCATION = 16;
	private static final int STATIC_FLAG = 1 << 30;

	public static int[] toArray(VSandApplication application)
	{
		final var materials = application.getMaterials();
		final var transformations = application.getTransformations();

		final int[] res = new int[MaterialUtil.MAX_MATERIAL_NUMBER
				* MaterialUtil.MAX_MATERIAL_NUMBER];
		for (int i = 0; i < res.length; i += 4)
		{
			res[i] = -1;
			res[i + 1] = -1;
			res[i + 2] = -1;
			res[i + 3] = -1;
		}

		final var materialList = materials.getMaterials();

		for (final Transformation transfo : transformations.getTransformations())
		{
			fillTransformation(res, materialList, transfo);
		}

		return res;
	}

	private static void fillTransformation(	int[] res,
											EList<Material> materialList,
											Transformation transfo)
	{
		final var catalyst = transfo.getCatalyst();
		final var reactant = transfo.getReactant();
		final int reactantIndex = materialList.indexOf(reactant);

		int value = materialList.indexOf(transfo.getTarget());
		value |= transfo.getProbability() << PROBABILITY_LOCATION;
		value |= transfo.isIsStaticTransformation() ? STATIC_FLAG : 0;
		value |= transfo.getPropagation() << PROPAGATION_LOCATION;

		if (transfo.isIsStaticTransformation())
		{
			// Ignore catalyst
			for (int i = 0; i < materialList.size(); i++)
			{
				if (materialList.get(i).isIsStatic() == true)
				{
					res[i * MaterialUtil.MAX_MATERIAL_NUMBER + reactantIndex] = value;
				}
			}
		}
		else if (catalyst != null)
		{
			final int catalystIndex = materialList.indexOf(catalyst);
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
