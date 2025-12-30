package org.sheepy.vsand.model.vsand.impl;

import java.nio.ByteBuffer;
import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.api.notification.Notification;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.DrawConstantBuffer;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class DrawConstantBufferImpl extends FeaturedObject<DrawConstantBuffer.Features<?>> implements DrawConstantBuffer {
  private static final int FEATURE_COUNT = 6;
  private final ModelNotifier<DrawConstantBuffer.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final String name;
  private ByteBuffer data;
  private int currentBoardBuffer;
  private boolean deterministicRandom;
  private long randomSeed;
  private BoardConstantBuffer boardConstantBuffer;

  public DrawConstantBufferImpl(final String name) {
    this.name = name;
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<DrawConstantBuffer.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public ByteBuffer data() {
    return data;
  }

  @Override
  public void data(final ByteBuffer data) {
    final var oldValue = this.data;
    this.data = data;
    notifier.notify(ConstantBuffer.FeatureIDs.DATA, false, false, oldValue, data);
  }

  @Override
  public int currentBoardBuffer() {
    return currentBoardBuffer;
  }

  @Override
  public void currentBoardBuffer(final int currentBoardBuffer) {
    final var oldValue = this.currentBoardBuffer;
    this.currentBoardBuffer = currentBoardBuffer;
    notifier.notifyInt(BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, false, false, oldValue, currentBoardBuffer);
  }

  @Override
  public boolean deterministicRandom() {
    return deterministicRandom;
  }

  @Override
  public void deterministicRandom(final boolean deterministicRandom) {
    final var oldValue = this.deterministicRandom;
    this.deterministicRandom = deterministicRandom;
    notifier.notifyBoolean(BoardConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM, false, false, oldValue, deterministicRandom);
  }

  @Override
  public long randomSeed() {
    return randomSeed;
  }

  @Override
  public void randomSeed(final long randomSeed) {
    final var oldValue = this.randomSeed;
    this.randomSeed = randomSeed;
    notifier.notifyLong(BoardConstantBuffer.FeatureIDs.RANDOM_SEED, false, false, oldValue, randomSeed);
  }

  @Override
  public BoardConstantBuffer boardConstantBuffer() {
    return boardConstantBuffer;
  }

  @Override
  public void boardConstantBuffer(final BoardConstantBuffer boardConstantBuffer) {
    final var oldValue = this.boardConstantBuffer;
    final var eventType = boardConstantBuffer == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.boardConstantBuffer = boardConstantBuffer;
    notifier.notify(DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, false, false, eventType, oldValue, boardConstantBuffer);
  }

  @Override
  public Group<DrawConstantBuffer> lmGroup() {
    return VSandModelDefinition.Groups.DRAW_CONSTANT_BUFFER;
  }

  @Override
  protected FeatureSetter<DrawConstantBuffer> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<DrawConstantBuffer> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case DrawConstantBuffer.FeatureIDs.NAME -> 0;
      case DrawConstantBuffer.FeatureIDs.DATA -> 1;
      case DrawConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER -> 2;
      case DrawConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM -> 3;
      case DrawConstantBuffer.FeatureIDs.RANDOM_SEED -> 4;
      case DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER -> 5;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<DrawConstantBuffer> GET_MAP = new FeatureGetter.Builder<DrawConstantBuffer>(FEATURE_COUNT, DrawConstantBufferImpl::featureIndexStatic).add(DrawConstantBuffer.FeatureIDs.NAME, DrawConstantBuffer::name).add(DrawConstantBuffer.FeatureIDs.DATA, DrawConstantBuffer::data).add(DrawConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, DrawConstantBuffer::currentBoardBuffer).add(DrawConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM, DrawConstantBuffer::deterministicRandom).add(DrawConstantBuffer.FeatureIDs.RANDOM_SEED, DrawConstantBuffer::randomSeed).add(DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, DrawConstantBuffer::boardConstantBuffer).build();
    private static final FeatureSetter<DrawConstantBuffer> SET_MAP = new FeatureSetter.Builder<DrawConstantBuffer>(FEATURE_COUNT, DrawConstantBufferImpl::featureIndexStatic).add(DrawConstantBuffer.FeatureIDs.DATA, (object, value) -> ((DrawConstantBufferImpl) object).data((ByteBuffer) value)).add(DrawConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, (object, value) -> ((DrawConstantBufferImpl) object).currentBoardBuffer((int) value)).add(DrawConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM, (object, value) -> ((DrawConstantBufferImpl) object).deterministicRandom((boolean) value)).add(DrawConstantBuffer.FeatureIDs.RANDOM_SEED, (object, value) -> ((DrawConstantBufferImpl) object).randomSeed((long) value)).add(DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, (object, value) -> ((DrawConstantBufferImpl) object).boardConstantBuffer((BoardConstantBuffer) value)).build();
  }
}
