package org.sheepy.vsand.adapter;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.resource.buffer.BufferAdapter;
import org.sheepy.vsand.buffer.ModificationsManager;

@Statefull
@Adapter(scope = Buffer.class, name = "Modification")
public class ModificationBufferLoader extends BufferAdapter
{
	public ModificationBufferLoader(Buffer buffer)
	{
		super(buffer);

		buffer.setSize(ModificationsManager.BYTE_SIZE);
	}
}
