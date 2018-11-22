package org.sheepy.vulkan.sand.buffer;

import java.nio.ByteBuffer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.vulkan.model.enumeration.EBufferUsage;
import org.sheepy.vulkan.model.enumeration.EDescriptorType;
import org.sheepy.vulkan.model.enumeration.EMemoryProperty;
import org.sheepy.vulkan.model.enumeration.EShaderStage;
import org.sheepy.vulkan.model.resource.Buffer;
import org.sheepy.vulkan.sand.logic.MaterialUtil;
import org.sheepy.vulkan.sand.model.Material;
import org.sheepy.vulkan.sand.model.VSandApplication;

public class ConfigurationBufferLoader
{
	private static final int BYTE_SIZE = MaterialUtil.MAX_MATERIAL_NUMBER * 4 * Integer.BYTES;

	public static final void load(Buffer buffer)
	{
		var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);

		buffer.setSize(BYTE_SIZE);
		buffer.getUsages().add(EBufferUsage.UNIFORM_BUFFER_BIT);
		buffer.getUsages().add(EBufferUsage.TRANSFER_DST_BIT);
		buffer.getProperties().add(EMemoryProperty.DEVICE_LOCAL_BIT);
		buffer.setDescriptorType(EDescriptorType.UNIFORM_BUFFER);
		buffer.getShaderStages().add(EShaderStage.COMPUTE_BIT);

		ByteBuffer bBuffer = MemoryUtil.memAlloc(BYTE_SIZE);
		for (Material material : application.getMaterials().getMaterials())
		{
			bBuffer.putInt(material.isIsStatic() ? 1 : 0);
			bBuffer.putInt(material.getDensity());
			bBuffer.putInt(material.getRunoff());

			int rgb = material.getR() << 16
					| material.getG() << 8
					| material.getB();

			bBuffer.putInt(rgb);
		}
		bBuffer.flip();

		buffer.setData(bBuffer);
	}
}
