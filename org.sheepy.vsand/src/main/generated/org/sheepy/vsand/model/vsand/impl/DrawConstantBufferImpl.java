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
  private static final int FEATURE_COUNT = 4;
  private final ModelNotifier<DrawConstantBuffer.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final String name;
  private ByteBuffer data;
  private int currentBoardBuffer;
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
      case DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER -> 3;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<DrawConstantBuffer> GET_MAP = new FeatureGetter.Builder<DrawConstantBuffer>(FEATURE_COUNT, DrawConstantBufferImpl::featureIndexStatic).add(DrawConstantBuffer.FeatureIDs.NAME, DrawConstantBuffer::name).add(DrawConstantBuffer.FeatureIDs.DATA, DrawConstantBuffer::data).add(DrawConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, DrawConstantBuffer::currentBoardBuffer).add(DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, DrawConstantBuffer::boardConstantBuffer).build();
    private static final FeatureSetter<DrawConstantBuffer> SET_MAP = new FeatureSetter.Builder<DrawConstantBuffer>(FEATURE_COUNT, DrawConstantBufferImpl::featureIndexStatic).add(DrawConstantBuffer.FeatureIDs.DATA, (object, value) -> ((DrawConstantBufferImpl) object).data((ByteBuffer) value)).add(DrawConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, (object, value) -> ((DrawConstantBufferImpl) object).currentBoardBuffer((int) value)).add(DrawConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER, (object, value) -> ((DrawConstantBufferImpl) object).boardConstantBuffer((BoardConstantBuffer) value)).build();
  }
}
