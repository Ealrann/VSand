package org.sheepy.vsand.model.vsand.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.joml.Vector2ic;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.logoce.lmf.core.util.BuildUtils;
import org.sheepy.lily.core.model.application.ApplicationExtensionPkg;
import org.sheepy.lily.core.model.application.IEngine;
import org.sheepy.lily.core.model.application.IModel;
import org.sheepy.lily.core.model.application.Scene;
import org.sheepy.lily.core.model.application.TimeConfiguration;
import org.sheepy.lily.core.model.resource.ResourcePkg;
import org.sheepy.lily.vulkan.model.process.CompositeTask;
import org.sheepy.vsand.model.vsand.DrawCommand;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.Materials;
import org.sheepy.vsand.model.vsand.Transformations;
import org.sheepy.vsand.model.vsand.VSandApplication;
import org.sheepy.vsand.model.vsand.VSandApplication.Builder;
import org.sheepy.vsand.model.vsand.impl.VSandApplicationImpl;

public final class VSandApplicationBuilder implements Builder {
  private String name;
  private String domain;
  private final List<String> imports = new ArrayList<>();
  private final List<String> metamodels = new ArrayList<>();
  private final List<Supplier<IEngine>> engines = new ArrayList<>();
  private boolean run = true;
  private String title = "Vulkan Application";
  private Supplier<Scene> scene = () -> null;
  private Supplier<TimeConfiguration> timeConfiguration = () -> null;
  private Supplier<ApplicationExtensionPkg> extensionPkg = () -> null;
  private final List<Supplier<IModel>> models = new ArrayList<>();
  private Supplier<ResourcePkg> resourcePkg = () -> null;
  private String version = "0.0.0";
  private Supplier<Materials> materials;
  private Supplier<Transformations> transformations;
  private final List<Supplier<DrawCommand>> drawQueue = new ArrayList<>();
  private Supplier<Material> mainMaterial = () -> null;
  private Supplier<Material> secondaryMaterial = () -> null;
  private boolean nextMode = false;
  private boolean paused = false;
  private int speed = 1;
  private boolean forceClear = false;
  private boolean showSleepZones = false;
  private int brushSize = 4;
  private Supplier<CompositeTask> boardUpdateTask = () -> null;
  private Vector2ic size;

  public VSandApplicationBuilder() {
  }

  @Override
  public VSandApplicationBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public VSandApplicationBuilder domain(String domain) {
    this.domain = domain;
    return this;
  }

  @Override
  public VSandApplicationBuilder addImport(String import_) {
    this.imports.add(import_);
    return this;
  }

  @Override
  public VSandApplicationBuilder addImports(final List<String> imports) {
    this.imports.addAll(imports);
    return this;
  }

  @Override
  public VSandApplicationBuilder addMetamodel(String metamodel) {
    this.metamodels.add(metamodel);
    return this;
  }

  @Override
  public VSandApplicationBuilder addMetamodels(final List<String> metamodels) {
    this.metamodels.addAll(metamodels);
    return this;
  }

  @Override
  public VSandApplicationBuilder addEngine(Supplier<IEngine> engine) {
    this.engines.add(engine);
    return this;
  }

  @Override
  public VSandApplicationBuilder addEngines(final List<IEngine> engines) {
    engines.forEach(value -> this.engines.add(() -> value));
    return this;
  }

  @Override
  public VSandApplicationBuilder run(boolean run) {
    this.run = run;
    return this;
  }

  @Override
  public VSandApplicationBuilder title(String title) {
    this.title = title;
    return this;
  }

  @Override
  public VSandApplicationBuilder scene(Supplier<Scene> scene) {
    this.scene = scene;
    return this;
  }

  @Override
  public VSandApplicationBuilder timeConfiguration(Supplier<TimeConfiguration> timeConfiguration) {
    this.timeConfiguration = timeConfiguration;
    return this;
  }

  @Override
  public VSandApplicationBuilder extensionPkg(Supplier<ApplicationExtensionPkg> extensionPkg) {
    this.extensionPkg = extensionPkg;
    return this;
  }

  @Override
  public VSandApplicationBuilder addModel(Supplier<IModel> model) {
    this.models.add(model);
    return this;
  }

  @Override
  public VSandApplicationBuilder addModels(final List<IModel> models) {
    models.forEach(value -> this.models.add(() -> value));
    return this;
  }

  @Override
  public VSandApplicationBuilder resourcePkg(Supplier<ResourcePkg> resourcePkg) {
    this.resourcePkg = resourcePkg;
    return this;
  }

  @Override
  public VSandApplicationBuilder version(String version) {
    this.version = version;
    return this;
  }

  @Override
  public VSandApplicationBuilder materials(Supplier<Materials> materials) {
    this.materials = materials;
    return this;
  }

  @Override
  public VSandApplicationBuilder transformations(Supplier<Transformations> transformations) {
    this.transformations = transformations;
    return this;
  }

  @Override
  public VSandApplicationBuilder addDrawQueue(Supplier<DrawCommand> drawQueue) {
    this.drawQueue.add(drawQueue);
    return this;
  }

  @Override
  public VSandApplicationBuilder addDrawQueue(final List<DrawCommand> drawQueue) {
    drawQueue.forEach(value -> this.drawQueue.add(() -> value));
    return this;
  }

  @Override
  public VSandApplicationBuilder mainMaterial(Supplier<Material> mainMaterial) {
    this.mainMaterial = mainMaterial;
    return this;
  }

  @Override
  public VSandApplicationBuilder secondaryMaterial(Supplier<Material> secondaryMaterial) {
    this.secondaryMaterial = secondaryMaterial;
    return this;
  }

  @Override
  public VSandApplicationBuilder nextMode(boolean nextMode) {
    this.nextMode = nextMode;
    return this;
  }

  @Override
  public VSandApplicationBuilder paused(boolean paused) {
    this.paused = paused;
    return this;
  }

  @Override
  public VSandApplicationBuilder speed(int speed) {
    this.speed = speed;
    return this;
  }

  @Override
  public VSandApplicationBuilder forceClear(boolean forceClear) {
    this.forceClear = forceClear;
    return this;
  }

  @Override
  public VSandApplicationBuilder showSleepZones(boolean showSleepZones) {
    this.showSleepZones = showSleepZones;
    return this;
  }

  @Override
  public VSandApplicationBuilder brushSize(int brushSize) {
    this.brushSize = brushSize;
    return this;
  }

  @Override
  public VSandApplicationBuilder boardUpdateTask(Supplier<CompositeTask> boardUpdateTask) {
    this.boardUpdateTask = boardUpdateTask;
    return this;
  }

  @Override
  public VSandApplicationBuilder size(Vector2ic size) {
    this.size = size;
    return this;
  }

  @Override
  public VSandApplication build() {
    final var builtEngines = BuildUtils.collectSuppliers(engines);
    final var builtModels = BuildUtils.collectSuppliers(models);
    final var builtDrawQueue = BuildUtils.collectSuppliers(drawQueue);
    final var built = new VSandApplicationImpl(name, domain, imports, metamodels, version, materials.get(), transformations.get(), size);
    built.engines().addAll(builtEngines);
    built.run(run);
    built.title(title);
    built.scene(scene.get());
    built.timeConfiguration(timeConfiguration.get());
    built.extensionPkg(extensionPkg.get());
    built.models().addAll(builtModels);
    built.resourcePkg(resourcePkg.get());
    built.drawQueue().addAll(builtDrawQueue);
    built.mainMaterial(mainMaterial.get());
    built.secondaryMaterial(secondaryMaterial.get());
    built.nextMode(nextMode);
    built.paused(paused);
    built.speed(speed);
    built.forceClear(forceClear);
    built.showSleepZones(showSleepZones);
    built.brushSize(brushSize);
    built.boardUpdateTask(boardUpdateTask.get());
    return built;
  }

  @Override
  public <AttributeType> void push(final Attribute<?, ?, ?, ?> attribute,
      final AttributeType value) {
    Inserters.ATTRIBUTE_INSERTER.push(this, attribute.id(), value);
  }

  @Override
  public <RelationType extends LMObject> void push(final Relation<RelationType, ?, ?, ?> relation,
      final Supplier<RelationType> supplier) {
    Inserters.RELATION_INSERTER.push(this, relation.id(), supplier);
  }

  private static final class Inserters {
    private static final FeatureInserter<VSandApplicationBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<VSandApplicationBuilder>(14, Inserters::attributeIndex).add(VSandApplication.FeatureIDs.NAME, (builder, value) -> builder.name((String) value)).add(VSandApplication.FeatureIDs.DOMAIN, (builder, value) -> builder.domain((String) value)).add(VSandApplication.FeatureIDs.IMPORTS, (builder, value) -> builder.addImport((String) value)).add(VSandApplication.FeatureIDs.METAMODELS, (builder, value) -> builder.addMetamodel((String) value)).add(VSandApplication.FeatureIDs.RUN, (builder, value) -> builder.run((boolean) value)).add(VSandApplication.FeatureIDs.TITLE, (builder, value) -> builder.title((String) value)).add(VSandApplication.FeatureIDs.VERSION, (builder, value) -> builder.version((String) value)).add(VSandApplication.FeatureIDs.NEXT_MODE, (builder, value) -> builder.nextMode((boolean) value)).add(VSandApplication.FeatureIDs.PAUSED, (builder, value) -> builder.paused((boolean) value)).add(VSandApplication.FeatureIDs.SPEED, (builder, value) -> builder.speed((int) value)).add(VSandApplication.FeatureIDs.FORCE_CLEAR, (builder, value) -> builder.forceClear((boolean) value)).add(VSandApplication.FeatureIDs.SHOW_SLEEP_ZONES, (builder, value) -> builder.showSleepZones((boolean) value)).add(VSandApplication.FeatureIDs.BRUSH_SIZE, (builder, value) -> builder.brushSize((int) value)).add(VSandApplication.FeatureIDs.SIZE, (builder, value) -> builder.size((Vector2ic) value)).build();
    private static final RelationLazyInserter<VSandApplicationBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<VSandApplicationBuilder>(12, Inserters::relationIndex).add(VSandApplication.FeatureIDs.ENGINES, (builder, value) -> builder.addEngine((Supplier<IEngine>) value)).add(VSandApplication.FeatureIDs.SCENE, (builder, value) -> builder.scene((Supplier<Scene>) value)).add(VSandApplication.FeatureIDs.TIME_CONFIGURATION, (builder, value) -> builder.timeConfiguration((Supplier<TimeConfiguration>) value)).add(VSandApplication.FeatureIDs.EXTENSION_PKG, (builder, value) -> builder.extensionPkg((Supplier<ApplicationExtensionPkg>) value)).add(VSandApplication.FeatureIDs.MODELS, (builder, value) -> builder.addModel((Supplier<IModel>) value)).add(VSandApplication.FeatureIDs.RESOURCE_PKG, (builder, value) -> builder.resourcePkg((Supplier<ResourcePkg>) value)).add(VSandApplication.FeatureIDs.MATERIALS, (builder, value) -> builder.materials((Supplier<Materials>) value)).add(VSandApplication.FeatureIDs.TRANSFORMATIONS, (builder, value) -> builder.transformations((Supplier<Transformations>) value)).add(VSandApplication.FeatureIDs.DRAW_QUEUE, (builder, value) -> builder.addDrawQueue((Supplier<DrawCommand>) value)).add(VSandApplication.FeatureIDs.MAIN_MATERIAL, (builder, value) -> builder.mainMaterial((Supplier<Material>) value)).add(VSandApplication.FeatureIDs.SECONDARY_MATERIAL, (builder, value) -> builder.secondaryMaterial((Supplier<Material>) value)).add(VSandApplication.FeatureIDs.BOARD_UPDATE_TASK, (builder, value) -> builder.boardUpdateTask((Supplier<CompositeTask>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case VSandApplication.FeatureIDs.NAME -> 0;
        case VSandApplication.FeatureIDs.DOMAIN -> 1;
        case VSandApplication.FeatureIDs.IMPORTS -> 2;
        case VSandApplication.FeatureIDs.METAMODELS -> 3;
        case VSandApplication.FeatureIDs.RUN -> 4;
        case VSandApplication.FeatureIDs.TITLE -> 5;
        case VSandApplication.FeatureIDs.VERSION -> 6;
        case VSandApplication.FeatureIDs.NEXT_MODE -> 7;
        case VSandApplication.FeatureIDs.PAUSED -> 8;
        case VSandApplication.FeatureIDs.SPEED -> 9;
        case VSandApplication.FeatureIDs.FORCE_CLEAR -> 10;
        case VSandApplication.FeatureIDs.SHOW_SLEEP_ZONES -> 11;
        case VSandApplication.FeatureIDs.BRUSH_SIZE -> 12;
        case VSandApplication.FeatureIDs.SIZE -> 13;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case VSandApplication.FeatureIDs.ENGINES -> 0;
        case VSandApplication.FeatureIDs.SCENE -> 1;
        case VSandApplication.FeatureIDs.TIME_CONFIGURATION -> 2;
        case VSandApplication.FeatureIDs.EXTENSION_PKG -> 3;
        case VSandApplication.FeatureIDs.MODELS -> 4;
        case VSandApplication.FeatureIDs.RESOURCE_PKG -> 5;
        case VSandApplication.FeatureIDs.MATERIALS -> 6;
        case VSandApplication.FeatureIDs.TRANSFORMATIONS -> 7;
        case VSandApplication.FeatureIDs.DRAW_QUEUE -> 8;
        case VSandApplication.FeatureIDs.MAIN_MATERIAL -> 9;
        case VSandApplication.FeatureIDs.SECONDARY_MATERIAL -> 10;
        case VSandApplication.FeatureIDs.BOARD_UPDATE_TASK -> 11;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
