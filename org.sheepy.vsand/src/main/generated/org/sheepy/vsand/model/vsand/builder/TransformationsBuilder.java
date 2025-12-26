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
import org.sheepy.vsand.model.vsand.ITransformation;
import org.sheepy.vsand.model.vsand.Transformations;
import org.sheepy.vsand.model.vsand.Transformations.Builder;
import org.sheepy.vsand.model.vsand.impl.TransformationsImpl;

public final class TransformationsBuilder implements Builder {
  private final List<Supplier<ITransformation>> transformations = new ArrayList<>();

  public TransformationsBuilder() {
  }

  @Override
  public TransformationsBuilder addTransformation(Supplier<ITransformation> transformation) {
    this.transformations.add(transformation);
    return this;
  }

  @Override
  public TransformationsBuilder addTransformations(final List<ITransformation> transformations) {
    transformations.forEach(value -> this.transformations.add(() -> value));
    return this;
  }

  @Override
  public Transformations build() {
    final var builtTransformations = BuildUtils.collectSuppliers(transformations);
    final var built = new TransformationsImpl();
    built.transformations().addAll(builtTransformations);
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
    private static final FeatureInserter<TransformationsBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<TransformationsBuilder>(0, Inserters::attributeIndex).build();
    private static final RelationLazyInserter<TransformationsBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<TransformationsBuilder>(1, Inserters::relationIndex).add(Transformations.FeatureIDs.TRANSFORMATIONS, (builder, value) -> builder.addTransformation((Supplier<ITransformation>) value)).build();

    private static int attributeIndex(final int featureId) {
      throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case Transformations.FeatureIDs.TRANSFORMATIONS -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
