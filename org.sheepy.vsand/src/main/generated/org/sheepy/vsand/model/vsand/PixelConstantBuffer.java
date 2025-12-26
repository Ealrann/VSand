package org.sheepy.vsand.model.vsand;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.Named;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.RelationBuilder;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.builder.PixelConstantBufferBuilder;

public interface PixelConstantBuffer extends BoardConstantBuffer {
  static Builder builder() {
    return new PixelConstantBufferBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  BoardConstantBuffer boardConstantBuffer();
  void boardConstantBuffer(final BoardConstantBuffer boardConstantBuffer);

  interface FeatureIDs {
    int NAME = Named.FeatureIDs.NAME;
    int DATA = ConstantBuffer.FeatureIDs.DATA;
    int CURRENT_BOARD_BUFFER = BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER;
    int BOARD_CONSTANT_BUFFER = -1751617681;
  }

  interface Features<T extends Features<T>> extends BoardConstantBuffer.Features<T> {
    Attribute<String, String, Listener<String>, Named.Features<?>> NAME = Named.Features.NAME;
    Attribute<ByteBuffer, ByteBuffer, Listener<ByteBuffer>, ConstantBuffer.Features<?>> DATA = ConstantBuffer.Features.DATA;
    Attribute<Integer, Integer, IntListener, BoardConstantBuffer.Features<?>> CURRENT_BOARD_BUFFER = BoardConstantBuffer.Features.CURRENT_BOARD_BUFFER;
    Relation<BoardConstantBuffer, BoardConstantBuffer, Listener<BoardConstantBuffer>, Features<?>> BOARD_CONSTANT_BUFFER = new RelationBuilder<BoardConstantBuffer, BoardConstantBuffer, Listener<BoardConstantBuffer>, Features<?>>().name("boardConstantBuffer").id(PixelConstantBuffer.FeatureIDs.BOARD_CONSTANT_BUFFER).concept(() -> VSandModelDefinition.Groups.BOARD_CONSTANT_BUFFER).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(NAME, DATA, CURRENT_BOARD_BUFFER, BOARD_CONSTANT_BUFFER);
  }

  interface Builder extends IFeaturedObject.Builder<PixelConstantBuffer> {
    Builder name(String name);
    Builder data(ByteBuffer data);
    Builder currentBoardBuffer(int currentBoardBuffer);
    Builder boardConstantBuffer(Supplier<BoardConstantBuffer> boardConstantBuffer);
  }
}
