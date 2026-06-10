package dxii.bws.mixin;


import dxii.bws.item.ItemWeapon;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.ItemWandSpawner;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.gamemode.Gamemodes;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(value = ItemWandSpawner.class, remap = false)
public class ItemWandSpawnerMixin {

	@Unique
	public ItemWandSpawner self = (ItemWandSpawner)(Object)this;

	@Unique
	public List<Player> recipients = new ArrayList<>();

	@Inject(
		method = "onUse",
		at = @At(value = "HEAD"))
	public void onUse(ItemStack selfStack, World world, Player player, CallbackInfoReturnable<ItemStack> cir){
		if(player.gamemode == Gamemodes.SURVIVAL){
			recipients.add(player);
		}
		player.gamemode = Gamemodes.CREATIVE;
	}
	@Inject(
		method = "onUse",
		at = @At(value = "RETURN"))
	public void onUse2(ItemStack selfStack, World world, Player player, CallbackInfoReturnable<ItemStack> cir){
		if(recipients.contains(player)){
			player.gamemode = Gamemodes.SURVIVAL;
			recipients.remove(player);
		}
	}


}
