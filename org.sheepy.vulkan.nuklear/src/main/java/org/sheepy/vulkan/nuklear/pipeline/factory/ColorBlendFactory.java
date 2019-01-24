package org.sheepy.vulkan.nuklear.pipeline.factory;

import org.sheepy.lily.vulkan.model.enumeration.EBlendFactor;
import org.sheepy.lily.vulkan.model.enumeration.EBlendOp;
import org.sheepy.lily.vulkan.model.process.graphic.ColorBlend;
import org.sheepy.lily.vulkan.model.process.graphic.impl.ColorBlendAttachmentImpl;
import org.sheepy.lily.vulkan.model.process.graphic.impl.ColorBlendImpl;

public class ColorBlendFactory
{
	public static final ColorBlend create()
	{
		var blendColorAttachment = new ColorBlendAttachmentImpl();
		blendColorAttachment.setBlendEnable(true);
		blendColorAttachment.setSrcColor(EBlendFactor.SRC_ALPHA);
		blendColorAttachment.setDstColor(EBlendFactor.ONE_MINUS_SRC_ALPHA);
		blendColorAttachment.setSrcAlpha(EBlendFactor.ONE);
		blendColorAttachment.setDstAlpha(EBlendFactor.ZERO);
		blendColorAttachment.setColorBlendOp(EBlendOp.ADD);
		blendColorAttachment.setAlphaBlendOp(EBlendOp.ADD);

		var colorBlend = new ColorBlendImpl();
		colorBlend.getAttachments().add(blendColorAttachment);

		return colorBlend;
	}
}
