package dxii.bws.mixin;


import dxii.bws.BWS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerController.class, remap = false)
public class PlayerControllerMixin {

//	@Unique
//	public PlayerController self = (PlayerController)(Object)this;

	@Inject(
		method = "startDestroyBlock",
		at = @At(value = "HEAD"), cancellable = true)
	public void noBlockDestroy(TilePosc tilePos, Side side, double xHit, double yHit, boolean repeat, CallbackInfo ci){
		if(BWS.playerHoldsWeapon(Minecraft.getMinecraft().thePlayer)){
			ci.cancel();
		}
	}
	@Inject(
		method = "continueDestroyBlock",
		at = @At(value = "HEAD"), cancellable = true)
	public void noBlockDestroy2(TilePosc tilePos, Side side, double xHit, double yHit, CallbackInfo ci){
		if(BWS.playerHoldsWeapon(Minecraft.getMinecraft().thePlayer)){
			ci.cancel();
		}
	}
}
