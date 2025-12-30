package org.sheepy.vsand.model.vsand.impl;

import java.util.List;
import org.joml.Vector2ic;
import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.api.notification.Notification;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.lily.core.model.application.Application;
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
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class VSandApplicationImpl extends FeaturedObject<VSandApplication.Features<?>> implements VSandApplication {
  private static final int FEATURE_COUNT = 28;
  private final ModelNotifier<VSandApplication.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private final String name;
  private final String domain;
  private final List<String> imports;
  private final List<String> metamodels;
  private final List<IEngine> engines = newObservableList(Application.FeatureIDs.ENGINES, true, true);
  private boolean run;
  private String title;
  private Scene scene;
  private TimeConfiguration timeConfiguration;
  private ApplicationExtensionPkg extensionPkg;
  private final List<IModel> models = newObservableList(Application.FeatureIDs.MODELS, true, true);
  private ResourcePkg resourcePkg;
  private String version;
  private final Materials materials;
  private final Transformations transformations;
  private final List<DrawCommand> drawQueue = newObservableList(VSandApplication.FeatureIDs.DRAW_QUEUE, true, true);
  private Material mainMaterial;
  private Material secondaryMaterial;
  private boolean nextMode;
  private boolean paused;
  private boolean fetchRequested;
  private int speed;
  private boolean forceClear;
  private boolean showSleepZones;
  private int brushSize;
  private CompositeTask boardUpdateTask;
  private CompositeTask fetchBoardTask;
  private Vector2ic size;

  public VSandApplicationImpl(final String name, final String domain, final List<String> imports,
      final List<String> metamodels, final String version, final Materials materials,
      final Transformations transformations, final Vector2ic size) {
    this.name = name;
    this.domain = domain;
    this.imports = List.copyOf(imports);
    this.metamodels = List.copyOf(metamodels);
    this.version = version;
    this.materials = materials;
    this.transformations = transformations;
    this.size = size;
    setContainer(materials, VSandApplication.FeatureIDs.MATERIALS);
    setContainer(transformations, VSandApplication.FeatureIDs.TRANSFORMATIONS);
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<VSandApplication.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String domain() {
    return domain;
  }

  @Override
  public List<String> imports() {
    return imports;
  }

  @Override
  public List<String> metamodels() {
    return metamodels;
  }

  @Override
  public List<IEngine> engines() {
    return engines;
  }

  @Override
  public boolean run() {
    return run;
  }

  @Override
  public void run(final boolean run) {
    final var oldValue = this.run;
    this.run = run;
    notifier.notifyBoolean(Application.FeatureIDs.RUN, false, false, oldValue, run);
  }

  @Override
  public String title() {
    return title;
  }

  @Override
  public void title(final String title) {
    final var oldValue = this.title;
    this.title = title;
    notifier.notify(Application.FeatureIDs.TITLE, false, false, oldValue, title);
  }

  @Override
  public Scene scene() {
    return scene;
  }

  @Override
  public void scene(final Scene scene) {
    final var oldValue = this.scene;
    final var eventType = scene == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.scene = scene;
    setContainer(scene, Application.FeatureIDs.SCENE);
    beforeContainmentNotify(eventType, oldValue, scene);
    notifier.notify(Application.FeatureIDs.SCENE, true, false, eventType, oldValue, scene);
    afterContainmentNotify(eventType, oldValue, scene);
  }

  @Override
  public TimeConfiguration timeConfiguration() {
    return timeConfiguration;
  }

  @Override
  public void timeConfiguration(final TimeConfiguration timeConfiguration) {
    final var oldValue = this.timeConfiguration;
    final var eventType = timeConfiguration == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.timeConfiguration = timeConfiguration;
    setContainer(timeConfiguration, Application.FeatureIDs.TIME_CONFIGURATION);
    beforeContainmentNotify(eventType, oldValue, timeConfiguration);
    notifier.notify(Application.FeatureIDs.TIME_CONFIGURATION, true, false, eventType, oldValue, timeConfiguration);
    afterContainmentNotify(eventType, oldValue, timeConfiguration);
  }

  @Override
  public ApplicationExtensionPkg extensionPkg() {
    return extensionPkg;
  }

  @Override
  public void extensionPkg(final ApplicationExtensionPkg extensionPkg) {
    final var oldValue = this.extensionPkg;
    final var eventType = extensionPkg == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.extensionPkg = extensionPkg;
    setContainer(extensionPkg, Application.FeatureIDs.EXTENSION_PKG);
    beforeContainmentNotify(eventType, oldValue, extensionPkg);
    notifier.notify(Application.FeatureIDs.EXTENSION_PKG, true, false, eventType, oldValue, extensionPkg);
    afterContainmentNotify(eventType, oldValue, extensionPkg);
  }

  @Override
  public List<IModel> models() {
    return models;
  }

  @Override
  public ResourcePkg resourcePkg() {
    return resourcePkg;
  }

  @Override
  public void resourcePkg(final ResourcePkg resourcePkg) {
    final var oldValue = this.resourcePkg;
    final var eventType = resourcePkg == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.resourcePkg = resourcePkg;
    setContainer(resourcePkg, Application.FeatureIDs.RESOURCE_PKG);
    beforeContainmentNotify(eventType, oldValue, resourcePkg);
    notifier.notify(Application.FeatureIDs.RESOURCE_PKG, true, false, eventType, oldValue, resourcePkg);
    afterContainmentNotify(eventType, oldValue, resourcePkg);
  }

  @Override
  public String version() {
    return version;
  }

  @Override
  public void version(final String version) {
    final var oldValue = this.version;
    this.version = version;
    notifier.notify(Application.FeatureIDs.VERSION, false, false, oldValue, version);
  }

  @Override
  public Materials materials() {
    return materials;
  }

  @Override
  public Transformations transformations() {
    return transformations;
  }

  @Override
  public List<DrawCommand> drawQueue() {
    return drawQueue;
  }

  @Override
  public Material mainMaterial() {
    return mainMaterial;
  }

  @Override
  public void mainMaterial(final Material mainMaterial) {
    final var oldValue = this.mainMaterial;
    final var eventType = mainMaterial == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.mainMaterial = mainMaterial;
    notifier.notify(VSandApplication.FeatureIDs.MAIN_MATERIAL, false, false, eventType, oldValue, mainMaterial);
  }

  @Override
  public Material secondaryMaterial() {
    return secondaryMaterial;
  }

  @Override
  public void secondaryMaterial(final Material secondaryMaterial) {
    final var oldValue = this.secondaryMaterial;
    final var eventType = secondaryMaterial == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.secondaryMaterial = secondaryMaterial;
    notifier.notify(VSandApplication.FeatureIDs.SECONDARY_MATERIAL, false, false, eventType, oldValue, secondaryMaterial);
  }

  @Override
  public boolean nextMode() {
    return nextMode;
  }

  @Override
  public void nextMode(final boolean nextMode) {
    final var oldValue = this.nextMode;
    this.nextMode = nextMode;
    notifier.notifyBoolean(VSandApplication.FeatureIDs.NEXT_MODE, false, false, oldValue, nextMode);
  }

  @Override
  public boolean paused() {
    return paused;
  }

  @Override
  public void paused(final boolean paused) {
    final var oldValue = this.paused;
    this.paused = paused;
    notifier.notifyBoolean(VSandApplication.FeatureIDs.PAUSED, false, false, oldValue, paused);
  }

  @Override
  public boolean fetchRequested() {
    return fetchRequested;
  }

  @Override
  public void fetchRequested(final boolean fetchRequested) {
    final var oldValue = this.fetchRequested;
    this.fetchRequested = fetchRequested;
    notifier.notifyBoolean(VSandApplication.FeatureIDs.FETCH_REQUESTED, false, false, oldValue, fetchRequested);
  }

  @Override
  public int speed() {
    return speed;
  }

  @Override
  public void speed(final int speed) {
    final var oldValue = this.speed;
    this.speed = speed;
    notifier.notifyInt(VSandApplication.FeatureIDs.SPEED, false, false, oldValue, speed);
  }

  @Override
  public boolean forceClear() {
    return forceClear;
  }

  @Override
  public void forceClear(final boolean forceClear) {
    final var oldValue = this.forceClear;
    this.forceClear = forceClear;
    notifier.notifyBoolean(VSandApplication.FeatureIDs.FORCE_CLEAR, false, false, oldValue, forceClear);
  }

  @Override
  public boolean showSleepZones() {
    return showSleepZones;
  }

  @Override
  public void showSleepZones(final boolean showSleepZones) {
    final var oldValue = this.showSleepZones;
    this.showSleepZones = showSleepZones;
    notifier.notifyBoolean(VSandApplication.FeatureIDs.SHOW_SLEEP_ZONES, false, false, oldValue, showSleepZones);
  }

  @Override
  public int brushSize() {
    return brushSize;
  }

  @Override
  public void brushSize(final int brushSize) {
    final var oldValue = this.brushSize;
    this.brushSize = brushSize;
    notifier.notifyInt(VSandApplication.FeatureIDs.BRUSH_SIZE, false, false, oldValue, brushSize);
  }

  @Override
  public CompositeTask boardUpdateTask() {
    return boardUpdateTask;
  }

  @Override
  public void boardUpdateTask(final CompositeTask boardUpdateTask) {
    final var oldValue = this.boardUpdateTask;
    final var eventType = boardUpdateTask == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.boardUpdateTask = boardUpdateTask;
    notifier.notify(VSandApplication.FeatureIDs.BOARD_UPDATE_TASK, false, false, eventType, oldValue, boardUpdateTask);
  }

  @Override
  public CompositeTask fetchBoardTask() {
    return fetchBoardTask;
  }

  @Override
  public void fetchBoardTask(final CompositeTask fetchBoardTask) {
    final var oldValue = this.fetchBoardTask;
    final var eventType = fetchBoardTask == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.fetchBoardTask = fetchBoardTask;
    notifier.notify(VSandApplication.FeatureIDs.FETCH_BOARD_TASK, false, false, eventType, oldValue, fetchBoardTask);
  }

  @Override
  public Vector2ic size() {
    return size;
  }

  @Override
  public void size(final Vector2ic size) {
    final var oldValue = this.size;
    this.size = size;
    notifier.notify(VSandApplication.FeatureIDs.SIZE, false, false, oldValue, size);
  }

  @Override
  public Group<VSandApplication> lmGroup() {
    return VSandModelDefinition.Groups.V_SAND_APPLICATION;
  }

  @Override
  protected FeatureSetter<VSandApplication> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<VSandApplication> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case VSandApplication.FeatureIDs.NAME -> 0;
      case VSandApplication.FeatureIDs.DOMAIN -> 1;
      case VSandApplication.FeatureIDs.IMPORTS -> 2;
      case VSandApplication.FeatureIDs.METAMODELS -> 3;
      case VSandApplication.FeatureIDs.ENGINES -> 4;
      case VSandApplication.FeatureIDs.RUN -> 5;
      case VSandApplication.FeatureIDs.TITLE -> 6;
      case VSandApplication.FeatureIDs.SCENE -> 7;
      case VSandApplication.FeatureIDs.TIME_CONFIGURATION -> 8;
      case VSandApplication.FeatureIDs.EXTENSION_PKG -> 9;
      case VSandApplication.FeatureIDs.MODELS -> 10;
      case VSandApplication.FeatureIDs.RESOURCE_PKG -> 11;
      case VSandApplication.FeatureIDs.VERSION -> 12;
      case VSandApplication.FeatureIDs.MATERIALS -> 13;
      case VSandApplication.FeatureIDs.TRANSFORMATIONS -> 14;
      case VSandApplication.FeatureIDs.DRAW_QUEUE -> 15;
      case VSandApplication.FeatureIDs.MAIN_MATERIAL -> 16;
      case VSandApplication.FeatureIDs.SECONDARY_MATERIAL -> 17;
      case VSandApplication.FeatureIDs.NEXT_MODE -> 18;
      case VSandApplication.FeatureIDs.PAUSED -> 19;
      case VSandApplication.FeatureIDs.FETCH_REQUESTED -> 20;
      case VSandApplication.FeatureIDs.SPEED -> 21;
      case VSandApplication.FeatureIDs.FORCE_CLEAR -> 22;
      case VSandApplication.FeatureIDs.SHOW_SLEEP_ZONES -> 23;
      case VSandApplication.FeatureIDs.BRUSH_SIZE -> 24;
      case VSandApplication.FeatureIDs.BOARD_UPDATE_TASK -> 25;
      case VSandApplication.FeatureIDs.FETCH_BOARD_TASK -> 26;
      case VSandApplication.FeatureIDs.SIZE -> 27;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<VSandApplication> GET_MAP = new FeatureGetter.Builder<VSandApplication>(FEATURE_COUNT, VSandApplicationImpl::featureIndexStatic).add(VSandApplication.FeatureIDs.NAME, VSandApplication::name).add(VSandApplication.FeatureIDs.DOMAIN, VSandApplication::domain).add(VSandApplication.FeatureIDs.IMPORTS, VSandApplication::imports).add(VSandApplication.FeatureIDs.METAMODELS, VSandApplication::metamodels).add(VSandApplication.FeatureIDs.ENGINES, VSandApplication::engines).add(VSandApplication.FeatureIDs.RUN, VSandApplication::run).add(VSandApplication.FeatureIDs.TITLE, VSandApplication::title).add(VSandApplication.FeatureIDs.SCENE, VSandApplication::scene).add(VSandApplication.FeatureIDs.TIME_CONFIGURATION, VSandApplication::timeConfiguration).add(VSandApplication.FeatureIDs.EXTENSION_PKG, VSandApplication::extensionPkg).add(VSandApplication.FeatureIDs.MODELS, VSandApplication::models).add(VSandApplication.FeatureIDs.RESOURCE_PKG, VSandApplication::resourcePkg).add(VSandApplication.FeatureIDs.VERSION, VSandApplication::version).add(VSandApplication.FeatureIDs.MATERIALS, VSandApplication::materials).add(VSandApplication.FeatureIDs.TRANSFORMATIONS, VSandApplication::transformations).add(VSandApplication.FeatureIDs.DRAW_QUEUE, VSandApplication::drawQueue).add(VSandApplication.FeatureIDs.MAIN_MATERIAL, VSandApplication::mainMaterial).add(VSandApplication.FeatureIDs.SECONDARY_MATERIAL, VSandApplication::secondaryMaterial).add(VSandApplication.FeatureIDs.NEXT_MODE, VSandApplication::nextMode).add(VSandApplication.FeatureIDs.PAUSED, VSandApplication::paused).add(VSandApplication.FeatureIDs.FETCH_REQUESTED, VSandApplication::fetchRequested).add(VSandApplication.FeatureIDs.SPEED, VSandApplication::speed).add(VSandApplication.FeatureIDs.FORCE_CLEAR, VSandApplication::forceClear).add(VSandApplication.FeatureIDs.SHOW_SLEEP_ZONES, VSandApplication::showSleepZones).add(VSandApplication.FeatureIDs.BRUSH_SIZE, VSandApplication::brushSize).add(VSandApplication.FeatureIDs.BOARD_UPDATE_TASK, VSandApplication::boardUpdateTask).add(VSandApplication.FeatureIDs.FETCH_BOARD_TASK, VSandApplication::fetchBoardTask).add(VSandApplication.FeatureIDs.SIZE, VSandApplication::size).build();
    private static final FeatureSetter<VSandApplication> SET_MAP = new FeatureSetter.Builder<VSandApplication>(FEATURE_COUNT, VSandApplicationImpl::featureIndexStatic).add(VSandApplication.FeatureIDs.RUN, (object, value) -> ((VSandApplicationImpl) object).run((boolean) value)).add(VSandApplication.FeatureIDs.TITLE, (object, value) -> ((VSandApplicationImpl) object).title((String) value)).add(VSandApplication.FeatureIDs.SCENE, (object, value) -> ((VSandApplicationImpl) object).scene((Scene) value)).add(VSandApplication.FeatureIDs.TIME_CONFIGURATION, (object, value) -> ((VSandApplicationImpl) object).timeConfiguration((TimeConfiguration) value)).add(VSandApplication.FeatureIDs.EXTENSION_PKG, (object, value) -> ((VSandApplicationImpl) object).extensionPkg((ApplicationExtensionPkg) value)).add(VSandApplication.FeatureIDs.RESOURCE_PKG, (object, value) -> ((VSandApplicationImpl) object).resourcePkg((ResourcePkg) value)).add(VSandApplication.FeatureIDs.VERSION, (object, value) -> ((VSandApplicationImpl) object).version((String) value)).add(VSandApplication.FeatureIDs.MAIN_MATERIAL, (object, value) -> ((VSandApplicationImpl) object).mainMaterial((Material) value)).add(VSandApplication.FeatureIDs.SECONDARY_MATERIAL, (object, value) -> ((VSandApplicationImpl) object).secondaryMaterial((Material) value)).add(VSandApplication.FeatureIDs.NEXT_MODE, (object, value) -> ((VSandApplicationImpl) object).nextMode((boolean) value)).add(VSandApplication.FeatureIDs.PAUSED, (object, value) -> ((VSandApplicationImpl) object).paused((boolean) value)).add(VSandApplication.FeatureIDs.FETCH_REQUESTED, (object, value) -> ((VSandApplicationImpl) object).fetchRequested((boolean) value)).add(VSandApplication.FeatureIDs.SPEED, (object, value) -> ((VSandApplicationImpl) object).speed((int) value)).add(VSandApplication.FeatureIDs.FORCE_CLEAR, (object, value) -> ((VSandApplicationImpl) object).forceClear((boolean) value)).add(VSandApplication.FeatureIDs.SHOW_SLEEP_ZONES, (object, value) -> ((VSandApplicationImpl) object).showSleepZones((boolean) value)).add(VSandApplication.FeatureIDs.BRUSH_SIZE, (object, value) -> ((VSandApplicationImpl) object).brushSize((int) value)).add(VSandApplication.FeatureIDs.BOARD_UPDATE_TASK, (object, value) -> ((VSandApplicationImpl) object).boardUpdateTask((CompositeTask) value)).add(VSandApplication.FeatureIDs.FETCH_BOARD_TASK, (object, value) -> ((VSandApplicationImpl) object).fetchBoardTask((CompositeTask) value)).add(VSandApplication.FeatureIDs.SIZE, (object, value) -> ((VSandApplicationImpl) object).size((Vector2ic) value)).build();
  }
}
