package dxii.betterwithsouls.item.weapon;

import dxii.betterwithsouls.enums.EMobAnim;
import dxii.betterwithsouls.enums.EReinforcementType;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.interfaces.IPlayer;
import dxii.betterwithsouls.item.ItemWeapon;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class WeaponTomahawk extends ItemWeapon {
	public WeaponTomahawk(String name, int id) {
		super(name, id);

		this.reinforcementType = EReinforcementType.NORMAL;

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

	@Override
	public void deploy(ItemStack itemstack, World world, Player entityplayer){
		super.deploy(itemstack, world, entityplayer);
	}

	//primary
	@Override
	public void attack1(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(timed){
			adjustSwingSpeed(entityplayer, 0.5f);

		}else {
			entityplayer.swingItem();
			adjustSwingSpeed(entityplayer, 0.8f);
		}
	}

	//secondary (also hold release)
	@Override
	public void attack2(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(timed) {

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
	public void attack3(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		super.attack3(itemstack, world, entityplayer, timed);

	}
	//parry hold
	@Override
	public void hold3(ItemStack itemstack, World world, Player entityplayer, int totalticks){
//		System.out.println("hold3!");
	}

}
