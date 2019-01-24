package org.sheepy.vulkan.nuklear.adapter;

import static org.lwjgl.nuklear.Nuklear.nk_button_label;

import java.nio.ByteBuffer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.action.context.ExecutionContext;
import org.sheepy.lily.core.api.adapter.impl.AbstractStatefullAdapter;
import org.sheepy.lily.core.api.application.IApplicationAdapter;
import org.sheepy.lily.core.model.action.Action;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.core.model.presentation.IUIElement;
import org.sheepy.lily.core.model.ui.Button;
import org.sheepy.lily.core.model.ui.UiPackage;

public class ButtonAdapter extends AbstractStatefullAdapter implements IUIElementAdapter
{
	private ByteBuffer textBuffer;

	@Override
	public void setTarget(Notifier newTarget)
	{
		Button button = (Button) newTarget;
		textBuffer = MemoryUtil.memASCII(button.getText());
	}
	
	@Override
	public boolean layout(UIContext context, IUIElement control)
	{
		boolean res = false;
		Button button = (Button) control;

		if (nk_button_label(context.nkContext, textBuffer))
		{
			var application = (Application) EcoreUtil.getRootContainer(button.getExecutor());
			var cadencer = IApplicationAdapter.adapt(application).getCadencer();

			for (Action action : button.getActions())
			{
				ExecutionContext ec = new ExecutionContext(button.getExecutor(), action, null);
				cadencer.postAction(ec);
			}
			res = true;
		}

		return res;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return UiPackage.Literals.BUTTON == eClass;
	}
}
