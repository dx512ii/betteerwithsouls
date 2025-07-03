package dxii.betterwithsouls.item.weapon;

import dxii.betterwithsouls.enums.EReinforcementType;
import dxii.betterwithsouls.interfaces.IReinforceable;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

//attack3 - parrying
public class WeaponShortsword extends ItemWeaponMelee {
	public WeaponShortsword(WeaponMaterial mat, String name, int id) {
		super(mat, name, id);

		this.reinforcementType = EReinforcementType.NORMAL;

		this.range = 2f;

		this.atkDelay1 = 10;
		this.atkDelay2 = 2;
		this.atkDelay3 = 15;

		this.atkTiming1 = 2;
		this.atkTiming2 = 0;
		this.atkTiming3 = 3;

		this.atk1hold = false;
		this.atk2hold = true;
		this.atk3hold = false;
	}


	@Override
	public void holster(ItemStack itemstack, World world, Player entityplayer){
		super.holster(itemstack, world, entityplayer);
	}

	//primary
	@Override
	public void attack1(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		super.attack1(itemstack, world, entityplayer, timed);
		((IReinforceable)(Object)itemstack).bws$reinforceItem();
	}

	//secondary (also hold release)
	@Override
	public void attack2(ItemStack itemstack, World world, Player entityplayer, boolean timed){
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

		entityplayer.swingItem();
	}

	//parry
	@Override
	public void attack3(ItemStack itemstack, World world, Player player, boolean timed){
		super.attack3(itemstack, world, player, timed);
		if(!timed){
			this.sendVMAnim(player, getParryAnimation());
			this.sendPlayerAnim(player, getPlayerParryAnimation());
			parry(player, 3);
		}
	}

}
