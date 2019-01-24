import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.cadence.IMainLoop;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vulkan.sand.VSandMainLoop;
import org.sheepy.vulkan.sand.adapter.MaterialSelectorPanelAdapter;
import org.sheepy.vulkan.sand.adapter.RepeatComputePipelineAdapter;
import org.sheepy.vulkan.sand.adapter.VSandConstantAdapter;
import org.sheepy.vulkan.sand.model.VSandModelExtension;

module org.sheepy.vulkan.sand
{
	requires org.sheepy.vulkan.nuklear;

	requires org.sheepy.lily.core.impl;

	requires org.sheepy.lily.vulkan.api;
	requires org.sheepy.lily.vulkan.common;
	requires org.sheepy.lily.vulkan.process;
	requires org.sheepy.lily.vulkan.process.compute;
	requires org.sheepy.lily.vulkan.process.graphic;
	requires org.sheepy.lily.vulkan.resource;

	opens org.sheepy.vulkan.sand.adapter;
	opens org.sheepy.vulkan.sand;

	uses IInputManager;

	provides IAdapter
			with RepeatComputePipelineAdapter, VSandConstantAdapter, MaterialSelectorPanelAdapter;
	provides IModelExtension with VSandModelExtension;
	provides IMainLoop with VSandMainLoop;
}
