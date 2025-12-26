package org.sheepy.vsand.model.vsand;

import java.util.List;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.RelationBuilder;

public interface DrawCommand extends LMObject {
  @Override
  IModelNotifier<? extends Features<?>> notifier();
  Material material();
  void material(final Material material);

  interface FeatureIDs {
    int MATERIAL = -1671836731;
  }

  interface Features<T extends Features<T>> extends LMObject.Features<T> {
    Relation<Material, Material, Listener<Material>, Features<?>> MATERIAL = new RelationBuilder<Material, Material, Listener<Material>, Features<?>>().name("material").id(DrawCommand.FeatureIDs.MATERIAL).concept(() -> VSandModelDefinition.Groups.MATERIAL).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(MATERIAL);
  }
}
