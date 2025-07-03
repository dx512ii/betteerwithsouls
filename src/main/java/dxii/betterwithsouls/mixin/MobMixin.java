package dxii.betterwithsouls.mixin;


import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.entity.BWSMonsterPursuer;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.mixin.accessor.IEntityAccessor;
import dxii.betterwithsouls.mixin.accessor.IMobAccessor;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.BlockingModule;
import dxii.betterwithsouls.util.DamageInfo;
import dxii.betterwithsouls.util.DamageResistModule;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixin implements IMob {


	@Shadow
	protected float moveForward;
	@Shadow
	protected float moveStrafing;
	@Shadow
	protected float moveSpeed;
	@Unique
	public Mob thisObject = (Mob)(Object)this;

	@Unique
	public DamageResistModule dResists = new DamageResistModule();
	@Unique
	public BlockingModule blockInfo = new BlockingModule();

	@Unique
	public int parryTicks;
	@Unique
	public int stunTicks;

	@Override
	public void setMobResist(DamageResistModule newresist){
		this.dResists = newresist;
	}
	@Override
	public DamageResistModule getMobResist(){
		return this.dResists;
	}

	@Inject(
		method = "tick",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;tick()V")
	)
	public void mobTick(CallbackInfo ci){
		if(parryTicks > 0){
			parryTicks--;
		}
		if(this.blockInfo.blockingTicks > 0){
			this.blockInfo.blockingTicks--;
		}
	}
	//ill stick to using bws$receiveDamageInfo for my stuff,
	//for the sake of compat ill redirect every vanilla hurt function to mine

	//p.s

	@Inject(
		method = "hurt",
		at = @At(value = "HEAD"), cancellable = true)
	public void hurtRedirect(Entity attacker, int damage, DamageType dtype, CallbackInfoReturnable<Boolean> cir){
		DamageInfo dinfo = new DamageInfo();

		DamageType dtypenew = dtype;
		if (dtype == DamageType.COMBAT) {
			dtypenew = BWSDamageTypes.SLASH;
		}else if(dtype == DamageType.FIRE){
			dtypenew = BWSDamageTypes.FIRE;
		}else if(dtype == DamageType.BLAST){
			dtypenew = BWSDamageTypes.STRIKE;
		}

		dinfo.setIgnoresIframes(false);
		dinfo.setDmgType(dtypenew);
		dinfo.setDmg(damage);
		dinfo.setAttacker(attacker);
		dinfo.disableStun();

		this.receiveDamageInfo(dinfo);

		cir.setReturnValue(false);
		cir.cancel();
	}


	@Unique
	public void receiveDamageInfo(DamageInfo dinfo){

		Entity atker = dinfo.getAttacker();
		if(atker != null) {
			if(this.parryTicks > 0 && atker instanceof BWSMonsterPursuer){
				String snd = dinfo.getDmg() > thisObject.getMaxHealth() ? "parry2" : "parry";
				thisObject.world.playSoundAtEntity(null, thisObject, MOD_ID+":"+snd, 0.75f, 1);
				((BWSMonsterPursuer)atker).parried();

				return;
			}

			Vec3 dir = Vec3.getTempVec3(
				thisObject.x - atker.x,
				thisObject.y - atker.y,
				thisObject.z - atker.z);
			dir = dir.normalize();

			int pushResist = this.dResists.getPushResist();
			if(this.blockInfo != null && this.blockInfo.isBlocking()){
				pushResist += Math.max(this.blockInfo.def.getPushResist(), 0);
			}
			//dont even get pushed if push resist == -1 (means it has push immunity)
			if(!(this.dResists.getPushResist() == -1 || this.blockInfo.def.getPushResist() == -1)) {
				double coeff = 0.05;
				thisObject.push(
					dir.x * coeff * dinfo.getKnockback() * (dinfo.getDmg() + 2 - pushResist),
					dir.y * coeff * dinfo.getKnockback() * (dinfo.getDmg() + 2 - pushResist),
					dir.z * coeff * dinfo.getKnockback() * (dinfo.getDmg() + 2 - pushResist)
				);
			}
		}

		int baseDmg = dinfo.getDmg();

		float weak = this.dResists.getWeakMulAgainstEDamage(dinfo.getDmgType());
		float resist = this.dResists.getDefence(dinfo.getDmgType());
		if(thisObject instanceof Player){
			resist += ((Player)thisObject).inventory.getTotalProtectionAmount(dinfo.getDmgType());
		}

		boolean infront = false;
		if(dinfo.getAttacker() != null){
			infront = BWSUtils.isEntityInFrontWide(thisObject, dinfo.getAttacker());
		}

		if(this.blockInfo != null && this.blockInfo.isBlocking() && infront){
			resist += blockInfo.def.getDefence(dinfo.getDmgType());
			if(resist > 0) {
				thisObject.world.playSoundAtEntity(null, thisObject, MOD_ID + ":" + blockInfo.blockSound, 0.45f, 1.2F / (BWSUtils.rand.nextFloat() * 0.4F + 1.0F));
			}
		}
		baseDmg *= (int)(weak != 0 ? weak : 1);
		/*dark souls defence formula that didnt work
		When Atk < Def, Damage = 0.4*(Atk^3/ Def^2) - 0.09*(Atk^2/ Def)+0.1*Atk
		When Atk >= Def, Damage = Atk - 0.79* Def*e^(-0.27* Def/Atk)
		 */

		baseDmg = BWSUtils.dmgAgainstResist(baseDmg, (int) resist);
		if(!(thisObject instanceof Player) && baseDmg < 0){
			baseDmg = 1;
			hurtFlinch(dinfo.getAttacker(), baseDmg, dinfo.ignoresIframes());
		}
		if((thisObject instanceof Player) && baseDmg == 1){
			hurtFlinch(dinfo.getAttacker(), baseDmg, dinfo.ignoresIframes());
		}

		thisObject.setHealthRaw(thisObject.getHealth() - baseDmg);
		if(baseDmg > 0 || dinfo.getDmgType() == DamageType.FIRE || dinfo.getDmgType() == DamageType.FALL || dinfo.getDmgType() == DamageType.DROWN) {
			if(thisObject instanceof BWSMonsterPursuer && dinfo.stuns()){
				((BWSMonsterPursuer)thisObject).stun(dinfo.getAttacker());//f you hitscan clicker swords!!!
			}
			double motionX = 0;
			double motionY = 0.02;
			double motionZ = 0;
			thisObject.world.spawnParticle("damage_text", thisObject.x, thisObject.y+thisObject.getHeadHeight(), thisObject.z, motionX, motionY, motionZ, baseDmg);
			if(baseDmg > 1){
				hurtFlinch(dinfo.getAttacker(), baseDmg, dinfo.ignoresIframes());
			}
		}else{
			thisObject.hurtTime = 5;
		}

	}


	@Unique
	public void hurtFlinch(Entity attacker, int damage, boolean iframes){
		if (thisObject.world != null && !thisObject.world.isClientSide) {
			((IMobAccessor)thisObject).setEntityAge(0);

			thisObject.walkAnimSpeed = 1.5F;
			boolean flag = true;
			if ((float)thisObject.heartsFlashTime > (float)thisObject.heartsHalvesLife / 2.0F) {
				flag = false;
			} else {
				thisObject.prevHealth = thisObject.getHealth();
				thisObject.prevBonusHealth = thisObject.bonusHealth;
				thisObject.hurtTime = thisObject.maxHurtTime = 10;
				if(iframes) {
					thisObject.heartsFlashTime = thisObject.heartsHalvesLife;
				}
			}

			thisObject.attackedAtYaw = 0.0F;
			if (flag) {
				((IEntityAccessor)thisObject).markHurt_();
				if (attacker == null) {
					thisObject.attackedAtYaw = (float)((int)(Math.random() * 2.0) * 180);
				}

				thisObject.world.sendTrackedEntityStatusUpdatePacket(thisObject, (byte)2, thisObject.attackedAtYaw);
			}

			if (thisObject.getHealth() <= 0) {
				if (flag) {
					thisObject.playDeathSound();
				}
				thisObject.onDeath(attacker);
				} else if (flag && damage > 0) {
					thisObject.playHurtSound();
				}
		}

	}

	@Override
	public void parry(int ticks){
		parryTicks+= ticks;
	}

	@Override
	public void raiseGuard(DamageResistModule def, String blockSound) {
		this.blockInfo.def = def;
		this.blockInfo.blockSound = blockSound;
		this.blockInfo.blockingTicks = 2;
	}

	@Inject(
		method = "damageEntity",
		at = @At(value = "HEAD"), cancellable = true)
	public void nuhUh(int i, DamageType damageType, CallbackInfo ci){
		ci.cancel();
	}
	@Inject(
		method = "knockBack",
		at = @At(value = "HEAD"), cancellable = true)
	public void nuhUh2(Entity entity, int i, double d, double d1, CallbackInfo ci){
		ci.cancel();
	}




}
