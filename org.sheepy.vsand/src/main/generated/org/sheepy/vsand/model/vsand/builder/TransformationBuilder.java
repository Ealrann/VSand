package org.sheepy.vsand.model.vsand.builder;

import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.Transformation;
import org.sheepy.vsand.model.vsand.Transformation.Builder;
import org.sheepy.vsand.model.vsand.impl.TransformationImpl;

public final class TransformationBuilder implements Builder {
  private int probability;
  private int propagation = 1;
  private boolean isStaticTransformation;
  private Supplier<Material> target = () -> null;
  private Supplier<Material> reactant = () -> null;
  private Supplier<Material> catalyst = () -> null;

  public TransformationBuilder() {
  }

  @Override
  public TransformationBuilder probability(int probability) {
    this.probability = probability;
    return this;
  }

  @Override
  public TransformationBuilder propagation(int propagation) {
    this.propagation = propagation;
    return this;
  }

  @Override
  public TransformationBuilder isStaticTransformation(boolean isStaticTransformation) {
    this.isStaticTransformation = isStaticTransformation;
    return this;
  }

  @Override
  public TransformationBuilder target(Supplier<Material> target) {
    this.target = target;
    return this;
  }

  @Override
  public TransformationBuilder reactant(Supplier<Material> reactant) {
    this.reactant = reactant;
    return this;
  }

  @Override
  public TransformationBuilder catalyst(Supplier<Material> catalyst) {
    this.catalyst = catalyst;
    return this;
  }

  @Override
  public Transformation build() {
    final var built = new TransformationImpl();
    built.probability(probability);
    built.propagation(propagation);
    built.isStaticTransformation(isStaticTransformation);
    built.target(target.get());
    built.reactant(reactant.get());
    built.catalyst(catalyst.get());
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
    private static final FeatureInserter<TransformationBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<TransformationBuilder>(3, Inserters::attributeIndex).add(Transformation.FeatureIDs.PROBABILITY, (builder, value) -> builder.probability((int) value)).add(Transformation.FeatureIDs.PROPAGATION, (builder, value) -> builder.propagation((int) value)).add(Transformation.FeatureIDs.IS_STATIC_TRANSFORMATION, (builder, value) -> builder.isStaticTransformation((boolean) value)).build();
    private static final RelationLazyInserter<TransformationBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<TransformationBuilder>(3, Inserters::relationIndex).add(Transformation.FeatureIDs.TARGET, (builder, value) -> builder.target((Supplier<Material>) value)).add(Transformation.FeatureIDs.REACTANT, (builder, value) -> builder.reactant((Supplier<Material>) value)).add(Transformation.FeatureIDs.CATALYST, (builder, value) -> builder.catalyst((Supplier<Material>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case Transformation.FeatureIDs.PROBABILITY -> 0;
        case Transformation.FeatureIDs.PROPAGATION -> 1;
        case Transformation.FeatureIDs.IS_STATIC_TRANSFORMATION -> 2;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case Transformation.FeatureIDs.TARGET -> 0;
        case Transformation.FeatureIDs.REACTANT -> 1;
        case Transformation.FeatureIDs.CATALYST -> 2;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
