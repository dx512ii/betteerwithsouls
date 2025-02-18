package dxii.betterwithsouls.item;

import dxii.betterwithsouls.enums.EAccBonus;
import net.minecraft.core.item.Item;

import java.util.Arrays;

public class ItemAccessory extends BWSModItem{

	public EAccBonus bonus;

	public ItemAccessory(String name, String namespaceId, int id, EAccBonus bonus) {
		super(name, namespaceId, id);
		this.bonus = bonus;
		this.maxStackSize = 1;
	}

	/**
	 *
	 * @param inv - any inventory (intended to be used with accessory inventory)
	 * @param bonus - any bonus that is present in the enum
	 * @return i
	 */
	public static boolean hasAccessoryBonus(Item[] inv, EAccBonus bonus){
		boolean r = false;

		for(Item item : inv){
			if(item instanceof ItemAccessory){
				if(((ItemAccessory)item).bonus == bonus){
					r = true;
				}
			}
		}

		return r;
	}
}
