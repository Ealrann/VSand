package org.sheepy.vsand.model.vsand;

import java.nio.ByteBuffer;
import java.util.List;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.BooleanListener;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.api.notification.listener.LongListener;
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
  boolean deterministicRandom();
  long randomSeed();
  void currentBoardBuffer(final int currentBoardBuffer);
  void deterministicRandom(final boolean deterministicRandom);
  void randomSeed(final long randomSeed);

  interface FeatureIDs {
    int NAME = Named.FeatureIDs.NAME;
    int DATA = ConstantBuffer.FeatureIDs.DATA;
    int CURRENT_BOARD_BUFFER = -2004445016;
    int DETERMINISTIC_RANDOM = 1885316538;
    int RANDOM_SEED = -1509830161;
  }

  interface Features<T extends Features<T>> extends ConstantBuffer.Features<T> {
    Attribute<String, String, Listener<String>, Named.Features<?>> NAME = Named.Features.NAME;
    Attribute<ByteBuffer, ByteBuffer, Listener<ByteBuffer>, ConstantBuffer.Features<?>> DATA = ConstantBuffer.Features.DATA;
    Attribute<Integer, Integer, IntListener, Features<?>> CURRENT_BOARD_BUFFER = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("currentBoardBuffer").defaultValue("0").id(BoardConstantBuffer.FeatureIDs.CURRENT_BOARD_BUFFER).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> DETERMINISTIC_RANDOM = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("deterministicRandom").defaultValue("false").id(BoardConstantBuffer.FeatureIDs.DETERMINISTIC_RANDOM).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    Attribute<Long, Long, LongListener, Features<?>> RANDOM_SEED = new AttributeBuilder<Long, Long, LongListener, Features<?>>().name("randomSeed").defaultValue("0").id(BoardConstantBuffer.FeatureIDs.RANDOM_SEED).datatype(() -> LMCoreModelDefinition.Units.LONG).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(NAME, DATA, CURRENT_BOARD_BUFFER, DETERMINISTIC_RANDOM, RANDOM_SEED);
  }

  interface Builder extends IFeaturedObject.Builder<BoardConstantBuffer> {
    Builder name(String name);
    Builder data(ByteBuffer data);
    Builder currentBoardBuffer(int currentBoardBuffer);
    Builder deterministicRandom(boolean deterministicRandom);
    Builder randomSeed(long randomSeed);
  }
}
