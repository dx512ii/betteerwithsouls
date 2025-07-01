package dxii.betterwithsouls.mixin.inventory;

import dxii.betterwithsouls.BWSConfig;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.interfaces.IPlayer;
import dxii.betterwithsouls.mixin.accessor.IGuiAccessor;
import dxii.betterwithsouls.mixin.accessor.IScreenAccessor;
import dxii.betterwithsouls.mixin.accessor.IScreenContainerAccessor;
import dxii.betterwithsouls.util.BWSDamageTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScreenInventory.class, remap = false)
public class ScreenInventoryMixin extends Screen {

	@Unique
	public ScreenInventory thisObject = (ScreenInventory)(Object)this;

	@Shadow
	private DamageType hoveredDamageType;
	@Shadow
	protected int armourValuesFloat;
	@Shadow
	protected Color protectionOverlayBgColor;

	@Shadow
	private ButtonElement armorButton;


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
		Player ply = Minecraft.getMinecraft().thePlayer;

		int w = 45;
		int h = numDef*13+15;
		GL11.glEnable(0xbe2);
		((IGuiAccessor)this)._drawGradientRect(x, y-10, x + w, y + h, this.protectionOverlayBgColor.getARGB(), this.protectionOverlayBgColor.getARGB());
		thisObject.drawString(((IScreenAccessor)thisObject).getFont(), "§ndef:", x+2, y-8, 0xffffff);

		float load = ((IPlayer)ply).getLoadDisplay();
		float coeff = load/BWSConfig.PLAYER_LOAD_DEFAULT;
		int loadCol = 0xffffff;

		if(coeff > BWSConfig.playerLoadLevelMedium && coeff < BWSConfig.playerLoadLevelHeavy){
			loadCol = 0xdede50;
		}else if(coeff > BWSConfig.playerLoadLevelHeavy && coeff < BWSConfig.playerLoadLevelOverweight){
			loadCol = 0xdea450;
		}else if(coeff > BWSConfig.playerLoadLevelOverweight){
			loadCol = 0xd95d37;
		}

		thisObject.drawString(((IScreenAccessor)thisObject).getFont(), "§nload:", x+2, y+h-20, loadCol);

		String loadStr = (load+"/"+ (int)BWSConfig.PLAYER_LOAD_DEFAULT);
		thisObject.drawString(((IScreenAccessor)thisObject).getFont(), loadStr, x+2, y+h-10, loadCol);
		GL11.glDisable(3042);
		GL11.glDisable(2884);
		int w2 = 26;
		int x2 = x;
		int h2 = 8;
		int i = 0;

		int counter = 0;
		for (DamageType damageType : BWSDamageTypes.values) {
			if (damageType.shouldDisplay()) {
				int y2 = y + i * 12;
				float protection = 0;
				if(((IMob)ply).getMobResist() != null) {
					protection = ((IMob) ply).getMobResist().getDefence(damageType);
				}
				protection+= ply.inventory.getTotalProtectionAmount(damageType);

				if(protection <= 0){
					continue;
				}
				counter++;

				GL11.glEnable(3553);
				GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
				thisObject.drawGuiIcon(x2 + 2, y2 + 4, 9, 9, TextureRegistry.getTexture(damageType.getIcon()));
				//((IScreenAccessor)thisObject).getFont().drawString(I18n.getInstance().translateKey("gui.inventory.label.crafting"), 106, 16, 4210752);


				protection = (float) (Math.round(protection * 1E4) / 1E4);

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
		if(counter == 0){
			thisObject.drawString(((IScreenAccessor)thisObject).getFont(), "...", x+5, y+4, 0xffffff);
		}

		GL11.glEnable(3553);
		if (this.hoveredDamageType != null) {
			String str = I18n.getInstance().translateDescKey(this.hoveredDamageType.getLanguageKey());
			((IScreenContainerAccessor)thisObject).tooltipElement().render(str, mouseX, mouseY, 8, -8);
		}
	}

	@Inject(
		method = "drawGuiContainerForegroundLayer",
		at = @At(value = "TAIL", target = "Lnet/minecraft/client/gui/container/ScreenInventory;drawTexturedModalRect(IIIIII)V", shift = At.Shift.BEFORE)
	)
	public void renderAccBelt(CallbackInfo ci){
		GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
		this.mc.textureManager.loadTexture("/assets/betterwithsouls/textures/gui/sprites/accessory_belt.png").bind();
		this.drawTexturedModalRect(81d, 61, 0, 0, 84, 21, 256, 256);
	}

	/**
	 * @author MEE
	 * @reason I WILL, I WILL CONTINUE THE STRUGGLE!!!!! AND FOR EVERY PRIVATE, FOR EVERY PROTECTED VARIABLE!! FOR EACH AND EVERY ONE NON-PUBLIC METHOD ILL WRITE A MIGHTY ACCESSOR
	 */
	@Overwrite
	public void tick() {
		this.armorButton.enabled = true;
	}

}
