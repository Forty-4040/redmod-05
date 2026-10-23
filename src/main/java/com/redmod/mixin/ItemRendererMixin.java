package com.redmod.mixin;

import com.redmod.ModItems;
import com.redmod.RedMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

	@Unique
	private static final ModelIdentifier REDMOD_HAND_MODEL = new ModelIdentifier(RedMod.MOD_ID, "red_button_hand", "inventory");

	@Inject(
			method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
			at = @At("HEAD"),
			cancellable = true
	)
	private void redmod$useHandModel(ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack matrices,
									 VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
		if (!stack.isOf(ModItems.RED_BUTTON_ITEM) && !stack.isOf(ModItems.RED_BUTTON_BIG_ITEM)) {
			return;
		}

		boolean inHand = mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND
				|| mode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND
				|| mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND
				|| mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
		if (!inHand) {
			return;
		}

		BakedModel handModel = MinecraftClient.getInstance().getBakedModelManager().getModel(REDMOD_HAND_MODEL);
		if (handModel == null || handModel == model) {
			return;
		}

		((ItemRenderer) (Object) this).renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, handModel);
		ci.cancel();
	}
}
