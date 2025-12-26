package org.sheepy.vsand.model.vsand.impl;

import java.util.List;
import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.MaterialProvider;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class MaterialProviderImpl extends FeaturedObject<MaterialProvider.Features<?>> implements MaterialProvider {
  private static final int FEATURE_COUNT = 2;
  private final ModelNotifier<MaterialProvider.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final List<Material> materials = newObservableList(MaterialProvider.FeatureIDs.MATERIALS, true, false);
  private boolean filterMode;

  public MaterialProviderImpl(final boolean filterMode) {
    this.filterMode = filterMode;
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<MaterialProvider.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public List<Material> materials() {
    return materials;
  }

  @Override
  public boolean filterMode() {
    return filterMode;
  }

  @Override
  public void filterMode(final boolean filterMode) {
    final var oldValue = this.filterMode;
    this.filterMode = filterMode;
    notifier.notifyBoolean(MaterialProvider.FeatureIDs.FILTER_MODE, false, false, oldValue, filterMode);
  }

  @Override
  public Group<MaterialProvider> lmGroup() {
    return VSandModelDefinition.Groups.MATERIAL_PROVIDER;
  }

  @Override
  protected FeatureSetter<MaterialProvider> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<MaterialProvider> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case MaterialProvider.FeatureIDs.MATERIALS -> 0;
      case MaterialProvider.FeatureIDs.FILTER_MODE -> 1;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<MaterialProvider> GET_MAP = new FeatureGetter.Builder<MaterialProvider>(FEATURE_COUNT, MaterialProviderImpl::featureIndexStatic).add(MaterialProvider.FeatureIDs.MATERIALS, MaterialProvider::materials).add(MaterialProvider.FeatureIDs.FILTER_MODE, MaterialProvider::filterMode).build();
    private static final FeatureSetter<MaterialProvider> SET_MAP = new FeatureSetter.Builder<MaterialProvider>(FEATURE_COUNT, MaterialProviderImpl::featureIndexStatic).add(MaterialProvider.FeatureIDs.FILTER_MODE, (object, value) -> ((MaterialProviderImpl) object).filterMode((boolean) value)).build();
  }
}
