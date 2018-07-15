package test.vulkan.model;

import static org.lwjgl.vulkan.VK10.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
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

import com.owens.oobjloader.builder.Build;
import com.owens.oobjloader.builder.Face;
import com.owens.oobjloader.builder.FaceVertex;
import com.owens.oobjloader.parser.Parse;

import test.vulkan.mesh.MeshRenderPipelinePool;
import test.vulkan.mesh.Mesh;
import test.vulkan.mesh.MeshRenderPass;
import test.vulkan.mesh.MeshSwapConfiguration;
import test.vulkan.mesh.MeshSwapPipeline;
import test.vulkan.mesh.UniformBufferObject;

public class MainMipMap
{
	private static final String TEXTURE_PATH = "test/vulkan/model/chalet.jpg";
	private static final String MODEL_PATH = "test/vulkan/model/chalet.obj";

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
				float progress = (current - start) / 2000f;

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

	private static Mesh createMeshBuffer(VulkanApplication app,
			LogicalDevice logicalDevice,
			CommandPool commandPool)
	{

		long startTime = System.currentTimeMillis();
		URL url = Thread.currentThread().getContextClassLoader().getResource(MODEL_PATH);
		File file = new File(url.getFile());

		System.out.println("Start parsing Model file");
		Build builder = new Build();
		try
		{
			new Parse(builder, file.getAbsolutePath());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		long delta = System.currentTimeMillis() - startTime;
		System.out.println("End parsing Model file in " + delta + "ms");

		Vector3f NO_COLOR = new Vector3f(1f, 1f, 1f);
		List<TextureVertex> vertices = new ArrayList<>();
		for (FaceVertex faceVertex : builder.faceVerticeList)
		{
			Vector3f v = faceVertex.vertexGeometric;
			Vector2f t = faceVertex.vertexTexture;
			TextureVertex vertex = new TextureVertex(v, NO_COLOR, t);
			vertices.add(vertex);
		}
		TextureVertex[] vertexArray = vertices.toArray(new TextureVertex[0]);

		int indiceCount = 0;
		for (Face face : builder.faces)
		{
			indiceCount += face.vertices.size();
		}

		int[] indexArray = new int[indiceCount];
		int i = 0;
		for (Face face : builder.faces)
		{
			for (FaceVertex faceVertex : face.vertices)
			{
				indexArray[i++] = faceVertex.index;
			}
		}

		System.err.println("Final vertices count: " + vertices.size());
		System.err.println("Indices count: " + indiceCount);

		IndexBuffer<TextureVertex> indexBuffer = IndexBuffer.alloc(logicalDevice,
				new TextureVertexDescriptor(), commandPool, vertexArray, indexArray);

		List<Shader> shaders = new ArrayList<>();
		shaders.add(new Shader(logicalDevice, VERTEX_SHADER_PATH, VK_SHADER_STAGE_VERTEX_BIT));
		shaders.add(new Shader(logicalDevice, FRAGMENT_SHADER_PATH, VK_SHADER_STAGE_FRAGMENT_BIT));

		Texture texture = new Texture(logicalDevice, commandPool, TEXTURE_PATH, true);
		ubo = new UniformBufferObject(app, logicalDevice);

		return new Mesh(logicalDevice, indexBuffer, shaders, ubo, texture);
	}
}
