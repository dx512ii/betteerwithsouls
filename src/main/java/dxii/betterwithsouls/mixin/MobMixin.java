package dxii.betterwithsouls.mixin;


import dxii.betterwithsouls.enums.EHoldType;
import dxii.betterwithsouls.enums.EMobAnim;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.mixin.accessor.IMobAccessor;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.DamageInfo;
import dxii.betterwithsouls.util.DamageResistModule;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Mob.class, remap = false)
public class MobMixin implements IMob {

	@Unique
	public EMobAnim currentAnim;
	@Unique
	public EHoldType holdType;
	@Unique
	public Mob thisObject = (Mob)(Object)this;

	@Unique
	public DamageResistModule dResists = new DamageResistModule();

	@Override
	public void bws$setMobResist(DamageResistModule newresist){
		this.dResists = newresist;
	}
	@Override
	public DamageResistModule bws$getMobResist(){
		return this.dResists;
	}

	@Override
	public void bws$sendMobAnim(EMobAnim anim) {
		this.currentAnim = anim;
	}

	@Override
	public EMobAnim bws$getMobAnim() {
		return this.currentAnim;
	}

	@Override
	public void bws$setHoldType(EHoldType type) {
		this.holdType = type;
	}

	@Override
	public EHoldType bws$getHoldType() {
		return this.holdType;
	}
//	@Override
//	public boolean bws$hurt(Entity attacker, int damage, DamageType type){
//		return thisObject.hurt(attacker,damage, type);
//	}



//	@Inject( --knockback inject
//		method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z",
//		at = @At(value = "INVOKE", target = "net/minecraft/core/entity/Mob.knockBack (Lnet/minecraft/core/entity/Entity;IDD)V" )
//	)
//	public void hurtInject(Entity attacker, int damage, DamageType dtype, CallbackInfoReturnable<Boolean> cir) {
//
//	}

	//ill stick to using bws$receiveDamageInfo for my stuff,
	//for the sake of compat ill redirect every vanilla hurt function to mine
	@Inject(
		method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/core/entity/Mob.damageEntity (ILnet/minecraft/core/util/helper/DamageType;)V" )
	)
	public void damageEntityInject(Entity attacker, int damage, DamageType dtype, CallbackInfoReturnable<Boolean> cir){
		DamageInfo dinfo = new DamageInfo();

		DamageType dtypenew = dtype;
		if (dtype == DamageType.COMBAT) {
			dtypenew = BWSDamageTypes.SLASH;
		}else if(dtype == DamageType.FIRE){
			dtypenew = BWSDamageTypes.FIRE;
		}

		dinfo.setDmgType(dtypenew);
		dinfo.setDmg(damage);
		dinfo.setAttacker(attacker);

		this.bws$receiveDamageInfo(dinfo);
	}

	@Redirect(
		method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z",
		at = @At(value = "FIELD", target = "net/minecraft/core/entity/Mob.heartsHalvesLife : I", ordinal = 1)
	)
	public int stopFlashingUDumass(Mob mob){
		return ((IMobAccessor)mob).lastDamage() > 2 ? mob.heartsHalvesLife : 0;
	}

	@Unique
	public void bws$receiveDamageInfo(DamageInfo dinfo){

		Entity atker = dinfo.getAttacker();
		if(atker != null) {
			Vec3 dir = Vec3.getPermanentVec3(
				thisObject.x - atker.x,
				thisObject.y - atker.y,
				thisObject.z - atker.z);
			dir = dir.normalize();

			double coeff = 0.1;

			System.out.println("complete: "+coeff*dinfo.getKnockback() * (dinfo.getDmg() + 1 - this.dResists.getPushResist()));
			System.out.println("dmg - resist: "+(dinfo.getDmg() + 1 - this.dResists.getPushResist()));

			thisObject.push(
				dir.x * coeff*dinfo.getKnockback() * (dinfo.getDmg() + 2 - this.dResists.getPushResist()),
				dir.y * coeff*dinfo.getKnockback() * (dinfo.getDmg() + 2 - this.dResists.getPushResist()),
				dir.z * coeff*dinfo.getKnockback() * (dinfo.getDmg() + 2 - this.dResists.getPushResist())
			);
		}else{
			thisObject.push(0, -1, 0);
		}

		thisObject.setHealthRaw(thisObject.getHealth() - dinfo.getDmg());
	}



	/**
	 * @author dxii
	 * @reason O=p
	 */
	@Overwrite
	public void damageEntity(int dmg, DamageType damageType) {
		//nah nah nah~
	}
	/**
	 * @author dxii
	 * @reason O=p
	 */
	@Overwrite
	public void knockBack(Entity entity, int i, double d, double d1) {
		//nah nah nah~
	}


}
