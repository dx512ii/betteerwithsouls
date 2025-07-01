package dxii.betterwithsouls.mixin;


import net.minecraft.core.entity.monster.MobSlime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MobSlime.class, remap = false)
public class FuckYouSlimeMixin {

	@Inject(
		method = "updateAI",
		at = @At(value = "TAIL")
	)
	public void tickMixin(CallbackInfo ci) {
		((MobSlime)(Object)this).remove();
	}

}
