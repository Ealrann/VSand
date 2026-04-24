package org.sheepy.vsand.testutil;

import org.sheepy.vsand.analysis.MassGrid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public record FetchedMass(int width, int height, int[] packedSwizzled) implements MassGrid
{
	public static FetchedMass fromSwizzledBytes(final int width, final int height, final byte[] swizzledBytes)
	{
		final int swizzledWidth = width / 2;
		final int swizzledHeight = height / 2;
		final int expectedByteLength = swizzledWidth * swizzledHeight * 2 * Integer.BYTES;
		if (swizzledBytes.length != expectedByteLength)
		{
			throw new IllegalArgumentException("Mass buffer size mismatch: expected %d, got %d"
													   .formatted(expectedByteLength, swizzledBytes.length));
		}

		final var buffer = ByteBuffer.wrap(swizzledBytes).order(ByteOrder.LITTLE_ENDIAN);
		final int packedLength = swizzledWidth * swizzledHeight * 2;
		final var packed = new int[packedLength];
		for (int i = 0; i < packedLength; i++)
		{
			packed[i] = buffer.getInt(i * Integer.BYTES);
		}

		return new FetchedMass(width, height, packed);
	}

	public int mass(final int x, final int y)
	{
		return massAt(x, y);
	}

	@Override
	public int massAt(final int x, final int y)
	{
		final int swizzledHeight = height / 2;
		final int swizzledX = x >> 1;
		final int swizzledY = y >> 1;
		final int swizzledLoc = swizzledX * swizzledHeight + swizzledY;

		final int wordIndex = swizzledLoc * 2 + (y & 1);
		final int word = packedSwizzled[wordIndex];
		final int shift = (x & 1) << 4;

		return (word >> shift) & 0xFFFF;
	}
}
