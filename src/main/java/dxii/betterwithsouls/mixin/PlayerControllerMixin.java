package dxii.betterwithsouls.mixin;

import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerController.class, remap = false)
public class PlayerControllerMixin {

	@Shadow
	protected int blockHitDelay;

	@Inject(
		method = "continueDestroyBlock",
		at = @At(value = "TAIL"), cancellable = true)
	public void whyNot(int x, int y, int z, Side side, double xHit, double yHit, CallbackInfo ci){
		blockHitDelay = 0;
	}
}
