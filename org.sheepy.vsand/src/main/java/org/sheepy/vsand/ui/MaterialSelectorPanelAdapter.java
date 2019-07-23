package org.sheepy.vsand.ui;

import static org.lwjgl.nuklear.Nuklear.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.joml.Vector2i;
import org.lwjgl.nuklear.NkColor;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.core.model.presentation.IUIElement;
import org.sheepy.lily.vulkan.api.util.UIUtil;
import org.sheepy.lily.vulkan.nuklear.ui.IUIElementAdapter;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.MaterialSelectorPanel;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandPackage;
import org.sheepy.vulkan.window.IWindowListener;

@Statefull
@org.sheepy.lily.core.api.adapter.annotation.Adapter(scope = MaterialSelectorPanel.class)
public final class MaterialSelectorPanelAdapter implements IUIElementAdapter
{
	private final Adapter materialAdapter = new AdapterImpl()
	{
		@Override
		public void notifyChanged(Notification notification)
		{
			final var feature = notification.getFeature();
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
		public void onResize(Vector2i size)
		{
			updateDataLocations(size);
		}
	};

	private final VSandApplication application;
	private final List<LineData> datas;

	private boolean loaded = false;

	private int width;
	private int height;

	private boolean dirty = false;

	private MaterialDrawer primaryMaterialDrawer;
	private MaterialDrawer secondaryMaterialDrawer;

	private final MaterialSelectorPanel panel;

	public MaterialSelectorPanelAdapter(MaterialSelectorPanel panel)
	{
		this.panel = panel;
		application = (VSandApplication) EcoreUtil.getRootContainer(panel);
		application.eAdapters().add(materialAdapter);

		datas = List.copyOf(buildLineDatas());
	}

	private List<LineData> buildLineDatas()
	{
		final List<LineData> tmpDatas = new ArrayList<>();
		final var materials = application.getMaterials().getMaterials();
		for (final Material material : materials)
		{
			if (material.isUserFriendly())
			{
				tmpDatas.add(new LineData(material));
			}
		}
		return tmpDatas;
	}

	@Dispose
	public void unsetTarget()
	{
		application.eAdapters().remove(materialAdapter);
		for (final LineData data : datas)
		{
			data.free();
		}
	}

	private void load(UIContext context, MaterialSelectorPanel panel)
	{
		final var surface = context.window.getSize();
		final int lineHeight = panel.getLineHeight();

		primaryMaterialDrawer = new MaterialDrawer(lineHeight, panel.getPrimaryR(),
				panel.getPrimaryG(), panel.getPrimaryB());
		secondaryMaterialDrawer = new MaterialDrawer(lineHeight, panel.getSecondaryR(),
				panel.getSecondaryG(), panel.getSecondaryB());

		width = lineHeight * 2 + LineData.TEXT_WIDTH;
		height = lineHeight * datas.size();

		updateDataLocations(surface);
		context.window.addListener(listener);
	}

	public void updateDataLocations(Vector2i size)
	{
		final int lineHeight = panel.getLineHeight();
		final int x = UIUtil.computeXRelative(size, panel, width);
		int y = UIUtil.computeYRelative(size, panel, height);
		for (final LineData data : datas)
		{
			data.updateRect(x, y, lineHeight);
			y += lineHeight;
		}
	}

	@Override
	public boolean layout(UIContext context, IUIElement control)
	{
		final boolean res = dirty;

		// !! must remain here- vvv
		dirty = false;
		// -------------------- ^^^

		final var nkContext = context.nkContext;

		if (loaded == false)
		{
			load(context, panel);
			loaded = true;
		}

		final var backgroundColor = nkContext.style().window().fixed_background().data().color();
		final byte defaultAlpha = backgroundColor.a();

		primaryMaterialDrawer.prepare(nkContext);
		secondaryMaterialDrawer.prepare(nkContext);

		final var mainMaterial = application.getMainMaterial();
		final var secondaryMaterial = application.getSecondaryMaterial();

		backgroundColor.a((byte) 0);

		for (final LineData data : datas)
		{
			if (nk_begin(nkContext, data.panelLabelId, data.rectLabel,
					NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_BACKGROUND | NK_WINDOW_NO_INPUT))
			{
				nk_layout_row_dynamic(context.nkContext, panel.getLineHeight() - 5, 1);
				nk_label(nkContext, data.textBuffer, NK_TEXT_RIGHT);
			}
			nk_end(nkContext);

			if (primaryMaterialDrawer.draw(nkContext, data, mainMaterial, data.panelButton1Id,
					data.rectButton1))
			{
				application.setMainMaterial(data.material);
				dirty = true;
			}

			if (secondaryMaterialDrawer.draw(nkContext, data, secondaryMaterial,
					data.panelButton2Id, data.rectButton2))
			{
				application.setSecondaryMaterial(data.material);
				dirty = true;
			}
		}

		primaryMaterialDrawer.finish();
		secondaryMaterialDrawer.finish();

		backgroundColor.a(defaultAlpha);

		return res;
	}

	public static final class LineData
	{
		public static final int TEXT_WIDTH = 80;

		public final Material material;
		public final ByteBuffer textBuffer;
		public final NkColor color;
		public final NkRect rectLabel = NkRect.create();
		public final NkRect rectButton1 = NkRect.create();
		public final NkRect rectButton2 = NkRect.create();
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

			final String name = material.getName();

			panelLabelId = MemoryUtil.memUTF8("Label" + name);
			panelButton1Id = MemoryUtil.memUTF8("Button1" + name);
			panelButton2Id = MemoryUtil.memUTF8("Button2" + name);
			textBuffer = MemoryUtil.memUTF8(name);
		}

		public void free()
		{
			color.free();
			MemoryUtil.memFree(panelLabelId);
			MemoryUtil.memFree(panelButton1Id);
			MemoryUtil.memFree(panelButton2Id);
			MemoryUtil.memFree(textBuffer);
		}

		public void updateRect(int x, int y, int lineHeight)
		{
			rectLabel.set(x, y, TEXT_WIDTH, lineHeight);
			rectButton1.set(x + TEXT_WIDTH, y, lineHeight, lineHeight);
			rectButton2.set(x + TEXT_WIDTH + lineHeight, y, lineHeight, lineHeight);
		}
	}
}
