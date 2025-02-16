//package dxii.betterwithsouls.ui;
//
//import dxii.betterwithsouls.mixin.accessor.IScreenAccessor;
//import dxii.betterwithsouls.mixin.accessor.IScreenContainerAccessor;
//import dxii.betterwithsouls.mixin.accessor.IScreenInvAccessor;
//import net.minecraft.client.gui.container.ScreenInventory;
//import net.minecraft.client.render.texture.stitcher.TextureRegistry;
//import net.minecraft.core.entity.player.Player;
//import net.minecraft.core.lang.I18n;
//import net.minecraft.core.util.helper.DamageType;
//import org.lwjgl.opengl.GL11;
//
//public class ScreenInventoryMod extends ScreenInventory {
//	public ScreenInventoryMod(Player player) {
//		super(player);
//	}
//
//	@Override
//	public void drawProtectionOverlay(int mouseX, int mouseY) {
//		((IScreenInvAccessor)this).setHoveredDamageType(null);
//		int x = this.width / 2 - this.armourValuesFloat - 4;
//		int y = this.height / 2 - 79;
//		int w = 44;
//		int h = 44;
//		GL11.glEnable(3042);
//		this.drawGradientRect(x, y, x + w, y + h, this.protectionOverlayBgColor.getARGB(), this.protectionOverlayBgColor.getARGB());
//		GL11.glDisable(3042);
//		GL11.glDisable(2884);
//		int w2 = 26;
//		int x2 = x;
//		int h2 = 4;
//		int i = 0;
//
//		for (DamageType damageType : DamageType.values()) {
//			if (damageType.shouldDisplay()) {
//				int y2 = y + i * 10;
//				float protection = this.mc.thePlayer.inventory.getTotalProtectionAmount(damageType);
//				if (protection > 1.0F) {
//					protection = 1.0F;
//				}
//
//				int l = (int)(protection * 255.0F);
//				int color = 255 - l << 16 | l << 8 | 0xFF000000;
//				GL11.glEnable(3553);
//				GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
//				this.drawGuiIcon(x2 + 2, y2 + 2, 9, 9, TextureRegistry.getTexture(damageType.getIcon()));
//				GL11.glDisable(3553);
//				this.drawRectWidthHeight(x2 + 14, y2 + 4, w2 + 2, h2 + 1, -16777216);
//				this.drawRectWidthHeight(x2 + 15, y2 + 4, (int)(protection * (float)w2), h2, color);
//				if (mouseX >= x2 && mouseY >= y2 + 2 && mouseX <= x2 + w && mouseY <= y2 + 12) {
//					((IScreenInvAccessor)this).setHoveredDamageType(damageType);
//				}
//			}
//
//			i++;
//		}
//
//		GL11.glEnable(3553);
//		if (((IScreenInvAccessor)this).getHoveredDamageType() != null) {
//			int protectionx = Math.round(this.mc.thePlayer.inventory.getTotalProtectionAmount(((IScreenInvAccessor)this).getHoveredDamageType()) * 100.0F);
//			if (protectionx < 0) {
//				protectionx = 0;
//			}
//
//			if (protectionx > 100) {
//				protectionx = 100;
//			}
//
//			String str = I18n.getInstance().translateKey(((IScreenInvAccessor)this).getHoveredDamageType().getLanguageKey()) + ":\n" + protectionx + " / 100";
//			((IScreenContainerAccessor)this).tooltipElement().render(str, mouseX, mouseY, 8, -8);
//		}
//	}
//}
