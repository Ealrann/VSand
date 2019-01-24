package org.sheepy.vulkan.sand.adapter;

import static org.lwjgl.nuklear.Nuklear.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.nuklear.NkColor;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.impl.AbstractStatefullAdapter;
import org.sheepy.lily.core.model.presentation.IUIElement;
import org.sheepy.lily.vulkan.api.window.IWindowListener;
import org.sheepy.lily.vulkan.api.window.Surface;
import org.sheepy.lily.vulkan.common.ui.UIUtil;
import org.sheepy.vulkan.nuklear.adapter.IUIElementAdapter;
import org.sheepy.vulkan.sand.adapter.drawer.MaterialDrawer;
import org.sheepy.vulkan.sand.model.Material;
import org.sheepy.vulkan.sand.model.MaterialSelectorPanel;
import org.sheepy.vulkan.sand.model.VSandApplication;
import org.sheepy.vulkan.sand.model.VSandPackage;

public class MaterialSelectorPanelAdapter extends AbstractStatefullAdapter
		implements IUIElementAdapter
{
	private final Adapter materialAdapter = new AdapterImpl()
	{
		@Override
		public void notifyChanged(Notification notification)
		{
			var feature = notification.getFeature();
			if (feature == VSandPackage.Literals.VSAND_APPLICATION__MAIN_MATERIAL
					|| feature == VSandPackage.Literals.VSAND_APPLICATION__SECONDARY_MATERIAL)
			{
				dirty = true;
			}
		}
	};

	private final IWindowListener listener = new IWindowListener()
	{
		@Override
		public void onWindowResize(Surface surface)
		{
			updateDataLocations(surface);
		}
	};

	private boolean loaded = false;

	private List<LineData> datas;
	private int width;
	private int height;

	private VSandApplication application;
	private boolean dirty = false;

	private MaterialDrawer primaryMaterialDrawer;
	private MaterialDrawer secondaryMaterialDrawer;

	private MaterialSelectorPanel panel;

	@Override
	public void setTarget(Notifier newTarget)
	{
		super.setTarget(newTarget);

		panel = (MaterialSelectorPanel) newTarget;
		application = (VSandApplication) EcoreUtil.getRootContainer(panel);
		application.eAdapters().add(materialAdapter);
	}

	@Override
	public void unsetTarget(Notifier oldTarget)
	{
		application.eAdapters().remove(materialAdapter);
		super.unsetTarget(oldTarget);
	}

	private void load(UIContext context, MaterialSelectorPanel panel)
	{
		var materials = application.getMaterials().getMaterials();
		int materialCount = countUserFriendlyMaterials(materials);
		var surface = context.window.getSurface();
		int lineHeight = panel.getLineHeight();

		primaryMaterialDrawer = new MaterialDrawer(lineHeight, panel.getPrimaryR(),
				panel.getPrimaryG(), panel.getPrimaryB());
		secondaryMaterialDrawer = new MaterialDrawer(lineHeight, panel.getSecondaryR(),
				panel.getSecondaryG(), panel.getSecondaryB());

		width = lineHeight * 2 + LineData.TEXT_WIDTH;
		height = lineHeight * materialCount;

		datas = new ArrayList<>();
		for (Material material : materials)
		{
			if (material.isUserFriendly())
			{
				datas.add(new LineData(material));
			}
		}
		datas = List.copyOf(datas);

		updateDataLocations(surface);
		context.window.addListener(listener);
	}

	public void updateDataLocations(Surface surface)
	{
		int lineHeight = panel.getLineHeight();
		int x = UIUtil.computeXRelative(surface, panel, width);
		int y = UIUtil.computeYRelative(surface, panel, height);
		for (LineData data : datas)
		{
			data.updateRect(x, y, lineHeight);
			y += lineHeight;
		}
	}

	private static int countUserFriendlyMaterials(EList<Material> materials)
	{
		return (int) materials.stream().filter(m -> m.isUserFriendly()).count();
	}

	@Override
	public boolean layout(UIContext context, IUIElement control)
	{
		boolean res = dirty;
		var nkContext = context.nkContext;

		if (loaded == false)
		{
			load(context, panel);
			loaded = true;
		}

		var backgroundColor = nkContext.style().window().fixed_background().data().color();
		byte defaultAlpha = backgroundColor.a();

		primaryMaterialDrawer.prepare(nkContext);
		secondaryMaterialDrawer.prepare(nkContext);

		backgroundColor.a((byte) 0);

		for (LineData data : datas)
		{
			if (nk_begin(nkContext, data.panelLabelId, data.rectLabel,
					NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_BACKGROUND | NK_WINDOW_NO_INPUT))
			{
				nk_layout_row_dynamic(context.nkContext, panel.getLineHeight() - 5, 1);
				nk_label(nkContext, data.textBuffer, NK_TEXT_RIGHT);
			}
			nk_end(nkContext);

			if (primaryMaterialDrawer.draw(nkContext, data, application.getMainMaterial(),
					data.panelButton1Id, data.rectButton1))
			{
				application.setMainMaterial(data.material);
			}

			if (secondaryMaterialDrawer.draw(nkContext, data, application.getSecondaryMaterial(),
					data.panelButton2Id, data.rectButton2))
			{
				application.setSecondaryMaterial(data.material);
			}
		}

		primaryMaterialDrawer.finish();
		secondaryMaterialDrawer.finish();

		backgroundColor.a(defaultAlpha);

		dirty = false;
		return res;
	}

	public static class LineData
	{
		public static final int TEXT_WIDTH = 80;

		public final Material material;
		public final ByteBuffer textBuffer;
		public final NkColor color;
		public final NkRect rectLabel = NkRect.malloc();
		public final NkRect rectButton1 = NkRect.malloc();
		public final NkRect rectButton2 = NkRect.malloc();
		public final ByteBuffer panelLabelId;
		public final ByteBuffer panelButton1Id;
		public final ByteBuffer panelButton2Id;

		public LineData(Material material)
		{
			this.material = material;

			color = NkColor.malloc();
			color.r((byte) material.getR());
			color.g((byte) material.getG());
			color.b((byte) material.getB());
			color.a((byte) 255);

			String name = material.getName();

			panelLabelId = MemoryUtil.memUTF8("Label" + name);
			panelButton1Id = MemoryUtil.memUTF8("Button1" + name);
			panelButton2Id = MemoryUtil.memUTF8("Button2" + name);

			textBuffer = MemoryUtil.memUTF8(name);
		}

		public void updateRect(int x, int y, int lineHeight)
		{
			rectLabel.set(x, y, TEXT_WIDTH, lineHeight);
			rectButton1.set(x + TEXT_WIDTH, y, lineHeight, lineHeight);
			rectButton2.set(x + TEXT_WIDTH + lineHeight, y, lineHeight, lineHeight);
		}
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return VSandPackage.Literals.MATERIAL_SELECTOR_PANEL == eClass;
	}
}
