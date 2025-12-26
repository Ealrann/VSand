package org.sheepy.vsand.model.vsand.builder;

import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.DrawLine;
import org.sheepy.vsand.model.vsand.DrawLine.Builder;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.impl.DrawLineImpl;

public final class DrawLineBuilder implements Builder {
  private Supplier<Material> material = () -> null;
  private int x1;
  private int y1;
  private int x2;
  private int y2;
  private int size;

  public DrawLineBuilder() {
  }

  @Override
  public DrawLineBuilder material(Supplier<Material> material) {
    this.material = material;
    return this;
  }

  @Override
  public DrawLineBuilder x1(int x1) {
    this.x1 = x1;
    return this;
  }

  @Override
  public DrawLineBuilder y1(int y1) {
    this.y1 = y1;
    return this;
  }

  @Override
  public DrawLineBuilder x2(int x2) {
    this.x2 = x2;
    return this;
  }

  @Override
  public DrawLineBuilder y2(int y2) {
    this.y2 = y2;
    return this;
  }

  @Override
  public DrawLineBuilder size(int size) {
    this.size = size;
    return this;
  }

  @Override
  public DrawLine build() {
    final var built = new DrawLineImpl();
    built.material(material.get());
    built.x1(x1);
    built.y1(y1);
    built.x2(x2);
    built.y2(y2);
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
    private static final FeatureInserter<DrawLineBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<DrawLineBuilder>(5, Inserters::attributeIndex).add(DrawLine.FeatureIDs.X1, (builder, value) -> builder.x1((int) value)).add(DrawLine.FeatureIDs.Y1, (builder, value) -> builder.y1((int) value)).add(DrawLine.FeatureIDs.X2, (builder, value) -> builder.x2((int) value)).add(DrawLine.FeatureIDs.Y2, (builder, value) -> builder.y2((int) value)).add(DrawLine.FeatureIDs.SIZE, (builder, value) -> builder.size((int) value)).build();
    private static final RelationLazyInserter<DrawLineBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<DrawLineBuilder>(1, Inserters::relationIndex).add(DrawLine.FeatureIDs.MATERIAL, (builder, value) -> builder.material((Supplier<Material>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case DrawLine.FeatureIDs.X1 -> 0;
        case DrawLine.FeatureIDs.Y1 -> 1;
        case DrawLine.FeatureIDs.X2 -> 2;
        case DrawLine.FeatureIDs.Y2 -> 3;
        case DrawLine.FeatureIDs.SIZE -> 4;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case DrawLine.FeatureIDs.MATERIAL -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
