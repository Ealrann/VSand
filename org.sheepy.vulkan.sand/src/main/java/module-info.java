import org.sheepy.common.api.adapter.IAdapter;
import org.sheepy.common.api.cadence.IMainLoop;
import org.sheepy.common.api.input.IInputManager;
import org.sheepy.common.api.resource.IModelExtension;
import org.sheepy.vulkan.sand.VSandMainLoop;
import org.sheepy.vulkan.sand.VSandModelExtension;
import org.sheepy.vulkan.sand.adapter.MaterialSelectorPanelAdapter;
import org.sheepy.vulkan.sand.adapter.RepeatComputePipelineAdapter;
import org.sheepy.vulkan.sand.adapter.VSandConstantAdapter;

module org.sheepy.vulkan.sand
{
	requires org.sheepy.vulkan.nuklear;

	requires org.sheepy.common.impl;

	requires org.sheepy.vulkan.api;
	requires org.sheepy.vulkan.common;
	requires org.sheepy.vulkan.process;
	requires org.sheepy.vulkan.process.compute;
	requires org.sheepy.vulkan.process.graphic;
	requires org.sheepy.vulkan.resource;

	opens org.sheepy.vulkan.sand.adapter;
	opens org.sheepy.vulkan.sand;

	uses IInputManager;

	provides IAdapter
			with RepeatComputePipelineAdapter, VSandConstantAdapter, MaterialSelectorPanelAdapter;
	provides IModelExtension with VSandModelExtension;
	provides IMainLoop with VSandMainLoop;
}
