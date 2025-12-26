package org.sheepy.vsand.ui;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.model.resource.IImage;
import org.sheepy.lily.vulkan.extra.api.nuklear.ISelectorInputProviderAdapter;
import org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider;
import org.sheepy.vsand.model.vsand.InputMaterialProvider;
import org.sheepy.vsand.model.vsand.Material;

import java.util.ArrayList;
import java.util.List;

@ModelExtender(scope = InputMaterialProvider.class)
@Adapter(singleton = true)
public final class MaterialSelectorInputProviderAdapter implements ISelectorInputProviderAdapter
{
	@Override
	public List<?> getElements(IInputProvider inputProvider)
	{
		final var materialProvider = (InputMaterialProvider) inputProvider;

		final List<Material> res = new ArrayList<>();
		for (final Material material : materialProvider.materials().materials())
		{
			if (material.userFriendly())
			{
				res.add(material);
			}
		}

		return List.copyOf(res);
	}

	@Override
	public String getName(Object element)
	{
		return ((Material) element).name();
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
		return new Vector3f(material.r(), material.g(), material.b());
	}
}
