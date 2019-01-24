import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.cadence.IMainLoop;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vsand.VSandMainLoop;
import org.sheepy.vsand.adapter.MaterialSelectorPanelAdapter;
import org.sheepy.vsand.adapter.RepeatComputePipelineAdapter;
import org.sheepy.vsand.adapter.VSandConstantAdapter;
import org.sheepy.vsand.model.VSandModelExtension;

module org.sheepy.vsand
{
	requires org.sheepy.lily.vulkan.nuklear;

	requires org.sheepy.lily.core.impl;

	requires org.sheepy.lily.vulkan.api;
	requires org.sheepy.lily.vulkan.common;
	requires org.sheepy.lily.vulkan.process;
	requires org.sheepy.lily.vulkan.process.compute;
	requires org.sheepy.lily.vulkan.process.graphic;
	requires org.sheepy.lily.vulkan.resource;

	opens org.sheepy.vsand.adapter;
	opens org.sheepy.vsand;

	uses IInputManager;

	provides IAdapter
			with RepeatComputePipelineAdapter, VSandConstantAdapter, MaterialSelectorPanelAdapter;
	provides IModelExtension with VSandModelExtension;
	provides IMainLoop with VSandMainLoop;
}
