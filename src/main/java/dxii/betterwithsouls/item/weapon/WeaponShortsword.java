package dxii.betterwithsouls.item.weapon;

import dxii.betterwithsouls.enums.EMobAnim;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.interfaces.IPlayer;
import dxii.betterwithsouls.item.ItemWeapon;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.world.World;

public class WeaponShortsword extends ItemWeapon {
	public WeaponShortsword(ToolMaterial mat, String name, String namespaceId, int id) {
		super(name, namespaceId, id);

		this.setMaxDamage(mat.getDurability());

		this.atkDelay1 = 6;
		this.atkDelay2 = 5;
		this.atkDelay3 = 3;

		this.atkTiming1 = 2;
		this.atkTiming2 = 3;
		this.atkTiming3 = 2;

		this.atk1hold = false;
		this.atk2hold = true;
		this.atk3hold = false;
	}

	//primary
	@Override
	public void attack1callback(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		this.atkDelay1 = 10;
		if(!timed){
			//System.out.println("attack1 timed!");
			entityplayer.swingItem();
			EMobAnim anim = itemRand.nextBoolean() ? EMobAnim.ATTACK1 : EMobAnim.ATTACK2;
			((IMob) entityplayer).bws$sendMobAnim(EMobAnim.ATTACK1);
			((IPlayer) entityplayer).bws$setSwingSpeed(1.0f);

		}else {
			((IPlayer) entityplayer).bws$setSwingSpeed(0.75f);
			//System.out.println("attack1");
		}
	}

	//secondary (also hold release)
	@Override
	public void attack2callback(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(timed) {
			//((IMob) entityplayer).bws$sendMobAnim(EMobAnim.ATTACK1);
		}else{

		}
	}
	//secondary hold
	@Override
	public void hold2start(ItemStack itemstack, World world, Player entityplayer){
		//((IMob) entityplayer).bws$sendMobAnim(EMobAnim.BLOCK);
		System.out.println("holdd!!");
	}


	//parry
	@Override
	public void attack3callback(ItemStack itemstack, World world, Player entityplayer, boolean timed){
//		if(timed) {
//			System.out.println("attack3 timed!");
//		}else{
//			System.out.println("attack3!");
//		}

	}
	//parry hold
	@Override
	public void hold3(ItemStack itemstack, World world, Player entityplayer, int totalticks){
//		System.out.println("hold3!");
	}

}
