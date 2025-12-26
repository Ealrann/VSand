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
  private static final int FEATURE_COUNT = 4;
  private final ModelNotifier<PixelConstantBuffer.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final String name;
  private ByteBuffer data;
  private int currentBoardBuffer;
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
      case PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER -> 3;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<PixelConstantBuffer> GET_MAP = new FeatureGetter.Builder<PixelConstantBuffer>(FEATURE_COUNT, PixelConstantBufferImpl::featureIndexStatic).add(PixelConstantBuffer.FeatureIDs.NAME, PixelConstantBuffer::name).add(PixelConstantBuffer.FeatureIDs.DATA, PixelConstantBuffer::data).add(PixelConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, PixelConstantBuffer::currentBoardBuffer).add(PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, PixelConstantBuffer::boardConstantBuffer).build();
    private static final FeatureSetter<PixelConstantBuffer> SET_MAP = new FeatureSetter.Builder<PixelConstantBuffer>(FEATURE_COUNT, PixelConstantBufferImpl::featureIndexStatic).add(PixelConstantBuffer.FeatureIDs.DATA, (object, value) -> ((PixelConstantBufferImpl) object).data((ByteBuffer) value)).add(PixelConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, (object, value) -> ((PixelConstantBufferImpl) object).currentBoardBuffer((int) value)).add(PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, (object, value) -> ((PixelConstantBufferImpl) object).boardConstantBuffer((BoardConstantBuffer) value)).build();
  }
}
