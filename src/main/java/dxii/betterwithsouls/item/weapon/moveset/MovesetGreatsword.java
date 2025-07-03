package dxii.betterwithsouls.item.weapon.moveset;

import dxii.betterwithsouls.anims.PlayerAnimations;
import dxii.betterwithsouls.anims.ViewModelAnimations;
import dxii.betterwithsouls.util.animation.Animation;

public class MovesetGreatsword extends AnimationMoveset {

	public Animation getPlayerIdleAnimation(){
		return PlayerAnimations.GREATSWORD_idle;
	}
	public Animation getPlayerAttackAnimation(int type){
		return PlayerAnimations.GREATSWORD_slash;
	}
	public Animation getAttackAnimation(int type){
		return ViewModelAnimations.GREATSWORD_slash;
	}
	public Animation getAttack2Animation() {
		return ViewModelAnimations.GENERIC_block;
	}
	public Animation getPlayerAttack2Animation(){
		return PlayerAnimations.GREATSWORD_block;
	}
	public Animation getAttack3Animation() {
		return ViewModelAnimations.GREATSWORD_thrust;
	}
	public Animation getPlayerAttack3Animation(){
		return PlayerAnimations.GREATSWORD_thrust;
	}
}
