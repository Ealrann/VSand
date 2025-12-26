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
import org.sheepy.vsand.model.vsand.MaterialProvider;
import org.sheepy.vsand.model.vsand.MultipleTransformation;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class MultipleTransformationImpl extends FeaturedObject<MultipleTransformation.Features<?>> implements MultipleTransformation {
  private static final int FEATURE_COUNT = 7;
  private final ModelNotifier<MultipleTransformation.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private int probability;
  private int propagation;
  private boolean isStaticTransformation;
  private Material target;
  private MaterialProvider reactants;
  private MaterialProvider catalysts;
  private String name;

  public MultipleTransformationImpl(final MaterialProvider reactants,
      final MaterialProvider catalysts) {
    this.reactants = reactants;
    this.catalysts = catalysts;
    setContainer(reactants, MultipleTransformation.FeatureIDs.REACTANTS);
    setContainer(catalysts, MultipleTransformation.FeatureIDs.CATALYSTS);
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<MultipleTransformation.Features<?>> notifier() {
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
  public MaterialProvider reactants() {
    return reactants;
  }

  @Override
  public void reactants(final MaterialProvider reactants) {
    final var oldValue = this.reactants;
    final var eventType = reactants == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.reactants = reactants;
    setContainer(reactants, MultipleTransformation.FeatureIDs.REACTANTS);
    beforeContainmentNotify(eventType, oldValue, reactants);
    notifier.notify(MultipleTransformation.FeatureIDs.REACTANTS, true, false, eventType, oldValue, reactants);
    afterContainmentNotify(eventType, oldValue, reactants);
  }

  @Override
  public MaterialProvider catalysts() {
    return catalysts;
  }

  @Override
  public void catalysts(final MaterialProvider catalysts) {
    final var oldValue = this.catalysts;
    final var eventType = catalysts == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.catalysts = catalysts;
    setContainer(catalysts, MultipleTransformation.FeatureIDs.CATALYSTS);
    beforeContainmentNotify(eventType, oldValue, catalysts);
    notifier.notify(MultipleTransformation.FeatureIDs.CATALYSTS, true, false, eventType, oldValue, catalysts);
    afterContainmentNotify(eventType, oldValue, catalysts);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public void name(final String name) {
    final var oldValue = this.name;
    this.name = name;
    notifier.notify(MultipleTransformation.FeatureIDs.NAME, false, false, oldValue, name);
  }

  @Override
  public Group<MultipleTransformation> lmGroup() {
    return VSandModelDefinition.Groups.MULTIPLE_TRANSFORMATION;
  }

  @Override
  protected FeatureSetter<MultipleTransformation> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<MultipleTransformation> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case MultipleTransformation.FeatureIDs.PROBABILITY -> 0;
      case MultipleTransformation.FeatureIDs.PROPAGATION -> 1;
      case MultipleTransformation.FeatureIDs.IS_STATIC_TRANSFORMATION -> 2;
      case MultipleTransformation.FeatureIDs.TARGET -> 3;
      case MultipleTransformation.FeatureIDs.REACTANTS -> 4;
      case MultipleTransformation.FeatureIDs.CATALYSTS -> 5;
      case MultipleTransformation.FeatureIDs.NAME -> 6;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<MultipleTransformation> GET_MAP = new FeatureGetter.Builder<MultipleTransformation>(FEATURE_COUNT, MultipleTransformationImpl::featureIndexStatic).add(MultipleTransformation.FeatureIDs.PROBABILITY, MultipleTransformation::probability).add(MultipleTransformation.FeatureIDs.PROPAGATION, MultipleTransformation::propagation).add(MultipleTransformation.FeatureIDs.IS_STATIC_TRANSFORMATION, MultipleTransformation::isStaticTransformation).add(MultipleTransformation.FeatureIDs.TARGET, MultipleTransformation::target).add(MultipleTransformation.FeatureIDs.REACTANTS, MultipleTransformation::reactants).add(MultipleTransformation.FeatureIDs.CATALYSTS, MultipleTransformation::catalysts).add(MultipleTransformation.FeatureIDs.NAME, MultipleTransformation::name).build();
    private static final FeatureSetter<MultipleTransformation> SET_MAP = new FeatureSetter.Builder<MultipleTransformation>(FEATURE_COUNT, MultipleTransformationImpl::featureIndexStatic).add(MultipleTransformation.FeatureIDs.PROBABILITY, (object, value) -> ((MultipleTransformationImpl) object).probability((int) value)).add(MultipleTransformation.FeatureIDs.PROPAGATION, (object, value) -> ((MultipleTransformationImpl) object).propagation((int) value)).add(MultipleTransformation.FeatureIDs.IS_STATIC_TRANSFORMATION, (object, value) -> ((MultipleTransformationImpl) object).isStaticTransformation((boolean) value)).add(MultipleTransformation.FeatureIDs.TARGET, (object, value) -> ((MultipleTransformationImpl) object).target((Material) value)).add(MultipleTransformation.FeatureIDs.REACTANTS, (object, value) -> ((MultipleTransformationImpl) object).reactants((MaterialProvider) value)).add(MultipleTransformation.FeatureIDs.CATALYSTS, (object, value) -> ((MultipleTransformationImpl) object).catalysts((MaterialProvider) value)).add(MultipleTransformation.FeatureIDs.NAME, (object, value) -> ((MultipleTransformationImpl) object).name((String) value)).build();
  }
}
