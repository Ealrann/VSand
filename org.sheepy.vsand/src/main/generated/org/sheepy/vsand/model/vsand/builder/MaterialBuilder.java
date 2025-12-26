package org.sheepy.vsand.model.vsand.builder;

import java.util.function.Supplier;
import org.logoce.lmf.core.feature.FeatureInserter;
import org.logoce.lmf.core.feature.RelationLazyInserter;
import org.logoce.lmf.core.lang.Attribute;
import org.logoce.lmf.core.lang.LMObject;
import org.logoce.lmf.core.lang.Relation;
import org.sheepy.lily.core.model.resource.Sound;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.Material.Builder;
import org.sheepy.vsand.model.vsand.impl.MaterialImpl;

public final class MaterialBuilder implements Builder {
  private String name;
  private boolean isStatic;
  private int density;
  private int runoff;
  private int r = 0;
  private int g = 0;
  private int b = 0;
  private boolean userFriendly = true;
  private Supplier<Sound> paintSound = () -> null;
  private float pitch = 1f;

  public MaterialBuilder() {
  }

  @Override
  public MaterialBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public MaterialBuilder isStatic(boolean isStatic) {
    this.isStatic = isStatic;
    return this;
  }

  @Override
  public MaterialBuilder density(int density) {
    this.density = density;
    return this;
  }

  @Override
  public MaterialBuilder runoff(int runoff) {
    this.runoff = runoff;
    return this;
  }

  @Override
  public MaterialBuilder r(int r) {
    this.r = r;
    return this;
  }

  @Override
  public MaterialBuilder g(int g) {
    this.g = g;
    return this;
  }

  @Override
  public MaterialBuilder b(int b) {
    this.b = b;
    return this;
  }

  @Override
  public MaterialBuilder userFriendly(boolean userFriendly) {
    this.userFriendly = userFriendly;
    return this;
  }

  @Override
  public MaterialBuilder paintSound(Supplier<Sound> paintSound) {
    this.paintSound = paintSound;
    return this;
  }

  @Override
  public MaterialBuilder pitch(float pitch) {
    this.pitch = pitch;
    return this;
  }

  @Override
  public Material build() {
    final var built = new MaterialImpl(pitch);
    built.name(name);
    built.isStatic(isStatic);
    built.density(density);
    built.runoff(runoff);
    built.r(r);
    built.g(g);
    built.b(b);
    built.userFriendly(userFriendly);
    built.paintSound(paintSound.get());
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
    private static final FeatureInserter<MaterialBuilder> ATTRIBUTE_INSERTER = new FeatureInserter.Builder<MaterialBuilder>(9, Inserters::attributeIndex).add(Material.FeatureIDs.NAME, (builder, value) -> builder.name((String) value)).add(Material.FeatureIDs.IS_STATIC, (builder, value) -> builder.isStatic((boolean) value)).add(Material.FeatureIDs.DENSITY, (builder, value) -> builder.density((int) value)).add(Material.FeatureIDs.RUNOFF, (builder, value) -> builder.runoff((int) value)).add(Material.FeatureIDs.R, (builder, value) -> builder.r((int) value)).add(Material.FeatureIDs.G, (builder, value) -> builder.g((int) value)).add(Material.FeatureIDs.B, (builder, value) -> builder.b((int) value)).add(Material.FeatureIDs.USER_FRIENDLY, (builder, value) -> builder.userFriendly((boolean) value)).add(Material.FeatureIDs.PITCH, (builder, value) -> builder.pitch((float) value)).build();
    private static final RelationLazyInserter<MaterialBuilder> RELATION_INSERTER = new RelationLazyInserter.Builder<MaterialBuilder>(1, Inserters::relationIndex).add(Material.FeatureIDs.PAINT_SOUND, (builder, value) -> builder.paintSound((Supplier<Sound>) value)).build();

    private static int attributeIndex(final int featureId) {
      return switch (featureId) {
        case Material.FeatureIDs.NAME -> 0;
        case Material.FeatureIDs.IS_STATIC -> 1;
        case Material.FeatureIDs.DENSITY -> 2;
        case Material.FeatureIDs.RUNOFF -> 3;
        case Material.FeatureIDs.R -> 4;
        case Material.FeatureIDs.G -> 5;
        case Material.FeatureIDs.B -> 6;
        case Material.FeatureIDs.USER_FRIENDLY -> 7;
        case Material.FeatureIDs.PITCH -> 8;
        default -> throw new IllegalArgumentException("Unknown attribute featureId: " + featureId);
      };
    }

    private static int relationIndex(final int featureId) {
      return switch (featureId) {
        case Material.FeatureIDs.PAINT_SOUND -> 0;
        default -> throw new IllegalArgumentException("Unknown relation featureId: " + featureId);
      };
    }
  }
}
