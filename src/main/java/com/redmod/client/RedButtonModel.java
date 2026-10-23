package com.redmod.client;

import com.redmod.RedMod;
import com.redmod.entity.RedButtonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RedButtonModel<T extends RedButtonEntity> extends GeoModel<T> {
	private final Identifier model;
	private final Identifier texture;
	private final Identifier animation;

	public RedButtonModel(String name) {
		this.model = new Identifier(RedMod.MOD_ID, "geo/entity/" + name + ".geo.json");
		this.texture = new Identifier(RedMod.MOD_ID, "textures/entity/" + name + ".png");
		this.animation = new Identifier(RedMod.MOD_ID, "animations/entity/red_button.animation.json");
	}

	@Override
	public Identifier getModelResource(T animatable) {
		return this.model;
	}

	@Override
	public Identifier getTextureResource(T animatable) {
		return this.texture;
	}

	@Override
	public Identifier getAnimationResource(T animatable) {
		return this.animation;
	}
}
