package org.sheepy.vsand.model.vsand.impl;

import java.util.List;
import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.vsand.model.vsand.ITransformation;
import org.sheepy.vsand.model.vsand.Transformations;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class TransformationsImpl extends FeaturedObject<Transformations.Features<?>> implements Transformations {
  private static final int FEATURE_COUNT = 1;
  private final ModelNotifier<Transformations.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final List<ITransformation> transformations = newObservableList(Transformations.FeatureIDs.TRANSFORMATIONS, true, true);

  public TransformationsImpl() {
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<Transformations.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public List<ITransformation> transformations() {
    return transformations;
  }

  @Override
  public Group<Transformations> lmGroup() {
    return VSandModelDefinition.Groups.TRANSFORMATIONS;
  }

  @Override
  protected FeatureSetter<Transformations> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<Transformations> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case Transformations.FeatureIDs.TRANSFORMATIONS -> 0;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<Transformations> GET_MAP = new FeatureGetter.Builder<Transformations>(FEATURE_COUNT, TransformationsImpl::featureIndexStatic).add(Transformations.FeatureIDs.TRANSFORMATIONS, Transformations::transformations).build();
    private static final FeatureSetter<Transformations> SET_MAP = new FeatureSetter.Builder<Transformations>(FEATURE_COUNT, TransformationsImpl::featureIndexStatic).build();
  }
}
