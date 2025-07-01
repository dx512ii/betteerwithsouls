package dxii.betterwithsouls;

import dxii.betterwithsouls.entity.EnemyZombie;
import dxii.betterwithsouls.entity.model.BWSModelBiped;
import dxii.betterwithsouls.entity.render.BWSMobRendererBiped;
import dxii.betterwithsouls.entity.render.BWSMobRendererPlayer;
import dxii.betterwithsouls.item.model.ItemDummyModel;
import dxii.betterwithsouls.item.model.WeaponModelStandart;
import dxii.betterwithsouls.mixin.accessor.IAEntityDispatcher;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.collection.NamespaceID;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import static dxii.betterwithsouls.BWSItems.*;
import static dxii.betterwithsouls._BWSMain.LOGGER;
import static dxii.betterwithsouls._BWSMain.MOD_ID;

public class BWSEntitiesAndModels implements ModelEntrypoint {

	public void initEntities(){
		EntityHelper.createEntity(EnemyZombie.class, NamespaceID.getPermanent(MOD_ID, "bws_zombie"), "zondbi");
	}

	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
		initEntities();
	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {


		ModelHelper.setItemModel(ITEM_DUMMY, () -> {
			ItemModelStandard model = new ItemDummyModel(ITEM_DUMMY, MOD_ID).setFull3D();
			model.icon = TextureRegistry.getTexture(ITEM_DUMMY.namespaceID);
			return model;
		});

		for(Item item : standartModels){
			assignStandartItemModel(item);
		}
		for(Item item : weaponModels){
			assignWeaponItemModel(item, false);
		}
		for(Item item : weaponModelsBig){
			assignWeaponItemModel(item, true);
		}
	}

	public void assignStandartItemModel(Item item){
		if(item == null){
			LOGGER.warn("item "+item+" is null!! no item model assigned!");
			return;
		}

		ModelHelper.setItemModel(item, () -> {
			ItemModelStandard model = new ItemModelStandard(item, MOD_ID).setFull3D();
			model.icon = TextureRegistry.getTexture(item.namespaceID);
			return model;
		});
	}

	//NOTE: if weapon texture exceeds 16x16 size it will search for world texture for 3d rendering,
	//normal texture will be used for inventory
	public void assignWeaponItemModel(Item item, boolean usesworld){
		if(item == null){
			LOGGER.warn("item "+item+" is null!! no item model assigned!");
			return;
		}

		ModelHelper.setItemModel(item, () -> {
			ItemModelStandard model = new WeaponModelStandart(item, MOD_ID, usesworld).setFull3D();
			model.icon = TextureRegistry.getTexture(item.namespaceID);
			return model;
		});
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {
		addEntityModel(dispatcher, EnemyZombie.class, new BWSMobRendererBiped<>(new BWSModelBiped(), 0.5F));
		addEntityModel(dispatcher, Player.class, new BWSMobRendererPlayer());
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}




	//UTIL:
	public void addEntityModel(EntityRenderDispatcher dispatcher, @NotNull Class<? extends Entity> clazz, EntityRenderer<?> renderer){
		renderer.init(dispatcher);
		((IAEntityDispatcher)dispatcher).getRenderers().put(clazz, renderer);
	}
}
