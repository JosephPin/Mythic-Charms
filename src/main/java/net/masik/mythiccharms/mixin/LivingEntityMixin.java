package net.masik.mythiccharms.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.masik.mythiccharms.MythicCharms;
import net.masik.mythiccharms.item.ModItems;
import net.masik.mythiccharms.util.CharmHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    //highBounds
    @Inject(method = "getJumpVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getJumpBoostVelocityModifier()F"), cancellable = true)
    private void highBoundsEffectJump(CallbackInfoReturnable<Float> cir) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.isPlayer()) return;


        if (!CharmHelper.charmHighBoundsEquipped(entity)) return;


        ((PlayerEntity) entity).addExhaustion(0.1F);

        float high = 0.6F;

        //featheredGrace combo
        if (CharmHelper.charmFeatheredGraceEquipped(entity) &&
                CharmHelper.charmCombinationFeatheredGraceAndHighBoundsEnabled(entity)) high += 0.05F;

        cir.setReturnValue(entity.getJumpBoostVelocityModifier() + high);

    }

    @ModifyArg(method = "computeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"), index = 0)
    private float highBoundsEffectFall(float value) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.isPlayer()) return value;


        if (!CharmHelper.charmHighBoundsEquipped(entity)) return value;


        return value - 2;

    }

    //climbersPath
    @Inject(method = "getStepHeight", at = @At("RETURN"), cancellable = true)
    private void climbersPathEffect(CallbackInfoReturnable<Float> cir) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.isPlayer()) return;


        if (!CharmHelper.charmClimbersPathEquipped(entity)) return;


        float height = 1.1F;

        //highBounds combo
        if (CharmHelper.charmHighBoundsEquipped(entity) &&
                CharmHelper.charmCombinationClimbersPathAndHighBoundsEnabled(entity)) height += 0.5F;

        cir.setReturnValue(height);

    }

    //mountainsStrength
    @Inject(method = "takeKnockback", at = @At(value = "HEAD"), cancellable = true)
    private void mountainsStrengthEffectKnock(double strength, double x, double z, CallbackInfo ci) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.isPlayer()) return;


        if (!CharmHelper.charmMountainsStrengthEquipped(entity)) return;


        ci.cancel();

    }

    @Inject(method = "isPushable", at = @At("RETURN"), cancellable = true)
    private void mountainsStrengthEffectPush(CallbackInfoReturnable<Boolean> cir) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.isPlayer()) return;


        if (!CharmHelper.charmMountainsStrengthEquipped(entity)) return;


        cir.setReturnValue(false);

    }

}
