package org.sheepy.vsand.loader;

import java.nio.ByteBuffer;
import java.util.Random;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Autorun;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.vsand.model.VSandApplication;

/**
 * This Buffer will store the movement decisions during the board computation.
 * 
 * For each cell :
 * 
 * 4 bit for the possible next location: Up, Down, Right, Left 4 bit that describes what neighbors
 * want to come: Up, Down, Right, Left 8 bit for the decision priority. The highest score will be
 * favored: Up, Down, Right, Left
 */
@Adapter(scope = Buffer.class, name = "Decision")
public final class BoardDecisionLoader implements IVulkanAdapter
{
	@Autorun
	public static void load(Buffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		final int width = application.getSize().x;
		final int height = application.getSize().y;
		final int size = width * height;
		final int byteSize = size * Integer.BYTES;

		buffer.setSize(byteSize);

		final ByteBuffer javaBuffer = MemoryUtil.memAlloc(byteSize);
		updateRandomness(javaBuffer, size);
		buffer.setData(javaBuffer);
	}

	@Dispose
	public static void dispose(Buffer buffer)
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}

	private static void updateRandomness(ByteBuffer javaBuffer, int size)
	{
		final Random random = new Random();

		for (int i = 0; i < size; i += 8)
		{
			final var rand = random.nextInt();

			javaBuffer.putInt(generateScore((rand & 1) != 0, (rand & 256) != 0));
			javaBuffer.putInt(generateScore((rand & 2) != 0, (rand & 512) != 0));
			javaBuffer.putInt(generateScore((rand & 4) != 0, (rand & 1024) != 0));
			javaBuffer.putInt(generateScore((rand & 8) != 0, (rand & 2048) != 0));
			javaBuffer.putInt(generateScore((rand & 16) != 0, (rand & 4096) != 0));
			javaBuffer.putInt(generateScore((rand & 32) != 0, (rand & 8192) != 0));
			javaBuffer.putInt(generateScore((rand & 64) != 0, (rand & 16384) != 0));
			javaBuffer.putInt(generateScore((rand & 128) != 0, (rand & 32768) != 0));
		}
		javaBuffer.flip();
	}

	private static int generateScore(final boolean vBool, final boolean hBool)
	{
		// The vertical array comes first, we favor vertical movements
		return ((vBool ? 0 : 1)
				| ((vBool ? 1 : 0) << 2)
				| ((hBool ? 2 : 3) << 4)
				| ((hBool ? 3 : 2) << 6)) << 8;
	}
}
