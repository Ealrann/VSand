package org.sheepy.vsand.load.dispatch;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.process.compute.DispatchTask;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = DispatchTask.class, name = "Board To Pixel")
@Adapter(singleton = true, lazy = false)
public final class BoardToPixelDispatchLoader implements IExtender
{
	private static final int WORKGROUP_SIZE = 16;

	@Load
	private static void load(DispatchTask task)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(task);
		final var size = application.getSize();

		task.setWorkgroupCountX((int) Math.ceil((float) size.x() / WORKGROUP_SIZE));
		task.setWorkgroupCountY((int) Math.ceil((float) size.y() / WORKGROUP_SIZE));
	}
}
