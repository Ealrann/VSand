package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.BooleanListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMCoreModelDefinition;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.AttributeBuilder;
import org.logoce.lmf.core.lang.builder.RelationBuilder;
import org.sheepy.vsand.model.vsand.builder.MaterialProviderBuilder;

public interface MaterialProvider extends LMObject {
  static Builder builder() {
    return new MaterialProviderBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  List<Material> materials();
  boolean filterMode();
  void filterMode(final boolean filterMode);

  interface FeatureIDs {
    int MATERIALS = -1870984939;
    int FILTER_MODE = 935481714;
  }

  interface Features<T extends Features<T>> extends LMObject.Features<T> {
    Relation<Material, List<Material>, Listener<List<Material>>, Features<?>> MATERIALS = new RelationBuilder<Material, List<Material>, Listener<List<Material>>, Features<?>>().name("materials").many(true).id(MaterialProvider.FeatureIDs.MATERIALS).concept(() -> VSandModelDefinition.Groups.MATERIAL).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> FILTER_MODE = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("filterMode").mandatory(true).defaultValue("false").id(MaterialProvider.FeatureIDs.FILTER_MODE).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(MATERIALS, FILTER_MODE);
  }

  interface Builder extends IFeaturedObject.Builder<MaterialProvider> {
    Builder addMaterial(Supplier<Material> material);
    Builder filterMode(boolean filterMode);
    Builder addMaterials(List<Material> materials);
  }
}
