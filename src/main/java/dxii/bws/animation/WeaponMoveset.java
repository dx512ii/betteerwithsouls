package dxii.bws.animation;

public class WeaponMoveset {

	public static final WeaponMoveset CLUB = new WeaponMoveset(
		null, AnimationsLibrary_humanoid.CLUB_ATTACK1, AnimationsLibrary_humanoid.CLUB_ATTACK2, AnimationsLibrary_humanoid.BLOCK_GENERIC
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
