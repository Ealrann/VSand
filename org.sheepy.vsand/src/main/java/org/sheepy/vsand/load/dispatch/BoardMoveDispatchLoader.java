package org.sheepy.vsand.load.dispatch;

import org.logoce.adapter.api.Adapter;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.process.compute.DispatchTask;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = DispatchTask.class, name = "Board Move")
@Adapter(singleton = true)
@AutoLoad
public final class BoardMoveDispatchLoader implements IAdapter
{
	public static final int WORKGROUP_SIZE = 32;

	@Load
	private static void load(DispatchTask task)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(task);
		final var size = application.getSize();

		final int horizontalSize = (int) Math.ceil((float) size.x() / WORKGROUP_SIZE);
		final int verticalSize = (int) Math.ceil((float) size.y() / WORKGROUP_SIZE);

		task.setWorkgroupCountX(horizontalSize + 1);
		task.setWorkgroupCountY(verticalSize + 1);
	}
}