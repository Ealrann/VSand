package org.sheepy.vsand.loader;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.process.compute.DispatchTask;
import org.sheepy.vsand.model.vsand.VSandApplication;

@ModelExtender(scope = DispatchTask.class, name = "Pressure head")
@Adapter(singleton = true)
@AutoLoad
public final class PressureHeadDispatchTaskLoader implements IAdapter
{
	private static final int WORKGROUP_SIZE_X = 64;

	@Load
	private static void load(final DispatchTask task)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(task);
		final var size = application.size();
		task.workgroupCountX((size.x() + WORKGROUP_SIZE_X - 1) / WORKGROUP_SIZE_X);
		task.workgroupCountY(1);
		task.workgroupCountZ(1);
	}
}

