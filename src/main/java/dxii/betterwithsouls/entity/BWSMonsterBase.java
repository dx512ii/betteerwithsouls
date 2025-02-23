package dxii.betterwithsouls.entity;

import com.mojang.nbt.tags.CompoundTag;
import dxii.betterwithsouls.BWSUtils;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.season.Seasons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BWSMonsterBase extends MobMonster {
	//general params
	public int attackStrength = 2;
	public float moveSpeedMul = 1;
	public int sightRadius = 32;
	public int xrayRadius = 3;
	public int maxSpawnedWinter = 8;
	public int maxSpawnedSummer = 2;
	public int maxSpawnedYearly = 4;
	public boolean obeyGameRules = true;


	/**
	 * @if_true: random roaming happens all the time, it will never stop until mob finds a target
	 */
	public boolean chaotic = false;
	/**
	 * @if_true: charges straight at the target, never randomly roams after attacking
	 */
	public boolean feral = false;
	/**
	 * @if_true: looks in random directions all the time, instead of randomly
	 */
	public boolean crazy = false;




	public BWSMonsterBase(@Nullable World world) {
		super(world);
		this.moveSpeed = 1.0F;
	}

	@Override
	public int getMaxHealth() {
		return 21;
	}

	@Override
	protected void updateAI() {
		if (this.world != null) {
//		if (false) {
			this.hasAttacked = this.isMovementCeased();
			float sightRadius = this.sightRadius*2;
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
				if ((!this.hasAttacked || this.feral) && this.pathToEntity == null && (this.chaotic || this.random.nextInt(80) == 0 || this.random.nextInt(80) == 0)) {
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
				}else if (this.crazy) {
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

	@Override
	public void onLivingUpdate() {
		float f = this.getBrightness(1.0F);
		if (f > 0.5F) {
			this.entityAge += 2;
		}

		super.onLivingUpdate();
	}

	@Override
	public void moveRelative(float f, float f1, float f2) {
		float f3 = MathHelper.sqrt_float(f * f + f1 * f1);
		if (!(f3 < 0.01F)) {
			if (f3 < 1.0F) {
				f3 = 1.0F;
			}

			f3 = f2 / f3;
			f *= f3;
			f1 *= f3;
			float sinYaw = MathHelper.sin(this.yRot * (float) Math.PI / 180.0F);
			float cosYaw = MathHelper.cos(this.yRot * (float) Math.PI / 180.0F);
			this.xd += (f * cosYaw - f1 * sinYaw)*moveSpeedMul;
			this.zd += (f1 * cosYaw + f * sinYaw)*moveSpeedMul;
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.obeyGameRules && !this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
			this.remove();
		}
	}

	@Override
	protected Entity findPlayerToAttack() {
		if(this.world == null){
			return null;
		}
		Player player = null;

		Player playerNear = this.world.getClosestPlayerToEntity(this, this.xrayRadius);
		if(playerNear != null &&  this.canEntityBeSeen(playerNear)){
			player = playerNear;
		}
		Player playerFar = this.world.getClosestPlayerToEntity(this, this.sightRadius);
		if(playerFar != null && this.canEntityBeSeen(playerFar) && BWSUtils.isEntityInFront(this, playerFar)){
			player = playerFar;
		}

		return player != null && player.getGamemode().areMobsHostile() ? player : null;
		//return player;
	}

	@Override
	public boolean canEntityBeSeen(Entity entity) {
		return this.world != null &&
			this.world.checkBlockCollisionBetweenPoints(
				Vec3.getTempVec3(this.x, this.y + (double)this.getHeadHeight(), this.z),
				Vec3.getTempVec3(entity.x, entity.y + (double)entity.getHeadHeight(), entity.z),
				false, true, false)
			== null;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		if (this.world.getSeasonManager().getCurrentSeason() == Seasons.OVERWORLD_SPRING) {
			return this.maxSpawnedSummer;
		} else {
			return this.world.getSeasonManager().getCurrentSeason() == Seasons.OVERWORLD_WINTER ? this.maxSpawnedWinter : this.maxSpawnedYearly;
		}
	}

	@Override
	public boolean hurt(Entity attacker, int i, DamageType type) {
		if (super.hurt(attacker, i, type)) {
			if (this.passenger != attacker && this.vehicle != attacker) {
				if (attacker != this) {
					this.target = attacker;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void attackEntity(@NotNull Entity entity, float distance) {
		if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
			this.attackTime = 20;
			entity.hurt(this, this.attackStrength, DamageType.COMBAT);
		}
	}

	@Override
	protected float getBlockPathWeight(int x, int y, int z) {
		return 0.5F - this.world.getLightBrightness(x, y, z);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
	}

	@Override
	public boolean canSpawnHere() {
		int blockX = MathHelper.floor(this.x);
		int blockY = MathHelper.floor(this.bb.minY);
		int blockZ = MathHelper.floor(this.z);
		if (this.world.getSavedLightValue(LightLayer.Block, blockX, blockY, blockZ) > 0) {
			return false;
		} else if (this.world.getSavedLightValue(LightLayer.Sky, blockX, blockY, blockZ) > this.random.nextInt(32)) {
			return false;
		} else {
			int blockLight = this.world.getBlockLightValue(blockX, blockY, blockZ);
			if (this.world.getCurrentWeather() != null && this.world.getCurrentWeather().doMobsSpawnInDaylight) {
				blockLight /= 2;
			}

			return blockLight <= 4 && super.canSpawnHere();
		}
	}

}
