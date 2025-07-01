package dxii.betterwithsouls.mixin;


import dxii.betterwithsouls.BWSItems;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Items.class, remap = false)
public class ItemsMixin {

	@Shadow
	public static Item TOOL_SWORD_STONE;


	@Inject(
		method = "setupItems()V",
		at = @At(value = "TAIL")
	)
	private static void overwriteSwords(CallbackInfo ci){
	}


}
