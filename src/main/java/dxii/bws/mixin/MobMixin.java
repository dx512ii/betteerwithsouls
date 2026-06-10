package dxii.bws.mixin;


import dxii.bws.BWS;
import dxii.bws.entity.DamageInfo;
import dxii.bws.entity.DamageResistModule;
import dxii.bws.entity.DamageTypeBWS;
import dxii.bws.entity.IMobExtra;
import dxii.bws.interfaces.IEntityBWS;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = Mob.class, remap = false)
public class MobMixin implements IMobExtra {
	@Unique
	public Mob self = (Mob)(Object)this;

	@Unique
	public DamageResistModule resistModule = new DamageResistModule();
	@Unique
	public DamageResistModule blockResist;
	@Unique
	public float blockElapse = 0;
	@Unique
	public float blockElapseLast = 0;


	@Unique
	private static Map<DamageType, DamageTypeBWS> dtypeRedirects = new HashMap<>();

	@Inject(
		method = "hurt",
		at = @At(value = "HEAD"), cancellable = true)
	public void hurtRedirect(Entity attacker, int damage, DamageType dtypeOrig, CallbackInfoReturnable<Boolean> cir){
		DamageTypeBWS dtype = DamageTypeBWS.GENERIC;
		if(dtypeRedirects.containsKey(dtypeOrig)){
			dtype = dtypeRedirects.get(dtypeOrig);
		}

		DamageInfo dinfo = new DamageInfo()
			.setDamage(damage)
			.setAttacker(attacker)
			.setDamageType(dtype);

		if(attacker !=  null){
			dinfo.setDamagePos(new Vector3d(attacker.x, attacker.y, attacker.z));
		}

		this.takeDamageInfo(dinfo);

		cir.cancel();
	}

	@Inject(
		method = "baseTick",
		at = @At(value = "HEAD"))
	public void tickExtra(CallbackInfo ci){

	}

	@Override
	public void takeDamageInfo(DamageInfo dinfo) {
		if(self instanceof Player && ((Player)self).gamemode.hasInvulnerablePlayer()){
			return;
		}

		int dmg = BWS.CalculateDamageForMob(self, dinfo, resistModule, this.getBlocking());
		setHealth(self.getHealth() - dmg);

		hurtFlinch(dinfo.getAttacker(), dmg, !(self instanceof IEntityBWS));

	}

	@Override
	public void setResistAgainst(DamageTypeBWS dtype, float def) {
		resistModule.setResist(dtype, def);
	}

	@Override
	public float getResistAgainst(DamageTypeBWS dtype) {
		return resistModule.getResist(dtype);
	}

	@Override
	public void setMaxPoise(int newPoise) {
		this.resistModule.setMaxPoise(newPoise);
	}

	@Override
	public void setPoise(int newPoise) {
		this.resistModule.setPoise(newPoise);
	}

	@Override
	public void raiseBlock(DamageResistModule resists, float duration) {
		this.raiseBlock(resists, duration, null);
	}

	@Override
	public void raiseBlock(DamageResistModule resists, float duration, @Nullable String sound) {
		this.blockResist = resists;
		this.blockResist.blockSound = sound;
		this.blockElapse = BWS.curtime() + duration;
	}

	@Override
	public DamageResistModule getBlocking() {
		if(BWS.curtime() <= this.blockElapse){
			return this.blockResist;
		}

		return null;
	}





	@Shadow
	protected int entityAge;
	@Shadow
	protected int lastDamage;

	public void hurtFlinch(Entity attacker, int damage, boolean doKnockback){
		if (self.world.isClientSide) {
			return;
		}
		if (self.getHealth() <= 0) {
			return;
		}

		this.entityAge = 0;
		self.walkAnimSpeed = 1.5F;
		self.prevHealth = self.getHealth();
		self.prevBonusHealth = self.bonusHealth;
		self.heartsFlashTime = self.heartsHalvesLife;

		self.attackedAtYaw = 0.0F;
		self.hurtMarked = true;
		if (attacker == null) {
			self.attackedAtYaw = (float)((int)(Math.random() * (double)2.0F) * 180);
		} else {
			double d = attacker.x - self.x;

			double d1;
			for(d1 = attacker.z - self.z; d * d + d1 * d1 < 1.0E-4; d1 = (Math.random() - Math.random()) * 0.01) {
				d = (Math.random() - Math.random()) * 0.01;
			}

			self.attackedAtYaw = (float)(Math.atan2(d1, d) * (double)180.0F / Math.PI) - self.yRot;
			if(doKnockback){
				self.knockBack(attacker, damage, d, d1);
			}
		}

		self.world.sendTrackedEntityStatusUpdatePacket(self, (byte)2, self.attackedAtYaw);

		if (self.getHealth() <= 0) {
			self.playDeathSound();

			self.onDeath(attacker);
		} else if (damage > 0) {
			self.playHurtSound();
		}
	}

	public boolean hurtLegacy(Entity attacker, int damage, DamageType type) {
		if (self.world.isClientSide) {
			return false;
		}
		if (self.getHealth() <= 0) {
			return false;
		}

		this.entityAge = 0;

		self.walkAnimSpeed = 1.5F;
		boolean flag = true;
		if ((float)self.heartsFlashTime > (float)self.heartsHalvesLife / 2.0F) {
			if (damage <= this.lastDamage) {
				return false;
			}

			setHealth(damage - this.lastDamage);
			this.lastDamage = damage;
			flag = false;
		} else {
			this.lastDamage = damage;
			self.prevHealth = self.getHealth();
			self.prevBonusHealth = self.bonusHealth;
			self.heartsFlashTime = self.heartsHalvesLife;
			setHealth(damage);
			if (damage > 0) {
				self.hurtTime = self.maxHurtTime = 10;
			}
		}

		self.attackedAtYaw = 0.0F;
		if (flag) {
			self.hurtMarked = true;
			if (attacker == null) {
				self.attackedAtYaw = (float)((int)(Math.random() * (double)2.0F) * 180);
			} else {
				double d = attacker.x - self.x;

				double d1;
				for(d1 = attacker.z - self.z; d * d + d1 * d1 < 1.0E-4; d1 = (Math.random() - Math.random()) * 0.01) {
					d = (Math.random() - Math.random()) * 0.01;
				}

				self.attackedAtYaw = (float)(Math.atan2(d1, d) * (double)180.0F / Math.PI) - self.yRot;
				self.knockBack(attacker, damage, d, d1);
			}

			self.world.sendTrackedEntityStatusUpdatePacket(self, (byte)2, self.attackedAtYaw);
		}

		if (self.getHealth() <= 0) {
			if (flag) {
				self.playDeathSound();
			}

			self.onDeath(attacker);
		} else if (flag && damage > 0) {
			self.playHurtSound();
		}

		return true;


	}

	@Unique
	public void setHealth(int health){
		self.setHealthRaw(health);
	}


	static{
		dtypeRedirects.put(DamageType.COMBAT, DamageTypeBWS.PHYS_SLASH);
		dtypeRedirects.put(DamageType.BLAST, DamageTypeBWS.PHYS_STRIKE);
		dtypeRedirects.put(DamageType.FALL, DamageTypeBWS.GENERIC);
		dtypeRedirects.put(DamageType.FIRE, DamageTypeBWS.ELEMENT_FIRE);
		dtypeRedirects.put(DamageType.DROWN, DamageTypeBWS.GENERIC);
		dtypeRedirects.put(DamageType.GENERIC, DamageTypeBWS.GENERIC);
	}
}
