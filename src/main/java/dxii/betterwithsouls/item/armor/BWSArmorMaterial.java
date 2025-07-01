package dxii.betterwithsouls.item.armor;

import dxii.betterwithsouls.item.BWSItemArmor;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.DamageResistModule;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;

public class BWSArmorMaterial {

	public static float[] ARMOR_PIECE_RESIST_MODIFIERS = new float[]{0.15F, 0.25F, 0.45F, 0.15F};


	public static BWSArmorMaterial WOOD = new BWSArmorMaterial().withTexture("wood")
		.withResist(BWSDamageTypes.SLASH, 5)
		.withResist(BWSDamageTypes.THRUST, 5)
		.withResist(BWSDamageTypes.STRIKE, 4)
		.withResist(BWSDamageTypes.DARK, 8)
		.withWeakness(BWSDamageTypes.FIRE, 2)
		.withDurability(120)
		.withTooltip("armor_material.wood")
		.withWeight(45)
	;

	public static BWSArmorMaterial LEATHER = new BWSArmorMaterial().withTexture("leather")
		.withResist(BWSDamageTypes.SLASH, 8)
		.withResist(BWSDamageTypes.STRIKE, 2)
		.withResist(BWSDamageTypes.POISON, 8)
		.withDurability(100)
		.withTooltip("armor_material.leather")
		.withWeight(8)
	;


	public DamageResistModule resists = new DamageResistModule();
	public String texture;
	public float weight;
	public int durability;
	public String tooltip;
	public String stepSound;

	public BWSArmorMaterial withTexture(String texture){
		this.texture = texture;
		return this;
	}
	public BWSArmorMaterial withDurability(int dur){
		this.durability = dur;
		return this;
	}
	public BWSArmorMaterial withTooltip(String tooltip){
		this.tooltip = tooltip;
		return this;
	}
	public BWSArmorMaterial withResist(DamageType dtype, int def){
		this.resists.setDefence(dtype, def);
		return this;
	}
	public BWSArmorMaterial withWeakness(DamageType dtype, float def){
		this.resists.setWeakness(dtype, def);
		return this;
	}
	public BWSArmorMaterial withWeight(float weight){
		this.weight = weight;
		return this;
	}
	public BWSArmorMaterial withStepSound(String snd){
		this.stepSound = snd;
		return this;
	}

	public static String armorStepSound(ContainerInventory inv){
		int[] weights = new int[]{1, 2, 3, 1};
		int weightTemp = 0;
		String snd = "";
		for(ItemStack stack : inv.armorInventory){
			if(stack != null && stack.getItem() instanceof BWSItemArmor){
				BWSItemArmor armor = ((BWSItemArmor)stack.getItem());
				if(weights[armor.armorPiece] > weightTemp) {
					snd = armor.material.stepSound;
					weightTemp = weights[armor.armorPiece];
				}
			}
		}

		return snd;
	}
}
