package dxii.betterwithsouls.item.weapon.moveset;

import dxii.betterwithsouls.anims.PlayerAnimations;
import dxii.betterwithsouls.anims.ViewModelAnimations;
import dxii.betterwithsouls.util.animation.Animation;

public abstract class AnimationMoveset {

	public AnimationMoveset(){

	}
	public Animation getPlayerIdleAnimation(){
		return null;
	}
	public Animation getIdleAnimation(){
		return null;
	}

	protected boolean animVariant;
	public Animation getPlayerAttackAnimation(int type){
		return PlayerAnimations.NULL_idle;
	}
	public Animation getAttackAnimation(int type){
		return ViewModelAnimations.NULL;
	}

	public Animation getAttack3Animation() {
		return ViewModelAnimations.NULL;
	}
	public Animation getPlayerAttack3Animation(){
		return PlayerAnimations.NULL_idle;
	}

	public Animation getAttack2Animation() {
		return ViewModelAnimations.NULL;
	}
	public Animation getPlayerAttack2Animation(){
		return PlayerAnimations.NULL_idle;
	}
}
