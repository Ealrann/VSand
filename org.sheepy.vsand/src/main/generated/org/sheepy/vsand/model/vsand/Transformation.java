package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.BooleanListener;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.RelationBuilder;
import org.sheepy.vsand.model.vsand.builder.TransformationBuilder;

public interface Transformation extends ITransformation {
  static Builder builder() {
    return new TransformationBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  Material reactant();
  Material catalyst();
  void reactant(final Material reactant);
  void catalyst(final Material catalyst);

  interface FeatureIDs {
    int PROBABILITY = ITransformation.FeatureIDs.PROBABILITY;
    int PROPAGATION = ITransformation.FeatureIDs.PROPAGATION;
    int IS_STATIC_TRANSFORMATION = ITransformation.FeatureIDs.IS_STATIC_TRANSFORMATION;
    int TARGET = ITransformation.FeatureIDs.TARGET;
    int REACTANT = 1998498702;
    int CATALYST = -1381975553;
  }

  interface Features<T extends Features<T>> extends ITransformation.Features<T> {
    Attribute<Integer, Integer, IntListener, ITransformation.Features<?>> PROBABILITY = ITransformation.Features.PROBABILITY;
    Attribute<Integer, Integer, IntListener, ITransformation.Features<?>> PROPAGATION = ITransformation.Features.PROPAGATION;
    Attribute<Boolean, Boolean, BooleanListener, ITransformation.Features<?>> IS_STATIC_TRANSFORMATION = ITransformation.Features.IS_STATIC_TRANSFORMATION;
    Relation<Material, Material, Listener<Material>, ITransformation.Features<?>> TARGET = ITransformation.Features.TARGET;
    Relation<Material, Material, Listener<Material>, Features<?>> REACTANT = new RelationBuilder<Material, Material, Listener<Material>, Features<?>>().name("reactant").id(Transformation.FeatureIDs.REACTANT).concept(() -> VSandModelDefinition.Groups.MATERIAL).build();
    Relation<Material, Material, Listener<Material>, Features<?>> CATALYST = new RelationBuilder<Material, Material, Listener<Material>, Features<?>>().name("catalyst").id(Transformation.FeatureIDs.CATALYST).concept(() -> VSandModelDefinition.Groups.MATERIAL).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(PROBABILITY, PROPAGATION, IS_STATIC_TRANSFORMATION, TARGET, REACTANT, CATALYST);
  }

  interface Builder extends IFeaturedObject.Builder<Transformation> {
    Builder probability(int probability);
    Builder propagation(int propagation);
    Builder isStaticTransformation(boolean isStaticTransformation);
    Builder target(Supplier<Material> target);
    Builder reactant(Supplier<Material> reactant);
    Builder catalyst(Supplier<Material> catalyst);
  }
}
