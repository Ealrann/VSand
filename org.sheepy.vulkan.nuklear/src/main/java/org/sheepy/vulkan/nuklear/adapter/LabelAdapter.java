package org.sheepy.vulkan.nuklear.adapter;

import static org.lwjgl.nuklear.Nuklear.*;

import java.nio.ByteBuffer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.impl.AbstractStatefullAdapter;
import org.sheepy.lily.core.model.presentation.IUIElement;
import org.sheepy.lily.core.model.ui.Label;
import org.sheepy.lily.core.model.ui.UiPackage;

public class LabelAdapter extends AbstractStatefullAdapter implements IUIElementAdapter
{
	private ByteBuffer textBuffer;

	@Override
	public void setTarget(Notifier newTarget)
	{
		Label label = (Label) newTarget;
		textBuffer = MemoryUtil.memASCII(label.getText());
	}

	@Override
	public boolean layout(UIContext context, IUIElement control)
	{
		Label label = (Label) control;

		int align = 0;
		switch (label.getHorizontalRelative())
		{
		case MIDDLE:
			align = NK_TEXT_CENTERED;
			break;
		case RIGHT:
			align = NK_TEXT_RIGHT;
			break;
		default:
			align = NK_TEXT_LEFT;
			break;
		}

		nk_label(context.nkContext, textBuffer, align);

		return false;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return UiPackage.Literals.LABEL == eClass;
	}
}
