package org.sheepy.vsand.loader;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.process.compute.DispatchTask;
import org.sheepy.vsand.model.vsand.VSandApplication;

@ModelExtender(scope = DispatchTask.class, name = "Pressure apply")
@Adapter(singleton = true)
@AutoLoad
public class PressureApplyDispatchTaskLoader implements IAdapter
{
	@Load
	private static void load(DispatchTask task)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(task);
		final var size = application.size();

		final int chunkWidth = (int) Math.floor(size.x() / 16.);
		final int chunkHeight = (int) Math.floor(size.y() / 16.);

		task.workgroupCountX(chunkWidth);
		task.workgroupCountY(chunkHeight);
		task.workgroupCountZ(1);
	}
}
