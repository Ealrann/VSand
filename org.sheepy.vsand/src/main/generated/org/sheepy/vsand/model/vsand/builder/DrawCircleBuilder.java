package org.sheepy.vsand.model.vsand.builder;

import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.DrawCircle;
import org.sheepy.vsand.model.vsand.DrawCircle.Builder;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.impl.DrawCircleImpl;

public final class DrawCircleBuilder implements Builder {
  private Supplier<Material> material = () -> null;
  private int x;
  private int y;
  private int size;

  public DrawCircleBuilder() {
  }

  @Override
  public DrawCircleBuilder material(Supplier<Material> material) {
    this.material = material;
    return this;
  }

  @Override
  public DrawCircleBuilder x(int x) {
    this.x = x;
    return this;
  }

  @Override
  public DrawCircleBuilder y(int y) {
    this.y = y;
    return this;
  }

  @Override
  public DrawCircleBuilder size(int size) {
    this.size = size;
    return this;
  }

  @Override
  public DrawCircle build() {
    final var built = new DrawCircleImpl();
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
    private static final FeatureInserter<DrawCircleBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<DrawCircleBuilder>(3, Inserters::attributeIndex).add(DrawCircle.FeatureIDs.X, (builder, value) -> builder.x((int) value)).add(DrawCircle.FeatureIDs.Y, (builder, value) -> builder.y((int) value)).add(DrawCircle.FeatureIDs.SIZE, (builder, value) -> builder.size((int) value)).build();
    private static final RelationLazyInserter<DrawCircleBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<DrawCircleBuilder>(1, Inserters::relationIndex).add(DrawCircle.FeatureIDs.MATERIAL, (builder, value) -> builder.material((Supplier<Material>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case DrawCircle.FeatureIDs.X -> 0;
        case DrawCircle.FeatureIDs.Y -> 1;
        case DrawCircle.FeatureIDs.SIZE -> 2;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case DrawCircle.FeatureIDs.MATERIAL -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
