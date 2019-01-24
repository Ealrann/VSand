package org.sheepy.vulkan.nuklear.pipeline;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.nuklear.NkAllocator;
import org.lwjgl.nuklear.NkBuffer;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkViewport;
import org.sheepy.lily.core.api.application.IApplicationAdapter;
import org.sheepy.lily.core.api.input.event.IInputEvent;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.core.model.application.IView;
import org.sheepy.lily.core.model.presentation.IPanel;
import org.sheepy.lily.core.model.presentation.IUIView;
import org.sheepy.lily.core.model.presentation.UIPage;
import org.sheepy.lily.vulkan.common.execution.IResourceAllocable;
import org.sheepy.lily.vulkan.common.input.VulkanInputManager;
import org.sheepy.lily.vulkan.common.util.ModelUtil;
import org.sheepy.lily.vulkan.common.window.Window;
import org.sheepy.lily.vulkan.model.enumeration.ECullMode;
import org.sheepy.lily.vulkan.model.process.graphic.ColorBlend;
import org.sheepy.lily.vulkan.model.process.graphic.DynamicState;
import org.sheepy.lily.vulkan.model.process.graphic.Rasterizer;
import org.sheepy.lily.vulkan.model.process.graphic.ViewportState;
import org.sheepy.lily.vulkan.model.process.graphic.impl.RasterizerImpl;
import org.sheepy.lily.vulkan.model.resource.AbstractConstants;
import org.sheepy.lily.vulkan.model.resource.Shader;
import org.sheepy.vulkan.nuklear.adapter.IUIElementAdapter;
import org.sheepy.vulkan.nuklear.adapter.IUIElementAdapter.UIContext;
import org.sheepy.vulkan.nuklear.input.NuklearInputCatcher;
import org.sheepy.vulkan.nuklear.model.NuklearPackage;
import org.sheepy.vulkan.nuklear.model.NuklearPipeline;
import org.sheepy.vulkan.nuklear.pipeline.draw.DrawCommandData;
import org.sheepy.vulkan.nuklear.pipeline.draw.DrawRecorder;
import org.sheepy.vulkan.nuklear.pipeline.draw.NuklearDrawer;
import org.sheepy.vulkan.nuklear.pipeline.draw.NuklearResources;
import org.sheepy.vulkan.nuklear.pipeline.factory.ColorBlendFactory;
import org.sheepy.vulkan.nuklear.pipeline.factory.DynamicStateFactory;
import org.sheepy.vulkan.nuklear.pipeline.factory.ViewportStateFactory;
import org.sheepy.lily.vulkan.process.graphic.execution.GraphicCommandBuffer;
import org.sheepy.lily.vulkan.process.graphic.pipeline.IGraphicsPipelineAdapter;
import org.sheepy.lily.vulkan.process.graphic.process.GraphicContext;
import org.sheepy.lily.vulkan.process.graphic.process.IGraphicContextAdapter;
import org.sheepy.lily.vulkan.process.graphic.swapchain.SwapChainManager.Extent2D;
import org.sheepy.lily.vulkan.resource.descriptor.IVkDescriptorSet;
import org.sheepy.lily.vulkan.resource.indexed.IVertexBufferDescriptor;

public class NuklearPipelineAdapter extends IGraphicsPipelineAdapter
{
	private static final int BUFFER_INITIAL_SIZE = 4 * 1024;
	private static final NkAllocator ALLOCATOR;
	static
	{
		ALLOCATOR = NkAllocator.calloc().alloc((handle, old, size) -> nmemAllocChecked(size))
				.mfree((handle, ptr) -> nmemFree(ptr));
	}

	private final NuklearInputCatcher inputCatcher = new NuklearInputCatcher();
	private final NkBuffer cmds = NkBuffer.create();
	private NkContext nkContext;

	private DrawRecorder recorder;
	private NuklearDrawer drawer;

	private GraphicContext context;
	private Window window;

	private boolean dirty = true;
	private VkViewport.Buffer viewport;

	private NuklearPipeline nkPipeline;

	private NuklearResources resources;

	@Override
	public void setTarget(Notifier target)
	{
		super.setTarget(target);
		nkPipeline = (NuklearPipeline) target;

		resources = new NuklearResources(nkPipeline);
	}

	@Override
	public List<IResourceAllocable> getResources()
	{
		return resources.toList();
	}

	@Override
	public void deepAllocate(MemoryStack stack)
	{
		super.deepAllocate(stack);

		context = IGraphicContextAdapter.adapt(target).getContext(target);
		window = context.logicalDevice.window;
		viewport = VkViewport.calloc(1);

		var application = (Application) EcoreUtil.getRootContainer(nkPipeline);
		var cadencer = IApplicationAdapter.adapt(application).getCadencer();
		var inputManager = (VulkanInputManager) cadencer.getInputManager();

		resources.allocate();

		createContext();
		inputManager.setInputCatcher(inputCatcher);

		recorder = new DrawRecorder(nkContext, application.isDebug());
		drawer = new NuklearDrawer(getDescriptorSets(), resources, pipelineLayout);

		// Prepare a first render for the opening of the screen
		layout(Collections.emptyList());
		prepare();
	}

	@Override
	public void free()
	{
		super.free();

		// Release all Vulkan resources required for rendering imGui
		viewport.free();

		recorder = null;

		resources.free();

		nk_free(nkContext);
		nk_buffer_free(cmds);
	}

	private void createContext()
	{
		nkContext = NkContext.create();
		nk_init(nkContext, ALLOCATOR, resources.getDefaultFont());
		nk_buffer_init(cmds, ALLOCATOR, BUFFER_INITIAL_SIZE);

		nkContext.style().window().fixed_background().data().color().a((byte) 150);

		inputCatcher.configure(nkContext, window.getId(), this);

		nkContext.clip(it -> it.copy((handle, text, len) -> {
			if (len == 0)
			{
				return;
			}

			try (MemoryStack stack = MemoryStack.stackPush())
			{
				ByteBuffer str = stack.malloc(len + 1);
				MemoryUtil.memCopy(text, MemoryUtil.memAddress(str), len);
				str.put(len, (byte) 0);

				glfwSetClipboardString(window.getId(), str);
			}
		}).paste((handle, edit) -> {
			long text = nglfwGetClipboardString(window.getId());
			if (text != 0)
			{
				nnk_textedit_paste(edit, text, nnk_strlen(text));
			}
		}));
	}

	@Override
	public void prepare()
	{
		var indexBuffer = nkPipeline.getIndexBuffer();
		var indexBufferAdapter = NuklearVertexBufferAdapter.adapt(indexBuffer);

		indexBufferAdapter.update(nkContext, cmds);

		recorder.prepare(cmds);
	}

	public void layout(List<IInputEvent> events)
	{
		Application application = ModelUtil.getApplication(nkPipeline);
		IView view = application.getCurrentView();
		UIContext context = new UIContext(window, nkContext, events);

		if (view != null && view instanceof IUIView)
		{
			IUIView uiView = (IUIView) view;
			UIPage uiPage = uiView.getCurrentUIPage();
			if (uiPage != null)
			{
				for (IPanel panel : uiPage.getPanels())
				{
					var panelAdapter = IUIElementAdapter.adapt(panel);
					dirty |= panelAdapter.layout(context, panel);
				}
			}
		}
	}

	@Override
	public boolean isRecordNeeded()
	{
		if (nk_item_is_any_active(nkContext))
		{
			dirty = true;
		}

		dirty |= super.isRecordNeeded();

		if (dirty == false)
		{
			recorder.clear();
		}

		return dirty;
	}

	@Override
	public void record(GraphicCommandBuffer graphicCommandBuffer, int bindPoint)
	{
		var indexBuffer = nkPipeline.getIndexBuffer();
		var indexBufferAdapter = NuklearVertexBufferAdapter.adapt(indexBuffer);
		var commandBuffer = graphicCommandBuffer.getVkCommandBuffer();

		vkCmdBindPipeline(commandBuffer, bindPoint, pipelineId);

		indexBufferAdapter.bind(commandBuffer);

		setViewport(commandBuffer);
		pushConstants(commandBuffer);

		drawer.prepare(bindPoint, context.swapChainManager.getExtent());
		for (DrawCommandData data : recorder.getDrawCommands())
		{
			drawer.draw(graphicCommandBuffer, data);
		}

		dirty = false;
	}

	private void setViewport(VkCommandBuffer commandBuffer)
	{
		Extent2D extent = context.swapChainManager.getExtent();
		viewport.get(0).set(0, 0, extent.getWidth(), extent.getHeight(), 1, 1);
		vkCmdSetViewport(commandBuffer, 0, viewport);
	}

	private void pushConstants(VkCommandBuffer commandBuffer)
	{
		var pushAdapter = NuklearConstantsAdapter.adapt(nkPipeline.getPushConstant());
		vkCmdPushConstants(commandBuffer, pipelineLayout, VK_SHADER_STAGE_VERTEX_BIT, 0,
				pushAdapter.getData());
	}

	@Override
	protected IVertexBufferDescriptor<?> getVertexBufferDescriptor()
	{
		return NuklearVertexBufferAdapter.VERTEX_DESCRIPTOR;
	}

	@Override
	protected AbstractConstants getConstants()
	{
		return nkPipeline.getPushConstant();
	}

	@Override
	public List<IVkDescriptorSet> getDescriptorSets()
	{
		return resources.getDescriptorSets();
	}

	@Override
	protected List<Shader> getShaders()
	{
		return resources.getShaders();
	}

	@Override
	protected ViewportState getViewportState()
	{
		return ViewportStateFactory.create();
	}

	@Override
	protected Rasterizer getRasterizer()
	{
		var rasterizer = new RasterizerImpl();
		rasterizer.setCullMode(ECullMode.NONE);
		return rasterizer;
	}

	@Override
	protected ColorBlend getColorBlend()
	{
		return ColorBlendFactory.create();
	}

	@Override
	protected DynamicState getDynamicState()
	{
		return DynamicStateFactory.create();
	}

	@Override
	protected int getSubpass()
	{
		return nkPipeline.getSubpass();
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return NuklearPackage.Literals.NUKLEAR_PIPELINE == eClass;
	}
}
