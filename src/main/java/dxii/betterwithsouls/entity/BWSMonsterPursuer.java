package dxii.betterwithsouls.entity;

import com.mojang.nbt.tags.CompoundTag;
import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.ai.EAiStates;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.animation.Animation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.season.Seasons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

public class BWSMonsterPursuer extends BWSMob {
	//general params
	public int attackStrength = 2;
	public float attackDistance = 3;
	public float hitDistance = 2;
	public DamageType attackDamageType = BWSDamageTypes.SLASH;

	public int swingTiming = 14;//time at which swing sound will be played
	public int attackTiming = 10;//time at which mob will attempt to damage target
	protected Animation idleAnim;
	protected Animation attackAnim;
	protected Animation stunAnim;
	protected Animation parriedAnim;


	public float moveSpeedMul = 1;
	public int sightRadius = 32;
	public int xrayRadius = 3;
	public int maxSpawnedWinter = 8;
	public int maxSpawnedSummer = 2;
	public int maxSpawnedYearly = 4;
	public boolean obeyGameRules = true;

	public boolean canBeParried = false;
	public int parriedTime = 35;

	/**
	 * @if_true: random roaming happens all the time, it will never stop until mob finds a target
	 */
	public boolean chaotic = false;
	/**
	 * @if_true: looks in random directions all the time, instead of randomly
	 */
	public boolean crazy = false;

	public BWSMonsterPursuer(@Nullable World world) {
		super(world);
		this.moveSpeed = 1.0F;
	}

	@Override
	public int getMaxHealth() {
		return 21;
	}

	public double distanceToTargetSqr(){
		if(this.target == null) {
			return 0;
		}
		return this.target.distanceToSqr(this.x, this.y, this.z);
	}
	public boolean canAttackEntity(Entity entity){
		return this.attackDistance * this.attackDistance >= entity.distanceToSqr(this.x, this.y+this.heightOffset, this.z) && this.canEntityBeSeen(entity) && BWSUtils.isEntityInFront(this, entity);
	}
	public boolean canHitEntity(Entity entity){
		return BWSUtils.canReachEntity(this, entity, this.hitDistance) && this.canEntityBeSeen(entity) && BWSUtils.isEntityInFrontWide(this, entity);
	}

	/*
	STATE MACHINE YAAAAY
	 */

	private EAiStates currentState = EAiStates.IDLE;
	private int currentStateDuration = -1;
	public void updateStates(){//basic pursuer ai
		switch(currentState){
			case IDLE: //roaming random paths, looking at random angles
				if ( (this.chaotic || (this.random.nextInt(80) == 0) ) ) {
					this.roamRandomPath();
				}
				if (this.crazy || this.random.nextInt(80) == 0) {
					this.randomYawVelocity = (this.random.nextFloat() - 0.5F) * 70.0F;

					this.yRot = this.yRot + this.randomYawVelocity;
					this.xRot = this.defaultPitch;
				}

				this.yawVelocityProcess();
				this.walkCurrentPath();

				this.target = this.findPlayerToAttack();
				if (this.target != null) {
					this.changeState(EAiStates.PURSUE, -1);
				}

				break;
			case PURSUE: //pursuing target
				if(this.target == null || !this.target.isAlive()){
					this.changeState(EAiStates.IDLE, -1);
					return;
				}

				this.pathToEntity = this.world.getPathToEntity(this, this.target, sightRadius);
				this.walkCurrentPath();
				this.lookAtTarget();

				if (this.canAttackEntity(this.target)) {
					this.changeState(EAiStates.ATTACK, this.attackAnim.getDuration() );
				}

				break;
			case ATTACK: //attack animation on enter, no walking, might push at some point
				this.immobilize();

				if(this.target == null || !this.target.isAlive()){
					this.changeState(EAiStates.IDLE, -1);
					return;
				}
				if(this.currentStateDuration <= 0){
					this.changeState(EAiStates.PURSUE, -1);
				}
				if(this.currentStateDuration == this.attackTiming){
					this.attackEntity(this.target, this.attackDistance);
				}
				this.lookAtTarget();

				if(this.currentStateDuration == this.swingTiming){
					playSound(MOD_ID+":"+"sword.swing");
					if(distanceToTargetSqr() >= this.hitDistance*this.hitDistance) {
						Vec3 view = BWSUtils.getEntityViewVec(this);
						this.xd += view.x * 0.5;
						this.yd += .1;
						this.zd += view.z * 0.5;
					}
				}
				break;
			case PARRIED://got parried, coping
			case STUN://got hit, coping
				this.immobilize();
				if(this.currentStateDuration <= 0){
					this.changeState(EAiStates.PURSUE, -1);
				}
				break;
		}
	}

	private boolean firstTick = true;
	@Override
	protected void updateAI() {
		if(this.firstTick){
			this.firstTick = false;
			this.init();
		}
		if (this.world != null) {
			if(this.currentStateDuration != 0){
				this.currentStateDuration--;
			}
			this.updateStates();
			super.updateAI();
		}
	}
	public void init(){
		this.stateEnter(currentState);
	}


	public void changeState(EAiStates newstate, int duration){
		this.stateExit(this.currentState);
		this.currentState = newstate;
		this.stateEnter(this.currentState);
		this.currentStateDuration = duration;
	}
	protected void stateEnter(EAiStates newstate){
		switch(newstate){
			case IDLE:
				this.sendAnim(this.idleAnim);
				break;
			case ATTACK:
				this.lookAtTarget();
				this.sendAnim(this.attackAnim);
				break;
			case PURSUE:
				this.sendAnim(this.idleAnim);
				break;
			case PARRIED:
				this.sendAnim(this.parriedAnim);
				break;
			case STUN:
				this.sendAnim(this.stunAnim);
				break;
		}
	}
	protected void stateExit(EAiStates newstate){
		switch(newstate){
			case IDLE:

				break;
			case ATTACK:

				break;
			case PURSUE:

				break;
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
	public boolean hurt(Entity attacker, int i, DamageType type) {
		if (super.hurt(attacker, i, type)) {
			this.changeState(EAiStates.STUN, this.stunAnim.getDuration());
			System.out.println("stun!!");
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
		if (this.isAlive() && this.canHitEntity(entity)) {
			entity.hurt(this, this.attackStrength, this.attackDamageType);
		}
	}
	public void stun(Entity attacker){
		this.target = attacker;

		if(this.getResists().getPoise() < 0){
			return;
		}
		if(this.stunAnim == null){
			System.out.println("PursuerAI: attempt to stun with null stun animation!!");

			return;
		}
		this.changeState(EAiStates.STUN, this.stunAnim.getDuration());
	}
	public void parried(){
		if(!this.canBeParried){
			return;
		}
		if(this.parriedAnim == null){
			System.out.println("PursuerAI: attempt to parry with null parried animation!!");

			return;
		}
		this.changeState(EAiStates.PARRIED, this.parriedAnim.getDuration());

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
	public void tick() {
		super.tick();
		if (this.obeyGameRules && !this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
			this.remove();
		}
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
