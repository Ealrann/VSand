package org.sheepy.vsand.model.vsand.impl;

import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.api.notification.Notification;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.lily.core.model.resource.Sound;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class MaterialImpl extends FeaturedObject<Material.Features<?>> implements Material {
  private static final int FEATURE_COUNT = 10;
  private final ModelNotifier<Material.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private String name;
  private boolean isStatic;
  private int density;
  private int runoff;
  private int r;
  private int g;
  private int b;
  private boolean userFriendly;
  private Sound paintSound;
  private float pitch;

  public MaterialImpl(final float pitch) {
    this.pitch = pitch;
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<Material.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public void name(final String name) {
    final var oldValue = this.name;
    this.name = name;
    notifier.notify(Material.FeatureIDs.NAME, false, false, oldValue, name);
  }

  @Override
  public boolean isStatic() {
    return isStatic;
  }

  @Override
  public void isStatic(final boolean isStatic) {
    final var oldValue = this.isStatic;
    this.isStatic = isStatic;
    notifier.notifyBoolean(Material.FeatureIDs.IS_STATIC, false, false, oldValue, isStatic);
  }

  @Override
  public int density() {
    return density;
  }

  @Override
  public void density(final int density) {
    final var oldValue = this.density;
    this.density = density;
    notifier.notifyInt(Material.FeatureIDs.DENSITY, false, false, oldValue, density);
  }

  @Override
  public int runoff() {
    return runoff;
  }

  @Override
  public void runoff(final int runoff) {
    final var oldValue = this.runoff;
    this.runoff = runoff;
    notifier.notifyInt(Material.FeatureIDs.RUNOFF, false, false, oldValue, runoff);
  }

  @Override
  public int r() {
    return r;
  }

  @Override
  public void r(final int r) {
    final var oldValue = this.r;
    this.r = r;
    notifier.notifyInt(Material.FeatureIDs.R, false, false, oldValue, r);
  }

  @Override
  public int g() {
    return g;
  }

  @Override
  public void g(final int g) {
    final var oldValue = this.g;
    this.g = g;
    notifier.notifyInt(Material.FeatureIDs.G, false, false, oldValue, g);
  }

  @Override
  public int b() {
    return b;
  }

  @Override
  public void b(final int b) {
    final var oldValue = this.b;
    this.b = b;
    notifier.notifyInt(Material.FeatureIDs.B, false, false, oldValue, b);
  }

  @Override
  public boolean userFriendly() {
    return userFriendly;
  }

  @Override
  public void userFriendly(final boolean userFriendly) {
    final var oldValue = this.userFriendly;
    this.userFriendly = userFriendly;
    notifier.notifyBoolean(Material.FeatureIDs.USER_FRIENDLY, false, false, oldValue, userFriendly);
  }

  @Override
  public Sound paintSound() {
    return paintSound;
  }

  @Override
  public void paintSound(final Sound paintSound) {
    final var oldValue = this.paintSound;
    final var eventType = paintSound == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.paintSound = paintSound;
    notifier.notify(Material.FeatureIDs.PAINT_SOUND, false, false, eventType, oldValue, paintSound);
  }

  @Override
  public float pitch() {
    return pitch;
  }

  @Override
  public void pitch(final float pitch) {
    final var oldValue = this.pitch;
    this.pitch = pitch;
    notifier.notifyFloat(Material.FeatureIDs.PITCH, false, false, oldValue, pitch);
  }

  @Override
  public Group<Material> lmGroup() {
    return VSandModelDefinition.Groups.MATERIAL;
  }

  @Override
  protected FeatureSetter<Material> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<Material> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case Material.FeatureIDs.NAME -> 0;
      case Material.FeatureIDs.IS_STATIC -> 1;
      case Material.FeatureIDs.DENSITY -> 2;
      case Material.FeatureIDs.RUNOFF -> 3;
      case Material.FeatureIDs.R -> 4;
      case Material.FeatureIDs.G -> 5;
      case Material.FeatureIDs.B -> 6;
      case Material.FeatureIDs.USER_FRIENDLY -> 7;
      case Material.FeatureIDs.PAINT_SOUND -> 8;
      case Material.FeatureIDs.PITCH -> 9;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<Material> GET_MAP = new FeatureGetter.Builder<Material>(FEATURE_COUNT, MaterialImpl::featureIndexStatic).add(Material.FeatureIDs.NAME, Material::name).add(Material.FeatureIDs.IS_STATIC, Material::isStatic).add(Material.FeatureIDs.DENSITY, Material::density).add(Material.FeatureIDs.RUNOFF, Material::runoff).add(Material.FeatureIDs.R, Material::r).add(Material.FeatureIDs.G, Material::g).add(Material.FeatureIDs.B, Material::b).add(Material.FeatureIDs.USER_FRIENDLY, Material::userFriendly).add(Material.FeatureIDs.PAINT_SOUND, Material::paintSound).add(Material.FeatureIDs.PITCH, Material::pitch).build();
    private static final FeatureSetter<Material> SET_MAP = new FeatureSetter.Builder<Material>(FEATURE_COUNT, MaterialImpl::featureIndexStatic).add(Material.FeatureIDs.NAME, (object, value) -> ((MaterialImpl) object).name((String) value)).add(Material.FeatureIDs.IS_STATIC, (object, value) -> ((MaterialImpl) object).isStatic((boolean) value)).add(Material.FeatureIDs.DENSITY, (object, value) -> ((MaterialImpl) object).density((int) value)).add(Material.FeatureIDs.RUNOFF, (object, value) -> ((MaterialImpl) object).runoff((int) value)).add(Material.FeatureIDs.R, (object, value) -> ((MaterialImpl) object).r((int) value)).add(Material.FeatureIDs.G, (object, value) -> ((MaterialImpl) object).g((int) value)).add(Material.FeatureIDs.B, (object, value) -> ((MaterialImpl) object).b((int) value)).add(Material.FeatureIDs.USER_FRIENDLY, (object, value) -> ((MaterialImpl) object).userFriendly((boolean) value)).add(Material.FeatureIDs.PAINT_SOUND, (object, value) -> ((MaterialImpl) object).paintSound((Sound) value)).add(Material.FeatureIDs.PITCH, (object, value) -> ((MaterialImpl) object).pitch((float) value)).build();
  }
}
