package dxii.betterwithsouls.mixin;

import dxii.betterwithsouls.BWSConfig;
import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.interfaces.IWorld;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCache;
import org.spongepowered.asm.mixin.*;

@Mixin(value = ChunkCache.class, remap = false)
public class ChunkCacheMixin {


	@Unique
	public ChunkCache thisObject = (ChunkCache) (Object)this;

	@Final
	@Shadow
	private World worldObj;


	/**
	 * @author eea
	 * @reason dada
	 */
	@Overwrite
	public float getBrightness(int x, int y, int z, int blockLightValue) {
		int i1 = thisObject.getLightValue(x, y, z);
		float light = ((IWorld)this.worldObj).bws$getDynLights().getBrightness(x, y, z);

		if (i1 < blockLightValue) {
			i1 = blockLightValue;
		}
		if(light > i1){
			i1 = (int)light;
		}


		return this.worldObj.worldType.getBrightnessRamp()[i1];
	}

	/**
	 * @reason for the love of dynamic lights
	 */
	@Overwrite
	public float getLightBrightness(int x, int y, int z) {
		int i1 = thisObject.getLightValue(x, y, z);
		float light = ((IWorld)this.worldObj).bws$getDynLights().getBrightness(x, y, z);

		if(light > i1){
			i1 = (int)light;
		}

		return this.worldObj.worldType.getBrightnessRamp()[i1];
	}

}
