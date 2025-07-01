package dxii.betterwithsouls.entity.render;

import dxii.betterwithsouls.entity.model.BWSModelBiped;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.Vec3;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BWSMobRendererBiped<T extends Mob> extends BWSMobRenderer<T> {
	protected BWSModelBiped modelBipedMain;

	public BWSMobRendererBiped(BWSModelBiped model, float shadowSize) {
		super(model, shadowSize);
		this.modelBipedMain = model;
	}

	@Override
	protected void renderAdditional(T entity, float f) {
		ItemStack itemstack = entity.getHeldItem();
		if (itemstack != null) {
			GL11.glPushMatrix();
			this.modelBipedMain.armRight.translateTo(0.0625F);

			Vec3 loc = modelBipedMain.getItemTranslation();
			GL11.glTranslated(loc.x, loc.y, loc.z);
			Vec3 rot = modelBipedMain.getItemRot();

			GL11.glRotated(rot.z, 0, 0, 1);
			GL11.glRotated(rot.y, 0, 1, 0);
			GL11.glRotated(rot.x, 1, 0, 0);

			ItemModelDispatcher.getInstance()
				.getDispatch(itemstack.getItem())
				.renderItemThirdPerson(Tessellator.instance, this.renderDispatcher.itemRenderer, entity, itemstack, true);
			GL11.glPopMatrix();
		}
	}
}
