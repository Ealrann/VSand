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
import org.sheepy.vsand.model.vsand.builder.MaterialsBuilder;

public interface Materials extends LMObject {
  static Builder builder() {
    return new MaterialsBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  List<Material> materials();

  interface FeatureIDs {
    int MATERIALS = -1949450477;
  }

  interface Features<T extends Features<T>> extends LMObject.Features<T> {
    Relation<Material, List<Material>, Listener<List<Material>>, Features<?>> MATERIALS = new RelationBuilder<Material, List<Material>, Listener<List<Material>>, Features<?>>().name("materials").many(true).contains(true).id(Materials.FeatureIDs.MATERIALS).concept(() -> VSandModelDefinition.Groups.MATERIAL).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(MATERIALS);
  }

  interface Builder extends IFeaturedObject.Builder<Materials> {
    Builder addMaterial(Supplier<Material> material);
    Builder addMaterials(List<Material> materials);
  }
}
