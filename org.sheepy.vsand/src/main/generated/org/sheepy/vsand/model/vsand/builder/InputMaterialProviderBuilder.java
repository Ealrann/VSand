package org.sheepy.vsand.model.vsand.builder;

import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.InputMaterialProvider;
import org.sheepy.vsand.model.vsand.InputMaterialProvider.Builder;
import org.sheepy.vsand.model.vsand.Materials;
import org.sheepy.vsand.model.vsand.impl.InputMaterialProviderImpl;

public final class InputMaterialProviderBuilder implements Builder {
  private Supplier<Materials> materials;

  public InputMaterialProviderBuilder() {
  }

  @Override
  public InputMaterialProviderBuilder materials(Supplier<Materials> materials) {
    this.materials = materials;
    return this;
  }

  @Override
  public InputMaterialProvider build() {
    final var built = new InputMaterialProviderImpl(materials.get());
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
    private static final FeatureInserter<InputMaterialProviderBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<InputMaterialProviderBuilder>(0, Inserters::attributeIndex).build();
    private static final RelationLazyInserter<InputMaterialProviderBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<InputMaterialProviderBuilder>(1, Inserters::relationIndex).add(InputMaterialProvider.FeatureIDs.MATERIALS, (builder, value) -> builder.materials((Supplier<Materials>) value)).build();

    private static int attributeIndex(final int featureId) {
      throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case InputMaterialProvider.FeatureIDs.MATERIALS -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
