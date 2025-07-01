package dxii.betterwithsouls.interfaces;

import dxii.betterwithsouls.util.animation.AnimManager;
import dxii.betterwithsouls.util.animation.Animation;

public interface IEntity {
	void bws$sendEntityAnim(Animation anim);
	void bws$sendEntityDiffAnim(Animation anim, Animation anim2);
	AnimManager bws$getAnimManager();
	AnimManager bws$getVMManager();

	void setCanBeParried(boolean yea);
	boolean canBeParried();
}
