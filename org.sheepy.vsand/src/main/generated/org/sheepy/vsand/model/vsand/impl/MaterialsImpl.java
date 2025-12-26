package org.sheepy.vsand.model.vsand.impl;

import java.util.List;
import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.Materials;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class MaterialsImpl extends FeaturedObject<Materials.Features<?>> implements Materials {
  private static final int FEATURE_COUNT = 1;
  private final ModelNotifier<Materials.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final List<Material> materials = newObservableList(Materials.FeatureIDs.MATERIALS, true, true);

  public MaterialsImpl() {
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<Materials.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public List<Material> materials() {
    return materials;
  }

  @Override
  public Group<Materials> lmGroup() {
    return VSandModelDefinition.Groups.MATERIALS;
  }

  @Override
  protected FeatureSetter<Materials> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<Materials> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case Materials.FeatureIDs.MATERIALS -> 0;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<Materials> GET_MAP = new FeatureGetter.Builder<Materials>(FEATURE_COUNT, MaterialsImpl::featureIndexStatic).add(Materials.FeatureIDs.MATERIALS, Materials::materials).build();
    private static final FeatureSetter<Materials> SET_MAP = new FeatureSetter.Builder<Materials>(FEATURE_COUNT, MaterialsImpl::featureIndexStatic).build();
  }
}
