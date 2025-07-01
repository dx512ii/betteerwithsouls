package dxii.betterwithsouls.mixin.rendering;

import dxii.betterwithsouls.entity.render.BWSMobRendererPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.model.ItemModelMap;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.saveddata.maps.ItemMapSavedData;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;

@Mixin(value = ItemModelMap.class, remap = false)
public class ItemModelMapMixin {

	@Unique
	public ItemModelMap thisObject = (ItemModelMap) (Object)this;
	@Mutable
	@Final
	@Shadow
	private final Minecraft mc;
	@Mutable
	@Final
	@Shadow
	private final MapItemRenderer renderMapInstance;

	public ItemModelMapMixin(Minecraft mc, MapItemRenderer renderMapInstance) {
		this.mc = mc;
		this.renderMapInstance = renderMapInstance;
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public void renderItemFirstPerson(Tessellator tessellator, ItemRenderer renderer, Player player, ItemStack stack, float partialTick) {
		int blockX = MathHelper.floor(player.x);
		int blockY = MathHelper.floor(player.y);
		int blockZ = MathHelper.floor(player.z);
		float brightness = 1.0F;
		if (LightmapHelper.isLightmapEnabled()) {
			LightmapHelper.setLightmapCoord(this.mc.currentWorld.getLightmapCoord(blockX, blockY, blockZ, 0));
		} else if (!this.mc.fullbright) {
			brightness = this.mc.currentWorld.getLightBrightness(blockX, blockY, blockZ);
		}

		GL11.glColor4f(brightness, brightness, brightness, 1.0F);
		float rotationPitch = player.xRotO + (player.xRot - player.xRotO) * partialTick;
		float swingProgress = player.getSwingProgress(partialTick);
		float f_z = MathHelper.sin(swingProgress * 3.1415927F);
		float f_x = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
		GL11.glTranslatef(-f_x * 0.4F, MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F) * 0.2F, -f_z * 0.2F);
		float f_val = MathHelper.clamp(1.0F - rotationPitch / 45.0F + 0.1F, 0.0F, 1.0F);
		f_val = -MathHelper.cos(f_val * 3.1415927F) * 0.5F + 0.5F;
		float zOff = -0.71999997F;
		GL11.glTranslatef(0.0F, -(1.0F - renderer.getEquippedProgress(partialTick)) * 1.2F - f_val * 0.5F + 0.04F, -0.71999997F);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f_val * -85.0F, 0.0F, 0.0F, 1.0F);
		GL11.glEnable(32826);
		this.mc.textureManager.bindDownloadableTexture(this.mc.thePlayer.skinURL, this.mc.thePlayer.getEntityTexture(), PlayerSkinParser.instance);

		EntityRenderer<? extends Entity> render = EntityRenderDispatcher.instance.getRenderer(this.mc.thePlayer);
		BWSMobRendererPlayer playerRenderer = (BWSMobRendererPlayer)render;

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.0F, -0.6F, -1.1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(31.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(65.0F, 0.0F, 1.0F, 0.0F);
		playerRenderer.drawFirstPersonHand(this.mc.thePlayer, true);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.0F, -0.6F, 1.1F);
		GL11.glRotatef(-45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-31.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-65.0F, 0.0F, 1.0F, 0.0F);
		playerRenderer.drawFirstPersonHand(this.mc.thePlayer, false);
		GL11.glPopMatrix();
		float f_val3 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
		float f_val4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
		GL11.glRotatef(-f_val3 * 20.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-f_val4 * 20.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-f_val4 * 80.0F, 1.0F, 0.0F, 0.0F);
		float scale1 = 0.38F;
		GL11.glScalef(0.38F, 0.38F, 0.38F);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
		float scale2 = 0.015625F;
		GL11.glScalef(0.015625F, 0.015625F, 0.015625F);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F);
		this.mc.textureManager.bindTexture(this.mc.textureManager.loadTexture("/assets/minecraft/textures/misc/mapbg.png"));
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0, 135.0, 0.0, 0.0, 1.0);
		tessellator.addVertexWithUV(135.0, 135.0, 0.0, 1.0, 1.0);
		tessellator.addVertexWithUV(135.0, -7.0, 0.0, 1.0, 0.0);
		tessellator.addVertexWithUV(-7.0, -7.0, 0.0, 0.0, 0.0);
		tessellator.draw();
		ItemMapSavedData mapData = Items.MAP.getOrCreateSavedData(stack, this.mc.currentWorld);
		if (mapData != null) {
			this.renderMapInstance.renderMap(this.mc.textureManager, mapData, brightness);
		}
	}


}
