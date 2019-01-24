package org.sheepy.vsand.model;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vsand.model.VSandPackage;

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
