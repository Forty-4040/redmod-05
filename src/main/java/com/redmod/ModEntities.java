package com.redmod;

import com.redmod.entity.RedButtonBigEntity;
import com.redmod.entity.RedButtonEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

	public static final float MODEL_SCALE = 2.0f;

	public static final float HITBOX_WIDTH = 0.3f * MODEL_SCALE;
	public static final float HITBOX_HEIGHT = 0.2f * MODEL_SCALE;

	public static final float BIG_MODEL_SCALE = MODEL_SCALE;

	public static final float BIG_HITBOX_WIDTH = 0.45f * BIG_MODEL_SCALE;
	public static final float BIG_HITBOX_HEIGHT = 0.3f * BIG_MODEL_SCALE;

	public static final EntityType<RedButtonEntity> RED_BUTTON = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(RedMod.MOD_ID, "red_button"),
			FabricEntityTypeBuilder.<RedButtonEntity>create(SpawnGroup.MISC, RedButtonEntity::new)
					.dimensions(EntityDimensions.fixed(HITBOX_WIDTH, HITBOX_HEIGHT))
					.trackRangeChunks(8)
					.build()
	);

	public static final EntityType<RedButtonBigEntity> RED_BUTTON_BIG = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(RedMod.MOD_ID, "red_button_big"),
			FabricEntityTypeBuilder.<RedButtonBigEntity>create(SpawnGroup.MISC, RedButtonBigEntity::new)
					.dimensions(EntityDimensions.fixed(BIG_HITBOX_WIDTH, BIG_HITBOX_HEIGHT))
					.trackRangeChunks(8)
					.build()
	);

	public static void init() {
		FabricDefaultAttributeRegistry.register(RED_BUTTON, RedButtonEntity.createRedButtonAttributes());
		FabricDefaultAttributeRegistry.register(RED_BUTTON_BIG, RedButtonBigEntity.createBigAttributes());
	}
}
