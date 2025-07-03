package dxii.betterwithsouls.item.weapon.moveset;

import dxii.betterwithsouls.anims.PlayerAnimations;
import dxii.betterwithsouls.anims.ViewModelAnimations;
import dxii.betterwithsouls.util.animation.Animation;

public class MovesetShortsword extends AnimationMoveset {

	public Animation getPlayerAttackAnimation(int type){
		animVariant = !animVariant;
		return animVariant ? PlayerAnimations.SHORTSWORD_attack1 : PlayerAnimations.SHORTSWORD_attack1_2;
	}
	public Animation getAttackAnimation(int type){
		return animVariant ? ViewModelAnimations.SHORTSWORD_SWING1 : ViewModelAnimations.SHORTSWORD_SWING2;
	}
	public Animation getAttack2Animation() {
		return ViewModelAnimations.GENERIC_block;
	}
	public Animation getPlayerAttack2Animation(){
		return PlayerAnimations.SHORTSWORD_block;
	}
	public Animation getAttack3Animation() {
		return ViewModelAnimations.SHORTSWORD_PARRY;
	}
	public Animation getPlayerAttack3Animation(){
		return PlayerAnimations.SHORTSWORD_parry;
	}
}
