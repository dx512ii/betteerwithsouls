package dxii.betterwithsouls.gui;

import dxii.betterwithsouls.enums.EAccBonus;
import dxii.betterwithsouls.item.ItemAccessory;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class SlotAccessory extends Slot {

	public ContainerInventory inventory;

	public SlotAccessory(Container container, int index, int x, int y) {
		super(container, index, x, y);
		if(container instanceof ContainerInventory) {
			this.inventory = (ContainerInventory) container;
		}
	}

	@Override
	public boolean mayPlace(ItemStack itemstack) {
		boolean samebonus = false;
		if(itemstack != null) {
			if (itemstack.getItem() instanceof ItemAccessory) {
				EAccBonus bonus = ((ItemAccessory) itemstack.getItem()).bonus;
				samebonus = ItemAccessory.hasAccessoryBonus(this.inventory, bonus);
			}
			return itemstack.getItem() instanceof ItemAccessory && !samebonus;
		}
		return false;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}


}
