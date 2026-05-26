package dxii.bws.mixin;


import dxii.bws.item.ItemWeapon;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, remap = false)
public class PlayerMixin {

	@Unique
	public Player self = (Player)(Object)this;

	@Inject(
		method = "dropItem",
		at = @At(value = "HEAD"))
	public void dropItemAdditional(ItemStack stack, boolean randomDirection, CallbackInfo ci){
		if(stack.getItem() instanceof ItemWeapon){
			((ItemWeapon)stack.getItem()).holster(self, self.world);
		}
	}
}
