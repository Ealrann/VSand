package test.vulkan.texture;

import static org.lwjgl.vulkan.VK10.*;

import java.util.ArrayList;
import java.util.List;

import org.sheepy.vulkan.BasicVulkanApplication;
import org.sheepy.vulkan.VulkanApplication;
import org.sheepy.vulkan.buffer.IndexBuffer;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.swap.graphic.GraphicsPipeline;
import org.sheepy.vulkan.pipeline.swap.graphic.impl.TextureVertexDescriptor;
import org.sheepy.vulkan.pipeline.swap.graphic.impl.TextureVertexDescriptor.TextureVertex;
import org.sheepy.vulkan.shader.Shader;
import org.sheepy.vulkan.texture.Texture;

import test.vulkan.mesh.Mesh;
import test.vulkan.mesh.MeshRenderPass;
import test.vulkan.mesh.MeshRenderPipelinePool;
import test.vulkan.mesh.MeshSwapConfiguration;
import test.vulkan.mesh.MeshSwapPipeline;
import test.vulkan.mesh.UniformBufferObject;

public class MainTexture
{
	private static final String IMAGE_PATH = "test/vulkan/texture/image_77MJJZ.png";
	private static final String VERTEX_SHADER_PATH = "test/vulkan/texture/triangle.vert.spv";
	private static final String FRAGMENT_SHADER_PATH = "test/vulkan/texture/triangle.frag.spv";

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

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

		MeshRenderPipelinePool pipelinePool = new MeshRenderPipelinePool(logicalDevice);
		CommandPool commandPool = pipelinePool.getCommandPool();

		Mesh mesh = createMeshBuffer(app, logicalDevice, commandPool);

		MeshSwapConfiguration configuration = new MeshSwapConfiguration(logicalDevice, commandPool, mesh);
		configuration.renderPass = new MeshRenderPass(configuration);
		configuration.depthBuffer = true;
		configuration.rasterizerFrontFace = VK_FRONT_FACE_COUNTER_CLOCKWISE;
		configuration.vertexInputState = new TextureVertexDescriptor();
		configuration.graphicsPipeline = new GraphicsPipeline(configuration);

		MeshSwapPipeline swapPipeline = new MeshSwapPipeline(logicalDevice, configuration,
				pipelinePool.getCommandPool());
		pipelinePool.setSwapPipeline(swapPipeline, configuration);
		app.attachPipelinePool(pipelinePool);
		
		try
		{
			app.run();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static Mesh createMeshBuffer(VulkanApplication app, LogicalDevice logicalDevice, CommandPool commandPool)
	{
		TextureVertex[] vertices = new TextureVertex[8];
		vertices[0] = new TextureVertex(-0.5f, -0.5f, 0f, 1.0f, 0.0f, 0.0f, 1f, 0f);
		vertices[1] = new TextureVertex(0.5f, -0.5f, 0f, 1.0f, 1.0f, 0.0f, 0f, 0f);
		vertices[2] = new TextureVertex(0.5f, 0.5f, 0f, 0.0f, 0.0f, 1.0f, 0f, 1f);
		vertices[3] = new TextureVertex(-0.5f, 0.5f, 0f, 1.0f, 1.0f, 0.0f, 1f, 1f);

		vertices[4] = new TextureVertex(-0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1f, 0f);
		vertices[5] = new TextureVertex(0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0f, 0f);
		vertices[6] = new TextureVertex(0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0f, 1f);
		vertices[7] = new TextureVertex(-0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1f, 1f);

		int[] indices = {
				0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4
		};

		IndexBuffer<TextureVertex> indexBuffer = IndexBuffer.alloc(logicalDevice,
				new TextureVertexDescriptor(), commandPool, vertices, indices);

		List<Shader> shaders = new ArrayList<>();
		shaders.add(new Shader(logicalDevice, VERTEX_SHADER_PATH, VK_SHADER_STAGE_VERTEX_BIT));
		shaders.add(new Shader(logicalDevice, FRAGMENT_SHADER_PATH, VK_SHADER_STAGE_FRAGMENT_BIT));

		Texture texture = new Texture(logicalDevice,commandPool, IMAGE_PATH, false);
		ubo = new UniformBufferObject(app, logicalDevice);

		return new Mesh(logicalDevice, indexBuffer, shaders, ubo, texture);
	}
}
