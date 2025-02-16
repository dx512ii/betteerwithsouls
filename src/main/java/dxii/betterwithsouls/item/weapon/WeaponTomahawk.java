package dxii.betterwithsouls.item.weapon;

import dxii.betterwithsouls.enums.EMobAnim;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.interfaces.IPlayer;
import dxii.betterwithsouls.item.ItemWeapon;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class WeaponTomahawk extends ItemWeapon {
	public WeaponTomahawk(String name, String namespaceId, int id) {
		super(name, namespaceId, id);

		atkDelay1 = 4;
		atkDelay2 = 3;
		atkDelay3 = 3;

		atkTiming1 = 2;
		atkTiming2 = 3;
		atkTiming3 = 2;

		atk1hold = false;
		atk2hold = false;
		atk3hold = false;
	}

	//primary
	@Override
	public void attack1callback(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(timed){
			System.out.println("attack1 timed!");
			((IPlayer) entityplayer).bws$setSwingSpeed(0.5f);
			((IMob) entityplayer).bws$sendMobAnim(EMobAnim.ATTACK1);

		}else {
			entityplayer.swingItem();
			((IPlayer) entityplayer).bws$setSwingSpeed(0.8f);
			System.out.println("attack1");
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
