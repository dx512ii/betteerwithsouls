package dxii.betterwithsouls.util;

import net.minecraft.core.util.helper.DamageType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static dxii.betterwithsouls.BWSMain.MOD_ID;


public class BWSDamageTypes {
	public static final DamageType SLASH = new DamageType("damagetype.slash", true, true, "betterwithsouls:gui/dmg_slash");
	public static final DamageType STRIKE = new DamageType("damagetype.strike", true, true, "betterwithsouls:gui/dmg_strike");
	public static final DamageType THRUST = new DamageType("damagetype.thrust", true, true, "betterwithsouls:gui/dmg_thrust");

	public static final DamageType MAGIC = new DamageType("damagetype.magic", true, true, "betterwithsouls:gui/dmg_magic");
	public static final DamageType FIRE = new DamageType("damagetype.fire", true, true, "betterwithsouls:gui/dmg_fire");
	public static final DamageType LIGHTNING = new DamageType("damagetype.lightning", true, true, "betterwithsouls:gui/dmg_light");
	public static final DamageType DARK = new DamageType("damagetype.dark", true, true, "betterwithsouls:gui/dmg_dark");

	public static final DamageType POISON = new DamageType("damagetype.poison", true, true, "betterwithsouls:gui/dmg_poison");
	public static final DamageType BLOOD = new DamageType("damagetype.blood", true, true, "betterwithsouls:gui/dmg_blood");

	public static final List<DamageType> values = new ArrayList();

	public String getDesc(DamageType type){
		return type.getLanguageKey()+".desc";
	}

	static{
		values.add(SLASH);
		values.add(STRIKE);
		values.add(THRUST);

		values.add(MAGIC);
		values.add(FIRE);
		values.add(LIGHTNING);
		values.add(DARK);

		values.add(POISON);
		values.add(BLOOD);
	}
}
