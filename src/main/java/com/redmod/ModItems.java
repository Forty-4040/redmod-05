package com.redmod;

import com.redmod.item.RedButtonItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
	public static final Item RED_BUTTON_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(RedMod.MOD_ID, "red_button_item"),
			new RedButtonItem(() -> ModEntities.RED_BUTTON, new Item.Settings().maxCount(16))
	);

	public static final Item RED_BUTTON_BIG_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(RedMod.MOD_ID, "red_button_big_item"),
			new RedButtonItem(() -> ModEntities.RED_BUTTON_BIG, new Item.Settings().maxCount(16))
	);

	public static final Item RED_BUTTON_HAND = Registry.register(
			Registries.ITEM,
			new Identifier(RedMod.MOD_ID, "red_button_hand"),
			new Item(new Item.Settings())
	);

	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
			entries.add(RED_BUTTON_ITEM);
			entries.add(RED_BUTTON_BIG_ITEM);
		});
	}
}
