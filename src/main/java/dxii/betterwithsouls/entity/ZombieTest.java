package dxii.betterwithsouls.entity;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class ZombieTest extends BWSMonsterBase{
	public ZombieTest(@Nullable World world) {
		super(world);
		this.textureIdentifier = NamespaceID.getPermanent("minecraft", "zombie");

		this.moveSpeedMul = 3;
		this.moveSpeed = 1.0F;

		this.attackStrength = 1;
		this.scoreValue = 300;
		this.mobDrops.add(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 0, 2));

		this.chaotic =  true;
		this.feral =  true;
	}
}
