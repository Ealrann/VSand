package org.sheepy.vulkan.imgui;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.sheepy.vulkan.buffer.IndexBuffer;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.imgui.ImGuiVertexDescriptor.ImGuiVertex;

import imgui.DrawData;
import imgui.DrawList;
import imgui.DrawVert;

public class ImGuiVertexBuffer implements IAllocable
{
	public static final ImGuiVertexDescriptor VERTEX_DESCRIPTOR = new ImGuiVertexDescriptor();
	private static final int SIZEOF_ImDrawVert = 20;
	private static final int SIZEOF_ImDrawIdx = 2;
	private static final int MAX_VERTEX_COUNT = 2000;
	private static final int MAX_INDEX_COUNT = 4500;

	private ByteBuffer stagingVertexBuffer;
	private ByteBuffer stagingIndexBuffer;

	private IndexBuffer<ImGuiVertex> indexBuffer;

	public ImGuiVertexBuffer(LogicalDevice logicalDevice, CommandPool commandPool)
	{
		indexBuffer = new IndexBuffer<ImGuiVertex>(logicalDevice, VERTEX_DESCRIPTOR,
				commandPool, MAX_VERTEX_COUNT, MAX_INDEX_COUNT);
	}

	public IndexBuffer<ImGuiVertex> getIndexBuffer()
	{
		return indexBuffer;
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		indexBuffer.allocate(stack);

		stagingVertexBuffer = MemoryUtil.memAlloc(MAX_VERTEX_COUNT * SIZEOF_ImDrawVert);
		stagingIndexBuffer = MemoryUtil.memAlloc(MAX_INDEX_COUNT * SIZEOF_ImDrawIdx);
	}

	@Override
	public void free()
	{
		indexBuffer.free();
	}

	public void update(DrawData imDrawData)
	{
		int vertexCount = imDrawData.getTotalVtxCount();
		int indexCount = imDrawData.getTotalIdxCount();

		if (vertexCount > MAX_VERTEX_COUNT || indexCount > MAX_INDEX_COUNT)
		{
			throw new AssertionError("UI Buffers too small");
		}

		for (int n = 0; n < imDrawData.getCmdListsCount(); n++)
		{
			DrawList cmd_list = imDrawData.getCmdLists().get(n);

			for (DrawVert vert : cmd_list.getVtxBuffer())
			{
				stagingVertexBuffer.putFloat(vert.getPos().component1());
				stagingVertexBuffer.putFloat(vert.getPos().component2());
				stagingVertexBuffer.putFloat(vert.getUv().component1());
				stagingVertexBuffer.putFloat(vert.getUv().component2());
				stagingVertexBuffer.putInt(vert.getCol());
			}
		}

		for (int n = 0; n < imDrawData.getCmdListsCount(); n++)
		{
			DrawList cmd_list = imDrawData.getCmdLists().get(n);

			for (Integer index : cmd_list.getIdxBuffer())
			{
				stagingIndexBuffer.putShort(index.shortValue());
			}
		}

		stagingVertexBuffer.flip();
		stagingIndexBuffer.flip();
		indexBuffer.fillBuffer(stagingVertexBuffer, vertexCount, stagingIndexBuffer, indexCount);

		stagingVertexBuffer.clear();
		stagingIndexBuffer.clear();
	}

	public void bind(VkCommandBuffer commandBuffer)
	{
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
}
