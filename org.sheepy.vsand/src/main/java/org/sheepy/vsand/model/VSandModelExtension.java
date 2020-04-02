package org.sheepy.vsand.model;

import org.eclipse.emf.ecore.EPackage;
import org.sheepy.lily.core.api.resource.IModelExtension;

import java.util.Collection;
import java.util.List;

public final class VSandModelExtension implements IModelExtension
{
	@Override
	public Collection<EPackage> getEPackages()
	{
		return List.of(VSandPackage.eINSTANCE);
	}
}
