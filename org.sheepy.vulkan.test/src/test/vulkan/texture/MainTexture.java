package test.vulkan.texture;

import static org.lwjgl.vulkan.VK10.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.vulkan.VkQueue;
import org.sheepy.lily.game.vulkan.BasicVulkanApplication;
import org.sheepy.lily.game.vulkan.UniformBufferObject;
import org.sheepy.lily.game.vulkan.VulkanApplication;
import org.sheepy.lily.game.vulkan.buffer.IndexBuffer;
import org.sheepy.lily.game.vulkan.buffer.Mesh;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.descriptor.IDescriptor;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.swap.BasicRenderPipelinePool;
import org.sheepy.lily.game.vulkan.pipeline.swap.MeshSwapPipeline;
import org.sheepy.lily.game.vulkan.pipeline.swap.SwapConfiguration;
import org.sheepy.lily.game.vulkan.pipeline.swap.graphic.graphic.impl.TextureVertexDescriptor;
import org.sheepy.lily.game.vulkan.pipeline.swap.graphic.graphic.impl.TextureVertexDescriptor.TextureVertex;
import org.sheepy.lily.game.vulkan.shader.Shader;
import org.sheepy.lily.game.vulkan.texture.Texture;

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

		VkQueue graphicQueue = logicalDevice.getQueueManager().getGraphicQueue();

		BasicRenderPipelinePool pipelinePool = new BasicRenderPipelinePool(logicalDevice);
		CommandPool commandPool = pipelinePool.getCommandPool();

		Texture texture = Texture.alloc(logicalDevice, IMAGE_PATH, commandPool, graphicQueue,
				false);
		ubo = new UniformBufferObject(app, logicalDevice);

		List<IDescriptor> descriptors = new ArrayList<>();
		descriptors.add(ubo);
		descriptors.add(texture);
		Mesh mesh = createMeshBuffer(logicalDevice, commandPool, descriptors);

		SwapConfiguration configuration = new SwapConfiguration();
		configuration.depthBuffer = true;
		configuration.rasterizerFrontFace = VK_FRONT_FACE_COUNTER_CLOCKWISE;
		configuration.setVertexInputState(new TextureVertexDescriptor());

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

	private static Mesh createMeshBuffer(LogicalDevice logicalDevice,
			CommandPool commandPool,
			List<IDescriptor> descriptors)
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

		IndexBuffer indexBuffer = new IndexBuffer(logicalDevice);
		indexBuffer.allocIndexBuffer(commandPool, logicalDevice.getQueueManager().getGraphicQueue(),
				new TextureVertexDescriptor(), vertices, indices);

		List<Shader> shaders = new ArrayList<>();
		shaders.add(new Shader(logicalDevice, VERTEX_SHADER_PATH, VK_SHADER_STAGE_VERTEX_BIT));
		shaders.add(new Shader(logicalDevice, FRAGMENT_SHADER_PATH, VK_SHADER_STAGE_FRAGMENT_BIT));

		return new Mesh(indexBuffer, shaders, descriptors);
	}
}
