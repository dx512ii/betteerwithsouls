package dxii.betterwithsouls.mixin;

import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ProjectileArrow.class, remap = false)
public class _ArrowMixin {

	@Unique
	public ProjectileArrow thisObject = (ProjectileArrow) (Object)this;

	@Inject(
		method ="lerpMotion",
		at = @At(value = "TAIL")
	)
	private void wandMixin(CallbackInfo ci){
		double rand = MathHelper.clamp(Math.random()*1, 0.1, 1);
		thisObject.xd = thisObject.xd+rand;
		thisObject.yd = thisObject.yd+rand;
		thisObject.zd = thisObject.zd+rand;
	}


}
