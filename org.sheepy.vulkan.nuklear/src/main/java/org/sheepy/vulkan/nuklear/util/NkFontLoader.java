package org.sheepy.vulkan.nuklear.util;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.nuklear.NkTextWidthCallback;
import org.lwjgl.nuklear.NkUserFont;
import org.lwjgl.nuklear.NkUserFontGlyph;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.vulkan.model.resource.Font;
import org.sheepy.lily.vulkan.resource.texture.FontAdapter;

public class NkFontLoader
{
	private static final IntBuffer unicode = MemoryUtil.memAllocInt(1);
	private static final IntBuffer advanceQuery = MemoryUtil.memAllocInt(1);
	private static final IntBuffer advanceWidth = MemoryUtil.memAllocInt(1);
	private static final long unicodeAddress = memAddress(unicode);

	private static final FloatBuffer X = MemoryUtil.memAllocFloat(1).put(0.0f).flip();
	private static final FloatBuffer Y = MemoryUtil.memAllocFloat(1).put(0.0f).flip();

	private static final STBTTAlignedQuad quad = STBTTAlignedQuad.calloc();

	private final int width = FontAdapter.BUFFER_WIDTH;
	private final int height = FontAdapter.BUFFER_HEIGHT;

	private final Font font;

	public NkFontLoader(Font font)
	{
		this.font = font;
	}

	public NkUserFont createNkFont(long id)
	{
		FontAdapter adapter = FontAdapter.adapt(font);

		int fontHeight = font.getHeight();
		STBTTFontinfo fontInfo = adapter.getFontInfo();
		STBTTPackedchar.Buffer cdata = adapter.getCdata();
		float descent = adapter.getDescent();
		float scale = adapter.getScale();

		NkUserFont default_font = NkUserFont.create();
		default_font.width(new TextWidthCallback(fontInfo, scale));
		default_font.height(fontHeight);
		default_font.query((handle, font_height, glyph, codepoint, next_codepoint) -> {

			X.put(0f).flip();
			Y.put(0f).flip();

			stbtt_GetPackedQuad(cdata, width, height, codepoint - 32, X, Y, quad, false);
			stbtt_GetCodepointHMetrics(fontInfo, codepoint, advanceQuery, null);

			NkUserFontGlyph ufg = NkUserFontGlyph.create(glyph);

			ufg.width(quad.x1() - quad.x0());
			ufg.height(quad.y1() - quad.y0());
			ufg.offset().set(quad.x0(), quad.y0() + (fontHeight + descent));
			ufg.xadvance(advanceQuery.get(0) * scale);
			ufg.uv(0).set(quad.s0(), quad.t0());
			ufg.uv(1).set(quad.s1(), quad.t1());
		});
		default_font.texture(it -> it.ptr(id));

		return default_font;
	}

	private static class TextWidthCallback extends NkTextWidthCallback
	{
		private final STBTTFontinfo fontInfo;
		private final float scale;

		public TextWidthCallback(STBTTFontinfo fontInfo, float scale)
		{
			this.fontInfo = fontInfo;
			this.scale = scale;
		}

		@Override
		public float invoke(long handle, float height, long text, int length)
		{
			if (length == 0)
			{
				return 0;
			}

			float textWidth = 0;
			int position = 0;

			while (position < length)
			{
				int currentGlyphLength = nnk_utf_decode(text + position, unicodeAddress, 1);
				int unicodeCodepoint = unicode.get(0);

				if (unicodeCodepoint == NK_UTF_INVALID || currentGlyphLength == 0)
				{
					break;
				}

				stbtt_GetCodepointHMetrics(fontInfo, unicodeCodepoint, advanceWidth, null);
				textWidth += advanceWidth.get(0) * scale;
				position += currentGlyphLength;
			}

			return textWidth;
		}
	};
}
