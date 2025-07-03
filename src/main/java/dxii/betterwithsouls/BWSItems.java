package dxii.betterwithsouls;



import dxii.betterwithsouls.enums.EAccBonus;
import dxii.betterwithsouls.item.*;
import dxii.betterwithsouls.item.armor.BWSArmorMaterial;
import dxii.betterwithsouls.item.weapon.moveset.MovesetGreatsword;
import dxii.betterwithsouls.item.weapon.WeaponMaterial;
import dxii.betterwithsouls.item.weapon.WeaponMelee;
import dxii.betterwithsouls.util.BWSDamageTypes;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.util.ConfigHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

public class BWSItems {
	public static int ITEM_ID = BWSConfig.CFG.getInt("IDs.startItemID");
	public static final ConfigHandler cfg;

	public static final List<Item> standartModels = new ArrayList<>();
	public static final List<Item> weaponModels = new ArrayList<>();
	public static final List<Item> weaponModelsBig = new ArrayList<>();

	public static BWSModItem ITEM_DUMMY;
	public static BWSModItem ITEM_ANIMATOR;

	public static BWSModItem SACRIFICE_RING;
	public static BWSModItem SACRIFICE_RING_RARE;
	public static BWSModItem SACRIFICE_RING_BROKEN;

	public static BWSModItem SHORTSWORD_STONE;
	public static BWSModItem CLAYMORE;

	public static BWSModItem ARMOR_WOODEN_HELMET;
	public static BWSModItem ARMOR_WOODEN_CHESTPLATE;
	public static BWSModItem ARMOR_WOODEN_PANTS;
	public static BWSModItem ARMOR_WOODEN_BOOTS;

	static {
		Properties prop = new Properties();
		prop.setProperty("test", "1");
		cfg = new ConfigHandler(MOD_ID, prop);
	}

	public static void initItems() {
		//wearables
		SACRIFICE_RING = new ItemBuilder(MOD_ID)
			.build(new ItemAccessory("sacrificering", nextID(), EAccBonus.SACRIFICE));
		SACRIFICE_RING_BROKEN = new ItemBuilder(MOD_ID)
			.build(new ItemAccessory("sacrificering_broken", nextID(), EAccBonus.SACRIFICE_BROKEN));
		SACRIFICE_RING_RARE = new ItemBuilder(MOD_ID)
			.build(new ItemAccessory("sacrificering_rare", nextID(), EAccBonus.SACRIFICE_RARE));

		//weapons
		CLAYMORE = new ItemBuilder(MOD_ID).addTags(ItemTags.PREVENT_CREATIVE_MINING)
			.build(new WeaponMelee(WeaponMaterial.IRON, "claymore", nextID())
				.withDefence(BWSDamageTypes.SLASH, 15)
				.withDefence(BWSDamageTypes.THRUST, 10)
				.withDefence(BWSDamageTypes.STRIKE, 10)
				.withDefence(BWSDamageTypes.MAGIC, 8)
				.withDefence(BWSDamageTypes.LIGHTNING, 5)
				.withDefence(BWSDamageTypes.FIRE, 8)
				.withDefence(BWSDamageTypes.DARK, 5)
				.heavySounds()
				.withAttackDelays(20, 0, 25)
				.withAttackTimigs(5, 0, 4)
				.withStats(BWSDamageTypes.SLASH, 10, 3)
				.withMoveset(new MovesetGreatsword())
			).withWeaponModel(true);

		//debug
		ITEM_DUMMY = new ItemBuilder(MOD_ID)
			.build(new ItemDummy("item_dummy", nextID()));
		ITEM_ANIMATOR = new ItemBuilder(MOD_ID)
			.build(new ItemAnimator("item_animator", nextID()))
			.withStandartModel();


		ARMOR_WOODEN_HELMET = new ItemBuilder(MOD_ID).build(new BWSItemArmor("armor_wooden_helmet", BWSArmorMaterial.WOOD, nextID(), 3))
			.withStandartModel();
		ARMOR_WOODEN_CHESTPLATE = new ItemBuilder(MOD_ID).build(new BWSItemArmor("armor_wooden_chestplate", BWSArmorMaterial.WOOD, nextID(), 2))
			.withStandartModel();
		ARMOR_WOODEN_PANTS = new ItemBuilder(MOD_ID).build(new BWSItemArmor("armor_wooden_pants", BWSArmorMaterial.WOOD, nextID(), 1))
			.withStandartModel();
		ARMOR_WOODEN_BOOTS = new ItemBuilder(MOD_ID).build(new BWSItemArmor("armor_wooden_boots", BWSArmorMaterial.WOOD, nextID(), 0))
			.withStandartModel();

	}

	public static int nextID(){
		return ITEM_ID++;
	}
}
