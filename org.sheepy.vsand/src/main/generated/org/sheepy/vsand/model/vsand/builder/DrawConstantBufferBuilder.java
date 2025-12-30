package org.sheepy.vsand.model.vsand.builder;

import java.nio.ByteBuffer;
import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.DrawConstantBuffer;
import org.sheepy.vsand.model.vsand.DrawConstantBuffer.Builder;
import org.sheepy.vsand.model.vsand.impl.DrawConstantBufferImpl;

public final class DrawConstantBufferBuilder implements Builder {
  private String name;
  private ByteBuffer data;
  private int currentBoardBuffer = 0;
  private boolean deterministicRandom = false;
  private long randomSeed = 0;
  private Supplier<BoardConstantBuffer> boardConstantBuffer = () -> null;

  public DrawConstantBufferBuilder() {
  }

  @Override
  public DrawConstantBufferBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public DrawConstantBufferBuilder data(ByteBuffer data) {
    this.data = data;
    return this;
  }

  @Override
  public DrawConstantBufferBuilder currentBoardBuffer(int currentBoardBuffer) {
    this.currentBoardBuffer = currentBoardBuffer;
    return this;
  }

  @Override
  public DrawConstantBufferBuilder deterministicRandom(boolean deterministicRandom) {
    this.deterministicRandom = deterministicRandom;
    return this;
  }

  @Override
  public DrawConstantBufferBuilder randomSeed(long randomSeed) {
    this.randomSeed = randomSeed;
    return this;
  }

  @Override
  public DrawConstantBufferBuilder boardConstantBuffer(
      Supplier<BoardConstantBuffer> boardConstantBuffer) {
    this.boardConstantBuffer = boardConstantBuffer;
    return this;
  }

  @Override
  public DrawConstantBuffer build() {
    final var built = new DrawConstantBufferImpl(name);
    built.data(data);
    built.currentBoardBuffer(currentBoardBuffer);
    built.deterministicRandom(deterministicRandom);
    built.randomSeed(randomSeed);
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
    private static final FeatureInserter<DrawConstantBufferBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<DrawConstantBufferBuilder>(5, Inserters::attributeIndex).add(DrawConstantBuffer.FeatureIDs.NAME, (builder, value) -> builder.name((String) value)).add(DrawConstantBuffer.FeatureIDs.DATA, (builder, value) -> builder.data((ByteBuffer) value)).add(DrawConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, (builder, value) -> builder.currentBoardBuffer((int) value)).add(DrawConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM, (builder, value) -> builder.deterministicRandom((boolean) value)).add(DrawConstantBuffer.FeatureIDs.RANDOM_SEED, (builder, value) -> builder.randomSeed((long) value)).build();
    private static final RelationLazyInserter<DrawConstantBufferBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<DrawConstantBufferBuilder>(1, Inserters::relationIndex).add(DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, (builder, value) -> builder.boardConstantBuffer((Supplier<BoardConstantBuffer>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case DrawConstantBuffer.FeatureIDs.NAME -> 0;
        case DrawConstantBuffer.FeatureIDs.DATA -> 1;
        case DrawConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER -> 2;
        case DrawConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM -> 3;
        case DrawConstantBuffer.FeatureIDs.RANDOM_SEED -> 4;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
