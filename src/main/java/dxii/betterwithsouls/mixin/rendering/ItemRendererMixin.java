package dxii.betterwithsouls.mixin.rendering;

import dxii.betterwithsouls.entity.render.BWSMobRendererPlayer;
import dxii.betterwithsouls.interfaces.ICube;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = ItemRenderer.class, remap = false)
public class ItemRendererMixin {

	@Unique
	public ItemRenderer thisObject = (ItemRenderer) (Object)this;
	@Mutable
	@Final
	@Shadow
	private final Minecraft mc;
	@Shadow
	private ItemStack itemToRender = null;

	public ItemRendererMixin(Minecraft mc) {
		this.mc = mc;
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public void renderItemInFirstPerson(float partialTick) {
		float equipProgress = thisObject.getEquippedProgress(partialTick);
		PlayerLocal player = this.mc.thePlayer;
		float rotationPitch = player.xRotO + (player.xRot - player.xRotO) * partialTick;
		GL11.glPushMatrix();
		GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(player.yRotO + (player.yRot - player.yRotO) * partialTick, 0.0F, 1.0F, 0.0F);
		Lighting.enableLight();
		GL11.glPopMatrix();
		ItemStack itemstack = this.itemToRender;
		GL11.glPushMatrix();
		if (itemstack != null) {
			(ItemModelDispatcher.getInstance().getDispatch(itemstack.getItem())).renderItemFirstPerson(Tessellator.instance, thisObject, player, itemstack, partialTick);
		} else {
			float brightness = 1.0F;
			if (!LightmapHelper.isLightmapEnabled() && !this.mc.fullbright) {
				brightness = this.mc.currentWorld.getLightBrightness(MathHelper.floor(player.x), MathHelper.floor(player.y), MathHelper.floor(player.z));

			} else {
				LightmapHelper.setLightmapCoord(this.mc.thePlayer.getLightmapCoord(partialTick));
			}

			GL11.glColor4f(brightness, brightness, brightness, 1.0F);
			EntityRenderer<? extends Entity> render = EntityRenderDispatcher.instance.getRenderer(this.mc.thePlayer);
			BWSMobRendererPlayer playerRenderer = (BWSMobRendererPlayer)render;
			float f6;
			if (player.getHeldObject() == null) {
				f6 = 0.8F;
				float swingProgress = player.getSwingProgress(partialTick);
				float f14 = MathHelper.sin(swingProgress * 3.1415927F);
				float f18 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
				GL11.glTranslatef(-f18 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F) * 0.4F, -f14 * 0.4F);
				GL11.glTranslatef(0.8F * f6, -0.75F * f6 - (1.0F - equipProgress) * 0.6F, -0.9F * f6);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glEnable(32826);
				f14 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
				f18 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
				GL11.glRotatef(f18 * 70.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-f14 * 20.0F, 0.0F, 0.0F, 1.0F);
				this.mc.textureManager.bindDownloadableTexture(this.mc.thePlayer.skinURL, this.mc.thePlayer.getEntityTexture(), PlayerSkinParser.instance);
				GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
				GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				GL11.glTranslatef(5.6F, 0.0F, 0.0F);
				playerRenderer.drawFirstPersonHand(this.mc.thePlayer, false);
			} else {
				GL11.glPushMatrix();
				f6 = player.getSwingProgress(partialTick);
				GL11.glTranslatef(0.0F, MathHelper.sin(f6 * 3.1415927F * 2.0F - 1.5707964F) / 33.0F, 0.0F);
				GL11.glScalef(1.0F, -1.0F, 1.0F);
				GL11.glRotatef(-MathHelper.sin(f6 * 3.1415927F * 2.0F) * 5.0F, 1.0F, 0.0F, 0.0F);
				playerRenderer.drawHeldObject(this.mc.thePlayer, partialTick);
				GL11.glPopMatrix();
			}
		}

		GL11.glPopMatrix();
		GL11.glDisable(32826);
		Lighting.disable();
	}


}
