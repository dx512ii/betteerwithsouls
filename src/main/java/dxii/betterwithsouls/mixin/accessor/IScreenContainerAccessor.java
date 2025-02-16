package dxii.betterwithsouls.mixin.accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.ScreenConnectFailed;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ScreenContainerAbstract.class, remap = false)
public interface IScreenContainerAccessor {

	@Accessor("tooltipElement")
	TooltipElement tooltipElement();

}
