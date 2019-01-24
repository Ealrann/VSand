package org.sheepy.vulkan.nuklear.adapter;

import static org.lwjgl.nuklear.Nuklear.*;

import java.nio.ByteBuffer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.impl.AbstractStatefullAdapter;
import org.sheepy.lily.core.api.variable.IVariableResolverAdapter;
import org.sheepy.lily.core.api.variable.IVariableResolverAdapter.IVariableListener;
import org.sheepy.lily.core.model.presentation.IUIElement;
import org.sheepy.lily.core.model.ui.UiPackage;
import org.sheepy.lily.core.model.ui.VariableLabel;
import org.sheepy.lily.core.model.variable.IVariableResolver;

public class VariableLabelAdapter extends AbstractStatefullAdapter implements IUIElementAdapter
{
	private final IVariableListener listener = new IVariableListener()
	{
		@Override
		public void onVariableChange(Object oldValue, Object newValue)
		{
			updateText(String.valueOf(newValue));
		}
	};

	private String text = "";
	private ByteBuffer textBuffer;

	private boolean dirty = false;

	private IVariableResolverAdapter<IVariableResolver> adapter;

	@Override
	public void setTarget(Notifier newTarget)
	{
		super.setTarget(newTarget);
		VariableLabel label = (VariableLabel) newTarget;

		var variableResolver = label.getVariableResolver();
		adapter = IVariableResolverAdapter.adapt(variableResolver);

		updateText(String.valueOf(adapter.getValue(variableResolver)));

		adapter.addListener(listener);
	}

	@Override
	public void unsetTarget(Notifier oldTarget)
	{
		adapter.removeListener(listener);
	}

	@Override
	public boolean layout(UIContext context, IUIElement control)
	{
		boolean res = dirty;
		VariableLabel label = (VariableLabel) control;

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

		dirty = false;
		return res;
	}

	private void updateText(String value)
	{
		var label = (VariableLabel) target;
		String labelText = label.getText();
		String resultString = "";

		if (labelText.isBlank()) resultString = String.format("%s%s", labelText, value);
		else resultString = String.format("%s: %s", labelText, value);

		if (resultString.equals(text) == false)
		{
			MemoryUtil.memFree(textBuffer);
			text = resultString;
			textBuffer = MemoryUtil.memASCII(text);
		}

		dirty = true;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return UiPackage.Literals.VARIABLE_LABEL == eClass;
	}
}
