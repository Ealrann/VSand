package org.sheepy.vsand.loader;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Autorun;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.vsand.buffer.ModificationsManager;

@Statefull
@Adapter(scope = Buffer.class, name = "Modification")
public final class ModificationBufferLoader implements IVulkanAdapter
{
	@Autorun
	public static void load(Buffer buffer)
	{
		buffer.setSize(ModificationsManager.BYTE_SIZE);
	}
}
