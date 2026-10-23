package com.redmod.item;

import com.redmod.entity.RedButtonEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class RedButtonItem extends Item {
	public static final String HEALTH_KEY = "RedButtonHealth";

	private final Supplier<EntityType<? extends RedButtonEntity>> entityType;

	public RedButtonItem(Supplier<EntityType<? extends RedButtonEntity>> entityType, Settings settings) {
		super(settings);
		this.entityType = entityType;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (!(world instanceof ServerWorld serverWorld)) {
			return ActionResult.SUCCESS;
		}

		BlockPos spawnPos = context.getBlockPos().offset(context.getSide());
		RedButtonEntity button = this.entityType.get().create(serverWorld);
		if (button == null) {
			return ActionResult.FAIL;
		}

		PlayerEntity player = context.getPlayer();
		float yaw = player != null ? player.getYaw() + 180.0f : 0.0f;
		button.refreshPositionAndAngles(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, yaw, 0.0f);

		if (!serverWorld.isSpaceEmpty(button)) {
			return ActionResult.FAIL;
		}

		ItemStack stack = context.getStack();
		NbtCompound nbt = stack.getNbt();
		if (nbt != null && nbt.contains(HEALTH_KEY)) {
			button.setHealth(Math.min(nbt.getFloat(HEALTH_KEY), button.getMaxHealth()));
		}

		serverWorld.spawnEntity(button);
		serverWorld.playSound(null, spawnPos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1.0f, 1.0f);

		if (player == null || !player.isCreative()) {
			stack.decrement(1);
		}
		return ActionResult.CONSUME;
	}
}
