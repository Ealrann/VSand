package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.RelationBuilder;
import org.sheepy.vsand.model.vsand.builder.TransformationsBuilder;

public interface Transformations extends LMObject {
  static Builder builder() {
    return new TransformationsBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  List<ITransformation> transformations();

  interface FeatureIDs {
    int TRANSFORMATIONS = -1469904177;
  }

  interface Features<T extends Features<T>> extends LMObject.Features<T> {
    Relation<ITransformation, List<ITransformation>, Listener<List<ITransformation>>, Features<?>> TRANSFORMATIONS = new RelationBuilder<ITransformation, List<ITransformation>, Listener<List<ITransformation>>, Features<?>>().name("transformations").many(true).contains(true).id(Transformations.FeatureIDs.TRANSFORMATIONS).concept(() -> VSandModelDefinition.Groups.I_TRANSFORMATION).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(TRANSFORMATIONS);
  }

  interface Builder extends IFeaturedObject.Builder<Transformations> {
    Builder addTransformation(Supplier<ITransformation> transformation);
    Builder addTransformations(List<ITransformation> transformations);
  }
}
