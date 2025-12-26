package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.Optional;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IJavaWrapperConverter;
import org.logoce.lmf.core.api.model.IModelPackage;
import org.logoce.lmf.core.lang.Enum;
import org.logoce.lmf.core.lang.Group;
import org.logoce.lmf.core.lang.JavaWrapper;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.MetaModel;
import org.logoce.lmf.core.lang.builder.MetaModelBuilder;

public final class VSandModelPackage implements IModelPackage {
  public static final VSandModelPackage Instance = new VSandModelPackage();

  public static final MetaModel MODEL = new MetaModelBuilder().name("VSand").domain("org.sheepy.vsand.model").addImports(List.of("org.sheepy.lily.core.model.Application", "org.sheepy.lily.core.model.Types", "org.sheepy.lily.core.model.Resource", "org.sheepy.lily.vulkan.model.Process", "org.sheepy.lily.vulkan.model.VulkanResource", "org.sheepy.lily.vulkan.extra.model.Nuklear")).genNamePackage(true).lmPackage(Instance).addGroups(VSandModelDefinition.Groups.ALL).addEnums(VSandModelDefinition.Enums.ALL).addUnits(VSandModelDefinition.Units.ALL).addAliases(VSandModelDefinition.Aliases.ALL).addJavaWrappers(VSandModelDefinition.JavaWrappers.ALL).build();

  private VSandModelPackage() {
  }

  @Override
  public MetaModel model() {
    return MODEL;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends LMObject> Optional<IFeaturedObject.Builder<T>> builder(Group<T> group) {
    if (group == VSandModelDefinition.Groups.V_SAND_APPLICATION) return Optional.of((IFeaturedObject.Builder<T>) VSandApplication.builder());
    else if (group == VSandModelDefinition.Groups.BOARD_CONSTANT_BUFFER) return Optional.of((IFeaturedObject.Builder<T>) BoardConstantBuffer.builder());
    else if (group == VSandModelDefinition.Groups.DRAW_CONSTANT_BUFFER) return Optional.of((IFeaturedObject.Builder<T>) DrawConstantBuffer.builder());
    else if (group == VSandModelDefinition.Groups.PIXEL_CONSTANT_BUFFER) return Optional.of((IFeaturedObject.Builder<T>) PixelConstantBuffer.builder());
    else if (group == VSandModelDefinition.Groups.DRAW_CIRCLE) return Optional.of((IFeaturedObject.Builder<T>) DrawCircle.builder());
    else if (group == VSandModelDefinition.Groups.DRAW_SQUARE) return Optional.of((IFeaturedObject.Builder<T>) DrawSquare.builder());
    else if (group == VSandModelDefinition.Groups.DRAW_LINE) return Optional.of((IFeaturedObject.Builder<T>) DrawLine.builder());
    else if (group == VSandModelDefinition.Groups.MATERIALS) return Optional.of((IFeaturedObject.Builder<T>) Materials.builder());
    else if (group == VSandModelDefinition.Groups.MATERIAL) return Optional.of((IFeaturedObject.Builder<T>) Material.builder());
    else if (group == VSandModelDefinition.Groups.TRANSFORMATIONS) return Optional.of((IFeaturedObject.Builder<T>) Transformations.builder());
    else if (group == VSandModelDefinition.Groups.TRANSFORMATION) return Optional.of((IFeaturedObject.Builder<T>) Transformation.builder());
    else if (group == VSandModelDefinition.Groups.MULTIPLE_TRANSFORMATION) return Optional.of((IFeaturedObject.Builder<T>) MultipleTransformation.builder());
    else if (group == VSandModelDefinition.Groups.MATERIAL_PROVIDER) return Optional.of((IFeaturedObject.Builder<T>) MaterialProvider.builder());
    else if (group == VSandModelDefinition.Groups.INPUT_MATERIAL_PROVIDER) return Optional.of((IFeaturedObject.Builder<T>) InputMaterialProvider.builder());
    return Optional.empty();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Optional<T> resolveEnumLiteral(Enum<T> enum_, String value) {
    return Optional.empty();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Optional<IJavaWrapperConverter<T>> resolveJavaWrapperConverter(
      JavaWrapper<T> wrapper) {
    return Optional.empty();
  }
}
