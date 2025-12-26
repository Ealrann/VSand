package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMCoreModelDefinition;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.AttributeBuilder;
import org.sheepy.vsand.model.vsand.builder.DrawLineBuilder;

public interface DrawLine extends DrawCommand {
  static Builder builder() {
    return new DrawLineBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  int x1();
  int y1();
  int x2();
  int y2();
  int size();
  void x1(final int x1);
  void y1(final int y1);
  void x2(final int x2);
  void y2(final int y2);
  void size(final int size);

  interface FeatureIDs {
    int MATERIAL = DrawCommand.FeatureIDs.MATERIAL;
    int X1 = -105454640;
    int Y1 = -105454609;
    int X2 = -105454639;
    int Y2 = -105454608;
    int SIZE = 1737214808;
  }

  interface Features<T extends Features<T>> extends DrawCommand.Features<T> {
    Relation<Material, Material, Listener<Material>, DrawCommand.Features<?>> MATERIAL = DrawCommand.Features.MATERIAL;
    Attribute<Integer, Integer, IntListener, Features<?>> X1 = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("x1").id(DrawLine.FeatureIDs.X1).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> Y1 = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("y1").id(DrawLine.FeatureIDs.Y1).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> X2 = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("x2").id(DrawLine.FeatureIDs.X2).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> Y2 = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("y2").id(DrawLine.FeatureIDs.Y2).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> SIZE = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("size").id(DrawLine.FeatureIDs.SIZE).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(MATERIAL, X1, Y1, X2, Y2, SIZE);
  }

  interface Builder extends IFeaturedObject.Builder<DrawLine> {
    Builder material(Supplier<Material> material);
    Builder x1(int x1);
    Builder y1(int y1);
    Builder x2(int x2);
    Builder y2(int y2);
    Builder size(int size);
  }
}
