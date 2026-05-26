package dxii.bws.item;

import dxii.bws.animation.AnimatableEntity;
import dxii.bws.animation.Animation;
import dxii.bws.animation.WeaponMoveset;
import dxii.bws.entity.DamageInfo;
import dxii.bws.entity.DamageTypeBWS;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

public class ItemWeapon extends ItemBWS{
	public ItemWeapon(@NotNull String name) {
		super(name);
	}

	public WeaponMoveset moveset = WeaponMoveset.CLUB;

	public int damage = 1;
	public DamageTypeBWS damageType = DamageTypeBWS.PHYS_SLASH;

	public float attackDelay1 = 1f;
	public float attackDelay2 = 1f;
	public float attackDelay3 = 1f;

	public float attackTiming1 = 0f;
	public float attackTiming2 = 0f;
	public float attackTiming3 = 0f;

	public float meleeRange;

	public DamageInfo DINFO = new DamageInfo();

	public ItemWeapon withStats(int damage){
		return this.withStats(damage, 1);
	}
	public ItemWeapon withStats(int damage, float meleeRange){
		this.damage = damage;
		this.meleeRange = meleeRange;

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

	public DamageTypeBWS getDamageType(int attackType){
		return this.damageType;
	}

	public void attack1(Player ply, World world, boolean timed){

	}
	public void attack2(Player ply, World world, boolean timed){

	}
	public void attack3(Player ply, World world, boolean timed){

	}

	public void deploy(Player ply, World world){
		sendAnimation(ply, null);
	}
	public void holster(Player ply, World world){
		sendAnimation(ply, moveset.idle());
	}

	public void sendAnimation(Player ply, Animation anim){
		((AnimatableEntity)ply).sendAnimation(anim);
	}
}
