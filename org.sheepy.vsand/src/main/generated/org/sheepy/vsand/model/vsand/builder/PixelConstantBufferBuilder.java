package org.sheepy.vsand.model.vsand.builder;

import java.nio.ByteBuffer;
import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.PixelConstantBuffer;
import org.sheepy.vsand.model.vsand.PixelConstantBuffer.Builder;
import org.sheepy.vsand.model.vsand.impl.PixelConstantBufferImpl;

public final class PixelConstantBufferBuilder implements Builder {
  private String name;
  private ByteBuffer data;
  private int currentBoardBuffer = 0;
  private Supplier<BoardConstantBuffer> boardConstantBuffer = () -> null;

  public PixelConstantBufferBuilder() {
  }

  @Override
  public PixelConstantBufferBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public PixelConstantBufferBuilder data(ByteBuffer data) {
    this.data = data;
    return this;
  }

  @Override
  public PixelConstantBufferBuilder currentBoardBuffer(int currentBoardBuffer) {
    this.currentBoardBuffer = currentBoardBuffer;
    return this;
  }

  @Override
  public PixelConstantBufferBuilder boardConstantBuffer(
      Supplier<BoardConstantBuffer> boardConstantBuffer) {
    this.boardConstantBuffer = boardConstantBuffer;
    return this;
  }

  @Override
  public PixelConstantBuffer build() {
    final var built = new PixelConstantBufferImpl(name);
    built.data(data);
    built.currentBoardBuffer(currentBoardBuffer);
    built.boardConstantBuffer(boardConstantBuffer.get());
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
    private static final FeatureInserter<PixelConstantBufferBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<PixelConstantBufferBuilder>(3, Inserters::attributeIndex).add(PixelConstantBuffer.FeatureIDs.NAME, (builder, value) -> builder.name((String) value)).add(PixelConstantBuffer.FeatureIDs.DATA, (builder, value) -> builder.data((ByteBuffer) value)).add(PixelConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, (builder, value) -> builder.currentBoardBuffer((int) value)).build();
    private static final RelationLazyInserter<PixelConstantBufferBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<PixelConstantBufferBuilder>(1, Inserters::relationIndex).add(PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, (builder, value) -> builder.boardConstantBuffer((Supplier<BoardConstantBuffer>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case PixelConstantBuffer.FeatureIDs.NAME -> 0;
        case PixelConstantBuffer.FeatureIDs.DATA -> 1;
        case PixelConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER -> 2;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
