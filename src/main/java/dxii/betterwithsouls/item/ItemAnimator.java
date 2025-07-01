package dxii.betterwithsouls.item;



import dxii.betterwithsouls.gui.ScreenPoser;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemAnimator extends BWSModItem {
	public ItemAnimator(String translationKey, int id) {
		super(translationKey, id);
		this.maxStackSize = 1;
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {

		Minecraft mc = Minecraft.getMinecraft();
		mc.displayScreen(new ScreenPoser());
		return itemstack;
	}
}
