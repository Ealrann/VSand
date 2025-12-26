package org.sheepy.vsand.model.vsand.impl;

import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.api.notification.Notification;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.vsand.model.vsand.InputMaterialProvider;
import org.sheepy.vsand.model.vsand.Materials;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class InputMaterialProviderImpl extends FeaturedObject<InputMaterialProvider.Features<?>> implements InputMaterialProvider {
  private static final int FEATURE_COUNT = 1;
  private final ModelNotifier<InputMaterialProvider.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private Materials materials;

  public InputMaterialProviderImpl(final Materials materials) {
    this.materials = materials;
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<InputMaterialProvider.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public Materials materials() {
    return materials;
  }

  @Override
  public void materials(final Materials materials) {
    final var oldValue = this.materials;
    final var eventType = materials == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.materials = materials;
    notifier.notify(InputMaterialProvider.FeatureIDs.MATERIALS, false, false, eventType, oldValue, materials);
  }

  @Override
  public Group<InputMaterialProvider> lmGroup() {
    return VSandModelDefinition.Groups.INPUT_MATERIAL_PROVIDER;
  }

  @Override
  protected FeatureSetter<InputMaterialProvider> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<InputMaterialProvider> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case InputMaterialProvider.FeatureIDs.MATERIALS -> 0;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<InputMaterialProvider> GET_MAP = new FeatureGetter.Builder<InputMaterialProvider>(FEATURE_COUNT, InputMaterialProviderImpl::featureIndexStatic).add(InputMaterialProvider.FeatureIDs.MATERIALS, InputMaterialProvider::materials).build();
    private static final FeatureSetter<InputMaterialProvider> SET_MAP = new FeatureSetter.Builder<InputMaterialProvider>(FEATURE_COUNT, InputMaterialProviderImpl::featureIndexStatic).add(InputMaterialProvider.FeatureIDs.MATERIALS, (object, value) -> ((InputMaterialProviderImpl) object).materials((Materials) value)).build();
  }
}
