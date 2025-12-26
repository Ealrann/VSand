package org.sheepy.vsand.model.vsand;

import java.util.List;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.BooleanListener;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMCoreModelDefinition;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.AttributeBuilder;
import org.logoce.lmf.core.lang.builder.RelationBuilder;

public interface ITransformation extends LMObject {
  @Override
  IModelNotifier<? extends Features<?>> notifier();
  int probability();
  int propagation();
  boolean isStaticTransformation();
  Material target();
  void probability(final int probability);
  void propagation(final int propagation);
  void isStaticTransformation(final boolean isStaticTransformation);
  void target(final Material target);

  interface FeatureIDs {
    int PROBABILITY = 1507571778;
    int PROPAGATION = 273108441;
    int IS_STATIC_TRANSFORMATION = 851387188;
    int TARGET = 1247526244;
  }

  interface Features<T extends Features<T>> extends LMObject.Features<T> {
    Attribute<Integer, Integer, IntListener, Features<?>> PROBABILITY = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("probability").id(ITransformation.FeatureIDs.PROBABILITY).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> PROPAGATION = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("propagation").defaultValue("1").id(ITransformation.FeatureIDs.PROPAGATION).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> IS_STATIC_TRANSFORMATION = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("isStaticTransformation").id(ITransformation.FeatureIDs.IS_STATIC_TRANSFORMATION).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    Relation<Material, Material, Listener<Material>, Features<?>> TARGET = new RelationBuilder<Material, Material, Listener<Material>, Features<?>>().name("target").id(ITransformation.FeatureIDs.TARGET).concept(() -> VSandModelDefinition.Groups.MATERIAL).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(PROBABILITY, PROPAGATION, IS_STATIC_TRANSFORMATION, TARGET);
  }
}
