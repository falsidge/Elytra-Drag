package net.bl4st.elytradrag.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraEntityModel.class)
public class ElytraDragAnimation {
    @Shadow
    private ModelPart rightWing;

    @Shadow
    private ModelPart leftWing;

    /**
     * Scuffed way to handle some kind of animation,
     * it just works™
     */

    @Inject(method = "setAngles", at = @At("TAIL"))
    private void SetAngles(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if(!livingEntity.isSneaking() || !livingEntity.isFallFlying())
            return;

        long FLAPPING_SPEED = 3;
        float progressCycle = (float)((System.currentTimeMillis() * FLAPPING_SPEED) % 1000) / 1000.0f;
        float progress = ((float)Math.sin(progressCycle * Math.PI * 2.0f) + 1.0f) / 2.0f;
        float animSpeedCoef = GetSpeedAnimationCoef((float)livingEntity.getVelocity().length() * 20f);

        rightWing.yaw = -1.5f * animSpeedCoef;
        leftWing.yaw = 1.5f * animSpeedCoef;

        rightWing.pitch = progress * (1f - animSpeedCoef);
        leftWing.pitch = progress * (1f - animSpeedCoef);

        rightWing.roll = 1f;
        leftWing.roll = -1f;
    }

    private float GetSpeedAnimationCoef(float value) {
        value = Math.max(2f, Math.min(45f, value));
        return (float)Math.pow((value - 2f) / (45f), 0.25f);
    }
}



