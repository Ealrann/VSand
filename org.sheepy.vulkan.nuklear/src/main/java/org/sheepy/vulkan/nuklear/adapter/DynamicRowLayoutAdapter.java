package org.sheepy.vulkan.nuklear.adapter;

import static org.lwjgl.nuklear.Nuklear.nk_layout_row_dynamic;

import org.eclipse.emf.ecore.EClass;
import org.sheepy.common.api.adapter.impl.AbstractSingletonAdapter;
import org.sheepy.common.model.presentation.IUIElement;
import org.sheepy.common.model.ui.DynamicRowLayout;
import org.sheepy.common.model.ui.UiPackage;

public class DynamicRowLayoutAdapter extends AbstractSingletonAdapter implements IUIElementAdapter
{
	@Override
	public boolean layout(UIContext context, IUIElement control)
	{
		DynamicRowLayout layout = (DynamicRowLayout) control;

		nk_layout_row_dynamic(context.nkContext, layout.getHeight(), layout.getColumnCount());

		return false;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return UiPackage.Literals.DYNAMIC_ROW_LAYOUT == eClass;
	}
}
