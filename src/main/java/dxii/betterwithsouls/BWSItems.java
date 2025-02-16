package dxii.betterwithsouls;



import dxii.betterwithsouls.item.weapon.WeaponShortsword;
import dxii.betterwithsouls.item.weapon.WeaponTomahawk;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.material.ToolMaterial;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.util.ConfigHandler;

import java.util.Properties;

import static dxii.betterwithsouls.BWSMain.MOD_ID;

public class BWSItems {
	public static int ITEM_ID = BWSConfig.CFG.getInt("IDs.startItemID");
	public static final ConfigHandler cfg;

	public static Item TOMAHAWK_STONE;
	public static Item SHORTSWORD_STONE;

	static {
		Properties prop = new Properties();
		prop.setProperty("test", "1");
		cfg = new ConfigHandler(MOD_ID, prop);
	}

	public static void initItems() {
		TOMAHAWK_STONE = new ItemBuilder(MOD_ID)
			.build(new WeaponTomahawk("tomahawk_stone", MOD_ID + (":item/tomahawk_stone"), nextID()).setWeaponDamage(6));
		SHORTSWORD_STONE = new ItemBuilder(MOD_ID)
			.build(new WeaponShortsword(ToolMaterial.stone, "shortsword_stone", MOD_ID + (":item/shortsword_stone"), nextID()).setWeaponDamage(6));

	}

	public static int nextID(){
		return ITEM_ID++;
	}
}
