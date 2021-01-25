package org.sheepy.vsand.load.dispatch;

import org.logoce.adapter.api.Adapter;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.process.compute.DispatchTask;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = DispatchTask.class, name = "Board To Pixel")
@Adapter(singleton = true)
@AutoLoad
public final class BoardToPixelDispatchLoader implements IAdapter
{
	private static final int WORKGROUP_SIZE = 8;

	@Load
	private static void load(DispatchTask task)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(task);
		final var size = application.getSize();

		task.setWorkgroupCountX((int) Math.ceil((float) size.x() / WORKGROUP_SIZE));
		task.setWorkgroupCountY((int) Math.ceil((float) size.y() / WORKGROUP_SIZE));
	}
}
