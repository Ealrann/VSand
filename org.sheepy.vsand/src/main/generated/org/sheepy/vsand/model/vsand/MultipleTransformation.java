package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.BooleanListener;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMCoreModelDefinition;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.AttributeBuilder;
import org.logoce.lmf.core.lang.builder.RelationBuilder;
import org.sheepy.vsand.model.vsand.builder.MultipleTransformationBuilder;

public interface MultipleTransformation extends ITransformation {
  static Builder builder() {
    return new MultipleTransformationBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  MaterialProvider reactants();
  MaterialProvider catalysts();
  String name();
  void reactants(final MaterialProvider reactants);
  void catalysts(final MaterialProvider catalysts);
  void name(final String name);

  interface FeatureIDs {
    int PROBABILITY = ITransformation.FeatureIDs.PROBABILITY;
    int PROPAGATION = ITransformation.FeatureIDs.PROPAGATION;
    int IS_STATIC_TRANSFORMATION = ITransformation.FeatureIDs.IS_STATIC_TRANSFORMATION;
    int TARGET = ITransformation.FeatureIDs.TARGET;
    int REACTANTS = -335100459;
    int CATALYSTS = -2050587260;
    int NAME = -1377738783;
  }

  interface Features<T extends Features<T>> extends ITransformation.Features<T> {
    Attribute<Integer, Integer, IntListener, ITransformation.Features<?>> PROBABILITY = ITransformation.Features.PROBABILITY;
    Attribute<Integer, Integer, IntListener, ITransformation.Features<?>> PROPAGATION = ITransformation.Features.PROPAGATION;
    Attribute<Boolean, Boolean, BooleanListener, ITransformation.Features<?>> IS_STATIC_TRANSFORMATION = ITransformation.Features.IS_STATIC_TRANSFORMATION;
    Relation<Material, Material, Listener<Material>, ITransformation.Features<?>> TARGET = ITransformation.Features.TARGET;
    Relation<MaterialProvider, MaterialProvider, Listener<MaterialProvider>, Features<?>> REACTANTS = new RelationBuilder<MaterialProvider, MaterialProvider, Listener<MaterialProvider>, Features<?>>().name("reactants").mandatory(true).contains(true).id(MultipleTransformation.FeatureIDs.REACTANTS).concept(() -> VSandModelDefinition.Groups.MATERIAL_PROVIDER).build();
    Relation<MaterialProvider, MaterialProvider, Listener<MaterialProvider>, Features<?>> CATALYSTS = new RelationBuilder<MaterialProvider, MaterialProvider, Listener<MaterialProvider>, Features<?>>().name("catalysts").mandatory(true).contains(true).id(MultipleTransformation.FeatureIDs.CATALYSTS).concept(() -> VSandModelDefinition.Groups.MATERIAL_PROVIDER).build();
    Attribute<String, String, Listener<String>, Features<?>> NAME = new AttributeBuilder<String, String, Listener<String>, Features<?>>().name("name").defaultValue("").id(MultipleTransformation.FeatureIDs.NAME).datatype(() -> LMCoreModelDefinition.Units.STRING).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(PROBABILITY, PROPAGATION, IS_STATIC_TRANSFORMATION, TARGET, REACTANTS, CATALYSTS, NAME);
  }

  interface Builder extends IFeaturedObject.Builder<MultipleTransformation> {
    Builder probability(int probability);
    Builder propagation(int propagation);
    Builder isStaticTransformation(boolean isStaticTransformation);
    Builder target(Supplier<Material> target);
    Builder reactants(Supplier<MaterialProvider> reactants);
    Builder catalysts(Supplier<MaterialProvider> catalysts);
    Builder name(String name);
  }
}
