package dxii.betterwithsouls.item;


import dxii.betterwithsouls.enums.EReinforcementType;
import dxii.betterwithsouls.util.DamageResistModule;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemWeapon extends BWSModItem {
	//attacks delay and hold
	public int atkDelay1 = 0;
	public int atkDelay2 = 0;
	public int atkDelay3 = 0;

	public int atkTiming1 = 0;
	public int atkTiming2 = 0;
	public int atkTiming3 = 0;

	//on true, if attack button id pressed, it will execute hold function every tick
	//and will execute delayed attack on release
	public boolean atk1hold = false;
	public boolean atk2hold = false;
	public boolean atk3hold = false;

	//weapon stats
	public int damage = 1;
	public double range = 3;
	public DamageResistModule blockDefence;
	public EReinforcementType reinforcementType = EReinforcementType.NORMAL;


	public ItemWeapon(String name, String namespaceId, int id) {
		super(name, namespaceId, id);
	}

	//primary attack
	//has timed and non-timed execution
	//change atkTiming* to change delay between these two executions, at 0
	//delay only timed one will be executed
	public void attack1(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(timed){

		}else{

		}
		attack1callback(itemstack, world, entityplayer, timed);
	}
	public void attack1callback(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		/* these things are used for you to execute your own weapon code, attack1 handles all the boring
		'routinous' code you can override whenever you want */
	}
	//primary hold
	public void hold1(ItemStack itemstack, World world, Player entityplayer, int totalticks){
	}
	public void hold1start(ItemStack itemstack, World world, Player entityplayer){
	}

	//secondary
	public void attack2(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		attack2callback(itemstack, world, entityplayer, timed);
	}
	public void attack2callback(ItemStack itemstack, World world, Player entityplayer, boolean timed){
	}
	//secondary hold
	public void hold2(ItemStack itemstack, World world, Player entityplayer, int totalticks){
	}
	public void hold2start(ItemStack itemstack, World world, Player entityplayer){
	}

	//parry
	public void attack3(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		attack3callback(itemstack, world, entityplayer, timed);
	}
	public void attack3callback(ItemStack itemstack, World world, Player entityplayer, boolean timed){
	}
	//parry hold
	public void hold3(ItemStack itemstack, World world, Player entityplayer, int totalticks){
	}
	public void hold3start(ItemStack itemstack, World world, Player entityplayer){
	}


	public Item setWeaponDamage(int dmg){
		this.damage = dmg;
		return this;
	}

	public int getWeaponDamage(){
		return this.damage;
	}
}
