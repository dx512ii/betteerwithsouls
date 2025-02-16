package dxii.betterwithsouls.mixin.accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.render.Font;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(value = Screen.class, remap = false)
public interface IScreenAccessor {

	@Accessor("mc")
	Minecraft getMC();

	@Accessor("font")
	Font getFont();

}
