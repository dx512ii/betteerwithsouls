package dxii.bws.mixin;

import dxii.bws.BWS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.camera.EntityCamera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityCamera.class, remap = false )
public class EntityCameraMixin {

	@Inject(
		method = "showPlayer",
		at = @At(value = "RETURN"),
		cancellable = true)
	private void showPlayer(CallbackInfoReturnable<Boolean> cir){
		if(!BWS.playerHoldsWeapon(Minecraft.getMinecraft().thePlayer)){
			return;
		}
		cir.setReturnValue(true);
	}
}
