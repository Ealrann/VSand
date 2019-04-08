package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;
import java.util.Random;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.resource.buffer.BufferAdapter;
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
@Statefull
@Adapter(scope = Buffer.class, name = "Decision")
public class BoardDecisionLoader extends BufferAdapter
{
	private static final Byte[] vScoreArray = {
			0, 1
	};
	private static final Byte[] hScoreArray = {
			2, 3
	};

	private static final Random random = new Random();

	public BoardDecisionLoader(Buffer buffer)
	{
		super(buffer);

		var application = (VSandApplication) ModelUtil.getApplication(buffer);
		int width = application.getSize().x;
		int height = application.getSize().y;
		int size = width * height + 1;
		int byteSize = size * Integer.BYTES;

		buffer.setSize(byteSize);

		ByteBuffer javaBuffer = MemoryUtil.memAlloc(byteSize);
		updateRandomness(javaBuffer, size);
		buffer.setData(javaBuffer);
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}

	private static void updateRandomness(ByteBuffer javaBuffer, int size)
	{
		for (int i = 0; i < size; i++)
		{
			int res = 0;

			shuffleScoreArray();

			int scoreByte = 0;

			// The vertical array comes first, we favor vertical movements
			scoreByte = scoreByte | vScoreArray[0];
			scoreByte = scoreByte | (vScoreArray[1] << 2);
			scoreByte = scoreByte | (hScoreArray[0] << 4);
			scoreByte = scoreByte | (hScoreArray[1] << 6);

			res = scoreByte << 8;

			javaBuffer.putInt(res);
		}
		javaBuffer.flip();
	}

	private static void shuffleScoreArray()
	{
		// Complicated for just reordering two arrays of two elements...
		// whatever.
		for (int i = 0; i < vScoreArray.length; i++)
		{
			int change = i + random.nextInt(vScoreArray.length - i);
			Byte tmp = vScoreArray[i];
			vScoreArray[i] = vScoreArray[change];
			vScoreArray[change] = tmp;
		}
		for (int i = 0; i < hScoreArray.length; i++)
		{
			int change = i + random.nextInt(hScoreArray.length - i);
			Byte tmp = hScoreArray[i];
			hScoreArray[i] = hScoreArray[change];
			hScoreArray[change] = tmp;
		}
	}
}
