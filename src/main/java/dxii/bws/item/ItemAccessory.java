package dxii.bws.item;

import net.minecraft.core.lang.I18n;
import org.jetbrains.annotations.NotNull;

public class ItemAccessory extends ItemBWS{
	public Bonus bonus = Bonus.NONE;

	public ItemAccessory(@NotNull String name, Bonus bonus) {
		super(name);
		this.bonus = bonus;
		this.setMaxStackSize(1);
	}

	public void onWear(){

	}
	public void onWearOff(){

	}

	public enum Bonus{
		NONE("none", true),

		INVIS("invis", false), // MOBS CAN SPOT ONLY FROM 2 BLOCKS DISTANCE, visual transparency

		DOUBLEJUMP("doublejump", false), //self explanatory

		FEATHERFALL("featherfall", false), //self explanatory

		RTSR("rtsr", false),// +50% ATK DMG IF HP < 20% (2 HEARTS)

		KNOCKBACK1("knockback1", false), //+50% KNOCKBACK

		NOSTEPS("nosteps", false), // DRAGONCREST RING, NO STEP SOUNDS, harder to get spotted by enemies

		ALLTARGET("alltarget", false), //ENEMY ATTRACTION

		FLAT_DMG_BONUS("flat_fmg_bonus", false), //+2 FLAT DAMAGE

		DMG_RESIST1("dmg_resist1", false), // +10% DAMAGE RESISTANCE, 5% MOVESPEED PENALTY
		DMG_RESIST2("dmg_resist2", false), // +20% DAMAGE RESISTANCE, 10% MOVESPEED PENALTY
		DMG_RESIST3("dmg_resist3", false), // +50% DAMAGE RESISTANCE, 25% MOVESPEED PENALTY

		LIQUID_MOBILITY("liquid_mobility", false), //EXTRA MOBILITY IN LIQUIDS

		SPEED_BONUS1("speed_bonus1", false), //EXTRA SPEED
		SPEED_BONUS2("speed_bonus2", false), //EXTRA SPEED
		SPEED_BONUS3("speed_bonus3", false), //EXTRA SPEED

		SACRIFICE("sacrifice", false), //NO ITEM DROP UPON DEATH
		SACRIFICE_BROKEN("sacrifice", false), //THE RING WONT DROP UPON DEATH
		SACRIFICE_RARE("sacrifice_rare", false), //SAME AS SACRIFICE, BUT DOESNT BREAK
		DURABILITY_BONUS("sacrifice_bonus", false), //increased tools' durability

		// INSTAND FOOD HEALING (gradual heal must be enforced) +50% healing bonus from food
		// HUNGER CURSE: you can only eat meat, not eating anyhing for a day will cause health drain
		HUNGER_CURSE("hunger_curse", false),

		DMG_BONUS_RANGED("dmg_bonus_ranged", false), //DAMAGE +25, PROJECTILES SPEED + 50%

		PARRY_DMG_BONUS("parry_dmg_bonus", false), //2x RIPOSTE DAMAGE ON PARRIED ENEMIES

		DODGE("dodge", false), //enables TIER 1 DODGING

		FROGLEG("frogleg", false), //100% HIGHER JUMPING, more blocks to fall to get damage

		DROPRATE("droprate", false), //50% chance to get extra loot FROM ENEMIES

		SOULSUCK("soulsuck", false), //souls will automatically appear in the player's inventory

		DEF_FIRE("def_fire", false), //50% FIRE RESISTANCE

		EVILEYE("evileye", false), //bit of health every few kills
		;

		public final String descKey;
		public final boolean stackable;

		public String getTranslatedDesc(){
			return I18n.getInstance().translateKey(this.descKey);
		}

		Bonus(String descKey, boolean stackable){
			this.descKey = "accessory_effect." + descKey + ".desc";
			this.stackable = stackable;

			System.out.println(this.descKey);
		}
	}
}
