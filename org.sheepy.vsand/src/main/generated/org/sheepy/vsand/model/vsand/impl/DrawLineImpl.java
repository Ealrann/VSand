package org.sheepy.vsand.model.vsand.impl;

import org.logoce.lmf.core.api.model.FeaturedObject;
import org.logoce.lmf.core.api.model.IModelNotifier;
import org.logoce.lmf.core.api.model.ModelNotifier;
import org.logoce.lmf.core.api.notification.Notification;
import org.logoce.lmf.core.feature.FeatureGetter;
import org.logoce.lmf.core.feature.FeatureSetter;
import org.logoce.lmf.core.lang.Group;
import org.sheepy.vsand.model.vsand.DrawCommand;
import org.sheepy.vsand.model.vsand.DrawLine;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandModelDefinition;

public final class DrawLineImpl extends FeaturedObject<DrawLine.Features<?>> implements DrawLine {
  private static final int FEATURE_COUNT = 6;
  private final ModelNotifier<DrawLine.Features<?>> notifier = new ModelNotifier<>(this, FEATURE_COUNT, this::featureIndex);
  private Material material;
  private int x1;
  private int y1;
  private int x2;
  private int y2;
  private int size;

  public DrawLineImpl() {
    notifier.eDeliver(true);
  }

  @Override
  public IModelNotifier.Impl<DrawLine.Features<?>> notifier() {
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
  public int x1() {
    return x1;
  }

  @Override
  public void x1(final int x1) {
    final var oldValue = this.x1;
    this.x1 = x1;
    notifier.notifyInt(DrawLine.FeatureIDs.X1, false, false, oldValue, x1);
  }

  @Override
  public int y1() {
    return y1;
  }

  @Override
  public void y1(final int y1) {
    final var oldValue = this.y1;
    this.y1 = y1;
    notifier.notifyInt(DrawLine.FeatureIDs.Y1, false, false, oldValue, y1);
  }

  @Override
  public int x2() {
    return x2;
  }

  @Override
  public void x2(final int x2) {
    final var oldValue = this.x2;
    this.x2 = x2;
    notifier.notifyInt(DrawLine.FeatureIDs.X2, false, false, oldValue, x2);
  }

  @Override
  public int y2() {
    return y2;
  }

  @Override
  public void y2(final int y2) {
    final var oldValue = this.y2;
    this.y2 = y2;
    notifier.notifyInt(DrawLine.FeatureIDs.Y2, false, false, oldValue, y2);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void size(final int size) {
    final var oldValue = this.size;
    this.size = size;
    notifier.notifyInt(DrawLine.FeatureIDs.SIZE, false, false, oldValue, size);
  }

  @Override
  public Group<DrawLine> lmGroup() {
    return VSandModelDefinition.Groups.DRAW_LINE;
  }

  @Override
  protected FeatureSetter<DrawLine> setterMap() {
    return Inserters.SET_MAP;
  }

  @Override
  protected FeatureGetter<DrawLine> getterMap() {
    return Inserters.GET_MAP;
  }

  public static int featureIndexStatic(int featureId) {
    return switch (featureId) {
      case DrawLine.FeatureIDs.MATERIAL -> 0;
      case DrawLine.FeatureIDs.X1 -> 1;
      case DrawLine.FeatureIDs.Y1 -> 2;
      case DrawLine.FeatureIDs.X2 -> 3;
      case DrawLine.FeatureIDs.Y2 -> 4;
      case DrawLine.FeatureIDs.SIZE -> 5;
      default -> throw new IllegalArgumentException("Unknown featureId: " + featureId);
    };
  }

  @Override
  public int featureIndex(int featureId) {
    return featureIndexStatic(featureId);
  }

  private static final class Inserters {
    private static final FeatureGetter<DrawLine> GET_MAP = new FeatureGetter.Builder<DrawLine>(FEATURE_COUNT, DrawLineImpl::featureIndexStatic).add(DrawLine.FeatureIDs.MATERIAL, DrawLine::material).add(DrawLine.FeatureIDs.X1, DrawLine::x1).add(DrawLine.FeatureIDs.Y1, DrawLine::y1).add(DrawLine.FeatureIDs.X2, DrawLine::x2).add(DrawLine.FeatureIDs.Y2, DrawLine::y2).add(DrawLine.FeatureIDs.SIZE, DrawLine::size).build();
    private static final FeatureSetter<DrawLine> SET_MAP = new FeatureSetter.Builder<DrawLine>(FEATURE_COUNT, DrawLineImpl::featureIndexStatic).add(DrawLine.FeatureIDs.MATERIAL, (object, value) -> ((DrawLineImpl) object).material((Material) value)).add(DrawLine.FeatureIDs.X1, (object, value) -> ((DrawLineImpl) object).x1((int) value)).add(DrawLine.FeatureIDs.Y1, (object, value) -> ((DrawLineImpl) object).y1((int) value)).add(DrawLine.FeatureIDs.X2, (object, value) -> ((DrawLineImpl) object).x2((int) value)).add(DrawLine.FeatureIDs.Y2, (object, value) -> ((DrawLineImpl) object).y2((int) value)).add(DrawLine.FeatureIDs.SIZE, (object, value) -> ((DrawLineImpl) object).size((int) value)).build();
  }
}
