package org.sheepy.vsand.model.vsand;

import java.util.List;
import org.logoce.lmf.core.api.model.BuilderSupplier;
import org.logoce.lmf.core.lang.Alias;
import org.logoce.lmf.core.lang.Enum;
import org.logoce.lmf.core.lang.Group;
import org.logoce.lmf.core.lang.JavaWrapper;
import org.logoce.lmf.core.lang.Primitive;
import org.logoce.lmf.core.lang.Unit;
import org.logoce.lmf.core.lang.builder.GroupBuilder;
import org.logoce.lmf.core.lang.builder.IncludeBuilder;
import org.logoce.lmf.core.lang.builder.UnitBuilder;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.core.model.application.ApplicationModelDefinition;
import org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider;
import org.sheepy.lily.vulkan.extra.model.nuklear.NuklearModelDefinition;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.lily.vulkan.model.vulkanresource.VulkanResourceModelDefinition;
import org.sheepy.vsand.model.vsand.builder.BoardConstantBufferBuilder;
import org.sheepy.vsand.model.vsand.builder.DrawCircleBuilder;
import org.sheepy.vsand.model.vsand.builder.DrawConstantBufferBuilder;
import org.sheepy.vsand.model.vsand.builder.DrawLineBuilder;
import org.sheepy.vsand.model.vsand.builder.DrawSquareBuilder;
import org.sheepy.vsand.model.vsand.builder.InputMaterialProviderBuilder;
import org.sheepy.vsand.model.vsand.builder.MaterialBuilder;
import org.sheepy.vsand.model.vsand.builder.MaterialProviderBuilder;
import org.sheepy.vsand.model.vsand.builder.MaterialsBuilder;
import org.sheepy.vsand.model.vsand.builder.MultipleTransformationBuilder;
import org.sheepy.vsand.model.vsand.builder.PixelConstantBufferBuilder;
import org.sheepy.vsand.model.vsand.builder.TransformationBuilder;
import org.sheepy.vsand.model.vsand.builder.TransformationsBuilder;
import org.sheepy.vsand.model.vsand.builder.VSandApplicationBuilder;

public interface VSandModelDefinition {
  interface Generics {
  }

  interface Groups {
    Group<VSandApplication> V_SAND_APPLICATION = new GroupBuilder<VSandApplication>().name("VSandApplication").concrete(true).addInclude(() -> new IncludeBuilder<Application>().group(() -> ApplicationModelDefinition.Groups.APPLICATION).build()).addFeatures(VSandApplication.Features.ALL).lmBuilder(new BuilderSupplier<>(VSandApplicationBuilder::new)).build();
    Group<BoardConstantBuffer> BOARD_CONSTANT_BUFFER = new GroupBuilder<BoardConstantBuffer>().name("BoardConstantBuffer").concrete(true).addInclude(() -> new IncludeBuilder<ConstantBuffer>().group(() -> VulkanResourceModelDefinition.Groups.CONSTANT_BUFFER).build()).addFeatures(BoardConstantBuffer.Features.ALL).lmBuilder(new BuilderSupplier<>(BoardConstantBufferBuilder::new)).build();
    Group<DrawConstantBuffer> DRAW_CONSTANT_BUFFER = new GroupBuilder<DrawConstantBuffer>().name("DrawConstantBuffer").concrete(true).addInclude(() -> new IncludeBuilder<BoardConstantBuffer>().group(() -> BOARD_CONSTANT_BUFFER).build()).addFeatures(DrawConstantBuffer.Features.ALL).lmBuilder(new BuilderSupplier<>(DrawConstantBufferBuilder::new)).build();
    Group<PixelConstantBuffer> PIXEL_CONSTANT_BUFFER = new GroupBuilder<PixelConstantBuffer>().name("PixelConstantBuffer").concrete(true).addInclude(() -> new IncludeBuilder<BoardConstantBuffer>().group(() -> BOARD_CONSTANT_BUFFER).build()).addFeatures(PixelConstantBuffer.Features.ALL).lmBuilder(new BuilderSupplier<>(PixelConstantBufferBuilder::new)).build();
    Group<DrawCommand> DRAW_COMMAND = new GroupBuilder<DrawCommand>().name("DrawCommand").addFeatures(DrawCommand.Features.ALL).build();
    Group<DrawCircle> DRAW_CIRCLE = new GroupBuilder<DrawCircle>().name("DrawCircle").concrete(true).addInclude(() -> new IncludeBuilder<DrawCommand>().group(() -> DRAW_COMMAND).build()).addFeatures(DrawCircle.Features.ALL).lmBuilder(new BuilderSupplier<>(DrawCircleBuilder::new)).build();
    Group<DrawSquare> DRAW_SQUARE = new GroupBuilder<DrawSquare>().name("DrawSquare").concrete(true).addInclude(() -> new IncludeBuilder<DrawCommand>().group(() -> DRAW_COMMAND).build()).addFeatures(DrawSquare.Features.ALL).lmBuilder(new BuilderSupplier<>(DrawSquareBuilder::new)).build();
    Group<DrawLine> DRAW_LINE = new GroupBuilder<DrawLine>().name("DrawLine").concrete(true).addInclude(() -> new IncludeBuilder<DrawCommand>().group(() -> DRAW_COMMAND).build()).addFeatures(DrawLine.Features.ALL).lmBuilder(new BuilderSupplier<>(DrawLineBuilder::new)).build();
    Group<Materials> MATERIALS = new GroupBuilder<Materials>().name("Materials").concrete(true).addFeatures(Materials.Features.ALL).lmBuilder(new BuilderSupplier<>(MaterialsBuilder::new)).build();
    Group<Material> MATERIAL = new GroupBuilder<Material>().name("Material").concrete(true).addFeatures(Material.Features.ALL).lmBuilder(new BuilderSupplier<>(MaterialBuilder::new)).build();
    Group<Transformations> TRANSFORMATIONS = new GroupBuilder<Transformations>().name("Transformations").concrete(true).addFeatures(Transformations.Features.ALL).lmBuilder(new BuilderSupplier<>(TransformationsBuilder::new)).build();
    Group<ITransformation> I_TRANSFORMATION = new GroupBuilder<ITransformation>().name("ITransformation").addFeatures(ITransformation.Features.ALL).build();
    Group<Transformation> TRANSFORMATION = new GroupBuilder<Transformation>().name("Transformation").concrete(true).addInclude(() -> new IncludeBuilder<ITransformation>().group(() -> I_TRANSFORMATION).build()).addFeatures(Transformation.Features.ALL).lmBuilder(new BuilderSupplier<>(TransformationBuilder::new)).build();
    Group<MultipleTransformation> MULTIPLE_TRANSFORMATION = new GroupBuilder<MultipleTransformation>().name("MultipleTransformation").concrete(true).addInclude(() -> new IncludeBuilder<ITransformation>().group(() -> I_TRANSFORMATION).build()).addFeatures(MultipleTransformation.Features.ALL).lmBuilder(new BuilderSupplier<>(MultipleTransformationBuilder::new)).build();
    Group<MaterialProvider> MATERIAL_PROVIDER = new GroupBuilder<MaterialProvider>().name("MaterialProvider").concrete(true).addFeatures(MaterialProvider.Features.ALL).lmBuilder(new BuilderSupplier<>(MaterialProviderBuilder::new)).build();
    Group<InputMaterialProvider> INPUT_MATERIAL_PROVIDER = new GroupBuilder<InputMaterialProvider>().name("InputMaterialProvider").concrete(true).addInclude(() -> new IncludeBuilder<IInputProvider>().group(() -> NuklearModelDefinition.Groups.I_INPUT_PROVIDER).build()).addFeatures(InputMaterialProvider.Features.ALL).lmBuilder(new BuilderSupplier<>(InputMaterialProviderBuilder::new)).build();
    List<Group<?>> ALL = List.of(V_SAND_APPLICATION, BOARD_CONSTANT_BUFFER, DRAW_CONSTANT_BUFFER, PIXEL_CONSTANT_BUFFER, DRAW_COMMAND, DRAW_CIRCLE, DRAW_SQUARE, DRAW_LINE, MATERIALS, MATERIAL, TRANSFORMATIONS, I_TRANSFORMATION, TRANSFORMATION, MULTIPLE_TRANSFORMATION, MATERIAL_PROVIDER, INPUT_MATERIAL_PROVIDER);
  }

  interface Units {
    Unit<Integer> SIGNED_INT = new UnitBuilder<Integer>().name("signedInt").matcher("rgx_match:<-?[0-9]+>").defaultValue("0").primitive(Primitive.Int).build();
    List<Unit<?>> ALL = List.of(SIGNED_INT);
  }

  interface Enums {
    List<Enum<?>> ALL = List.of();
  }

  interface Aliases {
    List<Alias> ALL = List.of();
  }

  interface JavaWrappers {
    List<JavaWrapper<?>> ALL = List.of();
  }
}
