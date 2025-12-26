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
import org.sheepy.vsand.model.vsand.builder.DrawCircleBuilder;

public interface DrawCircle extends DrawCommand {
  static Builder builder() {
    return new DrawCircleBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  int x();
  int y();
  int size();
  void x(final int x);
  void y(final int y);
  void size(final int size);

  interface FeatureIDs {
    int MATERIAL = DrawCommand.FeatureIDs.MATERIAL;
    int X = -1660376419;
    int Y = -1660376418;
    int SIZE = 864405436;
  }

  interface Features<T extends Features<T>> extends DrawCommand.Features<T> {
    Relation<Material, Material, Listener<Material>, DrawCommand.Features<?>> MATERIAL = DrawCommand.Features.MATERIAL;
    Attribute<Integer, Integer, IntListener, Features<?>> X = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("x").id(DrawCircle.FeatureIDs.X).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> Y = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("y").id(DrawCircle.FeatureIDs.Y).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> SIZE = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("size").id(DrawCircle.FeatureIDs.SIZE).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(MATERIAL, X, Y, SIZE);
  }

  interface Builder extends IFeaturedObject.Builder<DrawCircle> {
    Builder material(Supplier<Material> material);
    Builder x(int x);
    Builder y(int y);
    Builder size(int size);
  }
}
