package org.sheepy.vsand.model.vsand;

import java.nio.ByteBuffer;
import java.util.List;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMCoreModelDefinition;
import org.logoce.lmf.core.lang.Named;
import org.logoce.lmf.core.lang.builder.AttributeBuilder;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.builder.BoardConstantBufferBuilder;

public interface BoardConstantBuffer extends ConstantBuffer {
  static Builder builder() {
    return new BoardConstantBufferBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  int currentBoardBuffer();
  void currentBoardBuffer(final int currentBoardBuffer);

  interface FeatureIDs {
    int NAME = Named.FeatureIDs.NAME;
    int DATA = ConstantBuffer.FeatureIDs.DATA;
    int CURRENT_BOARD_BUFFER = -2004445016;
  }

  interface Features<T extends Features<T>> extends ConstantBuffer.Features<T> {
    Attribute<String, String, Listener<String>, Named.Features<?>> NAME = Named.Features.NAME;
    Attribute<ByteBuffer, ByteBuffer, Listener<ByteBuffer>, ConstantBuffer.Features<?>> DATA = ConstantBuffer.Features.DATA;
    Attribute<Integer, Integer, IntListener, Features<?>> CURRENT_BOARD_BUFFER = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("currentBoardBuffer").defaultValue("0").id(BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(NAME, DATA, CURRENT_BOARD_BUFFER);
  }

  interface Builder extends IFeaturedObject.Builder<BoardConstantBuffer> {
    Builder name(String name);
    Builder data(ByteBuffer data);
    Builder currentBoardBuffer(int currentBoardBuffer);
  }
}
