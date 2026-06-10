package dxii.bws.item;

import dxii.bws.animation.AnimatableEntity;
import dxii.bws.animation.Animation;
import dxii.bws.animation.WeaponMoveset;
import dxii.bws.entity.DamageInfo;
import dxii.bws.entity.DamageTypeBWS;
import dxii.bws.entity.IMobExtra;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBf;

import java.util.List;

public class ItemWeapon extends ItemBWS{
	public ItemWeapon(@NotNull String name) {
		super(name);
	}

	public WeaponMoveset moveset = WeaponMoveset.HAMMER;

	public int damage = 1;
	public DamageTypeBWS damageType = DamageTypeBWS.PHYS_SLASH;

	public float attackDelay1 = 1f;
	public float attackDelay2 = 1f;
	public float attackDelay3 = 1f;

	public float attackTiming1 = 0f;
	public float attackTiming2 = 0f;
	public float attackTiming3 = 0f;

	public float meleeRange;

	public String impactSound;

	public DamageInfo DINFO = new DamageInfo();

	public ItemWeapon withStats(int damage){
		return this.withStats(damage, 1, this.damageType);
	}
	public ItemWeapon withStats(int damage, float meleeRange, DamageTypeBWS dtype){
		this.damage = damage;
		this.meleeRange = meleeRange;
		this.damageType = dtype;

		return this;
	}
	public ItemWeapon withMoveset(WeaponMoveset moveset){
		this.moveset = moveset;

		return this;
	}
	public ItemWeapon withAttackDelays(double attackDelay1, double attackDelay2, double attackDelay3){
		return this.withAttackDelays((float)attackDelay1, (float)attackDelay2, (float)attackDelay3);
	}
	public ItemWeapon withAttackDelays(float attackDelay1, float attackDelay2, float attackDelay3){
		this.attackDelay1 = attackDelay1;
		this.attackDelay2 = attackDelay2;
		this.attackDelay3 = attackDelay3;

		return this;
	}
	public ItemWeapon withAttackTimings(double attackTiming1, double attackTiming2, double attackTiming3){
		return this.withAttackTimings((float)attackTiming1, (float)attackTiming2, (float)attackTiming3);
	}
	public ItemWeapon withAttackTimings(float attackTiming1, float attackTiming2, float attackTiming3){
		this.attackTiming1 = attackTiming1;
		this.attackTiming2 = attackTiming2;
		this.attackTiming3 = attackTiming3;

		return this;
	}
	public ItemWeapon withImpactSound(String newSound){
		this.impactSound = newSound;

		return this;
	}

	public DamageTypeBWS getDamageType(int attackType){
		return this.damageType;
	}

	public DamageInfo setupDInfo(Player ply, int attackType){
		DINFO
			.setDamageType(getDamageType(attackType))
			.setAttacker(ply)
			.setDamagePos(new Vector3d(ply.x, ply.y, ply.z))
		;

		return DINFO;
	}

	public void attack1(Player ply, World world, ItemStack stack, boolean timed){

	}
	public void attack2(Player ply, World world, ItemStack stack, boolean timed){

	}
	public void attack3(Player ply, World world, ItemStack stack, boolean timed){

	}

	public void attackBeginStop1(Player ply, World world, boolean begin){

	}
	public void attackBeginStop2(Player ply, World world, boolean begin){

	}
	public void attackBeginStop3(Player ply, World world, boolean begin){

	}

	public int getDamage(ItemStack stack){
		float fdmg = (float)this.damage;

		return (int)(fdmg + ( .1f * fdmg * ((IItemStackExtra)(Object)stack ).weaaponGetReinforcement() ));
	}

	public void onHitMob(Mob mob){
		//do something
	}

	public void deploy(Player ply, World world){
		sendAnimation(ply, moveset.idle());
	}
	public void holster(Player ply, World world){
		sendAnimation(ply, null);
	}

	public void sendAnimation(Player ply, Animation anim){
		this.sendAnimation(ply, anim ,false);
	}
	public void sendAnimation(Player ply, Animation anim, boolean keepOnEqual){
		((AnimatableEntity)ply).sendAnimation(anim, keepOnEqual);
	}
	public void sendNextAnimation(Player ply, Animation anim){
		((AnimatableEntity)ply).sendNextAnimation(anim);
	}
}
