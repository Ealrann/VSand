import org.sheepy.lily.core.api.adapter.annotation.Adapters;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vsand.constants.BoardConstantBufferAdapter;
import org.sheepy.vsand.constants.DrawConstantBufferAdapter;
import org.sheepy.vsand.constants.PixelConstantBufferAdapter;
import org.sheepy.vsand.draw.DrawCircleAdapter;
import org.sheepy.vsand.draw.DrawLineAdapter;
import org.sheepy.vsand.draw.DrawSquareAdapter;
import org.sheepy.vsand.loader.Board1BufferLoader;
import org.sheepy.vsand.loader.Board2BufferLoader;
import org.sheepy.vsand.loader.BoardImageLoader;
import org.sheepy.vsand.loader.ChunkBufferLoader;
import org.sheepy.vsand.loader.ConfigurationBufferLoader;
import org.sheepy.vsand.loader.TransformationBufferLoader;
import org.sheepy.vsand.logic.ApplicationBehaviour;
import org.sheepy.vsand.model.VSandModelExtension;
import org.sheepy.vsand.ui.MaterialSelectorInputProviderAdapter;

@Adapters(classifiers = {
		ConfigurationBufferLoader.class,
		Board1BufferLoader.class,
		Board2BufferLoader.class,
		ChunkBufferLoader.class,
		BoardImageLoader.class,
		TransformationBufferLoader.class,
		BoardConstantBufferAdapter.class,
		DrawConstantBufferAdapter.class,
		PixelConstantBufferAdapter.class,
		DrawCircleAdapter.class,
		DrawSquareAdapter.class,
		DrawLineAdapter.class,
		ApplicationBehaviour.class,
		MaterialSelectorInputProviderAdapter.class
})

module org.sheepy.vsand
{
	requires static openj9.sharedclasses;

	requires org.sheepy.lily.vulkan.base;
	requires org.sheepy.lily.vulkan.extra.api;
	requires org.sheepy.lily.vulkan.extra.nuklear;

	opens org.sheepy.vsand;
	opens org.sheepy.vsand.constants;
	opens org.sheepy.vsand.draw;
	opens org.sheepy.vsand.loader;
	opens org.sheepy.vsand.logic;
	opens org.sheepy.vsand.ui;

	uses IInputManager;

	provides IModelExtension with VSandModelExtension;
}
