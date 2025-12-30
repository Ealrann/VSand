package org.sheepy.vsand.testutil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public record FetchedBoard(int width, int height, int[] packedSwizzled)
{
	public static FetchedBoard fromSwizzledBytes(final int width, final int height, final byte[] swizzledBytes)
	{
		final int swizzledWidth = width / 2;
		final int swizzledHeight = height / 2;
		final int expectedByteLength = swizzledWidth * swizzledHeight * Integer.BYTES;
		if (swizzledBytes.length != expectedByteLength)
		{
			throw new IllegalArgumentException("Board buffer size mismatch: expected %d, got %d"
													   .formatted(expectedByteLength, swizzledBytes.length));
		}

		final var buffer = ByteBuffer.wrap(swizzledBytes).order(ByteOrder.LITTLE_ENDIAN);
		final int packedLength = swizzledWidth * swizzledHeight;
		final var packed = new int[packedLength];
		for (int i = 0; i < packedLength; i++)
		{
			packed[i] = buffer.getInt(i * Integer.BYTES);
		}

		return new FetchedBoard(width, height, packed);
	}

	public int cell(final int x, final int y)
	{
		final int swizzledHeight = height / 2;
		final int swizzledX = x >> 1;
		final int swizzledY = y >> 1;
		final int swizzledLoc = swizzledX * swizzledHeight + swizzledY;
		final int value = packedSwizzled[swizzledLoc];
		final int offset = ((x & 1) | ((y & 1) << 1)) << 3;
		return (value >> offset) & 0xFF;
	}
}

