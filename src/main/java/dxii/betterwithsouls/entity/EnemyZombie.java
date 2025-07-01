package dxii.betterwithsouls.entity;


import dxii.betterwithsouls.anims.UndeadAnimations;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

public class EnemyZombie extends BWSMonsterPursuer {
	public EnemyZombie(@Nullable World world) {
		super(world);

		this.textureIdentifier = NamespaceID.getPermanent(MOD_ID, "undead");

		this.moveSpeedMul = .6f;

		this.attackStrength = 4;

		this.attackDistance = 3;
		this.hitDistance = 2.25f;

		this.attackTiming = 16;
		this.swingTiming = 18;

		this.idleAnim = UndeadAnimations.shortswordIdle;

		this.attackAnim = UndeadAnimations.shortswordAttack;
		this.parriedAnim = UndeadAnimations.parried;
		this.stunAnim = UndeadAnimations.stun;

		this.scoreValue = 300;
		this.mobDrops.add(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 0, 2));

		this.chaotic		= false;
		this.crazy			= false;
		this.canBeParried	= true;

		this.debug = true;
	}

	@Override
	public int getMaxHealth() {
		return 40;
	}

	@Override
	public ItemStack getHeldItem(){
		return Items.TOOL_SWORD_STONE.getDefaultStack();
	}

	@Override
	protected String getHurtSound() {
		return "mob.zombiehurt";
	}
}
