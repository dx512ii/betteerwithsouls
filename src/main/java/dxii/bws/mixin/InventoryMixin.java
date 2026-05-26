package dxii.bws.mixin;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import dxii.bws.BWS;
import dxii.bws.item.ItemWeapon;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerInventory.class)
public class InventoryMixin {

	@Unique
	public ContainerInventory self = (ContainerInventory)(Object)this;
	@Unique
	public ItemStack[] accessories = new ItemStack[4];

	@Unique
	public int mainInventoryLen(){
		return self.mainInventory.length + self.armorInventory.length;
	}
	@Unique
	public boolean slotIsAccessory(int slot){
		return slot > mainInventoryLen();
	}


	@Inject(
		method = "setItem",
		at = @At(value = "HEAD"), cancellable = true)
	public void setItemCheck(int slot, @Nullable ItemStack stack, CallbackInfo ci){
		if(slotIsAccessory(slot)) {
			accessories[slot - mainInventoryLen()] = stack;

			ci.cancel();
		}
	}
	@Inject(
		method = "removeItem",
		at = @At(value = "HEAD"), cancellable = true)
	public void removeItemCheck(int slot, int takeAmount, CallbackInfoReturnable<ItemStack> ci){
		if(slotIsAccessory(slot)) {
			ItemStack currentStack = accessories[slot];
			if (currentStack != null) {
				if (currentStack.stackSize <= takeAmount) {
					accessories[slot] = null;

					ci.setReturnValue(currentStack);
				} else {
					ItemStack otherHalf = currentStack.splitStack(takeAmount);
					if (currentStack.stackSize <= 0) {
						accessories[slot] = null;
					}

					ci.setReturnValue(otherHalf);
				}
			}

			ci.setReturnValue(null);
		}
	}

	@Inject(
		method = "save",
		at = @At(value = "HEAD"))
	public void saveExtra(ListTag parentTag, CallbackInfoReturnable<ListTag> ci){
		for(int i = 0; i < this.accessories.length; ++i) {
			ItemStack stack = this.accessories[i];
			if (stack != null) {
				CompoundTag itemTag = new CompoundTag();
				itemTag.putByte("SlotAccessory", (byte)i);
				stack.writeToNBT(itemTag);
				parentTag.addTag(itemTag);
			}
		}
	}
	@Inject(
		method = "load",
		at = @At(value = "HEAD"))
	public void loadExtra(ListTag parentTag, CallbackInfo ci){
		this.accessories = new ItemStack[4];

		for(int i = 0; i < parentTag.tagCount(); ++i) {
			CompoundTag itemTag = (CompoundTag)parentTag.tagAt(i);
			int slot = itemTag.getByteOrDefault("SlotAccessory", (byte)100) & 255;
			if(slot > this.accessories.length)
				continue;

			ItemStack stack = ItemStack.readItemStackFromNbt(itemTag);
			if (stack != null) {
				this.accessories[slot] = stack;
			}
		}
	}

	@Inject(
		method = "setCurrentSlot",
		at = @At(value = "HEAD"), cancellable = true)
	public void setSlotCallback(int nextSlot, boolean overrideLock, CallbackInfo ci){
		if( BWS.getMinecraftExtra().playerIsAttacking() ){
			ci.cancel();
		}else{
			ItemWeapon wepPre = BWS.itemAsWeapon(self.mainInventory[self.getCurrentSlot()]);
			if(wepPre != null)
				wepPre.holster(self.player, self.player.world);

			ItemWeapon wepCur = BWS.itemAsWeapon(self.mainInventory[nextSlot]);
			if(wepCur != null)
				wepCur.deploy(self.player, self.player.world);
		}
	}

	@Inject(
		method = "setCurrentItem",
		at = @At(value = "HEAD"), cancellable = true)
	public void setItemCallback(ItemStack stack, CallbackInfoReturnable<Integer> ci){
		if( BWS.getMinecraftExtra().playerIsAttacking() ){
			ci.cancel();
		}else{
			ItemWeapon wepPre = BWS.itemAsWeapon(self.mainInventory[self.getCurrentSlot()]);
			if(wepPre != null)
				wepPre.holster(self.player, self.player.world);

			ItemWeapon wepCur = BWS.itemAsWeapon(stack);
			if(wepCur != null)
				wepCur.deploy(self.player, self.player.world);
		}
	}

	@Inject(
		method = "transferAllContents",
		at = @At(value = "TAIL")
	)
	public void transferAdditional(ContainerInventory inventoryPlayer, CallbackInfo ci){
		for (int i = 0; i < this.accessories.length; ++i) {
			this.accessories[i] = BWS.getAccessories(inventoryPlayer)[i];
			BWS.getAccessories(inventoryPlayer)[i] = null;
		}
	}
}
