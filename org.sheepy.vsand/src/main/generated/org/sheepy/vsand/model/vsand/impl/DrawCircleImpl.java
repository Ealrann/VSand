package org.sheepy.vsand.model.vsand.impl;

import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.api.notification.Notification;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.vsand.model.vsand.DrawCircle;
import org.sheepy.vsand.model.vsand.DrawCommand;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class DrawCircleImpl extends FeaturedObject<DrawCircle.Features<?>> implements DrawCircle {
  private static final int FEATURE_COUNT = 4;
  private final ModelNotifier<DrawCircle.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private Material material;
  private int x;
  private int y;
  private int size;

  public DrawCircleImpl() {
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<DrawCircle.Features<?>> notifier() {
    return notifier;
  }

  @Override
  public Material material() {
    return material;
  }

  @Override
  public void material(final Material material) {
    final var oldValue = this.material;
    final var eventType = material == null ? Notification.EventType.UNSET : Notification.EventType.SET;
    this.material = material;
    notifier.notify(DrawCommand.FeatureIDs.MATERIAL, false, false, eventType, oldValue, material);
  }

  @Override
  public int x() {
    return x;
  }

  @Override
  public void x(final int x) {
    final var oldValue = this.x;
    this.x = x;
    notifier.notifyInt(DrawCircle.FeatureIDs.X, false, false, oldValue, x);
  }

  @Override
  public int y() {
    return y;
  }

  @Override
  public void y(final int y) {
    final var oldValue = this.y;
    this.y = y;
    notifier.notifyInt(DrawCircle.FeatureIDs.Y, false, false, oldValue, y);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void size(final int size) {
    final var oldValue = this.size;
    this.size = size;
    notifier.notifyInt(DrawCircle.FeatureIDs.SIZE, false, false, oldValue, size);
  }

  @Override
  public Group<DrawCircle> lmGroup() {
    return VSandModelDefinition.Groups.DRAW_CIRCLE;
  }

  @Override
  protected FeatureSetter<DrawCircle> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<DrawCircle> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case DrawCircle.FeatureIDs.MATERIAL -> 0;
      case DrawCircle.FeatureIDs.X -> 1;
      case DrawCircle.FeatureIDs.Y -> 2;
      case DrawCircle.FeatureIDs.SIZE -> 3;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<DrawCircle> GET_MAP = new FeatureGetter.Builder<DrawCircle>(FEATURE_COUNT, DrawCircleImpl::featureIndexStatic).add(DrawCircle.FeatureIDs.MATERIAL, DrawCircle::material).add(DrawCircle.FeatureIDs.X, DrawCircle::x).add(DrawCircle.FeatureIDs.Y, DrawCircle::y).add(DrawCircle.FeatureIDs.SIZE, DrawCircle::size).build();
    private static final FeatureSetter<DrawCircle> SET_MAP = new FeatureSetter.Builder<DrawCircle>(FEATURE_COUNT, DrawCircleImpl::featureIndexStatic).add(DrawCircle.FeatureIDs.MATERIAL, (object, value) -> ((DrawCircleImpl) object).material((Material) value)).add(DrawCircle.FeatureIDs.X, (object, value) -> ((DrawCircleImpl) object).x((int) value)).add(DrawCircle.FeatureIDs.Y, (object, value) -> ((DrawCircleImpl) object).y((int) value)).add(DrawCircle.FeatureIDs.SIZE, (object, value) -> ((DrawCircleImpl) object).size((int) value)).build();
  }
}
