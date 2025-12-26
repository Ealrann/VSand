package org.sheepy.vsand.model.vsand.builder;

import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.MaterialProvider;
import org.sheepy.vsand.model.vsand.MultipleTransformation;
import org.sheepy.vsand.model.vsand.MultipleTransformation.Builder;
import org.sheepy.vsand.model.vsand.impl.MultipleTransformationImpl;

public final class MultipleTransformationBuilder implements Builder {
  private int probability;
  private int propagation = 1;
  private boolean isStaticTransformation;
  private Supplier<Material> target = () -> null;
  private Supplier<MaterialProvider> reactants;
  private Supplier<MaterialProvider> catalysts;
  private String name = "";

  public MultipleTransformationBuilder() {
  }

  @Override
  public MultipleTransformationBuilder probability(int probability) {
    this.probability = probability;
    return this;
  }

  @Override
  public MultipleTransformationBuilder propagation(int propagation) {
    this.propagation = propagation;
    return this;
  }

  @Override
  public MultipleTransformationBuilder isStaticTransformation(boolean isStaticTransformation) {
    this.isStaticTransformation = isStaticTransformation;
    return this;
  }

  @Override
  public MultipleTransformationBuilder target(Supplier<Material> target) {
    this.target = target;
    return this;
  }

  @Override
  public MultipleTransformationBuilder reactants(Supplier<MaterialProvider> reactants) {
    this.reactants = reactants;
    return this;
  }

  @Override
  public MultipleTransformationBuilder catalysts(Supplier<MaterialProvider> catalysts) {
    this.catalysts = catalysts;
    return this;
  }

  @Override
  public MultipleTransformationBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public MultipleTransformation build() {
    final var built = new MultipleTransformationImpl(reactants.get(), catalysts.get());
    built.probability(probability);
    built.propagation(propagation);
    built.isStaticTransformation(isStaticTransformation);
    built.target(target.get());
    built.name(name);
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
    private static final FeatureInserter<MultipleTransformationBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<MultipleTransformationBuilder>(4, Inserters::attributeIndex).add(MultipleTransformation.FeatureIDs.PROBABILITY, (builder, value) -> builder.probability((int) value)).add(MultipleTransformation.FeatureIDs.PROPAGATION, (builder, value) -> builder.propagation((int) value)).add(MultipleTransformation.FeatureIDs.IS_STATIC_TRANSFORMATION, (builder, value) -> builder.isStaticTransformation((boolean) value)).add(MultipleTransformation.FeatureIDs.NAME, (builder, value) -> builder.name((String) value)).build();
    private static final RelationLazyInserter<MultipleTransformationBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<MultipleTransformationBuilder>(3, Inserters::relationIndex).add(MultipleTransformation.FeatureIDs.TARGET, (builder, value) -> builder.target((Supplier<Material>) value)).add(MultipleTransformation.FeatureIDs.REACTANTS, (builder, value) -> builder.reactants((Supplier<MaterialProvider>) value)).add(MultipleTransformation.FeatureIDs.CATALYSTS, (builder, value) -> builder.catalysts((Supplier<MaterialProvider>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case MultipleTransformation.FeatureIDs.PROBABILITY -> 0;
        case MultipleTransformation.FeatureIDs.PROPAGATION -> 1;
        case MultipleTransformation.FeatureIDs.IS_STATIC_TRANSFORMATION -> 2;
        case MultipleTransformation.FeatureIDs.NAME -> 3;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case MultipleTransformation.FeatureIDs.TARGET -> 0;
        case MultipleTransformation.FeatureIDs.REACTANTS -> 1;
        case MultipleTransformation.FeatureIDs.CATALYSTS -> 2;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
