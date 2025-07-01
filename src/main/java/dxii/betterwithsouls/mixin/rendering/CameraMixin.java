package dxii.betterwithsouls.mixin.rendering;

import net.minecraft.client.render.camera.EntityCamera;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import org.spongepowered.asm.mixin.*;

@Mixin(value = EntityCamera.class, remap = false)
public class CameraMixin {

	@Unique
	public EntityCamera thisObject = (EntityCamera)(Object)this;
	@Unique
	public Vec3 mobViewVector;

	@Final
	@Shadow
	public Mob mob;

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public boolean showPlayer() {
		return false;
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public double getX(float partialTick) {
		double pos = partialTick == 1.0F ? this.mob.x : this.mob.xo + (this.mob.x - this.mob.xo) * (double)partialTick;
		return pos;
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public double getY(float partialTick) {
		double pos = partialTick == 1.0F ? this.mob.y : this.mob.yo + (this.mob.y - this.mob.yo) * (double)partialTick;
		return pos;
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public double getZ(float partialTick) {
		double pos = partialTick == 1.0F ? this.mob.z : this.mob.zo + (this.mob.z - this.mob.zo) * (double)partialTick;;
		return pos;
	}


	@Unique
	public Vec3 getViewVec(float partialTick){
		Vec3 view = this.mob.getViewVector(partialTick);
		view.y = 0;
		view.x = MathHelper.clamp(view.x, -0.44, 0.44);
		view.z = MathHelper.clamp(view.z, -0.44, 0.44);

		return view;
	}
}
