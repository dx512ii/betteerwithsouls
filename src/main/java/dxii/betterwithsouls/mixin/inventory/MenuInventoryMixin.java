package dxii.betterwithsouls.mixin.inventory;


import dxii.betterwithsouls.gui.SlotAccessory;
import dxii.betterwithsouls.mixin.accessor.IAMenuAbstract;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = MenuInventory.class, remap = false)
public abstract class MenuInventoryMixin extends MenuAbstract {

	@Unique
	public MenuInventory thisObject = (MenuInventory) (Object)this;

	@Inject(
		method = "<init>(Lnet/minecraft/core/player/inventory/container/ContainerInventory;Z)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/menu/MenuInventory;slotsChanged(Lnet/minecraft/core/player/inventory/container/Container;)V", shift = At.Shift.BEFORE))
	public void addAccSlots(ContainerInventory inventory, boolean active, CallbackInfo ci){
		for (int i = 0; i < 4; ++i) {
			this.addSlot(new SlotAccessory(inventory, 40 + i, 88 + i*18, 64));
		}
	}
}
