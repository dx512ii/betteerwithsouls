package dxii.betterwithsouls.item;

import dxii.betterwithsouls.enums.EAccBonus;
import dxii.betterwithsouls.interfaces.IInventory;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;

public class ItemAccessory extends BWSModItem{

	public EAccBonus bonus;

	public ItemAccessory(String name, int id, EAccBonus bonus) {
		super(name, id);
		this.bonus = bonus;
		this.maxStackSize = 1;
	}

	/**
	 *
	 * @param inv - any inventory (intended to be used with accessory inventory)
	 * @param bonus - any bonus that is present in the enum
	 * @return boolean
	 */
	public static boolean hasAccessoryBonus(ContainerInventory inv, EAccBonus bonus){
		ItemStack[] accInv = ((IInventory)inv).bws$getAccInv();
		boolean has = false;
		if(accInv == null){
			return has;
		}
		for(ItemStack stack: accInv){
			if(stack != null) {
				Item i = stack.getItem();
				if (i instanceof ItemAccessory && ((ItemAccessory) i).bonus == bonus) {
					has = true;
					break;
				}
			}
		}
		return has;
	}
}
