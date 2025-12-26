package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.function.Supplier;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.BooleanListener;
import org.logoce.lmf.core.api.notification.listener.FloatListener;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMCoreModelDefinition;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.AttributeBuilder;
import org.logoce.lmf.core.lang.builder.RelationBuilder;
import org.sheepy.lily.core.model.resource.ResourceModelDefinition;
import org.sheepy.lily.core.model.resource.Sound;
import org.sheepy.vsand.model.vsand.builder.MaterialBuilder;

public interface Material extends LMObject {
  static Builder builder() {
    return new MaterialBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  String name();
  boolean isStatic();
  int density();
  int runoff();
  int r();
  int g();
  int b();
  boolean userFriendly();
  Sound paintSound();
  float pitch();
  void name(final String name);
  void isStatic(final boolean isStatic);
  void density(final int density);
  void runoff(final int runoff);
  void r(final int r);
  void g(final int g);
  void b(final int b);
  void userFriendly(final boolean userFriendly);
  void paintSound(final Sound paintSound);
  void pitch(final float pitch);

  interface FeatureIDs {
    int NAME = -2054071949;
    int IS_STATIC = -956372192;
    int DENSITY = 1667460352;
    int RUNOFF = 1854842860;
    int R = 1182845578;
    int G = 1182845567;
    int B = 1182845562;
    int USER_FRIENDLY = 202159230;
    int PAINT_SOUND = 313883705;
    int PITCH = 750371160;
  }

  interface Features<T extends Features<T>> extends LMObject.Features<T> {
    Attribute<String, String, Listener<String>, Features<?>> NAME = new AttributeBuilder<String, String, Listener<String>, Features<?>>().name("name").id(Material.FeatureIDs.NAME).datatype(() -> LMCoreModelDefinition.Units.STRING).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> IS_STATIC = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("isStatic").id(Material.FeatureIDs.IS_STATIC).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    Attribute<Integer, Integer, IntListener, Features<?>> DENSITY = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("density").id(Material.FeatureIDs.DENSITY).datatype(() -> VSandModelDefinition.Units.SIGNED_INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> RUNOFF = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("runoff").id(Material.FeatureIDs.RUNOFF).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> R = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("r").defaultValue("0").id(Material.FeatureIDs.R).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> G = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("g").defaultValue("0").id(Material.FeatureIDs.G).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Integer, Integer, IntListener, Features<?>> B = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("b").defaultValue("0").id(Material.FeatureIDs.B).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> USER_FRIENDLY = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("userFriendly").defaultValue("true").id(Material.FeatureIDs.USER_FRIENDLY).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    Relation<Sound, Sound, Listener<Sound>, Features<?>> PAINT_SOUND = new RelationBuilder<Sound, Sound, Listener<Sound>, Features<?>>().name("paintSound").id(Material.FeatureIDs.PAINT_SOUND).concept(() -> ResourceModelDefinition.Groups.SOUND).build();
    Attribute<Float, Float, FloatListener, Features<?>> PITCH = new AttributeBuilder<Float, Float, FloatListener, Features<?>>().name("pitch").mandatory(true).defaultValue("1f").id(Material.FeatureIDs.PITCH).datatype(() -> LMCoreModelDefinition.Units.FLOAT).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(NAME, IS_STATIC, DENSITY, RUNOFF, R, G, B, USER_FRIENDLY, PAINT_SOUND, PITCH);
  }

  interface Builder extends IFeaturedObject.Builder<Material> {
    Builder name(String name);
    Builder isStatic(boolean isStatic);
    Builder density(int density);
    Builder runoff(int runoff);
    Builder r(int r);
    Builder g(int g);
    Builder b(int b);
    Builder userFriendly(boolean userFriendly);
    Builder paintSound(Supplier<Sound> paintSound);
    Builder pitch(float pitch);
  }
}
