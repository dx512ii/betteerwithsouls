package dxii.betterwithsouls.mixin.inventory;




import dxii.betterwithsouls.BWSItems;
import dxii.betterwithsouls.enums.EAccBonus;
import dxii.betterwithsouls.interfaces.IInventory;
import dxii.betterwithsouls.item.ItemAccessory;
import dxii.betterwithsouls.item.ItemWeapon;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ContainerInventory.class, remap = false)
public class ContainerInventoryMixin implements IInventory {
	@Shadow
	public Player player;

	@Unique
	public ItemStack[] accInventory = new ItemStack[4];

	@Unique
	protected ContainerInventory thisObject = (ContainerInventory)(Object)this ;

	@Override
	public ItemStack[] bws$getAccInv(){
		return this.accInventory;
	}

	@Shadow
	protected int currentItem;


	/**
	 * @author author
	 * @reason reason :troll:
	 */
	@Overwrite
	public void dropAllItems() {
		boolean sacrifice = false;
		boolean rare = false;
		int accSlot = 0;

		if (this.accInventory[0] != null && ((ItemAccessory) (this.accInventory[0].getItem())).bonus == EAccBonus.SACRIFICE) {
			sacrifice = true;
		}else if (this.accInventory[1] != null && ((ItemAccessory) (this.accInventory[1].getItem())).bonus == EAccBonus.SACRIFICE) {
			sacrifice = true;
			accSlot = 1;
		}else if (this.accInventory[2] != null && ((ItemAccessory) (this.accInventory[2].getItem())).bonus == EAccBonus.SACRIFICE) {
			sacrifice = true;
			accSlot = 2;
		}else if (this.accInventory[3] != null && ((ItemAccessory) (this.accInventory[3].getItem())).bonus == EAccBonus.SACRIFICE) {
			sacrifice = true;
			accSlot = 3;
		}
		if (this.accInventory[0] != null && ((ItemAccessory) (this.accInventory[0].getItem())).bonus == EAccBonus.SACRIFICE) {
			rare = true;
		}else if (this.accInventory[1] != null && ((ItemAccessory) (this.accInventory[1].getItem())).bonus == EAccBonus.SACRIFICE) {
			rare = true;
			accSlot = 1;
		}else if (this.accInventory[2] != null && ((ItemAccessory) (this.accInventory[2].getItem())).bonus == EAccBonus.SACRIFICE) {
			rare = true;
			accSlot = 2;
		}else if (this.accInventory[3] != null && ((ItemAccessory) (this.accInventory[3].getItem())).bonus == EAccBonus.SACRIFICE) {
			rare = true;
			accSlot = 3;
		}

		if(sacrifice){
			if(!rare) {
				this.accInventory[accSlot] = new ItemStack(BWSItems.SACRIFICE_RING_BROKEN, 1);
			}
			thisObject.player.world.playSoundEffect(thisObject.player, SoundCategory.ENTITY_SOUNDS, thisObject.player.x, thisObject.player.y, thisObject.player.z, "dxiimod.break_ring", 1, 1);
		}else {
			for (int i = 0; i < thisObject.mainInventory.length; ++i) {
				if (thisObject.mainInventory[i] == null || thisObject.mainInventory[i].getItem() == BWSItems.SACRIFICE_RING_BROKEN) continue;
				thisObject.player.dropPlayerItemWithRandomChoice(thisObject.mainInventory[i], true);
				thisObject.mainInventory[i] = null;
			}
			for (int j = 0; j < thisObject.armorInventory.length; ++j) {
				if (thisObject.armorInventory[j] == null) continue;
				thisObject.player.dropPlayerItemWithRandomChoice(thisObject.armorInventory[j], true);
				thisObject.armorInventory[j] = null;
			}
			for (int g = 0; g < this.accInventory.length; ++g) {
				if (this.accInventory[g] == null || this.accInventory[g].getItem() == BWSItems.SACRIFICE_RING_BROKEN) continue;
				thisObject.player.dropPlayerItemWithRandomChoice(this.accInventory[g], true);
				this.accInventory[g] = null;
			}
		}
	}

	@Inject(
		method = "setCurrentItemIndex",
		at = @At(value = "FIELD", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;currentItem:I"))
	public void deployHolster(int index, boolean overrideLock, CallbackInfo ci){
		ItemStack prevStack = thisObject.mainInventory[this.currentItem];
		ItemStack currentStack = thisObject.mainInventory[index];
		if (prevStack != null && prevStack.getItem() instanceof ItemWeapon) {
			((ItemWeapon)prevStack.getItem()).holster(prevStack, this.player.world, this.player);
		}
		if (currentStack != null && currentStack.getItem() instanceof ItemWeapon){
			((ItemWeapon)currentStack.getItem()).deploy(currentStack, this.player.world, this.player);
		}
	}

	@Inject(
		method = "changeCurrentItem",
		at = @At(value = "FIELD", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;currentItem:I", ordinal = 0))
	public void deployHolster2(int i, CallbackInfo ci){
		ItemStack prevStack = thisObject.mainInventory[this.currentItem];
		ItemStack currentStack = thisObject.mainInventory[MathHelper.clamp(this.currentItem-i, 0, this.currentItem-i)];
		if (prevStack != null && prevStack.getItem() instanceof ItemWeapon) {
			((ItemWeapon)prevStack.getItem()).holster(prevStack, this.player.world, this.player);
		}
		if (currentStack != null && currentStack.getItem() instanceof ItemWeapon){
			((ItemWeapon)currentStack.getItem()).deploy(prevStack, this.player.world, this.player);
		}
	}


}
