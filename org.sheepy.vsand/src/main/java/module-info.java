import org.logoce.lmf.core.api.extender.IAdapterProvider;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.vsand.Extenders;
import org.sheepy.vsand.model.VSandModelExtension;

module org.sheepy.vsand {
	// requires openj9.sharedclasses;

        requires transitive org.sheepy.lily.vulkan.api;
        requires transitive org.sheepy.lily.vulkan.extra.api;
        requires logoce.lmf.core.loader;

	exports org.sheepy.vsand;
	exports org.sheepy.vsand.logic;
	exports org.sheepy.vsand.fetch;

	exports org.sheepy.vsand.model;
	exports org.sheepy.vsand.model.vsand;

	opens org.sheepy.vsand;
	opens org.sheepy.vsand.constants;
	opens org.sheepy.vsand.draw;
	opens org.sheepy.vsand.fetch;
	opens org.sheepy.vsand.input;
	opens org.sheepy.vsand.loader;
	opens org.sheepy.vsand.logic;
	opens org.sheepy.vsand.ui;

	uses IInputManager;

	provides IModelExtension with VSandModelExtension;
	provides IAdapterProvider with Extenders;
}
