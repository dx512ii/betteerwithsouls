package dxii.betterwithsouls.mixin;

import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.mixin.accessor.IGuiAccessor;
import dxii.betterwithsouls.mixin.accessor.IScreenAccessor;
import dxii.betterwithsouls.mixin.accessor.IScreenContainerAccessor;
import dxii.betterwithsouls.util.BWSDamageTypes;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.DamageType;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ScreenInventory.class, remap = false)
public class ScreenInventoryMixin {

	@Unique
	public ScreenInventory thisObject = (ScreenInventory)(Object)this;

	@Shadow
	private DamageType hoveredDamageType;
	@Shadow
	protected int armourValuesFloat;
	@Shadow
	protected Color protectionOverlayBgColor;


	/**
	 * @author MEE
	 * @reason I WILL, I WILL CONTINUE THE STRUGGLE!!!!! AND FOR EVERY PRIVATE, FOR EVERY PROTECTED VARIABLE!! FOR EACH AND EVERY ONE NON-PUBLIC METHOD ILL WRITE A MIGHTY ACCESSOR
	 */
	@Overwrite
	public void drawProtectionOverlay(int mouseX, int mouseY) {
		this.hoveredDamageType = null;
		int x = thisObject.width / 2 - this.armourValuesFloat-8;
		int y = thisObject.height / 2 - 75;

		int numDef = 9;

		int w = 45;
		int h = numDef*13;
		GL11.glEnable(0xbe2);
		((IGuiAccessor)this)._drawGradientRect(x, y-10, x + w, y + h, this.protectionOverlayBgColor.getARGB(), this.protectionOverlayBgColor.getARGB());
		thisObject.drawString(((IScreenAccessor)thisObject).getFont(), "def:", x+5, y-8, 0xffffff);
		GL11.glDisable(3042);
		GL11.glDisable(2884);
		int w2 = 26;
		int x2 = x;
		int h2 = 8;
		int i = 0;

		Player ply = ((IScreenAccessor)thisObject).getMC().thePlayer;


		for (DamageType damageType : BWSDamageTypes.values) {
			if (damageType.shouldDisplay()) {
				int y2 = y + i * 12;
				int protection = ((IMob)ply).bws$getMobResist().getDefence(damageType);

				//int l = (int)(protection * 255.0F);
//				int color = 255 - l << 16 | l << 8 | -16777216;
				GL11.glEnable(3553);
				GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
				thisObject.drawGuiIcon(x2 + 2, y2 + 4, 9, 9, TextureRegistry.getTexture(damageType.getIcon()));
				//((IScreenAccessor)thisObject).getFont().drawString(I18n.getInstance().translateKey("gui.inventory.label.crafting"), 106, 16, 4210752);


				thisObject.drawString(((IScreenAccessor)thisObject).getFont(), ": "+ protection, x2+15, y2+5, 0xffffff);

				GL11.glDisable(3553);


//				((IScreenAccessor)thisObject).getFont().drawString(I18n.getInstance().translateKey(damageType.getLanguageKey()), x2+2, y2+2, 0x404040);

				//((IGuiAccessor)thisObject)._drawRectWidthHeight(x2 + 14, y2 + 4, w2 + 2, h2 + 1, 0xffffffff);
//				((IGuiAccessor)thisObject)._drawRectWidthHeight(x2 + 15, y2 + 4, (int)(protection * (float)w2), h2, color);
				if (mouseX >= x2 && mouseY >= y2 + 2 && mouseX <= x2 + w && mouseY <= y2 + 12) {
					this.hoveredDamageType = damageType;
				}
			}

			i++;
		}

		GL11.glEnable(3553);
		if (this.hoveredDamageType != null) {
			int protectionx = Math.round(((IScreenAccessor)thisObject).getMC().thePlayer.inventory.getTotalProtectionAmount(this.hoveredDamageType) * 100.0F);
			if (protectionx < 0) {
				protectionx = 0;
			}

			if (protectionx > 100) {
				protectionx = 100;
			}

			String str = I18n.getInstance().translateDescKey(this.hoveredDamageType.getLanguageKey());
			((IScreenContainerAccessor)thisObject).tooltipElement().render(str, mouseX, mouseY, 8, -8);
		}
	}
}
