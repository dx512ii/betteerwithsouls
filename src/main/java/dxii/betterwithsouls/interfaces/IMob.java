package dxii.betterwithsouls.interfaces;

import dxii.betterwithsouls.enums.EHoldType;
import dxii.betterwithsouls.enums.EMobAnim;
import dxii.betterwithsouls.util.DamageInfo;
import dxii.betterwithsouls.util.DamageResistModule;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;

public interface IMob {
	void bws$sendMobAnim(EMobAnim anim);
	EMobAnim bws$getMobAnim();
	void bws$setHoldType(EHoldType type);
	EHoldType bws$getHoldType();

	void bws$setMobResist(DamageResistModule newresist);
	DamageResistModule bws$getMobResist();

	void bws$receiveDamageInfo(DamageInfo dinfo);
//
//	boolean bws$hurt(Entity attacker, int damage, DamageType type);
}
