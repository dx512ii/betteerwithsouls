package dxii.betterwithsouls.item;

import dxii.betterwithsouls.gui.ScreenItemDummy;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemDummy extends BWSModItem {
	public ItemDummy(String translationKey, int id) {
		super(translationKey, id);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {

		Minecraft.getMinecraft().displayScreen(new ScreenItemDummy());

		return itemstack;
	}
}
