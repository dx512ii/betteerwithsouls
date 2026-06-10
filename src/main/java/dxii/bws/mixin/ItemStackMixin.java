package dxii.bws.mixin;

import dxii.bws.BWS;
import dxii.bws.item.IItemStackExtra;
import dxii.bws.item.ItemWeapon;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ItemStack.class, remap = false)
public class ItemStackMixin implements IItemStackExtra {

	@Unique
	public ItemStack self = (ItemStack)(Object)this;

	@Unique
	public int reinforcement = 0;


	@Override
	public void reinforceWeapon() {
		if( !(self.getItem() instanceof ItemWeapon) ){
			return;
		}

		this.reinforcement = Math.max(this.reinforcement + 1, BWS.MAX_WEAPONS_REINFORCEMENT);
	}

	@Override
	public int weaaponGetReinforcement() {
		return this.reinforcement;
	}
}
