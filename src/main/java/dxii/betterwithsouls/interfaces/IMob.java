package dxii.betterwithsouls.interfaces;

import dxii.betterwithsouls.enums.EMobAnim;
import dxii.betterwithsouls.util.DamageInfo;
import dxii.betterwithsouls.util.DamageResistModule;
import dxii.betterwithsouls.util.animation.AnimManager;
import dxii.betterwithsouls.util.animation.Animation;

public interface IMob {


	void bws$setMobResist(DamageResistModule newresist);
	DamageResistModule bws$getMobResist();

	void bws$receiveDamageInfo(DamageInfo dinfo);


//
//	boolean bws$hurt(Entity attacker, int damage, DamageType type);
}
