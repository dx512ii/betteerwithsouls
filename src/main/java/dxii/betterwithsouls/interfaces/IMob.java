package dxii.betterwithsouls.interfaces;

import dxii.betterwithsouls.util.DamageInfo;
import dxii.betterwithsouls.util.DamageResistModule;

public interface IMob {


	void setMobResist(DamageResistModule newresist);
	DamageResistModule getMobResist();
	void parry(int ticks);
	void stun(int ticks);
	void getParriedIdiot();
	void startBlocking(DamageResistModule def, String blockSound);
	void stopBlocking();
	void receiveDamageInfo(DamageInfo dinfo);


//
//	boolean bws$hurt(Entity attacker, int damage, DamageType type);
}
