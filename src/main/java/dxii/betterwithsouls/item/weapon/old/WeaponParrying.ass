package dxii.betterwithsouls.item.weapon;

import dxii.betterwithsouls.anims.PlayerAnimations;
import dxii.betterwithsouls.anims.ViewModelAnimations;
import dxii.betterwithsouls.enums.EReinforcementType;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.animation.Animation;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

//attack3 - parrying
public class WeaponParrying extends ItemWeaponMelee {
	public WeaponParrying(WeaponMaterial mat, String name, int id) {
		super(mat, name, id);

		this.reinforcementType = EReinforcementType.NORMAL;

		this.range = 2f;
		this.atkDelay1 = 10;
		this.atkDelay2 = 2;
		this.atkDelay3 = 20;

		this.atkTiming1 = 4;
		this.atkTiming2 = 0;
		this.atkTiming3 = 5;

		this.atk1hold = false;
		this.atk2hold = true;
		this.atk3hold = false;
	}



	//ANIMATIONS
	public float getModelOffsetX(){
		return 0;
	}
	public float getModelOffsetY(){
		return .1f;
	}


	public Animation getPlayerIdleAnimation(){
		return PlayerAnimations.GREATSWORD_idle;
	}
	public Animation getIdleAnimation(){
		return null;
	}
	public Animation getPlayerAttackAnimation(int type){
		if(type == 0) {
			return PlayerAnimations.GREATSWORD_slash;
		}else{
			return PlayerAnimations.GREATSWORD_thrust;
		}
	}
	public Animation getAttackAnimation(int type){
		if(type == 0) {
			return ViewModelAnimations.GREATSWORD_slash;
		}else{
			return ViewModelAnimations.GREATSWORD_thrust;
		}
	}

	public Animation getParryAnimation() {
		return super.getParryAnimation();
	}
	public Animation getPlayerParryAnimation(){
		return super.getPlayerParryAnimation();
	}

	public Animation getBlockAnimation() {
		return ViewModelAnimations.GENERIC_block;
	}
	public Animation getPlayerBlockAnimation(){
		return PlayerAnimations.GREATSWORD_block;
	}

	@Override
	public void holster(ItemStack itemstack, World world, Player entityplayer){
		super.holster(itemstack, world, entityplayer);
	}

	//primary
	@Override
	public void attack1(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		this.dinfo1.setDmgType(BWSDamageTypes.SLASH);
		attackDefault(0, 1, 1, -.4f, itemstack, world, entityplayer, timed);
	}

	//secondary (also hold release)
	@Override
	public void attack2(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		sendPlayerAnim(entityplayer, getPlayerIdleAnimation());
		switchBlocking(entityplayer, false);
	}
	//secondary hold
	@Override
	public void hold2start(ItemStack itemstack, World world, Player entityplayer){
		switchBlocking(entityplayer, true);
	}

	@Override
	public void hold2(ItemStack itemstack, World world, Player entityplayer, int totalticks) {
		sendVMAnim(entityplayer, getBlockAnimation());
		sendPlayerAnim(entityplayer, getPlayerBlockAnimation());
		sendPlayerNextAnim(entityplayer, getPlayerIdleAnimation());

		entityplayer.swingItem();
	}

	//parry
	@Override
	public void attack3(ItemStack itemstack, World world, Player player, boolean timed){
		if(!timed){
			this.sendVMAnim(player, getParryAnimation());
			this.sendPlayerAnim(player, getPlayerParryAnimation());
			parry(player, 3);
		}
	}

}
