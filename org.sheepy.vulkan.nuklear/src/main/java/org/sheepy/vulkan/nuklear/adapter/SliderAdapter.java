package org.sheepy.vulkan.nuklear.adapter;

import static org.lwjgl.nuklear.Nuklear.nk_slider_int;

import java.nio.IntBuffer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.common.api.adapter.impl.AbstractStatefullAdapter;
import org.sheepy.common.api.variable.IVariableResolverAdapter;
import org.sheepy.common.api.variable.IVariableResolverAdapter.IVariableListener;
import org.sheepy.common.model.presentation.IUIElement;
import org.sheepy.common.model.ui.Slider;
import org.sheepy.common.model.ui.UiPackage;
import org.sheepy.common.model.variable.IVariableResolver;

public class SliderAdapter extends AbstractStatefullAdapter implements IUIElementAdapter
{
	private IntBuffer buffer;

	private final IVariableListener listener = new IVariableListener()
	{
		@Override
		public void onVariableChange(Object oldValue, Object newValue)
		{
			updateValue();
			dirty = true;
		}
	};

	private IVariableResolverAdapter<IVariableResolver> resolverAdapter;
	private IVariableResolver variableResolver;

	private boolean dirty = false;

	@Override
	public void setTarget(Notifier target)
	{
		super.setTarget(target);
		Slider slider = (Slider) target;

		variableResolver = slider.getVariableResolver();
		resolverAdapter = IVariableResolverAdapter.adapt(variableResolver);

		buffer = MemoryUtil.memAllocInt(1);
		updateValue();

		resolverAdapter.addListener(listener);
	}

	@Override
	public void unsetTarget(Notifier oldTarget)
	{
		resolverAdapter.removeListener(listener);

		super.unsetTarget(oldTarget);
	}

	private void updateValue()
	{
		Integer val = (Integer) resolverAdapter.getValue(variableResolver);
		buffer.put(val);
		buffer.flip();
	}

	@Override
	public boolean layout(UIContext context, IUIElement control)
	{
		boolean res = dirty;
		Slider slider = (Slider) control;

		nk_slider_int(context.nkContext, slider.getMinValue(), buffer, slider.getMaxValue(),
				slider.getStep());

		Integer val = (Integer) resolverAdapter.getValue(variableResolver);
		if (val != buffer.get(0))
		{
			resolverAdapter.setValue(variableResolver, buffer.get(0));
			res = true;
		}

		dirty = false;
		return res;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return UiPackage.Literals.SLIDER == eClass;
	}
}
