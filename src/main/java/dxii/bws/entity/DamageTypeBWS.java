package dxii.bws.entity;

import dxii.bws.BWS;
import net.minecraft.core.util.helper.DamageType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamageTypeBWS {
	public static final List<DamageTypeBWS> values = new ArrayList<>();

	public boolean shouldDamageArmor;
	public boolean shouldDisplay;
	public String languageKey;
	public String icon;

	public DamageTypeBWS(boolean shouldDamageArmor, boolean shouldDisplay){
		this.shouldDamageArmor = shouldDamageArmor;
		this.shouldDisplay = shouldDisplay;

		values.add(this);
	}
	public DamageTypeBWS visuals(String languageKey, String icon){
		this.languageKey = languageKey;
		this.icon = icon;

		return this;
	}

	public static DamageTypeBWS GENERIC = new DamageTypeBWS(false, false);

	public static DamageTypeBWS PHYS_SLASH = new DamageTypeBWS(true, true)
		.visuals("damagetype.slash", BWS.MOD_ID+":gui/dmg_slash");

	public static DamageTypeBWS PHYS_STRIKE = new DamageTypeBWS(true, true)
		.visuals("damagetype.strike", BWS.MOD_ID+":gui/dmg_strike");

	public static DamageTypeBWS PHYS_THRUST = new DamageTypeBWS(true, true)
		.visuals("damagetype.thrust", BWS.MOD_ID+":gui/dmg_thrust");

	public static DamageTypeBWS ELEMENT_MAGIC = new DamageTypeBWS(true, true)
		.visuals("damagetype.thrust", BWS.MOD_ID+":gui/dmg_thrust");

	public static DamageTypeBWS ELEMENT_FIRE = new DamageTypeBWS(true, true)
		.visuals("damagetype.thrust", BWS.MOD_ID+":gui/dmg_thrust");

	public static DamageTypeBWS ELEMENT_LIGHTNING = new DamageTypeBWS(true, true)
		.visuals("damagetype.thrust", BWS.MOD_ID+":gui/dmg_thrust");

	public static DamageTypeBWS ELEMENT_DARK = new DamageTypeBWS(true, true)
		.visuals("damagetype.thrust", BWS.MOD_ID+":gui/dmg_thrust");

	public static DamageTypeBWS DEBUFF_POISON = new DamageTypeBWS(true, true)
		.visuals("damagetype.thrust", BWS.MOD_ID+":gui/dmg_thrust");

	public static DamageTypeBWS DEBUFF_BLOOD = new DamageTypeBWS(true, true)
		.visuals("damagetype.thrust", BWS.MOD_ID+":gui/dmg_thrust");



	public static final DamageType MAGIC = new DamageType("damagetype.magic", true, true, BWS.MOD_ID+":gui/dmg_magic");
	public static final DamageType FIRE = new DamageType("damagetype.fire", false, true, BWS.MOD_ID+":gui/dmg_fire");
	public static final DamageType LIGHTNING = new DamageType("damagetype.lightning", true, true, BWS.MOD_ID+":gui/dmg_light");
	public static final DamageType DARK = new DamageType("damagetype.dark", true, true, BWS.MOD_ID+":gui/dmg_dark");

	public static final DamageType POISON = new DamageType("damagetype.poison", false, true, BWS.MOD_ID+":gui/dmg_poison");
	public static final DamageType BLOOD = new DamageType("damagetype.blood", false, true, BWS.MOD_ID+":gui/dmg_blood");

}
