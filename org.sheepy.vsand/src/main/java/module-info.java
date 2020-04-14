import org.sheepy.lily.core.api.adapter.IAdapterProvider;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vsand.Adapters;
import org.sheepy.vsand.model.VSandModelExtension;


module org.sheepy.vsand
{
	// requires openj9.sharedclasses;

	requires transitive org.sheepy.lily.vulkan.api;
	requires org.sheepy.lily.vulkan.extra.api;

	exports org.sheepy.vsand;

	opens org.sheepy.vsand;
	opens org.sheepy.vsand.constants;
	opens org.sheepy.vsand.draw;
	opens org.sheepy.vsand.loader;
	opens org.sheepy.vsand.logic;
	opens org.sheepy.vsand.ui;

	uses IInputManager;

	provides IModelExtension with VSandModelExtension;
	provides IAdapterProvider with Adapters;
}
