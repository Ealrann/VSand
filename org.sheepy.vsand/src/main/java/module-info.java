import org.sheepy.lily.core.api.adapter.annotation.Adapters;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vsand.adapter.BoardBufferLoader;
import org.sheepy.vsand.adapter.BoardDecisionLoader;
import org.sheepy.vsand.adapter.BoardImageLoader;
import org.sheepy.vsand.adapter.ConfigurationBufferLoader;
import org.sheepy.vsand.adapter.MaterialSelectorPanelAdapter;
import org.sheepy.vsand.adapter.ModificationBufferLoader;
import org.sheepy.vsand.adapter.RepeatComputePipelineAdapter;
import org.sheepy.vsand.adapter.TransformationBufferLoader;
import org.sheepy.vsand.adapter.VSandConstantAdapter;
import org.sheepy.vsand.model.VSandModelExtension;

@Adapters(classifiers = {
		RepeatComputePipelineAdapter.class,
		VSandConstantAdapter.class,
		MaterialSelectorPanelAdapter.class,
		ConfigurationBufferLoader.class,
		BoardBufferLoader.class,
		BoardImageLoader.class,
		BoardDecisionLoader.class,
		TransformationBufferLoader.class,
		ModificationBufferLoader.class
})

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

	provides IModelExtension with VSandModelExtension;
}
