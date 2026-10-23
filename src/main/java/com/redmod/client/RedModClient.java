package com.redmod.client;

import com.redmod.ModEntities;
import com.redmod.entity.RedButtonBigEntity;
import com.redmod.entity.RedButtonEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RedModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.RED_BUTTON,
				context -> new RedButtonRenderer<RedButtonEntity>(context,
						new RedButtonModel<RedButtonEntity>("red_button"), ModEntities.MODEL_SCALE));

		EntityRendererRegistry.register(ModEntities.RED_BUTTON_BIG,
				context -> new RedButtonRenderer<RedButtonBigEntity>(context,
						new RedButtonModel<RedButtonBigEntity>("red_button_big"), ModEntities.BIG_MODEL_SCALE));
	}
}
