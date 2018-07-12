package test.vulkan.rotatingtriangle;

import static org.lwjgl.vulkan.VK10.*;

import java.util.ArrayList;
import java.util.List;

import org.sheepy.vulkan.BasicVulkanApplication;
import org.sheepy.vulkan.UniformBufferObject;
import org.sheepy.vulkan.VulkanApplication;
import org.sheepy.vulkan.buffer.IndexBuffer;
import org.sheepy.vulkan.buffer.Mesh;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.swap.BasicRenderPipelinePool;
import org.sheepy.vulkan.pipeline.swap.MeshSwapPipeline;
import org.sheepy.vulkan.pipeline.swap.SwapConfiguration;
import org.sheepy.vulkan.pipeline.swap.graphic.impl.VertexDescriptor;
import org.sheepy.vulkan.pipeline.swap.graphic.impl.VertexDescriptor.Vertex;
import org.sheepy.vulkan.shader.Shader;

public class MainRotating
{
	private static final String VERTEX_SHADER_PATH = "test/vulkan/rotatingtriangle/triangle.vert.spv";
	private static final String FRAGMENT_SHADER_PATH = "test/vulkan/rotatingtriangle/triangle.frag.spv";

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private static Mesh mesh;
	private static UniformBufferObject ubo;

	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();
		VulkanApplication app = new BasicVulkanApplication(WIDTH, HEIGHT)
		{
			@Override
			protected void updateAppState()
			{
				long current = System.currentTimeMillis();
				float progress = (current - start) / 1000f;

				ubo.update(progress);
			}
		};

		LogicalDevice logicalDevice = app.initLogicalDevice();

		BasicRenderPipelinePool pipelinePool = new BasicRenderPipelinePool(logicalDevice);

		createMeshBuffer(app, logicalDevice, pipelinePool.getCommandPool());

		SwapConfiguration configuration = new SwapConfiguration();
		configuration.rasterizerFrontFace = VK_FRONT_FACE_COUNTER_CLOCKWISE;

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
	private static void createMeshBuffer(VulkanApplication app,
			LogicalDevice logicalDevice,
			CommandPool commandPool)
	{
		Vertex[] vertices = new Vertex[4];
		vertices[0] = new Vertex(-0.5f, -0.5f, 1.0f, 1.0f, 1.0f);
		vertices[1] = new Vertex(0.5f, -0.5f, 0.0f, 1.0f, 0.0f);
		vertices[2] = new Vertex(0.5f, 0.5f, 0.0f, 0.0f, 0.0f);
		vertices[3] = new Vertex(-0.5f, 0.5f, 0.0f, 0.0f, 1.0f);

		int[] indices = {
				0, 1, 2, 2, 3, 0
		};

		IndexBuffer<Vertex> indexBuffer = new IndexBuffer<>(logicalDevice, new VertexDescriptor(),
				vertices, indices);

		List<Shader> shaders = new ArrayList<>();
		shaders.add(new Shader(logicalDevice, VERTEX_SHADER_PATH, VK_SHADER_STAGE_VERTEX_BIT));
		shaders.add(new Shader(logicalDevice, FRAGMENT_SHADER_PATH, VK_SHADER_STAGE_FRAGMENT_BIT));

		ubo = new UniformBufferObject(app, logicalDevice);

		mesh = new Mesh(commandPool, indexBuffer, shaders, ubo, null);
	}
}
