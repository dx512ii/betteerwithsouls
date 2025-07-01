package dxii.betterwithsouls.mixin;


import com.mojang.nbt.tags.CompoundTag;
import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.enums.EAccBonus;
import dxii.betterwithsouls.enums.EReinforcementType;
import dxii.betterwithsouls.interfaces.IReinforceable;
import dxii.betterwithsouls.item.ItemWeapon;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackMixin implements IReinforceable {

	@Shadow
	@NotNull
	private CompoundTag tag;

	@Unique
	public Entity currentPlayer;

	@Unique
	public ItemStack thisObject = (ItemStack)(Object)this;

	@Unique
	public byte reinforcement = 0;

	@Override
	public byte bws$getReinforcement(){
		return reinforcement;
	}

	@Override
	public void bws$setReinforcement(byte reinforcement){
		this.reinforcement = reinforcement;
	}

	@Override
	public boolean bws$reinforceItem(){
		if(thisObject != null) {
			EReinforcementType upgradeType = ((ItemWeapon) thisObject.getItem()).reinforcementType;
			int maxUpgrade = 1;

			if (upgradeType == EReinforcementType.NORMAL) {
				maxUpgrade = 10;
			} else if (upgradeType == EReinforcementType.UNIQUE) {
				maxUpgrade = 5;
			}

			if (this.reinforcement < maxUpgrade) {
				this.reinforcement++;
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean bws$downgradeItem(){
		if(thisObject != null) {
			if (this.reinforcement > 0) {
				this.reinforcement--;
				return true;
			}
		}

		return false;
	}

	@Inject(
		method = "writeToNBT",
		at = @At(value = "HEAD"))
	public void saveAdditional(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir){
		nbt.putByte("UpgradeAmt", this.reinforcement);
	}

	@Inject(
		method = "readFromNBT",
		at = @At(value = "HEAD"))
	public void loadAdditional(CompoundTag nbt, CallbackInfo ci){
		this.reinforcement = nbt.getByte("UpgradeAmt");;

	}

	//cherry wood grain ring
	@Inject(
		method = "damageItem(ILnet/minecraft/core/entity/Entity;)V",
		at = @At(value = "HEAD"))
	public void cherryRing(int i, Entity entity, CallbackInfo ci){
		currentPlayer = entity;
	}

	@Redirect(
		method = "damageItem(ILnet/minecraft/core/entity/Entity;)V",
		at = @At(value = "INVOKE", target = "net/minecraft/core/item/ItemStack.isItemStackDamageable ()Z")
	)
	private boolean cherryRing2(ItemStack instance){
		if(currentPlayer instanceof Player) {
			boolean cherry = BWSUtils.playerHasAccessoryEffect((Player)currentPlayer, EAccBonus.CHERRYWOOD);
			boolean rand = (Math.random() <= .66);

			return !instance.isItemStackDamageable() || !(cherry & rand);
		}else{
			return !instance.isItemStackDamageable();
		}
	}

//	/**
//	 * @author this mixin is purely to fix upgrade problems on dropped items
//	 * @reason	upon copying, it doesnt copy all the stuff i need
//	 */
//	@Overwrite
//	public ItemStack copy() {
//		ItemStack stack = new ItemStack(thisObject.itemID, thisObject.stackSize, thisObject.getMetadata(), new CompoundTag( thisObject.getData() ));
//		((IReinforceable)(Object)stack).bws$setReinforcement(this.reinforcement);
//		return stack;
//	}

	@Inject(
		method = "copy",
		at = @At(value = "RETURN"), cancellable = true)
	public void copyAdditional(CallbackInfoReturnable<ItemStack> cir){
		ItemStack stack = cir.getReturnValue();
		((IReinforceable)(Object)stack).bws$setReinforcement(this.reinforcement);
		cir.setReturnValue(stack);
	}

	@Inject(
		method = "splitStack",
		at = @At(value = "RETURN"), cancellable = true)
	public void splitAdditional(CallbackInfoReturnable<ItemStack> cir){
		ItemStack stack = cir.getReturnValue();
		((IReinforceable)(Object)stack).bws$setReinforcement(this.reinforcement);
		cir.setReturnValue(stack);
	}



}
