package org.sheepy.vsand.model.vsand.impl;

import java.nio.ByteBuffer;
import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class BoardConstantBufferImpl extends FeaturedObject<BoardConstantBuffer.Features<?>> implements BoardConstantBuffer {
  private static final int FEATURE_COUNT = 3;
  private final ModelNotifier<BoardConstantBuffer.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final String name;
  private ByteBuffer data;
  private int currentBoardBuffer;

  public BoardConstantBufferImpl(final String name) {
    this.name = name;
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<BoardConstantBuffer.Features<?>> notifier() {
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
  public Group<BoardConstantBuffer> lmGroup() {
    return VSandModelDefinition.Groups.BOARD_CONSTANT_BUFFER;
  }

  @Override
  protected FeatureSetter<BoardConstantBuffer> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<BoardConstantBuffer> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case BoardConstantBuffer.FeatureIDs.NAME -> 0;
      case BoardConstantBuffer.FeatureIDs.DATA -> 1;
      case BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER -> 2;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<BoardConstantBuffer> GET_MAP = new FeatureGetter.Builder<BoardConstantBuffer>(FEATURE_COUNT, BoardConstantBufferImpl::featureIndexStatic).add(BoardConstantBuffer.FeatureIDs.NAME, BoardConstantBuffer::name).add(BoardConstantBuffer.FeatureIDs.DATA, BoardConstantBuffer::data).add(BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, BoardConstantBuffer::currentBoardBuffer).build();
    private static final FeatureSetter<BoardConstantBuffer> SET_MAP = new FeatureSetter.Builder<BoardConstantBuffer>(FEATURE_COUNT, BoardConstantBufferImpl::featureIndexStatic).add(BoardConstantBuffer.FeatureIDs.DATA, (object, value) -> ((BoardConstantBufferImpl) object).data((ByteBuffer) value)).add(BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER, (object, value) -> ((BoardConstantBufferImpl) object).currentBoardBuffer((int) value)).build();
  }
}
