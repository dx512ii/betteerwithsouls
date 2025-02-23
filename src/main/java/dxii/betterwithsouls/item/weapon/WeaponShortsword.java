package dxii.betterwithsouls.item.weapon;

import dxii.betterwithsouls.item.ItemWeapon;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.world.World;

public class WeaponShortsword extends ItemWeapon {
	public WeaponShortsword(ToolMaterial mat, String name, String namespaceId, int id) {
		super(name, namespaceId, id);

		this.setMaxDamage(mat.getDurability());

		this.atkDelay1 = 10;
		this.atkDelay2 = 5;
		this.atkDelay3 = 3;

		this.atkTiming1 = 2;
		this.atkTiming2 = 3;
		this.atkTiming3 = 2;

		this.atk1hold = false;
		this.atk2hold = true;
		this.atk3hold = false;

		this.anim_IDLE = null;
	}

	@Override
	public void holster(ItemStack itemstack, World world, Player entityplayer){
		super.holster(itemstack, world, entityplayer);
	}

	//primary
	@Override
	public void attack1(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		super.attack1(itemstack, world, entityplayer, timed);
	}

	//secondary (also hold release)
	@Override
	public void attack2(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		super.attack2(itemstack, world, entityplayer, timed);
	}
	//secondary hold
	@Override
	public void hold2start(ItemStack itemstack, World world, Player entityplayer){
		//((IMob) entityplayer).bws$sendMobAnim(EMobAnim.BLOCK);
		System.out.println("holdd!!");
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
