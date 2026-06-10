package dxii.bws.entity;

import net.minecraft.core.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public class DamageInfo {
	private @Nullable Entity attacker;
	private @Nullable Vector3dc damagePos;
	private int damage = 0;
	private float damageForceMul = 1;
	public @NotNull DamageTypeBWS damageType = DamageTypeBWS.GENERIC;

	public @Nullable Entity getAttacker(){
		return this.attacker;
	}
	public int getDamage(){
		return this.damage;
	}
	public DamageTypeBWS getDamageType(){
		return this.damageType;
	}
	public Vector3dc getDamagePos(){
		return this.damagePos;
	}
	public float getDamageForceMul(){
		return this.damageForceMul;
	}

	public DamageInfo setAttacker(@Nullable Entity attacker){
		this.attacker = attacker;

		return this;
	}
	public DamageInfo setDamage(int dmg){
		this.damage = dmg;

		return this;
	}
	public DamageInfo setDamageType(DamageTypeBWS type){
		this.damageType = type;

		return this;
	}
	public DamageInfo setDamagePos(Vector3dc pos){
		this.damagePos = pos;

		return this;
	}
	public DamageInfo setDamageForceMul(float force){
		this.damageForceMul = force;

		return this;
	}



}
