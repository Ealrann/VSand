package org.sheepy.vsand.model.vsand.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.util.BuildUtils;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.Materials;
import org.sheepy.vsand.model.vsand.Materials.Builder;
import org.sheepy.vsand.model.vsand.impl.MaterialsImpl;

public final class MaterialsBuilder implements Builder {
  private final List<Supplier<Material>> materials = new ArrayList<>();

  public MaterialsBuilder() {
  }

  @Override
  public MaterialsBuilder addMaterial(Supplier<Material> material) {
    this.materials.add(material);
    return this;
  }

  @Override
  public MaterialsBuilder addMaterials(final List<Material> materials) {
    materials.forEach(value -> this.materials.add(() -> value));
    return this;
  }

  @Override
  public Materials build() {
    final var builtMaterials = BuildUtils.collectSuppliers(materials);
    final var built = new MaterialsImpl();
    built.materials().addAll(builtMaterials);
    return built;
  }

  @Override
  public <AttributeType> void push(final Attribute<?, ?, ?, ?> attribute,
      final AttributeType value) {
    Inserters.ATTRIBUTE_INSERTER.push(this, attribute.id(), value);
  }

  @Override
  public <RelationType extends LMObject> void push(final Relation<RelationType, ?, ?, ?> relation,
      final Supplier<RelationType> supplier) {
    Inserters.RELATION_INSERTER.push(this, relation.id(), supplier);
  }

  private static final class Inserters {
    private static final FeatureInserter<MaterialsBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<MaterialsBuilder>(0, Inserters::attributeIndex).build();
    private static final RelationLazyInserter<MaterialsBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<MaterialsBuilder>(1, Inserters::relationIndex).add(Materials.FeatureIDs.MATERIALS, (builder, value) -> builder.addMaterial((Supplier<Material>) value)).build();

    private static int attributeIndex(final int featureId) {
      throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case Materials.FeatureIDs.MATERIALS -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
