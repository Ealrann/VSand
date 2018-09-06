package org.sheepy.vulkan.imgui;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.IntBuffer;
import java.util.Collections;

import org.joml.Math;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkGraphicsPipelineCreateInfo;
import org.lwjgl.vulkan.VkPipelineColorBlendAttachmentState;
import org.lwjgl.vulkan.VkPipelineColorBlendStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineDepthStencilStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineDynamicStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineInputAssemblyStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineMultisampleStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineViewportStateCreateInfo;
import org.lwjgl.vulkan.VkRect2D;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;
import org.lwjgl.vulkan.VkViewport;
import org.sheepy.vulkan.command.graphic.RenderCommandBuffer;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.descriptor.IDescriptorSetContext;
import org.sheepy.vulkan.pipeline.AbstractPipeline;
import org.sheepy.vulkan.pipeline.graphic.GraphicContext;
import org.sheepy.vulkan.pipeline.graphic.IGraphicExecutable;
import org.sheepy.vulkan.pipeline.graphic.IGraphicProcessUnit;
import org.sheepy.vulkan.resource.Shader;
import org.sheepy.vulkan.util.ModuleResource;

import glm_.vec2.Vec2;
import glm_.vec2.Vec2i;
import imgui.Context;
import imgui.DrawCmd;
import imgui.DrawData;
import imgui.DrawList;
import imgui.IO;
import imgui.ImGui;
import imgui.Style;

public class ImGuiGraphicPipeline extends AbstractPipeline
		implements IAllocable, IGraphicProcessUnit, IGraphicExecutable, IDescriptorSetContext
{
	private final static String IMGUI_VERT_SHADER = "org/sheepy/vulkan/imgui/ui.vert.spv";
	private final static String IMGUI_FRAG_SHADER = "org/sheepy/vulkan/imgui/ui.frag.spv";

	// ----------------------------------------------------------------------------
	// ImGUI class
	// ----------------------------------------------------------------------------
	// Vulkan resources for rendering the UI
	private final static ImGuiFontTexture font = new ImGuiFontTexture();
	private long pipeline;

	private ImGui imgui;
	private IO io;

	private long[] lArray = new long[1];
	private VkViewport.Buffer viewport;
	private VkRect2D.Buffer scissorRect;

	private UIDescriptor uiDescriptor;
	private ImGuiVertexBuffer texture;

	private GraphicContext context;

	boolean firstFrame = true;
	private Shader vertShader;
	private Shader fragShader;

	public ImGuiGraphicPipeline(GraphicContext context, UIDescriptor uiConfiguration)
	{
		super(context, Collections.singletonList(font));
		this.uiDescriptor = uiConfiguration;
		this.context = context;

		imgui = ImGui.INSTANCE;
		new Context();

		setPushConstant(new ImGuiPushConstant(context.logicalDevice, imgui.getIo()));

		texture = new ImGuiVertexBuffer(context.logicalDevice, context.commandPool);

		Module thisModule = this.getClass().getModule();
		ModuleResource vertShaderResource = new ModuleResource(thisModule, IMGUI_VERT_SHADER);
		ModuleResource fragShaderResource = new ModuleResource(thisModule, IMGUI_FRAG_SHADER);

		vertShader = new Shader(context.logicalDevice, vertShaderResource,
				VK_SHADER_STAGE_VERTEX_BIT);
		fragShader = new Shader(context.logicalDevice, fragShaderResource,
				VK_SHADER_STAGE_FRAGMENT_BIT);

		context.resourceManager.addResource(vertShader);
		context.resourceManager.addResource(fragShader);

		context.resourceManager.addResource(font);
		context.resourceManager.addResource(texture);

	}

	@Override
	public void allocate(MemoryStack stack)
	{
		super.allocate(stack);

		firstFrame = true;

		viewport = VkViewport.calloc(1);
		scissorRect = VkRect2D.calloc(1);

		init();

		buildPipeline(stack);

		newFrame();
		updateBuffers();
	}

	@Override
	public void free()
	{
		// Release all Vulkan resources required for rendering imGui
		viewport.free();
		scissorRect.free();

		vkDestroyPipeline(context.getVkDevice(), pipeline, null);

		super.free();
	}

	// Initialize styles, keys, etc.
	private void init()
	{
		// Color scheme
		Style style = imgui.getStyle();

		uiDescriptor.configureStyle(style);

		int[] size = uiDescriptor.getSize();
		io = imgui.getIo();
		io.setDisplaySize(new Vec2i(size[0], size[1]));
		io.setDisplayFramebufferScale(new Vec2(1.0f, 1.0f));
	}

	@Override
	public boolean update()
	{
		if (newFrame())
		{
			updateBuffers();
			return true;
		}
		return false;
	}

	// Starts a new imGui frame and sets up windows and ui elements
	private boolean newFrame()
	{
		imgui.newFrame();

		if (uiDescriptor.newFrame(imgui) || firstFrame)
		{

			while (uiDescriptor.isDirty())
			{
				imgui.endFrame();
				imgui.newFrame();
				uiDescriptor.setDirty(false);
				uiDescriptor.newFrame(imgui);
			}

			// Render to generate draw buffers
			imgui.render();
			firstFrame = false;
			return true;
		}
		else
		{
			imgui.endFrame();
			return false;
		}
	}

	// Update vertex and index buffer containing the imGui elements when
	// required
	public void updateBuffers()
	{
		DrawData imDrawData = imgui.getDrawData();

		if (imDrawData != null) texture.update(imDrawData);
	}

	// Draw current imGui frame into a command buffer
	@Override
	public void execute(RenderCommandBuffer renderCommandBuffer)
	{
		VkCommandBuffer commandBuffer = renderCommandBuffer.getVkCommandBuffer();
		vkCmdBindPipeline(commandBuffer, VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline);

		texture.bind(commandBuffer);

		viewport.get(0).set(0, 0, io.getDisplaySize().getX(), io.getDisplaySize().getY(), 1, 1);
		vkCmdSetViewport(commandBuffer, 0, viewport);

		// Render commands
		int vertexOffset = 0;
		int indexOffset = 0;
		DrawData imDrawData = imgui.getDrawData();
		for (int i = 0; i < imDrawData.getCmdListsCount(); i++)
		{
			DrawList cmd_list = imDrawData.getCmdLists().get(i);
			for (int j = 0; j < cmd_list.getCmdBuffer().size(); j++)
			{
				DrawCmd pcmd = cmd_list.getCmdBuffer().get(j);
				scissorRect.offset().set((int) Math.max(pcmd.getClipRect().getX(), 0),
						(int) Math.max(pcmd.getClipRect().getY(), 0));
				scissorRect.extent()
						.width((int) (pcmd.getClipRect().getZ() - pcmd.getClipRect().getX()));
				scissorRect.extent()
						.height((int) (pcmd.getClipRect().getW() - pcmd.getClipRect().getY()));
				vkCmdSetScissor(commandBuffer, 0, scissorRect);
				vkCmdDrawIndexed(commandBuffer, pcmd.getElemCount(), 1, indexOffset, vertexOffset,
						0);
				indexOffset += pcmd.getElemCount();
			}
			vertexOffset += cmd_list.getVtxBuffer().size();
		}
	}

	private void VK_CHECK_RESULT(int res)
	{
		if (res != VK_SUCCESS)
		{
			throw new AssertionError("Error creating UI");
		}
	}

	// Initialize all Vulkan resources used by the ui
	public void buildPipeline(MemoryStack stack)
	{
		// Setup graphics pipeline for UI rendering
		VkPipelineInputAssemblyStateCreateInfo inputAssemblyState = VkPipelineInputAssemblyStateCreateInfo
				.callocStack(stack);
		inputAssemblyState.sType(VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO);
		inputAssemblyState.topology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST);
		inputAssemblyState.primitiveRestartEnable(false);

		VkPipelineRasterizationStateCreateInfo rasterizationState = VkPipelineRasterizationStateCreateInfo
				.callocStack(stack);
		rasterizationState.lineWidth(1.0f);
		rasterizationState.sType(VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO);
		rasterizationState.cullMode(VK_CULL_MODE_NONE);
		rasterizationState.polygonMode(VK_POLYGON_MODE_FILL);
		rasterizationState.frontFace(VK_FRONT_FACE_COUNTER_CLOCKWISE);

		// Enable blending
		VkPipelineColorBlendAttachmentState.Buffer blendAttachmentState = VkPipelineColorBlendAttachmentState
				.callocStack(1, stack);
		blendAttachmentState.blendEnable(true);
		blendAttachmentState.colorWriteMask(VK_COLOR_COMPONENT_R_BIT
				| VK_COLOR_COMPONENT_G_BIT
				| VK_COLOR_COMPONENT_B_BIT
				| VK_COLOR_COMPONENT_A_BIT);
		blendAttachmentState.srcColorBlendFactor(VK_BLEND_FACTOR_SRC_ALPHA);
		blendAttachmentState.dstColorBlendFactor(VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA);
		blendAttachmentState.colorBlendOp(VK_BLEND_OP_ADD);
		blendAttachmentState.srcAlphaBlendFactor(VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA);
		blendAttachmentState.dstAlphaBlendFactor(VK_BLEND_FACTOR_ZERO);
		blendAttachmentState.alphaBlendOp(VK_BLEND_OP_ADD);

		VkPipelineColorBlendStateCreateInfo colorBlendState = VkPipelineColorBlendStateCreateInfo
				.callocStack(stack);
		colorBlendState.sType(VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO);
		colorBlendState.pAttachments(blendAttachmentState);

		VkPipelineDepthStencilStateCreateInfo depthStencilState = VkPipelineDepthStencilStateCreateInfo
				.callocStack(stack);
		depthStencilState.depthTestEnable(false);
		depthStencilState.depthWriteEnable(false);
		depthStencilState.depthCompareOp(VK_COMPARE_OP_LESS_OR_EQUAL);

		VkPipelineViewportStateCreateInfo viewportState = VkPipelineViewportStateCreateInfo
				.callocStack(stack);
		viewportState.scissorCount(1);
		viewportState.viewportCount(1);
		viewportState.sType(VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO);

		VkPipelineMultisampleStateCreateInfo multisampleState = VkPipelineMultisampleStateCreateInfo
				.callocStack(stack);
		multisampleState.sType(VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO);
		multisampleState.rasterizationSamples(VK_SAMPLE_COUNT_1_BIT);

		IntBuffer dynamicStates = MemoryUtil.memAllocInt(2);
		dynamicStates.put(VK_DYNAMIC_STATE_VIEWPORT);
		dynamicStates.put(VK_DYNAMIC_STATE_SCISSOR);
		dynamicStates.flip();

		VkPipelineDynamicStateCreateInfo dynamicState = VkPipelineDynamicStateCreateInfo.malloc();
		dynamicState.sType(VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO);
		dynamicState.pDynamicStates(dynamicStates);
		dynamicState.pNext(VK_NULL_HANDLE);
		dynamicState.flags(0);

		VkPipelineShaderStageCreateInfo.Buffer shaderStages = VkPipelineShaderStageCreateInfo
				.callocStack(2, stack);

		VkGraphicsPipelineCreateInfo.Buffer pipelineCreateInfo = VkGraphicsPipelineCreateInfo
				.callocStack(1, stack);
		pipelineCreateInfo.sType(VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO);
		pipelineCreateInfo.layout(pipelineLayout);
		pipelineCreateInfo.renderPass(context.configuration.renderPass.getId());

		pipelineCreateInfo.pInputAssemblyState(inputAssemblyState);
		pipelineCreateInfo.pRasterizationState(rasterizationState);
		pipelineCreateInfo.pColorBlendState(colorBlendState);
		pipelineCreateInfo.pMultisampleState(multisampleState);
		pipelineCreateInfo.pViewportState(viewportState);
		pipelineCreateInfo.pDepthStencilState(depthStencilState);
		pipelineCreateInfo.pDynamicState(dynamicState);
		pipelineCreateInfo.pStages(shaderStages);

		// Vertex bindings an attributes based on ImGui vertex definition
		VkVertexInputBindingDescription.Buffer vertexInputBindings = ImGuiVertexBuffer.VERTEX_DESCRIPTOR
				.allocBindingDescription();

		VkVertexInputAttributeDescription.Buffer vertexInputAttributes = ImGuiVertexBuffer.VERTEX_DESCRIPTOR
				.allocAttributeDescriptions();

		VkPipelineVertexInputStateCreateInfo vertexInputState = VkPipelineVertexInputStateCreateInfo
				.callocStack(stack);
		vertexInputState.sType(VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO);
		vertexInputState.pVertexBindingDescriptions(vertexInputBindings);
		vertexInputState.pVertexAttributeDescriptions(vertexInputAttributes);
		pipelineCreateInfo.pVertexInputState(vertexInputState);

		shaderStages.put(vertShader.allocInfo());
		shaderStages.put(fragShader.allocInfo());
		shaderStages.flip();

		VK_CHECK_RESULT(vkCreateGraphicsPipelines(context.getVkDevice(), VK_NULL_HANDLE,
				pipelineCreateInfo, null, lArray));
		pipeline = lArray[0];
	}
}
