package dxii.betterwithsouls.entity;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProjectileBase extends Projectile {


	public boolean slowedInWater = false;

	public ProjectileBase(World world, Mob owner) {
		super(world);

		this.ticksInAir = 0;
		this.owner = owner;
		this.setSize(0.25F, 0.25F);
		this.moveTo(owner.x, owner.y + (double)owner.getHeadHeight(), owner.z, owner.yRot, owner.xRot);
		this.x = this.x - (double)(MathHelper.cos(this.yRot / 180.0F * (float) Math.PI) * 0.16F);
		this.y -= 0.1;
		this.z = this.z - (double)(MathHelper.sin(this.yRot / 180.0F * (float) Math.PI) * 0.16F);
		this.setPos(this.x, this.y, this.z);
		this.heightOffset = 0.0F;
		float f = 0.4F;
		this.xd = (double)(-MathHelper.sin(this.yRot / 180.0F * (float) Math.PI) * MathHelper.cos(this.xRot / 180.0F * (float) Math.PI) * f);
		this.zd = (double)(MathHelper.cos(this.yRot / 180.0F * (float) Math.PI) * MathHelper.cos(this.xRot / 180.0F * (float) Math.PI) * f);
		this.yd = (double)(-MathHelper.sin(this.xRot / 180.0F * (float) Math.PI) * f);
		this.setHeading(this.xd, this.yd, this.zd, 1.5F, 1.0F);
		this.initProjectile();
	}


	protected void initProjectile() {
		this.damage = 0;
		this.defaultGravity = 0.03F;
		this.defaultProjectileSpeed = 0.99F;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		double d1 = this.bb.getSize() * 4.0;
		d1 *= 64.0;
		return distance < d1 * d1;
	}

	public void setHeadingPrecise(double newMotionX, double newMotionY, double newMotionZ, float speed) {
		float velocity = MathHelper.sqrt(newMotionX * newMotionX + newMotionY * newMotionY + newMotionZ * newMotionZ);
		newMotionX /= velocity;
		newMotionY /= velocity;
		newMotionZ /= velocity;
		newMotionX *= speed;
		newMotionY *= speed;
		newMotionZ *= speed;
		this.xd = newMotionX;
		this.yd = newMotionY;
		this.zd = newMotionZ;
		float horizontalVelocity = MathHelper.sqrt(newMotionX * newMotionX + newMotionZ * newMotionZ);
		this.yRotO = this.yRot = (float)(Math.atan2(newMotionX, newMotionZ) * 180.0 / Math.PI);
		this.xRotO = this.xRot = (float)(Math.atan2(newMotionY, horizontalVelocity) * 180.0 / Math.PI);
		this.ticksInGround = 0;
	}

	@Override
	public void lerpMotion(double xd, double yd, double zd) {
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			float f = MathHelper.sqrt(xd * xd + zd * zd);
			this.yRotO = this.yRot = (float)(Math.atan2(xd, zd) * 180.0 / Math.PI);
			this.xRotO = this.xRot = (float)(Math.atan2(yd, (double)f) * 180.0 / Math.PI);
		}
	}

	@Override
	public void tick() {
		this.gravity = this.defaultGravity;
		this.projectileSpeed = this.defaultProjectileSpeed;
		super.tick();
		this.ticksInAir++;
		HitResult movingobjectposition = this.getHitResult();
		Vec3 oldPosition = Vec3.getTempVec3(this.x, this.y, this.z);
		Vec3 newPosition = Vec3.getTempVec3(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		if (movingobjectposition != null) {
			newPosition = Vec3.getTempVec3(movingobjectposition.location.x, movingobjectposition.location.y, movingobjectposition.location.z);
		}

		if (!this.world.isClientSide) {
			Entity entity = null;
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(this.xd, this.yd, this.zd).grow(1.0, 1.0, 1.0));
			double d = 0.0;

			for (Entity entity1 : list) {
				if (entity1.isPickable() && (entity1 != this.owner || this.ticksInAir >= 5)) {
					float f4 = 0.3F;
					AABB axisalignedbb = entity1.bb.grow((double)f4, (double)f4, (double)f4);
					HitResult movingobjectposition1 = axisalignedbb.clip(oldPosition, newPosition);
					if (movingobjectposition1 != null) {
						double d1 = oldPosition.distanceTo(movingobjectposition1.location);
						if (d1 < d || d == 0.0) {
							entity = entity1;
							d = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new HitResult(entity);
			}
		}

		if (movingobjectposition != null) {
			this.onHit(movingobjectposition);
		}

		this.afterTick();
	}

	public HitResult getHitResult() {
		Vec3 oldPosition = Vec3.getTempVec3(this.x, this.y, this.z);
		Vec3 newPosition = Vec3.getTempVec3(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		return this.world.checkBlockCollisionBetweenPoints(oldPosition, newPosition);
	}

	public void onHit(HitResult hitResult) {
		if (hitResult.entity != null) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
		}

		if (this.modelItem != null) {
			for (int j = 0; j < 8; j++) {
				this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, this.modelItem.id);
			}
		}

		this.remove();
	}

	public void afterTick() {
		this.x = this.x + this.xd;
		this.y = this.y + this.yd;
		this.z = this.z + this.zd;
		float f = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
		this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);
		this.xRot = (float)(Math.atan2(this.yd, (double)f) * 180.0 / Math.PI);

		while (this.xRot - this.xRotO < -180.0F) {
			this.xRotO -= 360.0F;
		}

		while (this.xRot - this.xRotO >= 180.0F) {
			this.xRotO += 360.0F;
		}

		while (this.yRot - this.yRotO < -180.0F) {
			this.yRotO -= 360.0F;
		}

		while (this.yRot - this.yRotO >= 180.0F) {
			this.yRotO += 360.0F;
		}

		this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2F;
		this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2F;
		if (this.isInWater()) {
			this.waterTick();
		}

		this.xd = this.xd * (double)this.projectileSpeed;
		this.yd = this.yd * (double)this.projectileSpeed;
		this.zd = this.zd * (double)this.projectileSpeed;
		this.yd = this.yd - (double)this.gravity;
		this.setPos(this.x, this.y, this.z);
	}

	public void waterTick() {
		for (int k = 0; k < 4; k++) {
			double particleDistance = 0.25;
			this.world
				.spawnParticle(
					"bubble", this.x - this.xd * particleDistance, this.y - this.yd * particleDistance, this.z - this.zd * particleDistance, this.xd, this.yd, this.zd, 0
				);
		}

		if(this.slowedInWater){
			this.projectileSpeed = 0.8F;
			}
	}
}
