package dxii.betterwithsouls.mixin.inventory;




import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import dxii.betterwithsouls.BWSItems;
import dxii.betterwithsouls.enums.EAccBonus;
import dxii.betterwithsouls.interfaces.IInventory;
import dxii.betterwithsouls.item.BWSItemArmor;
import dxii.betterwithsouls.item.ItemAccessory;
import dxii.betterwithsouls.item.ItemWeapon;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

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
	 * @author
	 * @reason
	 */
	@Overwrite
	public float getTotalProtectionAmount(DamageType damageType) {
		float resist = 0.0F;

		for(int i = 0; i < thisObject.armorInventory.length; ++i) {
			ItemStack itemStack = thisObject.armorInventory[i];
			if (itemStack != null && itemStack.getItem() instanceof BWSItemArmor) {
				IArmorItem armor = (IArmorItem)itemStack.getItem();
				BWSItemArmor armorbws = (BWSItemArmor)itemStack.getItem();
				if (armor.getArmorPiece() == i) {
					resist += armorbws.resists.getDefence(damageType);
				}
			}
		}

		return resist > 0 ? MathHelper.clamp(resist, 1, resist) : 0;
	}

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
		if (this.accInventory[0] != null && ((ItemAccessory) (this.accInventory[0].getItem())).bonus == EAccBonus.SACRIFICE_RARE) {
			sacrifice = true;
			rare = true;
		}else if (this.accInventory[1] != null && ((ItemAccessory) (this.accInventory[1].getItem())).bonus == EAccBonus.SACRIFICE_RARE) {
			sacrifice = true;
			rare = true;
			accSlot = 1;
		}else if (this.accInventory[2] != null && ((ItemAccessory) (this.accInventory[2].getItem())).bonus == EAccBonus.SACRIFICE_RARE) {
			sacrifice = true;
			rare = true;
			accSlot = 2;
		}else if (this.accInventory[3] != null && ((ItemAccessory) (this.accInventory[3].getItem())).bonus == EAccBonus.SACRIFICE_RARE) {
			sacrifice = true;
			rare = true;
			accSlot = 3;
		}

		if(sacrifice){
			if(!rare) {
				this.accInventory[accSlot] = new ItemStack(BWSItems.SACRIFICE_RING_BROKEN, 1);
				thisObject.player.world.playSoundEffect(thisObject.player, SoundCategory.ENTITY_SOUNDS, thisObject.player.x, thisObject.player.y, thisObject.player.z, MOD_ID+":ring.break", 1, 1);
			}
			thisObject.player.world.playSoundEffect(thisObject.player, SoundCategory.ENTITY_SOUNDS, thisObject.player.x, thisObject.player.y, thisObject.player.z, MOD_ID+":ring.break", 1, 1);
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
		ItemStack currentStack = thisObject.mainInventory[MathHelper.clamp(this.currentItem-i, 0, 35)];
		if (prevStack != null && prevStack.getItem() instanceof ItemWeapon) {
			((ItemWeapon)prevStack.getItem()).holster(prevStack, this.player.world, this.player);
		}
		if (currentStack != null && currentStack.getItem() instanceof ItemWeapon){
			((ItemWeapon)currentStack.getItem()).deploy(prevStack, this.player.world, this.player);
		}
	}

	/**
	 * @author
	 * @reason
	 */
	@Nullable
	@Overwrite
	public ItemStack getItem(int i) {
		ItemStack[] aitemstack = thisObject.mainInventory;

		if(i >= aitemstack.length + thisObject.armorInventory.length){
			i -= aitemstack.length + thisObject.armorInventory.length;
			aitemstack = this.accInventory;
		}
		if (i >= aitemstack.length) {

			i -= aitemstack.length;
			aitemstack = thisObject.armorInventory;
		}
		return aitemstack[i];
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public void setItem(int index, @Nullable ItemStack itemstack) {
		ItemStack[] aitemstack = thisObject.mainInventory;

		if(index >= aitemstack.length + thisObject.armorInventory.length){
			index -= aitemstack.length + thisObject.armorInventory.length;
			aitemstack = this.accInventory;
		}
		if (index >= aitemstack.length) {
			index -= aitemstack.length;
			aitemstack = thisObject.armorInventory;
		}

		aitemstack[index] = itemstack;
	}



	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public ListTag writeToNBT(ListTag nbttaglist) {
		CompoundTag nbttagcompound1;
		for(int j = 0; j < thisObject.mainInventory.length; ++j) {
			if (thisObject.mainInventory[j] != null) {
				nbttagcompound1 = new CompoundTag();
				nbttagcompound1.putByte("Slot", (byte)j);
				thisObject.mainInventory[j].writeToNBT(nbttagcompound1);
				nbttaglist.addTag(nbttagcompound1);
			}
		}

		for(int j = 0; j < thisObject.armorInventory.length; ++j) {
			if (thisObject.armorInventory[j] != null) {
				nbttagcompound1 = new CompoundTag();
				nbttagcompound1.putByte("Slot", (byte)(j + 100));
				thisObject.armorInventory[j].writeToNBT(nbttagcompound1);
				nbttaglist.addTag(nbttagcompound1);
			}
		}
		for (int j = 0; j < this.accInventory.length; ++j) {
			if (this.accInventory[j] == null) continue;
			CompoundTag nbttagcompound2 = new CompoundTag();
			nbttagcompound2.putInt("Acc", j + 512 );
			this.accInventory[j].writeToNBT(nbttagcompound2);
			nbttaglist.addTag(nbttagcompound2);
		}

		return nbttaglist;
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public void readFromNBT(ListTag nbttaglist) {
		thisObject.mainInventory = new ItemStack[36];
		thisObject.armorInventory = new ItemStack[4];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			CompoundTag nbttagcompound = (CompoundTag)nbttaglist.tagAt(i);
			ItemStack itemstack = ItemStack.readItemStackFromNbt(nbttagcompound);
			int g = nbttagcompound.getInteger("Acc");
			if (itemstack == null) continue;
			if (g >= 512) {
				this.accInventory[g - 512] = itemstack;
			}
			int j = nbttagcompound.getByte("Slot") & 0xFF;
			if (j >= 0 && j < thisObject.mainInventory.length && g < 512) {
				thisObject.mainInventory[j] = itemstack;
			}
			if (j < 100 || j >= thisObject.armorInventory.length + 100) continue;
			thisObject.armorInventory[j - 100] = itemstack;
		}

	}

	/**
	 * @author
	 * @reason
	 */
	@Nullable
	@Overwrite
	public ItemStack removeItem(int index, int takeAmount) {
		ItemStack[] aitemstack = thisObject.mainInventory;

		if(index >= aitemstack.length + thisObject.armorInventory.length){
			index -= aitemstack.length + thisObject.armorInventory.length;
			aitemstack = this.accInventory;
		}
		if (index >= thisObject.mainInventory.length) {
			aitemstack = thisObject.armorInventory;
			index -= thisObject.mainInventory.length;
		}
		if (aitemstack[index] != null) {
			if (aitemstack[index].stackSize <= takeAmount) {
				ItemStack itemstack = aitemstack[index];
				aitemstack[index] = null;
				return itemstack;
			} else {
				ItemStack itemstack1 = aitemstack[index].splitStack(takeAmount);
				if (aitemstack[index].stackSize <= 0) {
					aitemstack[index] = null;
				}

				return itemstack1;
			}
		} else {
			return null;
		}
	}

	@Inject(
		method = "transferAllContents",
		at = @At(value = "TAIL")
	)
	public void transferAdditional(ContainerInventory inventoryPlayer, CallbackInfo ci){
		for (int i = 0; i < this.accInventory.length; ++i) {
			this.accInventory[i] = ((IInventory)(inventoryPlayer)).bws$getAccInv()[i];
			((IInventory)(inventoryPlayer)).bws$getAccInv()[i] = null;
		}
	}



}
