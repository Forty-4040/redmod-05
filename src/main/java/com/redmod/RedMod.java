package com.redmod;

import net.fabricmc.api.ModInitializer;

public class RedMod implements ModInitializer {
	public static final String MOD_ID = "redmod";

	@Override
	public void onInitialize() {
		ModSounds.init();
		ModEntities.init();
		ModItems.init();
	}
}
