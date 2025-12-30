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
import org.sheepy.vsand.model.vsand.PixelConstantBuffer;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class PixelConstantBufferImpl extends FeaturedObject<PixelConstantBuffer.Features<?>> implements PixelConstantBuffer {
  private static final int FEATURE_COUNT = 6;
  private final ModelNotifier<PixelConstantBuffer.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final String name;
  private ByteBuffer data;
  private int currentBoardBuffer;
  private boolean deterministicRandom;
  private long randomSeed;
  private BoardConstantBuffer boardConstantBuffer;

  public PixelConstantBufferImpl(final String name) {
    this.name = name;
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<PixelConstantBuffer.Features<?>> notifier() {
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
    notifier.notify(PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, false, false, eventType, oldValue, boardConstantBuffer);
  }

  @Override
  public Group<PixelConstantBuffer> lmGroup() {
    return VSandModelDefinition.Groups.PIXEL_CONSTANT_BUFFER;
  }

  @Override
  protected FeatureSetter<PixelConstantBuffer> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<PixelConstantBuffer> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case PixelConstantBuffer.FeatureIDs.NAME -> 0;
      case PixelConstantBuffer.FeatureIDs.DATA -> 1;
      case PixelConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER -> 2;
      case PixelConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM -> 3;
      case PixelConstantBuffer.FeatureIDs.RANDOM_SEED -> 4;
      case PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER -> 5;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<PixelConstantBuffer> GET_MAP = new FeatureGetter.Builder<PixelConstantBuffer>(FEATURE_COUNT, PixelConstantBufferImpl::featureIndexStatic).add(PixelConstantBuffer.FeatureIDs.NAME, PixelConstantBuffer::name).add(PixelConstantBuffer.FeatureIDs.DATA, PixelConstantBuffer::data).add(PixelConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, PixelConstantBuffer::currentBoardBuffer).add(PixelConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM, PixelConstantBuffer::deterministicRandom).add(PixelConstantBuffer.FeatureIDs.RANDOM_SEED, PixelConstantBuffer::randomSeed).add(PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, PixelConstantBuffer::boardConstantBuffer).build();
    private static final FeatureSetter<PixelConstantBuffer> SET_MAP = new FeatureSetter.Builder<PixelConstantBuffer>(FEATURE_COUNT, PixelConstantBufferImpl::featureIndexStatic).add(PixelConstantBuffer.FeatureIDs.DATA, (object, value) -> ((PixelConstantBufferImpl) object).data((ByteBuffer) value)).add(PixelConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, (object, value) -> ((PixelConstantBufferImpl) object).currentBoardBuffer((int) value)).add(PixelConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM, (object, value) -> ((PixelConstantBufferImpl) object).deterministicRandom((boolean) value)).add(PixelConstantBuffer.FeatureIDs.RANDOM_SEED, (object, value) -> ((PixelConstantBufferImpl) object).randomSeed((long) value)).add(PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, (object, value) -> ((PixelConstantBufferImpl) object).boardConstantBuffer((BoardConstantBuffer) value)).build();
  }
}
