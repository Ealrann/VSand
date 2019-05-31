import org.sheepy.lily.core.api.adapter.annotation.Adapters;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vsand.loader.BoardBufferLoader;
import org.sheepy.vsand.loader.BoardDecisionLoader;
import org.sheepy.vsand.loader.BoardImageLoader;
import org.sheepy.vsand.loader.ConfigurationBufferLoader;
import org.sheepy.vsand.loader.ModificationBufferLoader;
import org.sheepy.vsand.loader.TransformationBufferLoader;
import org.sheepy.vsand.loader.VSandPushConstantAdapter;
import org.sheepy.vsand.model.VSandModelExtension;
import org.sheepy.vsand.ui.MaterialSelectorPanelAdapter;

@Adapters(classifiers = {
		MaterialSelectorPanelAdapter.class,
		ConfigurationBufferLoader.class,
		BoardBufferLoader.class,
		BoardImageLoader.class,
		BoardDecisionLoader.class,
		TransformationBufferLoader.class,
		ModificationBufferLoader.class,
		VSandPushConstantAdapter.class
})

module org.sheepy.vsand
{
	requires org.sheepy.lily.vulkan.extra.nuklear;

	requires org.sheepy.lily.core.impl;

	requires org.sheepy.lily.vulkan.api;
	requires org.sheepy.lily.vulkan.common;
	requires org.sheepy.lily.vulkan.process;
	requires org.sheepy.lily.vulkan.process.compute;
	requires org.sheepy.lily.vulkan.process.graphic;
	requires org.sheepy.lily.vulkan.resource;

	requires org.sheepy.lily.vulkan.extra.api;
	requires org.sheepy.lily.vulkan.extra.graphic;

	opens org.sheepy.vsand;
	opens org.sheepy.vsand.loader;
	opens org.sheepy.vsand.ui;

	uses IInputManager;

	provides IModelExtension with VSandModelExtension;
}
