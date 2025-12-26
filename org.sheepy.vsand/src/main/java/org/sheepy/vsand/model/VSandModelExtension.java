package org.sheepy.vsand.model;

import org.logoce.lmf.core.api.model.IModelPackage;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vsand.model.vsand.VSandModelPackage;

import java.util.Collection;
import java.util.List;

public final class VSandModelExtension implements IModelExtension
{
	@Override
	public Collection<IModelPackage> getEPackages()
	{
		return List.of(VSandModelPackage.Instance);
	}
}
