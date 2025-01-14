package com.skirlez.fabricatedexchange.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.skirlez.fabricatedexchange.item.ChargeableItem;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;


@Mixin(EnchantmentHelper.class)
public abstract class ModEnchantmentHelper {
    @Inject(method = "getAttackDamage", at = @At("RETURN"), cancellable = true)
    private static void considerChargeAttackDamage(ItemStack stack, EntityGroup group, CallbackInfoReturnable<Float> cir) {
        Item item = stack.getItem();
        if (item instanceof ChargeableItem && item instanceof SwordItem) {
            cir.setReturnValue(cir.getReturnValue() + ChargeableItem.getCharge(stack));
        }
        
    }
}
