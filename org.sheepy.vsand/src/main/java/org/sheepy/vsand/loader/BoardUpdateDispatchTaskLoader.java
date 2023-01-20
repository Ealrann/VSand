package org.sheepy.vsand.loader;

import org.logoce.adapter.api.Adapter;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.process.compute.DispatchTask;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = DispatchTask.class, name = "Board update")
@Adapter(singleton = true)
@AutoLoad
public class BoardUpdateDispatchTaskLoader implements IAdapter
{
	@Load
	private static void load(DispatchTask task)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(task);
		final var size = application.getSize();
		final int width = size.x();
		final int height = size.y();
		final int chunkWidth = (int) Math.floor(width / 16.);
		final int chunkHeight = (int) Math.floor(height / 16.);

		task.setWorkgroupCountX(chunkWidth);
		task.setWorkgroupCountY(chunkHeight);
		task.setWorkgroupCountZ(1);
	}
}
