package com.restonic4.fire_aspect_fix.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.entity.mob.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Shadow
	public PlayerInventory inventory;

	@Inject(
		method = "attack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;takeDamage(Lnet/minecraft/entity/damage/DamageSource;I)Z",
			shift = At.Shift.BEFORE
		)
	)
	private void fire_aspect_fix$applyEarlyFire(Entity target, CallbackInfo ci) {
		int level = EnchantmentHelper.getFireAspectLevel(this.inventory, (MobEntity) target);
		if (level > 0) target.setOnFireFor(level * 4);
	}
}
