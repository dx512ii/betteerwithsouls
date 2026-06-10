package dxii.bws.animation;


import static dxii.bws.animation.AnimationsLibrary_humanoid.*;

public class WeaponMoveset {

	public static final WeaponMoveset HAMMER = new WeaponMoveset(
		null,

		HAMMER_ATTACK1,
		BLOCK_GENERIC,
		HAMMER_ATTACK_OVERHEAD
	);
	public static final WeaponMoveset GREATSWORD = new WeaponMoveset(
		GREATSWORD_IDLE,

		GREATSWORD_ATTACK1,
		BLOCK_GENERIC,
		GREATSWORD_ATTACK2
	);
	public static final WeaponMoveset GREATHAMMER = new WeaponMoveset(
		GREATSWORD_IDLE,

		GREATSWORD_ATTACK1,
		BLOCK_GENERIC,
		HAMMER_ATTACK_OVERHEAD
	);



	private final Animation animIdle;
	private final Animation animAttack1;
	private final Animation animAttack2;
	private final Animation animAttack3;

	public WeaponMoveset(Animation animIdle, Animation animAttack1, Animation animAttack2, Animation animAttack3){
		this.animIdle = animIdle;
		this.animAttack1 = animAttack1;
		this.animAttack2 = animAttack2;
		this.animAttack3 = animAttack3;
	}


	public Animation idle(){
		return animIdle;
	}
	public Animation attack1(){
		return animAttack1;
	}
	public Animation attack2(){
		return animAttack2;
	}
	public Animation attack3(){
		return animAttack3;
	}
}
