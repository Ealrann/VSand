package org.sheepy.vulkan.nuklear.pipeline;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;
import org.sheepy.vulkan.nuklear.pipeline.NuklearVertexDescriptor.GuiVertex;
import org.sheepy.vulkan.resource.indexed.IIndexBufferDescriptor;
import org.sheepy.vulkan.resource.indexed.IVertex;

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
		VkVertexInputBindingDescription.Buffer bindingDescription = VkVertexInputBindingDescription
				.calloc(1);
		
		bindingDescription.binding(0);
		bindingDescription.stride(SIZE_OF);
		bindingDescription.inputRate(VK_VERTEX_INPUT_RATE_VERTEX);

		return bindingDescription;
	}

	public static VkVertexInputAttributeDescription.Buffer allocAttributeDescriptions()
	{
		VkVertexInputAttributeDescription.Buffer attributeDescriptions = VkVertexInputAttributeDescription
				.calloc(3);

		
		VkVertexInputAttributeDescription attributeDescriptionPosition = attributeDescriptions
				.get(0);
		attributeDescriptionPosition.binding(0);
		attributeDescriptionPosition.location(0);
		attributeDescriptionPosition.format(VK_FORMAT_R32G32_SFLOAT);
		attributeDescriptionPosition.offset(POSITION_OFFSET);

		VkVertexInputAttributeDescription attributeDescriptionTexCoord = attributeDescriptions
				.get(2);
		attributeDescriptionTexCoord.binding(0);
		attributeDescriptionTexCoord.location(1);
		attributeDescriptionTexCoord.format(VK_FORMAT_R32G32_SFLOAT);
		attributeDescriptionTexCoord.offset(TEX_COORD_OFFSET);

		VkVertexInputAttributeDescription attributeDescriptionColor = attributeDescriptions.get(1);
		attributeDescriptionColor.binding(0);
		attributeDescriptionColor.location(2);
		attributeDescriptionColor.format(VK_FORMAT_R8G8B8A8_UNORM);
		attributeDescriptionColor.offset(COLOR_OFFSET);

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
		FloatBuffer fb = res.asFloatBuffer();

		for (GuiVertex vertex : vertices)
		{
			fb.put(vertex.posX);
			fb.put(vertex.posY);
			fb.put(vertex.uvX);
			fb.put(vertex.uvY);
			fb.put(vertex.tex);
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