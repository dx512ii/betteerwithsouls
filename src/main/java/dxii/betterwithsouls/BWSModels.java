package dxii.betterwithsouls;

import dxii.betterwithsouls.item.model.WeaponModelStandart;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import static dxii.betterwithsouls.BWSItems.*;
import static dxii.betterwithsouls.BWSMain.MOD_ID;

public class BWSModels implements ModelEntrypoint {

	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {

	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		ModelHelper.setItemModel(TOMAHAWK_STONE, () -> {
			ItemModelStandard model = new WeaponModelStandart(TOMAHAWK_STONE, MOD_ID, false).setFull3D();
			model.icon = TextureRegistry.getTexture(TOMAHAWK_STONE.namespaceID);
			return model;
		});

		ModelHelper.setItemModel(SHORTSWORD_STONE, () -> {
			ItemModelStandard model = new WeaponModelStandart(SHORTSWORD_STONE, MOD_ID, false).setFull3D();
			model.icon = TextureRegistry.getTexture(SHORTSWORD_STONE.namespaceID);
			return model;
		});
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
