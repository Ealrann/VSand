package test.vulkan.mesh;

import org.lwjgl.system.MemoryStack;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.swap.graphic.GraphicSwapConfiguration;

public class MeshSwapConfiguration extends GraphicSwapConfiguration
{
	public final Mesh mesh;

	public MeshSwapConfiguration(LogicalDevice logicalDevice, CommandPool commandPool, Mesh mesh)
	{
		super(logicalDevice, commandPool, mesh.getDescriptorPool(), mesh.getShaders());
		
		this.mesh = mesh;
	}
	
	@Override
	public void allocate(MemoryStack stack)
	{
		mesh.allocate(stack);
		super.allocate(stack);
	}
	
	@Override
	public void free()
	{
		super.free();
		mesh.free();
	}
}
