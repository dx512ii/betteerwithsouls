package dxii.betterwithsouls.entity;

import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.util.DamageResistModule;
import dxii.betterwithsouls.util.animation.Animation;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class BWSMob extends MobPathfinder {
	public BWSMob(@Nullable World world) {
		super(world);
	}

	public boolean debug = false;

	public ItemStack[] armor = new ItemStack[4];

	public DamageResistModule getResists(){
		return ((IMob)this).getMobResist();
	}

	public void immobilize(){
		this.moveStrafing	= 0;
		this.moveForward	= 0;
		this.isJumping		= false;
	}

	@Override
	protected void updateAI() {
		++this.entityAge;
		this.tryToDespawn();
	}

	public void yawVelocityProcess(){
		this.yRot += this.randomYawVelocity;
		this.xRot = this.defaultPitch;
	}
	public void walkCurrentPath(){
		if (this.pathToEntity != null) {
			Vec3 coordsForNextPath = this.pathToEntity.getPos(this);
			double d = (this.bbWidth * 2.0F);

			while (coordsForNextPath != null && coordsForNextPath.distanceToSquared(this.x, coordsForNextPath.y, this.z) < d * d) {
				this.pathToEntity.next();
				if (this.pathToEntity.isDone()) {
					coordsForNextPath = null;
					this.pathToEntity = null;
					this.moveForward = 0;
				} else {
					coordsForNextPath = this.pathToEntity.getPos(this);
				}
			}

			int i = MathHelper.floor(this.bb.minY + 0.5);
			boolean inWater = this.isInWater();
			boolean inLava = this.isInLava();
			if (this.random.nextFloat() < 0.8F && (inWater || inLava)) {
				this.isJumping = true;
			}
			this.xRot = 0.0F;
			this.isJumping = false;
			if (coordsForNextPath != null) {
				d = coordsForNextPath.x - this.x;
				double z1 = coordsForNextPath.z - this.z;
				double y1 = coordsForNextPath.y - (double) i;
				float f2 = (float) (Math.atan2(z1, d) * 180.0 / Math.PI) - 90.0F;
				float f3 = f2 - this.yRot;
				this.moveForward = this.moveSpeed;

				while (f3 < -180.0F) {
					f3 += 360.0F;
				}

				while (f3 >= 180.0F) {
					f3 -= 360.0F;
				}

				if (f3 > 30.0F) {
					f3 = 30.0F;
				}

				if (f3 < -30.0F) {
					f3 = -30.0F;
				}

				this.yRot += f3;

				if (y1 > 0.0) {
					this.isJumping = true;
				}
			}
		}
	}
	public void lookAtTarget(){
		if(this.target == null){
			return;
		}
		this.lookAt(this.target, 30.0F, 30.0F);
	}

	protected void updateAI_OLD() {
		if (this.world != null) {
			this.hasAttacked = this.isMovementCeased();
			float sightRadius = 16*2;
			if (this.target == null) {
				this.target = this.findPlayerToAttack();
				if (this.target != null) {
					this.pathToEntity = this.world.getPathToEntity(this, this.target, sightRadius);
				}
			} else if (!this.target.isAlive()) {
				this.target = null;
			} else {
				float distanceToEntity = this.target.distanceTo(this);
				if (this.canEntityBeSeen(this.target)) {
					this.attackEntity(this.target, distanceToEntity);
				} else {
					this.attackBlockedEntity(this.target, distanceToEntity);
				}
			}

			if (this.hasAttacked || this.target == null || this.pathToEntity != null) {
				if ((!this.hasAttacked || true) && this.pathToEntity == null && (true || this.random.nextInt(80) == 0 || this.random.nextInt(80) == 0)) {
					this.roamRandomPath();
				}
			} else {
				this.pathToEntity = this.world.getPathToEntity(this, this.target, sightRadius);
			}

			int i = MathHelper.floor(this.bb.minY + 0.5);
			boolean inWater = this.isInWater();
			boolean inLava = this.isInLava();
			this.xRot = 0.0F;
			if (this.pathToEntity != null && this.random.nextInt(100) != 0) {
				Vec3 coordsForNextPath = this.pathToEntity.getPos(this);
				double d = (this.bbWidth * 2.0F);

				while (coordsForNextPath != null && coordsForNextPath.distanceToSquared(this.x, coordsForNextPath.y, this.z) < d * d) {
					this.pathToEntity.next();
					if (this.pathToEntity.isDone()) {
						this.closestFireflyEntity = null;
						coordsForNextPath = null;
						this.pathToEntity = null;
					} else {
						coordsForNextPath = this.pathToEntity.getPos(this);
					}
				}

				this.isJumping = false;
				if (coordsForNextPath != null) {
					d = coordsForNextPath.x - this.x;
					double z1 = coordsForNextPath.z - this.z;
					double y1 = coordsForNextPath.y - (double)i;
					float f2 = (float)(Math.atan2(z1, d) * 180.0 / Math.PI) - 90.0F;
					float f3 = f2 - this.yRot;
					this.moveForward = this.moveSpeed;

					while (f3 < -180.0F) {
						f3 += 360.0F;
					}

					while (f3 >= 180.0F) {
						f3 -= 360.0F;
					}

					if (f3 > 30.0F) {
						f3 = 30.0F;
					}

					if (f3 < -30.0F) {
						f3 = -30.0F;
					}

					this.yRot += f3;
					if (this.hasAttacked && this.target != null) {
						double d4 = this.target.x - this.x;
						double d5 = this.target.z - this.z;
						float f5 = this.yRot;
						this.yRot = (float)(Math.atan2(d5, d4) * 180.0 / Math.PI) - 90.0F;
						float f4 = (f5 - this.yRot + 90.0F) * (float) Math.PI / 180.0F;
						this.moveStrafing = -MathHelper.sin(f4) * this.moveForward * 1.0F;
						this.moveForward = MathHelper.cos(f4) * this.moveForward * 1.0F;
					}

					if (y1 > 0.0) {
						this.isJumping = true;
					}
				}

				if (this.target != null) {
					this.lookAt(this.target, 30.0F, 30.0F);
				}else if (true) {
					this.randomYawVelocity = (this.random.nextFloat() - 0.5F) * 70.0F;

					this.yRot = this.yRot + this.randomYawVelocity;
					this.xRot = this.defaultPitch;
				}

				if (this.horizontalCollision && !this.hasPath()) {
					this.isJumping = true;
				}

				if (this.random.nextFloat() < 0.8F && (inWater || inLava)) {
					this.isJumping = true;
				}
			} else {
				super.updateAI();
				this.pathToEntity = null;
			}
		}
	}

	public void sendAnim(Animation anim){
		((IEntity)this).bws$sendEntityAnim(anim);
	}
	public void sendDiffAnim(Animation anim, Animation anim2){
		((IEntity)this).bws$sendEntityDiffAnim(anim, anim2);
	}
	public void stopAnims(){
		((IEntity)this).bws$getAnimManager().stopAnimation();
	}

	public void playSound(String sound){
		this.playSound(sound, .45f);
	}
	public void playSound(String sound, float vol){
		if(this.world == null){return;}
		this.world.playSoundAtEntity(null, this, sound, vol, 1 + this.random.nextFloat()*0.2f);
	}
}
