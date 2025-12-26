package org.sheepy.vsand.model.vsand.builder;

import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.DrawSquare;
import org.sheepy.vsand.model.vsand.DrawSquare.Builder;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.impl.DrawSquareImpl;

public final class DrawSquareBuilder implements Builder {
  private Supplier<Material> material = () -> null;
  private int x;
  private int y;
  private int size;

  public DrawSquareBuilder() {
  }

  @Override
  public DrawSquareBuilder material(Supplier<Material> material) {
    this.material = material;
    return this;
  }

  @Override
  public DrawSquareBuilder x(int x) {
    this.x = x;
    return this;
  }

  @Override
  public DrawSquareBuilder y(int y) {
    this.y = y;
    return this;
  }

  @Override
  public DrawSquareBuilder size(int size) {
    this.size = size;
    return this;
  }

  @Override
  public DrawSquare build() {
    final var built = new DrawSquareImpl();
    built.material(material.get());
    built.x(x);
    built.y(y);
    built.size(size);
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
    private static final FeatureInserter<DrawSquareBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<DrawSquareBuilder>(3, Inserters::attributeIndex).add(DrawSquare.FeatureIDs.X, (builder, value) -> builder.x((int) value)).add(DrawSquare.FeatureIDs.Y, (builder, value) -> builder.y((int) value)).add(DrawSquare.FeatureIDs.SIZE, (builder, value) -> builder.size((int) value)).build();
    private static final RelationLazyInserter<DrawSquareBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<DrawSquareBuilder>(1, Inserters::relationIndex).add(DrawSquare.FeatureIDs.MATERIAL, (builder, value) -> builder.material((Supplier<Material>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case DrawSquare.FeatureIDs.X -> 0;
        case DrawSquare.FeatureIDs.Y -> 1;
        case DrawSquare.FeatureIDs.SIZE -> 2;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case DrawSquare.FeatureIDs.MATERIAL -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
