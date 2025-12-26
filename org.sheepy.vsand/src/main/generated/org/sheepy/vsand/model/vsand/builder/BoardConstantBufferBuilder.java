package org.sheepy.vsand.model.vsand.builder;

import java.nio.ByteBuffer;
import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer.Builder;
import org.sheepy.vsand.model.vsand.impl.BoardConstantBufferImpl;

public final class BoardConstantBufferBuilder implements Builder {
  private String name;
  private ByteBuffer data;
  private int currentBoardBuffer = 0;

  public BoardConstantBufferBuilder() {
  }

  @Override
  public BoardConstantBufferBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public BoardConstantBufferBuilder data(ByteBuffer data) {
    this.data = data;
    return this;
  }

  @Override
  public BoardConstantBufferBuilder currentBoardBuffer(int currentBoardBuffer) {
    this.currentBoardBuffer = currentBoardBuffer;
    return this;
  }

  @Override
  public BoardConstantBuffer build() {
    final var built = new BoardConstantBufferImpl(name);
    built.data(data);
    built.currentBoardBuffer(currentBoardBuffer);
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
    private static final FeatureInserter<BoardConstantBufferBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<BoardConstantBufferBuilder>(3, Inserters::attributeIndex).add(BoardConstantBuffer.FeatureIDs.NAME, (builder, value) -> builder.name((String) value)).add(BoardConstantBuffer.FeatureIDs.DATA, (builder, value) -> builder.data((ByteBuffer) value)).add(BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, (builder, value) -> builder.currentBoardBuffer((int) value)).build();
    private static final RelationLazyInserter<BoardConstantBufferBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<BoardConstantBufferBuilder>(0, Inserters::relationIndex).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case BoardConstantBuffer.FeatureIDs.NAME -> 0;
        case BoardConstantBuffer.FeatureIDs.DATA -> 1;
        case BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER -> 2;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
    }
  }
}
