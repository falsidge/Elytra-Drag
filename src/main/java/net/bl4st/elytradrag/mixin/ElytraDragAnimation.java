package net.bl4st.elytradrag.mixin;

import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraModel.class)
public class ElytraDragAnimation {
    @Shadow
    @Final
    private ModelPart rightWing;

    @Shadow
    @Final
    private ModelPart leftWing;

    /**
     * Scuffed way to handle some kind of animation,
     * it just works™
     */

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void SetAngles(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if(!livingEntity.isShiftKeyDown() || !livingEntity.isFallFlying())
            return;

        long FLAPPING_SPEED = 3;
        float progressCycle = (float)((System.currentTimeMillis() * FLAPPING_SPEED) % 1000) / 1000.0f;
        float progress = ((float)Math.sin(progressCycle * Math.PI * 2.0f) + 1.0f) / 2.0f;
        float animSpeedCoef = GetSpeedAnimationCoef((float)livingEntity.getDeltaMovement().length() * 20f);

        rightWing.yRot = -1.5f * animSpeedCoef;
        leftWing.yRot = 1.5f * animSpeedCoef;

        rightWing.xRot = progress * (1f - animSpeedCoef);
        leftWing.xRot = progress * (1f - animSpeedCoef);

        rightWing.zRot = 1f;
        leftWing.zRot = -1f;
    }

    @Unique
    private float GetSpeedAnimationCoef(float value) {
        value = Math.max(2f, Math.min(45f, value));
        return (float)Math.pow((value - 2f) / (45f), 0.25f);
    }
}



