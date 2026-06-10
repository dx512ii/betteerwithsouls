package dxii.bws;

import dxii.bws.animation.WeaponMoveset;
import dxii.bws.entity.DamageTypeBWS;
import dxii.bws.item.ItemAccessory;
import dxii.bws.item.ItemBWS;
import dxii.bws.item.ItemWeaponMelee;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DamageType;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryCategory;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryRegistry;

public class BWSItems {
	public static int ITEM_ID = BWSConfig.CFG.getInt("IDs.startItemID");

	// BASIC
	public static Item SOUL_TIER1_LIGHT;
	public static Item SOUL_TIER2_LIGHT;
	public static Item SOUL_TIER3_LIGHT;
	public static Item SOUL_TIER4_LIGHT;
	public static Item SOUL_TIER5_LIGHT;

	public static Item SOUL_TIER1_DARK;
	public static Item SOUL_TIER2_DARK;
	public static Item SOUL_TIER3_DARK;
	public static Item SOUL_TIER4_DARK;
	public static Item SOUL_TIER5_DARK;

	// WEAPONS
	public static Item WEAPON_CLUB;
	public static Item WEAPON_CLUB_BONE;
	public static Item WEAPON_CLUB_GREAT;
	public static Item WEAPON_CLUB_GIANT;

	public static Item WEAPON_CLAYMORE;
	public static Item WEAPON_ZWEIHANDER;

	public static Item WEAPON_SPEAR_WOODEN;
	public static Item WEAPON_SPEAR_STONE;
	public static Item WEAPON_SPEAR_IRON;

	public static Item WEAPON_HAMMER_WOODEN;
	public static Item WEAPON_HAMMER_STONE;
	public static Item WEAPON_HAMMER_OBSIDIAN;
	public static Item WEAPON_HAMMER_NETHERITE;

	//TRINKETS
	public static Item ACC_RING_IRON;


	static void init(){
		BWS.LOGGER.info("BWS Items Init!");

		SOUL_TIER1_LIGHT = NewItem(new ItemBWS("soul_tier1_light")).withStandartFullbrightModel();
		SOUL_TIER2_LIGHT = NewItem(new ItemBWS("soul_tier2_light")).withStandartFullbrightModel();
		SOUL_TIER3_LIGHT = NewItem(new ItemBWS("soul_tier3_light")).withStandartFullbrightModel();
		SOUL_TIER4_LIGHT = NewItem(new ItemBWS("soul_tier4_light")).withStandartFullbrightModel();
		SOUL_TIER5_LIGHT = NewItem(new ItemBWS("soul_tier5_light")).withStandartFullbrightModel();

		SOUL_TIER1_DARK = NewItem(new ItemBWS("soul_tier1_dark")).withStandartFullbrightModel();
		SOUL_TIER2_DARK = NewItem(new ItemBWS("soul_tier2_dark")).withStandartFullbrightModel();
		SOUL_TIER3_DARK = NewItem(new ItemBWS("soul_tier3_dark")).withStandartFullbrightModel();
		SOUL_TIER4_DARK = NewItem(new ItemBWS("soul_tier4_dark")).withStandartFullbrightModel();
		SOUL_TIER5_DARK = NewItem(new ItemBWS("soul_tier5_dark")).withStandartFullbrightModel();

		ACC_RING_IRON = NewItem(new ItemAccessory("acc_ring_iron", ItemAccessory.Bonus.NONE)).withStandartModel();
		WEAPON_CLUB_BONE = NewItem(
			new ItemWeaponMelee("club_bone")
				.withStats(7, 1.33f, DamageTypeBWS.PHYS_STRIKE)
				.withAttackDelays(.5, 0, 1.25)
				.withAttackTimings(.33, 0, .6)
				.withMoveset(WeaponMoveset.HAMMER)

		).withWeaponModel(false);
		WEAPON_CLUB = NewItem(
			new ItemWeaponMelee("club")
				.withStats(7, 1.33f, DamageTypeBWS.PHYS_STRIKE)
				.withAttackDelays(.75, 0, 1.5)
				.withAttackTimings(.33, 0, .6)
				.withMoveset(WeaponMoveset.HAMMER)
		).withWeaponModel(false);

		WEAPON_CLUB_GREAT = NewItem(
			new ItemWeaponMelee("club_great")
				.withStats(15, 2f, DamageTypeBWS.PHYS_STRIKE)
				.withAttackDelays(1.25, 0, 1.5)
				.withAttackTimings(.45, 0, .66)
				.withMoveset(WeaponMoveset.GREATHAMMER)

		).withWeaponModel(true);

//		ACC_RING_SACRIFICE = NewItem(new ItemAccessory("accessory.ring.sacrifice"){
//			@Override
//			public void onWear() {
//				System.out.println("sosai");
//			}
//		});
//		ACC_RING_SACRIFICE.setMaxStackSize(1);
	}

	static CreativeInventoryPlacement.Category cat_misc = new CreativeInventoryPlacement.Category(CreativeInventoryCategory.MISCELLANEOUS);
	static <T extends Item> T NewItem(T item){
		BWS.LOGGER.info("	adding item '" + item.namespaceID.value() + "'");
		CreativeInventoryRegistry.INSTANCE.register(item, cat_misc);

		return new ItemBuilder(BWS.MOD_ID).build(item);
	}

	public static int next_id(){
		return ITEM_ID++;
	}


}
