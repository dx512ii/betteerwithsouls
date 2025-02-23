package dxii.betterwithsouls.item;



import dxii.betterwithsouls.enums.EReinforcementType;
import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.util.DamageResistModule;
import dxii.betterwithsouls.util.animation.Animation;
import dxii.betterwithsouls.util.animation.Frame;
import dxii.betterwithsouls.util.animation.Key;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import static dxii.betterwithsouls.anims.BipedHumanoidAnimations.*;
import static dxii.betterwithsouls.anims.ViewModelAnimations.*;

/*
DXII'S WEAPON BASE
IT WORKS, I LIKE IT, IT CAN DO COOLDOWNS, LMB/RMB ATTACKS AND 3RD ONE SPECIAL
WITH KEYBIND, FEEL FREE TO TAKE IT
 */

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

	//player model animations
	public Animation anim_IDLE = ZWEIHANDER_idle;
	public Animation anim_ATTACK1 = SHORTSWORD_attack;

	public Animation vm_ATTACK1 = SHORTSWORD_SWING1;


	public ItemWeapon(String name, String namespaceId, int id) {
		super(name, namespaceId, id);
	}

	public void deploy(ItemStack itemstack, World world, Player entityplayer){
		((IEntity)entityplayer).bws$sendEntityAnim(anim_IDLE);
	}
	public void holster(ItemStack itemstack, World world, Player entityplayer){
		((IEntity)entityplayer).bws$getAnimManager().stopAnimation();
		((IEntity)entityplayer).bws$getAnimManager().stopAnimation();
	}

	//primary attack
	//has timed and non-timed execution
	//change atkTiming* to change delay between these two executions, at 0
	//delay only timed one will be executed
	public void attack1(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(!timed){
			((IEntity)entityplayer).bws$sendEntityAnim(anim_ATTACK1);
			vm_ATTACK1 = new Animation(false)
				.withFrame(new Frame(0)
					.withKey(new Key("weapon", .15f, .1f, -0.05f, 20, 10, -85, true, true )))

				.withFrame(new Frame(1)
					.withKey(new Key("weapon", .05f, .05f, -0.85f, 64, 60, -150, true, true )))

				.withFrame(new Frame(1)
					.withKey(new Key("weapon", -.75f, .15f, -0.8f, 20, 120, -125, true, true )))

				.withFrame(new Frame(4)
					.withKey(new Key("weapon", -.35f, -0, -0.3f, -40, 130, -65, true, true )))

				.withFrame(new Frame(4)
					.withKey(new Key("weapon", -0, -0, -0, -0, 0, -0, true, true )))

			;
			((IEntity)entityplayer).bws$getVMManager().sendAnimation(vm_ATTACK1);
			entityplayer.swingItem();
		}
	}
	//primary hold
	public void hold1(ItemStack itemstack, World world, Player entityplayer, int totalticks){
	}
	public void hold1start(ItemStack itemstack, World world, Player entityplayer){
	}

	//secondary
	public void attack2(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(!timed){
			entityplayer.swingItem();
		}
	}
	//secondary hold
	public void hold2(ItemStack itemstack, World world, Player entityplayer, int totalticks){
	}
	public void hold2start(ItemStack itemstack, World world, Player entityplayer){
	}

	//parry
	public void attack3(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(!timed){
			entityplayer.swingItem();
		}
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
