import org.sheepy.common.api.adapter.IAdapter;
import org.sheepy.vulkan.nuklear.adapter.ButtonAdapter;
import org.sheepy.vulkan.nuklear.adapter.DynamicRowLayoutAdapter;
import org.sheepy.vulkan.nuklear.adapter.LabelAdapter;
import org.sheepy.vulkan.nuklear.adapter.PanelAdapter;
import org.sheepy.vulkan.nuklear.adapter.SliderAdapter;
import org.sheepy.vulkan.nuklear.adapter.VariableLabelAdapter;
import org.sheepy.vulkan.nuklear.pipeline.NuklearConstantsAdapter;
import org.sheepy.vulkan.nuklear.pipeline.NuklearPipelineAdapter;
import org.sheepy.vulkan.nuklear.pipeline.NuklearVertexBufferAdapter;

module org.sheepy.vulkan.nuklear
{
	requires transitive org.sheepy.vulkan.resource;
	requires transitive org.sheepy.vulkan.api;
	requires transitive org.sheepy.vulkan.process;
	requires transitive org.sheepy.vulkan.process.graphic;

	requires transitive org.lwjgl.nuklear;
	requires org.lwjgl.nuklear.natives;

	exports org.sheepy.vulkan.nuklear.adapter;
	exports org.sheepy.vulkan.nuklear.model;
	exports org.sheepy.vulkan.nuklear.model.impl;
	exports org.sheepy.vulkan.nuklear.model.util;

	opens org.sheepy.vulkan.nuklear.adapter;
	opens org.sheepy.vulkan.nuklear.pipeline;
	opens org.sheepy.vulkan.nuklear;

	provides IAdapter with NuklearPipelineAdapter, NuklearConstantsAdapter,
			NuklearVertexBufferAdapter, ButtonAdapter, PanelAdapter, LabelAdapter,
			DynamicRowLayoutAdapter, VariableLabelAdapter, SliderAdapter;

}
