package org.sheepy.vulkan.imgui;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;
import org.sheepy.vulkan.buffer.IVertex;
import org.sheepy.vulkan.imgui.ImGuiVertexDescriptor.ImGuiVertex;
import org.sheepy.vulkan.pipeline.graphic.render.IIndexBufferDescriptor;

public class ImGuiVertexDescriptor implements IIndexBufferDescriptor<ImGuiVertex>
{
	public static final int SIZE_OF = 5 * 4;
	public static final int POSITION_OFFSET = 0;
	public static final int COLOR_OFFSET = 2 * 4;
	public static final int TEX_COORD_OFFSET = 4 * 4;

	private VkPipelineVertexInputStateCreateInfo vertexInputInfo;
	private VkVertexInputBindingDescription.Buffer allocBindingDescription;
	private VkVertexInputAttributeDescription.Buffer getgetAttributeDescriptions;

	@Override
	public VkPipelineVertexInputStateCreateInfo allocInputStateCreateInfo()
	{
		vertexInputInfo = VkPipelineVertexInputStateCreateInfo.calloc();
		vertexInputInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO);

		allocBindingDescription = allocBindingDescription();
		getgetAttributeDescriptions = allocAttributeDescriptions();

		vertexInputInfo.pVertexBindingDescriptions(allocBindingDescription); // Optional
		vertexInputInfo.pVertexAttributeDescriptions(getgetAttributeDescriptions); // Optional

		return vertexInputInfo;
	}

	public VkVertexInputBindingDescription.Buffer allocBindingDescription()
	{
		VkVertexInputBindingDescription.Buffer bindingDescription = VkVertexInputBindingDescription
				.calloc(1);
		
		bindingDescription.binding(0);
		bindingDescription.stride(SIZE_OF);
		bindingDescription.inputRate(VK_VERTEX_INPUT_RATE_VERTEX);

		return bindingDescription;
	}

	public VkVertexInputAttributeDescription.Buffer allocAttributeDescriptions()
	{
		VkVertexInputAttributeDescription.Buffer attributeDescriptions = VkVertexInputAttributeDescription
				.calloc(3);

		VkVertexInputAttributeDescription attributeDescriptionPosition = attributeDescriptions
				.get(0);
		attributeDescriptionPosition.binding(0);
		attributeDescriptionPosition.location(0);
		attributeDescriptionPosition.format(VK_FORMAT_R32G32B32_SFLOAT);
		attributeDescriptionPosition.offset(POSITION_OFFSET);

		VkVertexInputAttributeDescription attributeDescriptionColor = attributeDescriptions.get(1);
		attributeDescriptionColor.binding(0);
		attributeDescriptionColor.location(1);
		attributeDescriptionColor.format(VK_FORMAT_R32G32B32_SFLOAT);
		attributeDescriptionColor.offset(COLOR_OFFSET);

		VkVertexInputAttributeDescription attributeDescriptionTexCoord = attributeDescriptions
				.get(2);
		attributeDescriptionTexCoord.binding(0);
		attributeDescriptionTexCoord.location(2);
		attributeDescriptionTexCoord.format(VK_FORMAT_R8G8B8A8_UNORM);
		attributeDescriptionTexCoord.offset(TEX_COORD_OFFSET);

		return attributeDescriptions;
	}

	@Override
	public void freeInputStateCreateInfo()
	{
		vertexInputInfo.free();
		allocBindingDescription.free();
		getgetAttributeDescriptions.free();
	}

	@Override
	public void free()
	{}

	@Override
	public int sizeOfVertex()
	{
		return SIZE_OF;
	}

	@Override
	public ByteBuffer toVertexBuffer(ImGuiVertex[] vertices)
	{
		ByteBuffer res = MemoryUtil.memAlloc(SIZE_OF * vertices.length);
		FloatBuffer fb = res.asFloatBuffer();

		for (ImGuiVertex vertex : vertices)
		{
			fb.put(vertex.posX);
			fb.put(vertex.posY);
			fb.put(vertex.uvX);
			fb.put(vertex.uvY);
			fb.put(vertex.tex);
		}

		return res;
	}

	public class ImGuiVertex implements IVertex
	{
		float posX;
		float posY;
		float uvX;
		float uvY;
		int tex;
	}

	@Override
	public int sizeOfIndex()
	{
		return Short.BYTES;
	}

	@Override
	public ByteBuffer toIndexBuffer(int[] indices)
	{
		ByteBuffer res = MemoryUtil.memAlloc(indices.length * Short.BYTES);

		for (Integer index : indices)
		{
			res.putShort(index.shortValue());
		}
		res.flip();

		return res;
	}
}