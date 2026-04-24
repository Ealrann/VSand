package org.sheepy.vsand.analysis;

import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.Materials;

import java.util.List;
import java.util.Set;

public final class LiquidMaterials
{
	private static final Set<String> PRESSURE_LIQUID_NAMES = Set.of("Water",
																	"LiquidWax",
																	"Lava",
																	"LavaBoiling",
																	"Petrol",
																	"PetrolFire",
																	"HotWax",
																	"Acid");

	private LiquidMaterials()
	{
	}

	public static boolean isPressureLiquid(final Material material)
	{
		if (material == null) return false;

		final var name = material.name();
		return name != null && PRESSURE_LIQUID_NAMES.contains(name);
	}

	public static List<Material> pressureLiquids(final Materials materials)
	{
		if (materials == null) return List.of();

		return materials.materials().stream().filter(LiquidMaterials::isPressureLiquid).toList();
	}
}
