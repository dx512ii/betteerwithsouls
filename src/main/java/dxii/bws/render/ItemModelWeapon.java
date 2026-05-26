package dxii.bws.render;

import net.minecraft.client.render.item.model.ItemModelStandard;
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


	private final IconCoordinate iconWorld;
	private String lastDisplayPosID;
	private final boolean usesWorld;

	public ItemModelWeapon(@NotNull Item item, boolean usesWorld) {
		super(item);
		this.iconWorld = TextureRegistry.getTexture(item.namespaceID+"_world");
		this.usesWorld = usesWorld;
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

		super.render(tessellator, holder, itemStack, displayPosId, items3d, clusterSize, lightIndex, partialTick, mirrorX);
	}

	@Override
	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, @NotNull ItemStack itemStack) {
		return !this.usesWorld || this.lastDisplayPosID.equals("fixed") ? this.icon : this.iconWorld;
	}
}
