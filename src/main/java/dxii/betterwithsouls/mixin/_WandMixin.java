package dxii.betterwithsouls.mixin;

import net.minecraft.core.item.ItemWandSpawner;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.gamemode.Gamemode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemWandSpawner.class, remap = false)
public class _WandMixin {
	@Redirect(
		method ="onUseItem",
		at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/player/Player;gamemode:Lnet/minecraft/core/player/gamemode/Gamemode;")
	)
	private Gamemode wandMixin(Player instance){
		return Gamemode.creative;
	}


}
