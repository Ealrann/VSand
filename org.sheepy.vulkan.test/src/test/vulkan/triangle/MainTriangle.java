package test.vulkan.triangle;

import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_FRAGMENT_BIT;
import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_VERTEX_BIT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sheepy.lily.game.vulkan.BasicVulkanApplication;
import org.sheepy.lily.game.vulkan.buffer.IndexBuffer;
import org.sheepy.lily.game.vulkan.buffer.Mesh;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.swap.BasicRenderPipelinePool;
import org.sheepy.lily.game.vulkan.pipeline.swap.MeshSwapPipeline;
import org.sheepy.lily.game.vulkan.pipeline.swap.SwapConfiguration;
import org.sheepy.lily.game.vulkan.pipeline.swap.graphic.graphic.impl.VertexDescriptor;
import org.sheepy.lily.game.vulkan.pipeline.swap.graphic.graphic.impl.VertexDescriptor.Vertex;
import org.sheepy.lily.game.vulkan.shader.Shader;

public class MainTriangle
{
	private static final String VERTEX_SHADER_PATH = "test/vulkan/triangle/triangle.vert.spv";
	private static final String FRAGMENT_SHADER_PATH = "test/vulkan/triangle/triangle.frag.spv";

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private static Mesh mesh;

	public static void main(String[] args)
	{
		BasicVulkanApplication app = new BasicVulkanApplication(WIDTH, HEIGHT);

		LogicalDevice logicalDevice = app.initLogicalDevice();

		SwapConfiguration configuration = new SwapConfiguration();
		BasicRenderPipelinePool pipelinePool = new BasicRenderPipelinePool(logicalDevice);

		createMeshBuffer(logicalDevice, pipelinePool.getCommandPool());
		MeshSwapPipeline swapPipeline = new MeshSwapPipeline(logicalDevice, mesh, configuration,
				pipelinePool.getCommandPool());
		pipelinePool.setSwapPipeline(swapPipeline);
		app.attachPipelinePool(pipelinePool);

		try
		{
			app.run();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// {{-0.5f, -0.5f}, {1.0f, 0.0f, 0.0f}},
	// {{0.5f, -0.5f}, {0.0f, 1.0f, 0.0f}},
	// {{0.5f, 0.5f}, {0.0f, 0.0f, 1.0f}},
	// {{-0.5f, 0.5f}, {1.0f, 1.0f, 1.0f}}
	private static void createMeshBuffer(LogicalDevice logicalDevice, CommandPool commandPool)
	{
		Vertex[] vertices = new Vertex[4];
		vertices[0] = new Vertex(-0.5f, -0.5f, 1.0f, 1.0f, 1.0f);
		vertices[1] = new Vertex(0.5f, -0.5f, 0.0f, 1.0f, 0.0f);
		vertices[2] = new Vertex(0.5f, 0.5f, 0.0f, 0.0f, 0.0f);
		vertices[3] = new Vertex(-0.5f, 0.5f, 0.0f, 0.0f, 1.0f);

		int[] indices = {
				0, 1, 2, 2, 3, 0
		};

		IndexBuffer indexBuffer = new IndexBuffer(logicalDevice);
		indexBuffer.allocIndexBuffer(commandPool, logicalDevice.getQueueManager().getGraphicQueue(),
				new VertexDescriptor(), vertices, indices);

		List<Shader> shaders = new ArrayList<>();
		shaders.add(new Shader(logicalDevice, VERTEX_SHADER_PATH, VK_SHADER_STAGE_VERTEX_BIT));
		shaders.add(new Shader(logicalDevice, FRAGMENT_SHADER_PATH, VK_SHADER_STAGE_FRAGMENT_BIT));

		mesh = new Mesh(indexBuffer, shaders, Collections.emptyList());
	}
}
