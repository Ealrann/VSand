package org.sheepy.vsand.model.vsand;

import java.util.List;
import java.util.function.Supplier;
import org.joml.Vector2ic;
import org.logoce.lmf.core.api.model.IFeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.notification.listener.BooleanListener;
import org.logoce.lmf.core.api.notification.listener.IntListener;
import org.logoce.lmf.core.api.notification.listener.Listener;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.Feature;
import org.logoce.lmf.core.lang.LMCoreModelDefinition;
import org.logoce.lmf.core.lang.Model;
import org.logoce.lmf.core.lang.Named;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.lang.builder.AttributeBuilder;
import org.logoce.lmf.core.lang.builder.RelationBuilder;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.core.model.application.ApplicationExtensionPkg;
import org.sheepy.lily.core.model.application.IEngine;
import org.sheepy.lily.core.model.application.IModel;
import org.sheepy.lily.core.model.application.Scene;
import org.sheepy.lily.core.model.application.TimeConfiguration;
import org.sheepy.lily.core.model.resource.ResourcePkg;
import org.sheepy.lily.core.model.types.TypesModelDefinition;
import org.sheepy.lily.vulkan.model.process.CompositeTask;
import org.sheepy.lily.vulkan.model.process.ProcessModelDefinition;
import org.sheepy.vsand.model.vsand.builder.VSandApplicationBuilder;

public interface VSandApplication extends Application {
  static Builder builder() {
    return new VSandApplicationBuilder();
  }

  @Override
  IModelNotifier<? extends Features<?>> notifier();
  Materials materials();
  Transformations transformations();
  List<DrawCommand> drawQueue();
  Material mainMaterial();
  Material secondaryMaterial();
  boolean nextMode();
  boolean paused();
  int speed();
  boolean forceClear();
  boolean showSleepZones();
  int brushSize();
  CompositeTask boardUpdateTask();
  Vector2ic size();
  void mainMaterial(final Material mainMaterial);
  void secondaryMaterial(final Material secondaryMaterial);
  void nextMode(final boolean nextMode);
  void paused(final boolean paused);
  void speed(final int speed);
  void forceClear(final boolean forceClear);
  void showSleepZones(final boolean showSleepZones);
  void brushSize(final int brushSize);
  void boardUpdateTask(final CompositeTask boardUpdateTask);
  void size(final Vector2ic size);

  interface FeatureIDs {
    int NAME = Named.FeatureIDs.NAME;
    int DOMAIN = Model.FeatureIDs.DOMAIN;
    int IMPORTS = Model.FeatureIDs.IMPORTS;
    int METAMODELS = Model.FeatureIDs.METAMODELS;
    int ENGINES = Application.FeatureIDs.ENGINES;
    int RUN = Application.FeatureIDs.RUN;
    int TITLE = Application.FeatureIDs.TITLE;
    int SCENE = Application.FeatureIDs.SCENE;
    int TIME_CONFIGURATION = Application.FeatureIDs.TIME_CONFIGURATION;
    int EXTENSION_PKG = Application.FeatureIDs.EXTENSION_PKG;
    int MODELS = Application.FeatureIDs.MODELS;
    int RESOURCE_PKG = Application.FeatureIDs.RESOURCE_PKG;
    int VERSION = Application.FeatureIDs.VERSION;
    int MATERIALS = -760982989;
    int TRANSFORMATIONS = 1216249681;
    int DRAW_QUEUE = -1308248012;
    int MAIN_MATERIAL = -859381095;
    int SECONDARY_MATERIAL = 296460738;
    int NEXT_MODE = -977627057;
    int PAUSED = -434692985;
    int SPEED = -1534841394;
    int FORCE_CLEAR = -1647213317;
    int SHOW_SLEEP_ZONES = -271197466;
    int BRUSH_SIZE = -1512386462;
    int BOARD_UPDATE_TASK = 145713723;
    int SIZE = 2028692890;
  }

  interface Features<T extends Features<T>> extends Application.Features<T> {
    Attribute<String, String, Listener<String>, Named.Features<?>> NAME = Named.Features.NAME;
    Attribute<String, String, Listener<String>, Model.Features<?>> DOMAIN = Model.Features.DOMAIN;
    Attribute<String, List<String>, Listener<List<String>>, Model.Features<?>> IMPORTS = Model.Features.IMPORTS;
    Attribute<String, List<String>, Listener<List<String>>, Model.Features<?>> METAMODELS = Model.Features.METAMODELS;
    Relation<IEngine, List<IEngine>, Listener<List<IEngine>>, Application.Features<?>> ENGINES = Application.Features.ENGINES;
    Attribute<Boolean, Boolean, BooleanListener, Application.Features<?>> RUN = Application.Features.RUN;
    Attribute<String, String, Listener<String>, Application.Features<?>> TITLE = Application.Features.TITLE;
    Relation<Scene, Scene, Listener<Scene>, Application.Features<?>> SCENE = Application.Features.SCENE;
    Relation<TimeConfiguration, TimeConfiguration, Listener<TimeConfiguration>, Application.Features<?>> TIME_CONFIGURATION = Application.Features.TIME_CONFIGURATION;
    Relation<ApplicationExtensionPkg, ApplicationExtensionPkg, Listener<ApplicationExtensionPkg>, Application.Features<?>> EXTENSION_PKG = Application.Features.EXTENSION_PKG;
    Relation<IModel, List<IModel>, Listener<List<IModel>>, Application.Features<?>> MODELS = Application.Features.MODELS;
    Relation<ResourcePkg, ResourcePkg, Listener<ResourcePkg>, Application.Features<?>> RESOURCE_PKG = Application.Features.RESOURCE_PKG;
    Attribute<String, String, Listener<String>, Application.Features<?>> VERSION = Application.Features.VERSION;
    Relation<Materials, Materials, Listener<Materials>, Features<?>> MATERIALS = new RelationBuilder<Materials, Materials, Listener<Materials>, Features<?>>().name("materials").immutable(true).mandatory(true).contains(true).id(VSandApplication.FeatureIDs.MATERIALS).concept(() -> VSandModelDefinition.Groups.MATERIALS).build();
    Relation<Transformations, Transformations, Listener<Transformations>, Features<?>> TRANSFORMATIONS = new RelationBuilder<Transformations, Transformations, Listener<Transformations>, Features<?>>().name("transformations").immutable(true).mandatory(true).contains(true).id(VSandApplication.FeatureIDs.TRANSFORMATIONS).concept(() -> VSandModelDefinition.Groups.TRANSFORMATIONS).build();
    Relation<DrawCommand, List<DrawCommand>, Listener<List<DrawCommand>>, Features<?>> DRAW_QUEUE = new RelationBuilder<DrawCommand, List<DrawCommand>, Listener<List<DrawCommand>>, Features<?>>().name("drawQueue").many(true).contains(true).id(VSandApplication.FeatureIDs.DRAW_QUEUE).concept(() -> VSandModelDefinition.Groups.DRAW_COMMAND).build();
    Relation<Material, Material, Listener<Material>, Features<?>> MAIN_MATERIAL = new RelationBuilder<Material, Material, Listener<Material>, Features<?>>().name("mainMaterial").id(VSandApplication.FeatureIDs.MAIN_MATERIAL).concept(() -> VSandModelDefinition.Groups.MATERIAL).build();
    Relation<Material, Material, Listener<Material>, Features<?>> SECONDARY_MATERIAL = new RelationBuilder<Material, Material, Listener<Material>, Features<?>>().name("secondaryMaterial").id(VSandApplication.FeatureIDs.SECONDARY_MATERIAL).concept(() -> VSandModelDefinition.Groups.MATERIAL).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> NEXT_MODE = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("nextMode").defaultValue("false").id(VSandApplication.FeatureIDs.NEXT_MODE).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> PAUSED = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("paused").defaultValue("false").id(VSandApplication.FeatureIDs.PAUSED).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    Attribute<Integer, Integer, IntListener, Features<?>> SPEED = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("speed").defaultValue("1").id(VSandApplication.FeatureIDs.SPEED).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> FORCE_CLEAR = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("forceClear").defaultValue("false").id(VSandApplication.FeatureIDs.FORCE_CLEAR).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    Attribute<Boolean, Boolean, BooleanListener, Features<?>> SHOW_SLEEP_ZONES = new AttributeBuilder<Boolean, Boolean, BooleanListener, Features<?>>().name("showSleepZones").defaultValue("false").id(VSandApplication.FeatureIDs.SHOW_SLEEP_ZONES).datatype(() -> LMCoreModelDefinition.Units.BOOLEAN).build();
    Attribute<Integer, Integer, IntListener, Features<?>> BRUSH_SIZE = new AttributeBuilder<Integer, Integer, IntListener, Features<?>>().name("brushSize").defaultValue("4").id(VSandApplication.FeatureIDs.BRUSH_SIZE).datatype(() -> LMCoreModelDefinition.Units.INT).build();
    Relation<CompositeTask, CompositeTask, Listener<CompositeTask>, Features<?>> BOARD_UPDATE_TASK = new RelationBuilder<CompositeTask, CompositeTask, Listener<CompositeTask>, Features<?>>().name("boardUpdateTask").id(VSandApplication.FeatureIDs.BOARD_UPDATE_TASK).concept(() -> ProcessModelDefinition.Groups.COMPOSITE_TASK).build();
    Attribute<Vector2ic, Vector2ic, Listener<Vector2ic>, Features<?>> SIZE = new AttributeBuilder<Vector2ic, Vector2ic, Listener<Vector2ic>, Features<?>>().name("size").mandatory(true).id(VSandApplication.FeatureIDs.SIZE).datatype(() -> TypesModelDefinition.JavaWrappers.VECTOR2I).build();
    List<Feature<?, ?, ?, ?>> ALL = List.of(NAME, DOMAIN, IMPORTS, METAMODELS, ENGINES, RUN, TITLE, SCENE, TIME_CONFIGURATION, EXTENSION_PKG, MODELS, RESOURCE_PKG, VERSION, MATERIALS, TRANSFORMATIONS, DRAW_QUEUE, MAIN_MATERIAL, SECONDARY_MATERIAL, NEXT_MODE, PAUSED, SPEED, FORCE_CLEAR, SHOW_SLEEP_ZONES, BRUSH_SIZE, BOARD_UPDATE_TASK, SIZE);
  }

  interface Builder extends IFeaturedObject.Builder<VSandApplication> {
    Builder name(String name);
    Builder domain(String domain);
    Builder addImport(String import_);
    Builder addMetamodel(String metamodel);
    Builder addEngine(Supplier<IEngine> engine);
    Builder run(boolean run);
    Builder title(String title);
    Builder scene(Supplier<Scene> scene);
    Builder timeConfiguration(Supplier<TimeConfiguration> timeConfiguration);
    Builder extensionPkg(Supplier<ApplicationExtensionPkg> extensionPkg);
    Builder addModel(Supplier<IModel> model);
    Builder resourcePkg(Supplier<ResourcePkg> resourcePkg);
    Builder version(String version);
    Builder materials(Supplier<Materials> materials);
    Builder transformations(Supplier<Transformations> transformations);
    Builder addDrawQueue(Supplier<DrawCommand> drawQueue);
    Builder mainMaterial(Supplier<Material> mainMaterial);
    Builder secondaryMaterial(Supplier<Material> secondaryMaterial);
    Builder nextMode(boolean nextMode);
    Builder paused(boolean paused);
    Builder speed(int speed);
    Builder forceClear(boolean forceClear);
    Builder showSleepZones(boolean showSleepZones);
    Builder brushSize(int brushSize);
    Builder boardUpdateTask(Supplier<CompositeTask> boardUpdateTask);
    Builder size(Vector2ic size);
    Builder addEngines(List<IEngine> engines);
    Builder addModels(List<IModel> models);
    Builder addDrawQueue(List<DrawCommand> drawQueue);
    Builder addImports(List<String> imports);
    Builder addMetamodels(List<String> metamodels);
  }
}
