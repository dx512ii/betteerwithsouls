package dxii.betterwithsouls.item.weapon;

import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.item.ItemWeapon;
import dxii.betterwithsouls.util.DamageResistModule;
import dxii.betterwithsouls.util.animation.Animation;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

public class ItemWeaponMelee extends ItemWeapon {

	public DamageResistModule blockDefence = new DamageResistModule();

	//sounds
	public String swingSound;
	public String wooshSound;
	public String blockSound;
	/**
	 * makes all weapon sounds low pitched
	 */
	public boolean heavy = false;
	public ItemWeapon heavy(){
		this.heavy = true;
		return this;
	}

	public ItemWeaponMelee(WeaponMaterial mat, String name, int id) {
		super(name, id);
		dinfo1.setDmgType(dtype);
		this.swingSound = mat.swingSound;
		this.wooshSound = mat.wooshSound;
		this.blockSound = mat.blockSound;

		this.setMaxDamage(mat.durability);
	}

	public ItemWeaponMelee withDefence(DamageType dtype, int def){
		this.blockDefence.setDefence(dtype, def);
		return this;
	}
	public ItemWeaponMelee withAttackStats(int dmg, float range){

		return this;
	}

	public void holster(ItemStack itemstack, World world, Player player){
		super.holster(itemstack, world, player);
		this.switchBlocking(player, false);
	}


	public void attackDefault(int animtype, ItemStack itemstack, World world, Player entityplayer, boolean timed){
		attackDefault(animtype, 1, 1, -.4f, itemstack, world, entityplayer, timed);
	}
	public void attackDefault(int animtype, float rangemul, float dmgmul, float dot, ItemStack itemstack, World world, Player entityplayer, boolean timed){
		if(!timed){
			world.playSoundAtEntity(entityplayer, entityplayer, MOD_ID+":"+swingSound, 0.5F, 1.2F / (itemRand.nextFloat() * 0.4F + (heavy ? 1.5f : 1.0F)));
			Animation atk = getPlayerAttackAnimation(animtype);
			sendPlayerNextAnim(entityplayer, getPlayerIdleAnimation());
			this.sendPlayerAnim(entityplayer, atk);
			this.sendVMAnim(entityplayer, getAttackAnimation(animtype));
			entityplayer.swingItem();
		}else{
			dinfo1.setDmg((int) (getWeaponDamage(itemstack)*dmgmul));
			dinfo1.setAttacker(entityplayer);
			dinfo1.setIgnoresIframes(true);
			entityplayer.swingItem();
			meleeAttack(entityplayer, dinfo1, this.range*rangemul, dot < 0 ? dot : -dot, MOD_ID+":dmg.flesh");
		}
	}
	public void attack1(ItemStack itemstack, World world, Player entityplayer, boolean timed){
		attackDefault(0, 1, 1, -.4f, itemstack, world, entityplayer, timed);
	}

	public void attack2(ItemStack itemstack, World world, Player player, boolean timed){
		this.sendPlayerAnim(player, getPlayerBlockAnimation());
		switchBlocking(player, true);
	}

	public void attack3(ItemStack itemstack, World world, Player player, boolean timed){
		if(!timed){
			player.swingItem();
			((IMob)player).parry(2);
			world.playSoundAtEntity(player, player, MOD_ID+":"+wooshSound, 0.5F, 1.2F / (itemRand.nextFloat() * 0.4F + 1.0F));
		}
	}

	public void switchBlocking(Player player, boolean on){
		if(on){
			((IMob)player).startBlocking(this.blockDefence, this.blockSound);
		}else{
			((IMob)player).stopBlocking();
		}
	}

	public void parry(Player player, int ticks){
		((IMob)player).parry(ticks);
	}



}
