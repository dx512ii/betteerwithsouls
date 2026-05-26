package dxii.bws.modules;

import dxii.bws.BWS;

public class AttackModule {

	public float nextAttack = 0;
	public float nextAttackTiming = 0;

	public void performAttack(float attackDelay, float timedDelay){
		nextAttack = BWS.curtime() + attackDelay;

		if(timedDelay > 0) nextAttackTiming = BWS.curtime() + timedDelay;
	}

	public boolean shouldAttackTimed(){
		boolean should = BWS.curtime() >= nextAttackTiming && nextAttackTiming > 0;
		if(should)
			nextAttackTiming = -1;

		return should;
	}

	public boolean canAttack(){
		return BWS.curtime() >= nextAttack;
	}
}
