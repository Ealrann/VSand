package org.sheepy.vsand.util;

import org.sheepy.vsand.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TransformationUtil
{
	private static final int PROPAGATION_LOCATION = 8;
	private static final int PROBABILITY_LOCATION = 16;
	private static final int STATIC_FLAG = 1 << 30;

	public static int[] toArray(VSandApplication application)
	{
		final var materials = application.getMaterials();
		final var materialList = materials.getMaterials();
		final int materialCount = materialList.size();
		final var transformations = application.getTransformations();

		final int[] res = new int[materialCount * materialCount];
		Arrays.fill(res, -1);

		for (final var transformation : transformations.getTransformations())
		{
			final var reactants = extractReactants(materialList, transformation);
			final var catalysts = extractCatalysts(materialList, transformation);

			fillTransformation(res, materialList, transformation, reactants, catalysts);
		}

		return res;
	}

	private static List<Material> extractReactants(List<Material> materialList,
												   ITransformation transformation)
	{
		if (transformation instanceof Transformation)
		{
			return List.of(((Transformation) transformation).getReactant());
		}
		else if (transformation instanceof MultipleTransformation)
		{
			final MaterialProvider reactants = ((MultipleTransformation) transformation).getReactants();
			return resolveMaterials(materialList, reactants);
		}

		throw new AssertionError();
	}

	private static List<Material> extractCatalysts(List<Material> materialList,
												   ITransformation transformation)
	{
		if (transformation instanceof Transformation)
		{
			final Material catalyst = ((Transformation) transformation).getCatalyst();

			if (catalyst == null)
			{
				return materialList;
			}
			else
			{
				return List.of(catalyst);
			}
		}
		else if (transformation instanceof MultipleTransformation)
		{
			final MaterialProvider catalysts = ((MultipleTransformation) transformation).getCatalysts();
			return resolveMaterials(materialList, catalysts);
		}

		throw new AssertionError();
	}

	private static List<Material> resolveMaterials(List<Material> materialList,
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

	private static void fillTransformation(int[] res,
										   List<Material> materialList,
										   ITransformation transformation,
										   List<Material> reactants,
										   List<Material> catalysts)
	{
		final int materialCount = materialList.size();
		for (final Material reactant : reactants)
		{
			for (final Material catalyst : catalysts)
			{
				final int reactantIndex = materialList.indexOf(reactant);

				if (transformation.getTarget() == reactant)
				{
					warnRecursiveTransformation(reactant, catalyst);
				}

				int value = materialList.indexOf(transformation.getTarget());
				value |= transformation.getProbability() << PROBABILITY_LOCATION;
				value |= transformation.isIsStaticTransformation() ? STATIC_FLAG : 0;
				value |= transformation.getPropagation() << PROPAGATION_LOCATION;

				if (transformation.isIsStaticTransformation())
				{
					// Ignore catalyst
					for (int i = 0; i < materialList.size(); i++)
					{
						final var potentialCatalyst = materialList.get(i);
						if (potentialCatalyst != reactant
								&& (potentialCatalyst.isIsStatic() == true
								|| potentialCatalyst.getDensity() >= reactant.getDensity()))
						{
							res[i * materialCount + reactantIndex] = value;
						}
					}
				}
				else if (catalyst != null)
				{
					final int catalystIndex = materialList.indexOf(catalyst);
					res[catalystIndex * materialCount + reactantIndex] = value;
				}
			}
		}
	}

	private static void warnRecursiveTransformation(final Material reactant,
													final Material catalyst)
	{
		System.err.println(String.format(
				"[Warning] Recursive transformation: The material %s react with %s to create %s",
				reactant.getName(),
				catalyst.getName(),
				reactant.getName()));
	}
}
