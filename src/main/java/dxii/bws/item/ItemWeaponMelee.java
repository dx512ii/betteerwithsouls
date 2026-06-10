package dxii.bws.item;

import dxii.bws.entity.DamageInfo;
import dxii.bws.entity.DamageResistModule;
import dxii.bws.entity.DamageTypeBWS;
import dxii.bws.entity.IMobExtra;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.primitives.AABBd;

import java.util.List;

public class ItemWeaponMelee extends ItemWeapon{
	public ItemWeaponMelee(@NotNull String name) {
		super(name);
	}

	private final DamageResistModule blockResist = new DamageResistModule();

	public ItemWeapon withBlockResist(DamageTypeBWS dtype, float mul){
		this.blockResist.setResist(dtype, mul);

		return this;
	}
	public float getResist(DamageTypeBWS dtype){
		return this.blockResist.getResist(dtype);
	}

	public float getMeleeScale(int attackType){
		return 1f;
	}

	@Override
	public void attack1(Player ply, World world, ItemStack stack, boolean timed) {
		if(!timed){
			this.sendNextAnimation(ply, moveset.idle());
			this.sendAnimation(ply, moveset.attack1()
			);
		}else{
			meleeAttack(this.getDamageType(0), this.meleeRange, getMeleeScale(0), stack, ply);
		}

		ply.swingItem();
	}

	@Override
	public void attack2(Player ply, World world, ItemStack stack, boolean timed) {
		if(!timed){
			this.sendNextAnimation(ply, moveset.idle());
			this.sendAnimation(ply, moveset.attack2());
		}

		ply.swingItem();

	}

	@Override
	public void attack3(Player ply, World world, ItemStack stack, boolean timed) {
		if(!timed){
			this.sendNextAnimation(ply, moveset.idle());
			this.sendAnimation(ply, moveset.attack3());
		}else{
			meleeAttack(1.5f, this.getDamageType(2), this.meleeRange, getMeleeScale(2), stack, ply);
		}

		ply.swingItem();
	}


	AABBd taabb = new AABBd();
	public void meleeAttack(DamageTypeBWS dtype, float range, float scale, ItemStack stack, Player ply){
		meleeAttack(1, dtype, range, scale, stack, ply);
	}
	public void meleeAttack(float dmgmul, DamageTypeBWS dtype, float range, float scale, ItemStack stack, Player ply){
		DamageInfo dinfo = setupDInfo(ply, 0)
			.setDamageType(dtype)
			.setDamage((int) (getDamage(stack) * dmgmul))
			;


		Vector3d eyepos = (Vector3d) ply.getPosition(1, true);
		Vector3d viewVec = (Vector3d) ply.getViewVector(1);
		if(viewVec == null){
			return;
		}

		float width = ply.bbWidth * scale * 1.5f;
		range -= width*.5f;

		// first we check at no range, then 1/2 of the range, then at full range to avoid no reg for long weapons
		taabb.minX = eyepos.x - width;
		taabb.minY = eyepos.y - width*1.5f;
		taabb.minZ = eyepos.z - width;

		taabb.maxX = eyepos.x + width;
		taabb.maxY = eyepos.y + width*1.5f;
		taabb.maxZ = eyepos.z + width;

		List<Mob> ents = ply.world.getEntitiesWithinAABB(Mob.class, taabb);

		taabb.minX = (eyepos.x + viewVec.x*range) - width;
		taabb.minY = (eyepos.y + viewVec.y*range) - width*1.5f;
		taabb.minZ = (eyepos.z + viewVec.z*range) - width;

		taabb.maxX = (eyepos.x + viewVec.x*range) + width;
		taabb.maxY = (eyepos.y + viewVec.y*range) + width*1.5f;
		taabb.maxZ = (eyepos.z + viewVec.z*range) + width;

		for(Mob m : ply.world.getEntitiesWithinAABB(Mob.class, taabb)){
			if(!ents.contains(m)){
				ents.add(m);
			}
		}


		for(Mob m : ply.world.getEntitiesWithinAABB(Mob.class, taabb)){
			if(!ents.contains(m)){
				ents.add(m);
			}
		}

		boolean hitsmb = false;
		for(Mob mob : ents){
			if(mob == ply){
				continue;
			}

			hitsmb = true;
			((IMobExtra)mob).takeDamageInfo(dinfo);

			onHitMob(mob);
		}
	}


	@Override
	public void attackBeginStop2(Player ply, World world, boolean begin) {
		if(!begin){
			this.sendAnimation(ply, moveset.idle());
		}
	}

	public void raiseBlock(Player ply, float duration){
		((IMobExtra)(ply)).raiseBlock(blockResist, duration);
	}


}
