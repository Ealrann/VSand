package org.sheepy.vsand.util;

import java.util.ArrayList;
import java.util.List;

import org.sheepy.vsand.model.ITransformation;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.MaterialProvider;
import org.sheepy.vsand.model.MultipleTransformation;
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

		for (final var transfo : transformations.getTransformations())
		{
			final var reactants = extractReactants(materialList, transfo);
			final var catalysts = extractCatalysts(materialList, transfo);

			fillTransformation(res, materialList, transfo, reactants, catalysts);
		}

		return res;
	}

	private static List<Material> extractReactants(	List<Material> materialList,
													ITransformation transfo)
	{
		if (transfo instanceof Transformation)
		{
			return List.of(((Transformation) transfo).getReactant());
		}
		else if (transfo instanceof MultipleTransformation)
		{
			final MaterialProvider reactants = ((MultipleTransformation) transfo).getReactants();
			return resolveMaterials(materialList, reactants);
		}

		throw new AssertionError();
	}

	private static List<Material> extractCatalysts(	List<Material> materialList,
													ITransformation transfo)
	{
		if (transfo instanceof Transformation)
		{
			final Material catalyst = ((Transformation) transfo).getCatalyst();

			if (catalyst == null)
			{
				return materialList;
			}
			else
			{
				return List.of(catalyst);
			}
		}
		else if (transfo instanceof MultipleTransformation)
		{
			final MaterialProvider catalysts = ((MultipleTransformation) transfo).getCatalysts();
			return resolveMaterials(materialList, catalysts);
		}

		throw new AssertionError();
	}

	private static List<Material> resolveMaterials(	List<Material> materialList,
													final MaterialProvider catalysts)
	{
		final var materials = catalysts.getMaterials();
		if (catalysts.isFilterMode())
		{
			final List<Material> res = new ArrayList<>(materialList);
			res.removeAll(materials);
			return res;
		}
		else
		{
			return materials;
		}
	}

	private static void fillTransformation(	int[] res,
											List<Material> materialList,
											ITransformation transfo,
											List<Material> reactants,
											List<Material> catalysts)
	{
		for (final Material reactant : reactants)
		{
			for (final Material catalyst : catalysts)
			{
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
						final var potentialCatalyst = materialList.get(i);
						if (potentialCatalyst != reactant
								&& (potentialCatalyst.isIsStatic() == true
										|| potentialCatalyst.getDensity() >= reactant.getDensity()))
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
				// else
				// {
				// // null catalyst means: react with everything.
				// for (int i = 0; i < MaterialUtil.MAX_MATERIAL_NUMBER; i++)
				// {
				// res[i * MaterialUtil.MAX_MATERIAL_NUMBER + reactantIndex] = value;
				// }
				// }
			}
		}
	}
}
