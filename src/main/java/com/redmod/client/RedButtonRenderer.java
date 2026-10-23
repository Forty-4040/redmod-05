package com.redmod.client;

import com.redmod.entity.RedButtonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class RedButtonRenderer<T extends RedButtonEntity> extends GeoEntityRenderer<T> {

	private static final float HELMET_SCALE = 0.3f;
	private static final float HELMET_OFFSET_X = 0.0f;
	private static final float HELMET_OFFSET_Y = 0.05f;
	private static final float HELMET_OFFSET_Z = 0.0f;

	private static final float TORCH_SCALE = 0.5f;
	private static final float TORCH_OFFSET_X = 0.0f;
	private static final float TORCH_OFFSET_Y = 0.0f;
	private static final float TORCH_OFFSET_Z = 0.0f;
	private static final float TORCH_TILT = 90.0f;

	public RedButtonRenderer(EntityRendererFactory.Context context, RedButtonModel<T> model, float modelScale) {
		super(context, model);
		this.shadowRadius = 0.15f * modelScale;
		this.withScale(modelScale);
		this.addRenderLayer(new HeldItemsLayer<>(this));
	}

	private static class HeldItemsLayer<T extends RedButtonEntity> extends BlockAndItemGeoLayer<T> {
		HeldItemsLayer(GeoRenderer<T> renderer) {
			super(renderer);
		}

		@Nullable
		@Override
		protected ItemStack getStackForBone(GeoBone bone, T animatable) {
			EquipmentSlot slot = null;
			if ("helmet".equals(bone.getName())) {
				slot = EquipmentSlot.HEAD;
			} else if ("torch".equals(bone.getName())) {
				slot = EquipmentSlot.MAINHAND;
			}
			if (slot == null) {
				return null;
			}
			ItemStack stack = animatable.getEquippedStack(slot);
			return stack.isEmpty() ? null : stack;
		}

		@Override
		protected ModelTransformationMode getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
			return ModelTransformationMode.FIXED;
		}

		@Override
		protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack, T animatable,
										  VertexConsumerProvider bufferSource, float partialTick, int packedLight, int packedOverlay) {
			boolean torch = "torch".equals(bone.getName());
			poseStack.push();
			if (torch) {
				poseStack.translate(TORCH_OFFSET_X, TORCH_OFFSET_Y, TORCH_OFFSET_Z);
				poseStack.scale(TORCH_SCALE, TORCH_SCALE, TORCH_SCALE);
				poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(TORCH_TILT));
			} else {
				poseStack.translate(HELMET_OFFSET_X, HELMET_OFFSET_Y, HELMET_OFFSET_Z);
				poseStack.scale(HELMET_SCALE, HELMET_SCALE, HELMET_SCALE);
			}
			super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
			poseStack.pop();
		}
	}
}
