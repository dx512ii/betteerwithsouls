package dxii.betterwithsouls.mixin.accessor;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Gui.class, remap = false)
public interface IGuiAccessor {

	@Invoker("drawGradientRect")
	void _drawGradientRect(int minX, int minY, int maxX, int maxY, int argb1, int argb2);

	@Invoker("drawRectWidthHeight")
	void _drawRectWidthHeight(int x, int y, int width, int height, int argb);

}
