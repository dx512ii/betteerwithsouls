package dxii.betterwithsouls.item.weapon;

import dxii.betterwithsouls.item.ItemWeapon;
import dxii.betterwithsouls.util.DamageResistModule;
import dxii.betterwithsouls.util.animation.Animation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

public class WeaponMelee extends ItemWeapon {
	public WeaponMelee(WeaponMaterial mat, String name, int id) {
		super(name, id);
		dinfo.setDmgType(dtype);
		this.attackSound = mat.attackSound;
		this.wooshSound = mat.wooshSound;
		this.blockSound = mat.blockSound;
		this.hitSoundWorld = mat.hitSoundWorld;

		this.setMaxDamage(mat.durability);
	}

	//sounds
	private String attackSound;
	private String wooshSound;
	private String blockSound;
	private String hitSoundWorld;
	/**
	 * makes all weapon sounds low pitched
	 */
	public boolean heavy = false;
	public ItemWeapon heavySounds(){
		this.heavy = true;
		return this;
	}

	public DamageResistModule blockDefence = new DamageResistModule();
	public WeaponMelee withDefence(DamageType dtype, int def){
		this.blockDefence.setDefence(dtype, def);
		return this;
	}

	public void attackDefault(int animtype, float rangemul, float dmgmul, float dot, ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(!timed){
			playSound(entityplayer, MOD_ID+":"+this.attackSound, this.heavy, 1, 1);

			Animation atk;
			Animation atkvm;
			switch(animtype){
				case 1:
					atk = moveset.getPlayerAttack2Animation();
					atkvm = moveset.getAttack2Animation();
					break;
				case 2:
					atk = moveset.getPlayerAttack3Animation();
					atkvm = moveset.getAttack3Animation();
					break;
				default:
					atk = moveset.getPlayerAttackAnimation(animtype);
					atkvm = moveset.getAttackAnimation(animtype);
					break;
			}

			sendPlayerNextAnim(entityplayer, moveset.getPlayerIdleAnimation());
			this.sendPlayerAnim(entityplayer, atk);
			this.sendVMAnim(entityplayer, atkvm);

			entityplayer.swingItem();
		}else{
			dinfo.setDmg((int) (getWeaponDamage(itemstack)*dmgmul));
			dinfo.setAttacker(entityplayer);
			dinfo.setIgnoresIframes(true);
			entityplayer.swingItem();
			meleeAttack(entityplayer, dinfo, this.meleeRange*rangemul, dot < 0 ? dot : -dot, MOD_ID+":dmg.flesh", MOD_ID+":"+this.hitSoundWorld, this.heavy);
		}
	}


	public void attack1(ItemStack itemstack, World world, Player player, boolean timed){
		attackDefault(0, 1, 1, -.4f, itemstack, world, player, timed);
	}

	public void attack2(ItemStack itemstack, World world, Player player, boolean timed){
		if(!timed){
			this.sendPlayerAnim(player, moveset.getPlayerAttack2Animation());
			this.sendVMAnim(player, moveset.getAttack2Animation());
			raiseGuard(player, this.blockDefence, this.blockSound);
			player.swingItem();
		}
	}

	public void attack3(ItemStack itemstack, World world, Player player, boolean timed){
		attackDefault(2, 1.25f, 1.1f, -.85f, itemstack, world, player, timed);
	}

}
