package org.sheepy.vsand.ui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.model.application.IImage;
import org.sheepy.lily.vulkan.extra.api.nuklear.ISelectorInputProviderAdapter;
import org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider;
import org.sheepy.vsand.model.InputMaterialProvider;
import org.sheepy.vsand.model.Material;

@Adapter(scope = InputMaterialProvider.class)
public class MaterialSelectorInputProviderAdapter implements ISelectorInputProviderAdapter
{
	@Override
	public List<? extends Object> getElements(IInputProvider inputProvider)
	{
		final var materialProvider = (InputMaterialProvider) inputProvider;

		final List<Material> res = new ArrayList<>();
		for (final Material material : materialProvider.getMaterials().getMaterials())
		{
			if (material.isUserFriendly())
			{
				res.add(material);
			}
		}

		return List.copyOf(res);
	}

	@Override
	public String getName(Object element)
	{
		return ((Material) element).getName();
	}

	@Override
	public IImage getImage(Object element)
	{
		return null;
	}

	@Override
	public Vector3fc getColor(Object element)
	{
		final var material = (Material) element;
		return new Vector3f(material.getR(), material.getG(), material.getB());
	}

}
