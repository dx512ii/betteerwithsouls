package dxii.betterwithsouls.mixin;


import dxii.betterwithsouls.BWSMain;
import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.util.DynamicLight;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.Explosion;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Explosion.class, remap = false)
public class _ExplosionMixin {

	@Unique
	public Explosion thisObject = (Explosion)(Object)this;
	@Unique
	public DynamicLight dyn;

	@Shadow
	protected World worldObj;

	@Inject(
		method = "<init>",
		at = @At(value = "TAIL"))
	public void plyInit(CallbackInfo ci) {
		if(BWSMain.dynLightExplosions.value) {
			dyn = BWSUtils.addDynamicLightFading(this.worldObj, (int) MathHelper.clamp(thisObject.explosionSize, 2, 10) * 2, 31, (int) thisObject.explosionX, (int) thisObject.explosionY, (int) thisObject.explosionZ);
		}
	}

}
