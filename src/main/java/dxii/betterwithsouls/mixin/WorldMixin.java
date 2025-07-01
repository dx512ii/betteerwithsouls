package dxii.betterwithsouls.mixin;

import dxii.betterwithsouls.interfaces.IWorld;
import dxii.betterwithsouls.util.DynamicLightsModule;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = World.class, remap = false)
public class WorldMixin implements IWorld {


	@Unique
	public World thisObject = (World) (Object)this;
	@Unique
	public DynamicLightsModule dynLights = new DynamicLightsModule();

	@Override
	public DynamicLightsModule bws$getDynLights(){
		return this.dynLights;
	}

	@Inject(at = @At(value = "TAIL"), method = "notifyBlockOfNeighborChange")
	private void injected(int i, int j, int k, int blockID, CallbackInfo ci) {
		thisObject.markBlockNeedsUpdate(i, j, k);
	}

	@Inject(at = @At(value = "TAIL"), method = "tick()V")
	private void injected(CallbackInfo ci) {
		dynLights.update();
	}

	/**
	 * @author eea
	 * @reason dada
	 */
	@Overwrite
	public float getBrightness(int x, int y, int z, int blockLightValue) {
		int i1 = thisObject.getBlockLightValue(x, y, z);
		float light = this.dynLights.getBrightness(x, y, z);

		if (i1 < blockLightValue) {
			i1 = blockLightValue;
		}
		if(light > i1){
			i1 = (int)light;
		}

		return thisObject.worldType.getBrightnessRamp()[i1];
	}

	/**
	 * @author me
	 * @reason for the love of dynamic lights
	 */
	@Overwrite
	public float getLightBrightness(int x, int y, int z) {
		int i1 = thisObject.getBlockLightValue(x, y, z);
		float light = ((IWorld)thisObject).bws$getDynLights().getBrightness(x, y, z);

		if(light > i1){
			i1 = (int)light;
		}

		return thisObject.worldType.getBrightnessRamp()[i1];
	}
}
