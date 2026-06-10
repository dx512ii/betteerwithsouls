package dxii.bws.render;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.DisplayPos;

public class ItemModelWeapon extends ItemModelStandard {


	private @Nullable IconCoordinate iconWorld;
	private String lastDisplayPosID;
	private final boolean usesWorld;

	public ItemModelWeapon(@NotNull Item item, boolean usesWorld) {
		super(item);
		this.usesWorld = usesWorld;

		if(this.usesWorld){
			this.iconWorld = TextureRegistry.getTexture(item.namespaceID+"_world");
		}
	}

	@Override
	public @NotNull DisplayPos getDisplayPos(@NotNull String id) {
		this.lastDisplayPosID = id;
		return super.getDisplayPos(id);
	}



	@Override
	public void render(@NotNull TessellatorGeneral tessellator, @Nullable Entity holder, @NotNull ItemStack itemStack, @NotNull String displayPosId, boolean items3d, int clusterSize, byte lightIndex, float partialTick, boolean mirrorX) {
		if(displayPosId.equals("firstperson_righthand")){
			return;
		}

		if(this.usesWorld && !displayPosId.equals("gui")){
			float pxH = this.icon.height;
			float pxW = this.icon.width;

			float pxHw = this.iconWorld.height;
			float pxWw = this.iconWorld.width;

			float diffH = pxHw/pxH;
			float diffW = pxWw/pxW;

			GLRenderer.modelM4f().scale((diffH+diffW) * .5f, diffH, diffW);
			GLRenderer.modelM4f().translate(0, diffH * .05f, diffH * -.05f);
		}

		super.render(tessellator, holder, itemStack, displayPosId, items3d, clusterSize, lightIndex, partialTick, mirrorX);
	}

	@Override
	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, @NotNull ItemStack itemStack) {
		if(!this.usesWorld){
			return this.icon;
		}
		return this.lastDisplayPosID.equals("gui") ? this.icon : this.iconWorld;
	}
}
