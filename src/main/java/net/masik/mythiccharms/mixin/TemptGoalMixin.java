package net.masik.mythiccharms.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.masik.mythiccharms.item.ModItems;
import net.masik.mythiccharms.util.CharmHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(TemptGoal.class)
public class TemptGoalMixin {

    @Inject(method = "isTemptedBy", at = @At("RETURN"), cancellable = true)
    private void naturesCallEffect(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {

        if (!entity.isPlayer()) return;


        if (!CharmHelper.charmNaturesCallEquipped(entity)) return;


        if (entity.isSneaking()) return;

        cir.setReturnValue(true);

    }

}
