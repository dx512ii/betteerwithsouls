package dxii.betterwithsouls.item.model;

import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.interfaces.IPlayer;
import dxii.betterwithsouls.mixin.accessor.IItemModelAccessor;
import dxii.betterwithsouls.util.animation.AnimManager;
import dxii.betterwithsouls.util.animation.Animation;
import dxii.betterwithsouls.util.animation.Frame;
import dxii.betterwithsouls.util.animation.Key;
import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class WeaponModelStandart extends ItemModelStandard {
	public IconCoordinate worldTex;



	public WeaponModelStandart(Item item, String namespace, boolean usesworld) {
		super(item, namespace);

		//if 'usesworld' is true, it will search for <texture>_world for world/first person rendering
		this.worldTex = usesworld ? TextureRegistry.getTexture(item.namespaceID+"_world") : TextureRegistry.getTexture(item.namespaceID);
	}

	@Override
	public void renderItemInWorld(Tessellator tessellator, Entity entity, ItemStack itemStack, float brightness, float alpha, boolean worldTransform) {
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);


		if (this.useColor) {
			int color = this.getColor(itemStack);
			float r = (float)(color >> 16 & 0xFF) / 255.0F;
			float g = (float)(color >> 8 & 0xFF) / 255.0F;
			float b = (float)(color & 0xFF) / 255.0F;
			GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
		} else {
			GL11.glColor4f(brightness, brightness, brightness, alpha);
		}

		IconCoordinate tex = this.worldTex;
		tex.parentAtlas.bind();
		int tileWidth = tex.width;
		float uMin = (float)tex.getIconUMin();
		float uMax = (float)tex.getIconUMax();
		float vMin = (float)tex.getIconVMin();
		float vMax = (float)tex.getIconVMax();
		float uDiff = uMin - uMax;
		float vDiff = vMin - vMax;
		float width = 1.0F;
		float foon = 0.5F / (float)tex.parentAtlas.getHeight();
		float goon = 0.0625F * (16.0F / (float)tileWidth);
		GL11.glEnable(32826);
		float thickness = 0.0625F;
		float pixelWidth = 1.0F / (float)tileWidth;
		if (worldTransform) {
			GL11.glTranslatef(-0.5F, -0.5F, 0.03125F);
		}

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)uMax, (double)vMax);
		tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)uMin, (double)vMax);
		tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)uMin, (double)vMin);
		tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)uMax, (double)vMin);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		tessellator.addVertexWithUV(0.0, 1.0, -0.0625, (double)uMax, (double)vMin);
		tessellator.addVertexWithUV(1.0, 1.0, -0.0625, (double)uMin, (double)vMin);
		tessellator.addVertexWithUV(1.0, 0.0, -0.0625, (double)uMin, (double)vMax);
		tessellator.addVertexWithUV(0.0, 0.0, -0.0625, (double)uMax, (double)vMax);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);

		for (int i = 0; i < tileWidth; i++) {
			float texProgress = (float)i * pixelWidth;
			float u = uMax + uDiff * texProgress - foon;
			float x = texProgress;
			tessellator.addVertexWithUV((double)x, 0.0, -0.0625, (double)u, (double)vMax);
			tessellator.addVertexWithUV((double)x, 0.0, 0.0, (double)u, (double)vMax);
			tessellator.addVertexWithUV((double)x, 1.0, 0.0, (double)u, (double)vMin);
			tessellator.addVertexWithUV((double)x, 1.0, -0.0625, (double)u, (double)vMin);
		}

		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);

		for (int i = 0; i < tileWidth; i++) {
			float texProgress = (float)i * pixelWidth;
			float u = uMax + uDiff * texProgress - foon;
			float x = texProgress + goon;
			tessellator.addVertexWithUV((double)x, 1.0, -0.0625, (double)u, (double)vMin);
			tessellator.addVertexWithUV((double)x, 1.0, 0.0, (double)u, (double)vMin);
			tessellator.addVertexWithUV((double)x, 0.0, 0.0, (double)u, (double)vMax);
			tessellator.addVertexWithUV((double)x, 0.0, -0.0625, (double)u, (double)vMax);
		}

		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);

		for (int i = 0; i < tileWidth; i++) {
			float texProgress = (float)i * pixelWidth;
			float v = vMax + vDiff * texProgress - foon;
			float y = texProgress + goon;
			tessellator.addVertexWithUV(0.0, (double)y, 0.0, (double)uMax, (double)v);
			tessellator.addVertexWithUV(1.0, (double)y, 0.0, (double)uMin, (double)v);
			tessellator.addVertexWithUV(1.0, (double)y, -0.0625, (double)uMin, (double)v);
			tessellator.addVertexWithUV(0.0, (double)y, -0.0625, (double)uMax, (double)v);
		}

		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);

		for (int i = 0; i < tileWidth; i++) {
			float texProgress = (float)i * pixelWidth;
			float v = vMax + vDiff * texProgress - foon;
			tessellator.addVertexWithUV(1.0, (double) texProgress, 0.0, (double)uMin, (double)v);
			tessellator.addVertexWithUV(0.0, (double) texProgress, 0.0, (double)uMax, (double)v);
			tessellator.addVertexWithUV(0.0, (double) texProgress, -0.0625, (double)uMax, (double)v);
			tessellator.addVertexWithUV(1.0, (double) texProgress, -0.0625, (double)uMin, (double)v);
		}

		tessellator.draw();
		GL11.glDisable(32826);
		GL11.glDisable(3042);
	}

	@NotNull
	public IconCoordinate getThaIcon(@Nullable Entity entity, ItemStack itemStack, boolean world) {
		return this.icon;
	}

	@Override
	public void renderItemFirstPerson(Tessellator tessellator, ItemRenderer renderer, Player player, ItemStack stack, float partialTick) {
		float brightness = 1.0F;
		if (!((IItemModelAccessor)this).getMc().fullbright && !this.itemfullBright && !LightmapHelper.isLightmapEnabled()) {
			brightness = player.getBrightness(1.0F);
		} else if (LightmapHelper.isLightmapEnabled()) {
			int lightmapCoord = player.getLightmapCoord(partialTick);
			if (this.itemfullBright) {
				lightmapCoord = LightmapHelper.setBlocklightValue(lightmapCoord, 15);
			}

			LightmapHelper.setLightmapCoord(lightmapCoord);
		}


		GL11.glEnable(32826);

		AnimManager VMmanager = ((IEntity)player).bws$getVMManager();
		if(viewModelAnimated(VMmanager)) {
			GL11.glTranslatef(0.56F, -0.52F - (1.0F - renderer.getEquippedProgress(partialTick)) * 0.6F, -0.71999997F);
			GL11.glRotatef(45, 0, 1, 0);

			float x = VMmanager.getXLerp("weapon");
			float y = VMmanager.getYLerp("weapon");
			float z = VMmanager.getZLerp("weapon");

			float rotX = VMmanager.getRotXLerp("weapon");
			float rotY = VMmanager.getRotYLerp("weapon");
			float rotZ = VMmanager.getRotZLerp("weapon");

			GL11.glTranslatef(
				x,
				y,
				z);
			GL11.glRotatef(rotX, 1, 0, 0);
			GL11.glRotatef(rotY, 0, 1, 0);
			GL11.glRotatef(rotZ, 0, 0, 1);
		}else{
			float swingProgress = player.getSwingProgress(partialTick);
			float animationProgress2 = MathHelper.sin(swingProgress * (float) Math.PI);
			float animationProgress = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
			GL11.glTranslatef(-animationProgress * 0.4F, MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI * 2.0F) * 0.2F, -animationProgress2 * 0.2F);
			GL11.glTranslatef(0.56F, -0.52F - (1.0F - renderer.getEquippedProgress(partialTick)) * 0.6F, -0.71999997F);
			GL11.glRotatef(45, 0.0F, 1.0F, 0.0F);

			float animationProgress3 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
			GL11.glRotatef(-animationProgress3 * 20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-animationProgress * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-animationProgress * 80.0F, 1.0F, 0.0F, 0.0F);
		}

		float scale = 0.4F;
		GL11.glScalef(scale, scale, scale);

		this.heldTransformFirstPerson(renderer, player, stack);
		this.renderItem(tessellator, renderer, stack, player, brightness, true);
	}

	@Override
	public void heldTransformThirdPerson(ItemRenderer renderer, Entity entity, ItemStack itemStack) {
		if (this.bFull3D) {
			float scale = 0.625F;
			if (this.rotateWhenRendering) {
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(0.0F, -0.125F, 0.0F);
			}

			if (this.pointInfrontOfPlayer) {
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.125F, 0.0F);
			}

			GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
			GL11.glScalef(scale, -scale, scale);
			GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		} else {
			float scalex = 0.375F;
			GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
			GL11.glScalef(scalex, scalex, scalex);
			GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
		}
	}

	@Override
	public void heldTransformFirstPerson(ItemRenderer renderer, Entity entity, ItemStack itemStack) {
		if (this.rotateWhenRendering) {
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		}

		if (this.pointInfrontOfPlayer) {
			GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
		}

		GL11.glTranslatef(0, 0, 0);
		GL11.glRotatef(0, 1, 0, 0);
		GL11.glRotatef(-0, 0, 1, 0);
		GL11.glRotatef(-0, 0, 0, 1);
	}

	public boolean viewModelAnimated(AnimManager manager){
		if(manager == null){return false;}
		return manager.isLimbAnimated("weapon");
	}

}
