package org.sheepy.vulkan.nuklear.pipeline;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;
import org.sheepy.vulkan.nuklear.pipeline.NuklearVertexDescriptor.GuiVertex;
import org.sheepy.lily.vulkan.resource.indexed.IIndexBufferDescriptor;
import org.sheepy.lily.vulkan.resource.indexed.IVertex;

public class NuklearVertexDescriptor implements IIndexBufferDescriptor<GuiVertex>
{
	public static final int SIZE_OF = 5 * 4;
	public static final int POSITION_OFFSET = 0;
	public static final int TEX_COORD_OFFSET = 2 * 4;
	public static final int COLOR_OFFSET = 4 * 4;

	private VkPipelineVertexInputStateCreateInfo vertexInputInfo;
	private VkVertexInputBindingDescription.Buffer bindingDescription;
	private VkVertexInputAttributeDescription.Buffer attributeDescriptions;

	@Override
	public VkPipelineVertexInputStateCreateInfo allocCreateInfo()
	{
		vertexInputInfo = VkPipelineVertexInputStateCreateInfo.calloc();
		vertexInputInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO);

		bindingDescription = allocBindingDescription();
		attributeDescriptions = allocAttributeDescriptions();

		vertexInputInfo.pVertexBindingDescriptions(bindingDescription); // Optional
		vertexInputInfo.pVertexAttributeDescriptions(attributeDescriptions); // Optional

		return vertexInputInfo;
	}

	public static VkVertexInputBindingDescription.Buffer allocBindingDescription()
	{
		var bindingDescription = VkVertexInputBindingDescription.create(1);

		bindingDescription.binding(0);
		bindingDescription.stride(SIZE_OF);
		bindingDescription.inputRate(VK_VERTEX_INPUT_RATE_VERTEX);

		return bindingDescription;
	}

	public static VkVertexInputAttributeDescription.Buffer allocAttributeDescriptions()
	{
		var attributeDescriptions = VkVertexInputAttributeDescription.create(3);

		var positionAttribute = attributeDescriptions.get(0);
		positionAttribute.binding(0);
		positionAttribute.location(0);
		positionAttribute.format(VK_FORMAT_R32G32_SFLOAT);
		positionAttribute.offset(POSITION_OFFSET);

		var texCoordAttribute = attributeDescriptions.get(1);
		texCoordAttribute.binding(0);
		texCoordAttribute.location(1);
		texCoordAttribute.format(VK_FORMAT_R32G32_SFLOAT);
		texCoordAttribute.offset(TEX_COORD_OFFSET);

		var colorAttribute = attributeDescriptions.get(2);
		colorAttribute.binding(0);
		colorAttribute.location(2);
		colorAttribute.format(VK_FORMAT_R8G8B8A8_UNORM);
		colorAttribute.offset(COLOR_OFFSET);

		return attributeDescriptions;
	}

	@Override
	public void freeInputStateCreateInfo()
	{
		vertexInputInfo.free();
		bindingDescription.free();
		attributeDescriptions.free();

		vertexInputInfo = null;
		bindingDescription = null;
		attributeDescriptions = null;
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
	public ByteBuffer toVertexBuffer(GuiVertex[] vertices)
	{
		ByteBuffer res = MemoryUtil.memAlloc(SIZE_OF * vertices.length);

		for (GuiVertex vertex : vertices)
		{
			res.putFloat(vertex.posX);
			res.putFloat(vertex.posY);
			res.putFloat(vertex.uvX);
			res.putFloat(vertex.uvY);
			res.putInt(vertex.tex);
		}

		return res;
	}

	public class GuiVertex implements IVertex
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
	@Deprecated
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