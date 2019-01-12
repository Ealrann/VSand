package org.sheepy.vulkan.sand.model;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.sheepy.common.api.resource.IModelExtension;
import org.sheepy.vulkan.sand.model.VSandPackage;

public class VSandModelExtension implements IModelExtension
{
	@Override
	public Collection<EPackage> getEPackages()
	{
		Collection<EPackage> res = new ArrayList<>();

		res.add(VSandPackage.eINSTANCE);

		return res;
	}
}
