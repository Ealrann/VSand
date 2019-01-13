package org.sheepy.vulkan.nuklear.pipeline;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.vulkan.VK10.*;

import org.eclipse.emf.ecore.EClass;
import org.lwjgl.PointerBuffer;
import org.lwjgl.nuklear.NkBuffer;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkConvertConfig;
import org.lwjgl.nuklear.NkDrawNullTexture;
import org.lwjgl.nuklear.NkDrawVertexLayoutElement;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkDevice;
import org.sheepy.common.api.adapter.IServiceAdapterFactory;
import org.sheepy.vulkan.common.device.LogicalDevice;
import org.sheepy.vulkan.common.execution.ExecutionManager;
import org.sheepy.vulkan.nuklear.model.NuklearIndexBuffer;
import org.sheepy.vulkan.nuklear.model.NuklearPackage;
import org.sheepy.vulkan.nuklear.pipeline.NuklearVertexDescriptor.GuiVertex;
import org.sheepy.vulkan.resource.ResourceAdapter;
import org.sheepy.vulkan.resource.indexed.IndexBuffer;

public class NuklearVertexBufferAdapter extends ResourceAdapter
{
	public static final NuklearVertexDescriptor VERTEX_DESCRIPTOR = new NuklearVertexDescriptor();

	private static final int VERTEX_BUFFER_SIZE = 512 * 1024;
	private static final int INDEX_BUFFER_SIZE = 128 * 1024;

	private static final NkDrawVertexLayoutElement.Buffer VERTEX_LAYOUT;

	static
	{
		VERTEX_LAYOUT = NkDrawVertexLayoutElement.create(4);
		VERTEX_LAYOUT.position(0).attribute(NK_VERTEX_POSITION).format(NK_FORMAT_FLOAT).offset(0);
		VERTEX_LAYOUT.position(1).attribute(NK_VERTEX_TEXCOORD).format(NK_FORMAT_FLOAT).offset(8);
		VERTEX_LAYOUT.position(2).attribute(NK_VERTEX_COLOR).format(NK_FORMAT_R8G8B8A8).offset(16);
		VERTEX_LAYOUT.position(3).attribute(NK_VERTEX_ATTRIBUTE_COUNT).format(NK_FORMAT_COUNT)
				.offset(0);
		VERTEX_LAYOUT.flip();
	}

	private final NkDrawNullTexture nkNullTexture = NkDrawNullTexture.create();
	private final NkConvertConfig config = NkConvertConfig.create();

	private final IndexBuffer<?>[] indexBuffers = new IndexBuffer[2];
	private int currentIndexBuffer = -1;

	private LogicalDevice logicalDevice;

	private NullTexture nullTexture = null;
	private NkBuffer.Buffer vbuf;
	private NkBuffer.Buffer ebuf;

	private PointerBuffer[] vertexMemories = new PointerBuffer[2];
	private PointerBuffer[] indexMemories = new PointerBuffer[2];

	private long vertexSize;

	private long indexSize;

	@Override
	public void allocate(MemoryStack stack, ExecutionManager executionManager)
	{
		nkNullTexture.texture().ptr(nullTexture.getSamplerId());
		nkNullTexture.uv().set(0.5f, 0.5f);

		logicalDevice = executionManager.getLogicalDevice();
		VkDevice vkDevice = logicalDevice.getVkDevice();

		vbuf = NkBuffer.calloc(2);
		ebuf = NkBuffer.calloc(2);

		for (int i = 0; i < 2; i++)
		{
			indexBuffers[i] = new IndexBuffer<GuiVertex>(executionManager, VERTEX_DESCRIPTOR,
					VERTEX_BUFFER_SIZE, INDEX_BUFFER_SIZE);
			indexBuffers[i].allocate(stack);

			long vertexMemoryAddress = indexBuffers[i].getVertexBufferMemoryId();
			long indexMemoryAddress = indexBuffers[i].getIndexBufferMemoryId();

			vertexSize = indexBuffers[i].getVertexBuffer().getInfos().size;
			indexSize = indexBuffers[i].getIndexBuffer().getInfos().size;

			vertexMemories[i] = MemoryUtil.memAllocPointer(1);
			indexMemories[i] = MemoryUtil.memAllocPointer(1);

			PointerBuffer vertexMemory = vertexMemories[i];
			PointerBuffer indexMemory = indexMemories[i];

			vkMapMemory(vkDevice, vertexMemoryAddress, 0, vertexSize, 0, vertexMemory);
			vkMapMemory(vkDevice, indexMemoryAddress, 0, indexSize, 0, indexMemory);
		}

		config.null_texture(nkNullTexture);
		config.vertex_layout(VERTEX_LAYOUT);
		config.vertex_size(20);
		config.vertex_alignment(4);
		config.circle_segment_count(22);
		config.curve_segment_count(22);
		config.arc_segment_count(22);
		config.global_alpha(1.0f);
		config.shape_AA(NK_ANTI_ALIASING_ON);
		config.line_AA(NK_ANTI_ALIASING_ON);
	}

	@Override
	public void free()
	{
		VkDevice vkDevice = logicalDevice.getVkDevice();

		for (int i = 0; i < 2; i++)
		{
			vkUnmapMemory(vkDevice, indexBuffers[i].getVertexBufferMemoryId());
			vkUnmapMemory(vkDevice, indexBuffers[i].getIndexBufferMemoryId());

			indexBuffers[i].free();
			indexBuffers[i] = null;

			MemoryUtil.memFree(vertexMemories[i]);
			MemoryUtil.memFree(indexMemories[i]);
		}

		vbuf.free();
		ebuf.free();
		vbuf = null;
		ebuf = null;

		vertexMemories = null;
		indexMemories = null;
	}

	public void update(NkContext ctx, NkBuffer cmds)
	{
		currentIndexBuffer++;
		if (currentIndexBuffer == 2)
		{
			currentIndexBuffer = 0;
		}
		NkBuffer vertices = vbuf.get(currentIndexBuffer);
		NkBuffer elements = ebuf.get(currentIndexBuffer);

		PointerBuffer vertexMemory = vertexMemories[currentIndexBuffer];
		PointerBuffer indexMemory = indexMemories[currentIndexBuffer];

		nk_buffer_init_fixed(vertices, vertexMemory.getByteBuffer((int) vertexSize));
		nk_buffer_init_fixed(elements, indexMemory.getByteBuffer((int) indexSize));

		vertexMemory.flip();
		indexMemory.flip();

		// load draw vertices & elements directly into vertex + element buffer
		nk_convert(ctx, cmds, vertices, elements, config);
	}

	public void bind(VkCommandBuffer commandBuffer)
	{
		var indexBuffer = indexBuffers[currentIndexBuffer];

		// Bind vertex and index buffer
		long[] pBuffer = {
				indexBuffer.getVertexBufferId()
		};
		long[] offsets = {
				0
		};
		vkCmdBindVertexBuffers(commandBuffer, 0, pBuffer, offsets);

		vkCmdBindIndexBuffer(commandBuffer, indexBuffer.getIndexBufferId(), 0,
				VK_INDEX_TYPE_UINT16);
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return NuklearPackage.Literals.NUKLEAR_INDEX_BUFFER == eClass;
	}

	public static NuklearVertexBufferAdapter adapt(NuklearIndexBuffer buffer)
	{
		return IServiceAdapterFactory.INSTANCE.adapt(buffer, NuklearVertexBufferAdapter.class);
	}

	@Override
	public boolean isAllocationDirty()
	{
		return false;
	}

	public void setNullTexture(NullTexture nullTexture)
	{
		this.nullTexture = nullTexture;
	}
}
