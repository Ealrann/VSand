package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.RelationBuilder;
import org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider;
import org.sheepy.vsand.model.vsand.builder.InputMaterialProviderBuilder;

public interface InputMaterialProvider extends IInputProvider {
  static Builder builder() {
    return new InputMaterialProviderBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  Materials materials();
  void materials(final Materials materials);

  interface FeatureIDs {
    int MATERIALS = 1911004169;
  }

  interface Features<T extends Features<T>> extends IInputProvider.Features<T> {
    Relation<Materials, Materials, Listener<Materials>, Features<?>> MATERIALS = new RelationBuilder<Materials, Materials, Listener<Materials>, Features<?>>().name("materials").mandatory(true).id(InputMaterialProvider.FeatureIDs.MATERIALS).concept(() -> VSandModelDefinition.Groups.MATERIALS).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(MATERIALS);
  }

  interface Builder extends IFeaturedObject.Builder<InputMaterialProvider> {
    Builder materials(Supplier<Materials> materials);
  }
}
