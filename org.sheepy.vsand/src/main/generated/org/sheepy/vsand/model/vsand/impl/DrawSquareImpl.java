package org.sheepy.vsand.model.vsand.impl;

import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.api.notification.Notification;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.vsand.model.vsand.DrawCommand;
import org.sheepy.vsand.model.vsand.DrawSquare;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class DrawSquareImpl extends FeaturedObject<DrawSquare.Features<?>> implements DrawSquare {
  private static final int FEATURE_COUNT = 4;
  private final ModelNotifier<DrawSquare.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private Material material;
  private int x;
  private int y;
  private int size;

  public DrawSquareImpl() {
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<DrawSquare.Features<?>> notifier() {
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
    notifier.notifyInt(DrawSquare.FeatureIDs.X, false, false, oldValue, x);
  }

  @Override
  public int y() {
    return y;
  }

  @Override
  public void y(final int y) {
    final var oldValue = this.y;
    this.y = y;
    notifier.notifyInt(DrawSquare.FeatureIDs.Y, false, false, oldValue, y);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void size(final int size) {
    final var oldValue = this.size;
    this.size = size;
    notifier.notifyInt(DrawSquare.FeatureIDs.SIZE, false, false, oldValue, size);
  }

  @Override
  public Group<DrawSquare> lmGroup() {
    return VSandModelDefinition.Groups.DRAW_SQUARE;
  }

  @Override
  protected FeatureSetter<DrawSquare> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<DrawSquare> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case DrawSquare.FeatureIDs.MATERIAL -> 0;
      case DrawSquare.FeatureIDs.X -> 1;
      case DrawSquare.FeatureIDs.Y -> 2;
      case DrawSquare.FeatureIDs.SIZE -> 3;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<DrawSquare> GET_MAP = new FeatureGetter.Builder<DrawSquare>(FEATURE_COUNT, DrawSquareImpl::featureIndexStatic).add(DrawSquare.FeatureIDs.MATERIAL, DrawSquare::material).add(DrawSquare.FeatureIDs.X, DrawSquare::x).add(DrawSquare.FeatureIDs.Y, DrawSquare::y).add(DrawSquare.FeatureIDs.SIZE, DrawSquare::size).build();
    private static final FeatureSetter<DrawSquare> SET_MAP = new FeatureSetter.Builder<DrawSquare>(FEATURE_COUNT, DrawSquareImpl::featureIndexStatic).add(DrawSquare.FeatureIDs.MATERIAL, (object, value) -> ((DrawSquareImpl) object).material((Material) value)).add(DrawSquare.FeatureIDs.X, (object, value) -> ((DrawSquareImpl) object).x((int) value)).add(DrawSquare.FeatureIDs.Y, (object, value) -> ((DrawSquareImpl) object).y((int) value)).add(DrawSquare.FeatureIDs.SIZE, (object, value) -> ((DrawSquareImpl) object).size((int) value)).build();
  }
}
