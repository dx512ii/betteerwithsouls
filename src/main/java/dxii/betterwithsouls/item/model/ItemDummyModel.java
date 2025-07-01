package dxii.betterwithsouls.item.model;

import dxii.betterwithsouls.interfaces.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.Vec3;
import org.lwjgl.opengl.GL11;

public class ItemDummyModel extends ItemModelStandard {
	public ItemDummyModel(Item item, String namespace) {
		super(item, namespace);
	}

	@Override
	public void renderItemFirstPerson(Tessellator tessellator, ItemRenderer renderer, Player player, ItemStack stack, float partialTick) {
		float brightness = 1.0F;
		if (!Minecraft.getMinecraft().fullbright && !this.itemfullBright && !LightmapHelper.isLightmapEnabled()) {
			brightness = player.getBrightness(1.0F);
		} else if (LightmapHelper.isLightmapEnabled()) {
			int lightmapCoord = player.getLightmapCoord(partialTick);
			if (this.itemfullBright) {
				lightmapCoord = LightmapHelper.setBlocklightValue(lightmapCoord, 15);
			}

			LightmapHelper.setLightmapCoord(lightmapCoord);
		}


		GL11.glEnable(32826);
		GL11.glTranslatef(0.56F, -0.52F - (1.0F - renderer.getEquippedProgress(partialTick)) * 0.6F, -0.71999997F);
		GL11.glRotatef(45, 0, 1, 0);

		Vec3 pos = ((IMinecraft)Minecraft.getMinecraft()).getItemDummyPos();
		Vec3 rot = ((IMinecraft)Minecraft.getMinecraft()).getItemDummyRot();

		float x = (float) pos.x;
		float y = (float) pos.y;
		float z = (float) pos.z;

		float rotX = (float) rot.x;
		float rotY = (float) rot.y;
		float rotZ = (float) rot.z;

		GL11.glTranslatef(x, y, z);

		GL11.glRotatef(rotZ, 0, 0, 1);
		GL11.glRotatef(rotY, 0, 1, 0);
		GL11.glRotatef(rotX, 1, 0, 0);

		float scale = 0.4F;
		GL11.glScalef(scale, scale, scale);

		this.heldTransformFirstPerson(renderer, player, stack);
		this.renderItem(tessellator, renderer, stack, player, brightness, true);
	}
}
