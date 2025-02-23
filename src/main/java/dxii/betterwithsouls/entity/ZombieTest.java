package dxii.betterwithsouls.entity;

import dxii.betterwithsouls.interfaces.IEntity;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import static dxii.betterwithsouls.anims.BipedHumanoidAnimations.ZWEIHANDER_idle;

public class ZombieTest extends BWSMonsterBase{
	public ZombieTest(@Nullable World world) {
		super(world);
		this.textureIdentifier = NamespaceID.getPermanent("minecraft", "zombie");

		this.moveSpeedMul = 2.0f;

		this.attackStrength = 1;
		this.scoreValue = 300;
		this.mobDrops.add(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 0, 2));

		this.chaotic = true;
		this.crazy = false;
		this.feral = false;

		((IEntity)this).bws$sendEntityAnim(ZWEIHANDER_idle);
	}

	@Nullable
	public ItemStack getHeldItem() {
		return Items.TOOL_SWORD_STONE.getDefaultStack();
	}

	@Override
	protected void updateAI() {
//		super.updateAI();
	}

}
