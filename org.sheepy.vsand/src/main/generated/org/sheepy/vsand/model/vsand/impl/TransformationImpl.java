package org.sheepy.vsand.model.vsand.impl;

import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.api.notification.Notification;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.vsand.model.vsand.ITransformation;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.Transformation;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class TransformationImpl extends FeaturedObject<Transformation.Features<?>> implements Transformation {
  private static final int FEATURE_COUNT = 6;
  private final ModelNotifier<Transformation.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private int probability;
  private int propagation;
  private boolean isStaticTransformation;
  private Material target;
  private Material reactant;
  private Material catalyst;

  public TransformationImpl() {
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<Transformation.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public int probability() {
    return probability;
  }

  @Override
  public void probability(final int probability) {
    final var oldValue = this.probability;
    this.probability = probability;
    notifier.notifyInt(ITransformation.FeatureIDs.PROBABILITY, false, false, oldValue, probability);
  }

  @Override
  public int propagation() {
    return propagation;
  }

  @Override
  public void propagation(final int propagation) {
    final var oldValue = this.propagation;
    this.propagation = propagation;
    notifier.notifyInt(ITransformation.FeatureIDs.PROPAGATION, false, false, oldValue, propagation);
  }

  @Override
  public boolean isStaticTransformation() {
    return isStaticTransformation;
  }

  @Override
  public void isStaticTransformation(final boolean isStaticTransformation) {
    final var oldValue = this.isStaticTransformation;
    this.isStaticTransformation = isStaticTransformation;
    notifier.notifyBoolean(ITransformation.FeatureIDs.IS_STATIC_TRANSFORMATION, false, false, oldValue, isStaticTransformation);
  }

  @Override
  public Material target() {
    return target;
  }

  @Override
  public void target(final Material target) {
    final var oldValue = this.target;
    final var eventType = target == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.target = target;
    notifier.notify(ITransformation.FeatureIDs.TARGET, false, false, eventType, oldValue, target);
  }

  @Override
  public Material reactant() {
    return reactant;
  }

  @Override
  public void reactant(final Material reactant) {
    final var oldValue = this.reactant;
    final var eventType = reactant == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.reactant = reactant;
    notifier.notify(Transformation.FeatureIDs.REACTANT, false, false, eventType, oldValue, reactant);
  }

  @Override
  public Material catalyst() {
    return catalyst;
  }

  @Override
  public void catalyst(final Material catalyst) {
    final var oldValue = this.catalyst;
    final var eventType = catalyst == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.catalyst = catalyst;
    notifier.notify(Transformation.FeatureIDs.CATALYST, false, false, eventType, oldValue, catalyst);
  }

  @Override
  public Group<Transformation> lmGroup() {
    return VSandModelDefinition.Groups.TRANSFORMATION;
  }

  @Override
  protected FeatureSetter<Transformation> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<Transformation> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case Transformation.FeatureIDs.PROBABILITY -> 0;
      case Transformation.FeatureIDs.PROPAGATION -> 1;
      case Transformation.FeatureIDs.IS_STATIC_TRANSFORMATION -> 2;
      case Transformation.FeatureIDs.TARGET -> 3;
      case Transformation.FeatureIDs.REACTANT -> 4;
      case Transformation.FeatureIDs.CATALYST -> 5;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<Transformation> GET_MAP = new FeatureGetter.Builder<Transformation>(FEATURE_COUNT, TransformationImpl::featureIndexStatic).add(Transformation.FeatureIDs.PROBABILITY, Transformation::probability).add(Transformation.FeatureIDs.PROPAGATION, Transformation::propagation).add(Transformation.FeatureIDs.IS_STATIC_TRANSFORMATION, Transformation::isStaticTransformation).add(Transformation.FeatureIDs.TARGET, Transformation::target).add(Transformation.FeatureIDs.REACTANT, Transformation::reactant).add(Transformation.FeatureIDs.CATALYST, Transformation::catalyst).build();
    private static final FeatureSetter<Transformation> SET_MAP = new FeatureSetter.Builder<Transformation>(FEATURE_COUNT, TransformationImpl::featureIndexStatic).add(Transformation.FeatureIDs.PROBABILITY, (object, value) -> ((TransformationImpl) object).probability((int) value)).add(Transformation.FeatureIDs.PROPAGATION, (object, value) -> ((TransformationImpl) object).propagation((int) value)).add(Transformation.FeatureIDs.IS_STATIC_TRANSFORMATION, (object, value) -> ((TransformationImpl) object).isStaticTransformation((boolean) value)).add(Transformation.FeatureIDs.TARGET, (object, value) -> ((TransformationImpl) object).target((Material) value)).add(Transformation.FeatureIDs.REACTANT, (object, value) -> ((TransformationImpl) object).reactant((Material) value)).add(Transformation.FeatureIDs.CATALYST, (object, value) -> ((TransformationImpl) object).catalyst((Material) value)).build();
  }
}
