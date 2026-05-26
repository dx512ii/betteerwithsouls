package dxii.bws;

import dxii.bws.render.ItemModelWeapon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.item.Item;
import org.useless.dragonfly.DisplayPos;
import turniplabs.halplibe.util.ModelEntrypoint;

import java.util.HashMap;
import java.util.Map;

import static dxii.bws.BWS.LOGGER;

@Environment(EnvType.CLIENT)
public class BWSModels implements ModelEntrypoint {
	public static Map<Item, Boolean> modelsStandart = new HashMap<>();
	public static Map<Item, Boolean> modelsStandartFullbright = new HashMap<>();
	public static Map<Item, Boolean> modelsWeapons = new HashMap<>();

	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {

	}
	protected static final DisplayPos HANDHELD_FIRST_PERSON_RIGHT_HAND = new DisplayPos(0.070625F, 0.2F, 0.070625F, 0.0F, -90.0F, 25.0F, 0.68F, 0.68F, 0.68F);
	protected static final DisplayPos HANDHELD_FIRST_PERSON_LEFT_HAND = new DisplayPos(0.070625F, 0.2F, 0.070625F, 0.0F, 90.0F, -25.0F, 0.68F, 0.68F, 0.68F);
	protected static final DisplayPos HANDHELD_THIRD_PERSON_RIGHT_HAND = new DisplayPos(0.0F, 0.25F, 0.03125F, 0.0F, -90.0F, 55.0F, 0.85F, 0.85F, 0.85F);
	protected static final DisplayPos HANDHELD_THIRD_PERSON_LEFT_HAND = new DisplayPos(0.0F, 0.25F, 0.03125F, 0.0F, 90.0F, -55.0F, 0.85F, 0.85F, 0.85F);

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		LOGGER.info("Better With Souls Init item models");

		initItemModelsBro(dispatcher);
	}

	public static void setupWeaponModel(ItemModelStandard model){
		model.setDisplayPos("firstperson_righthand", HANDHELD_FIRST_PERSON_RIGHT_HAND)
				.setDisplayPos("firstperson_lefthand", HANDHELD_FIRST_PERSON_LEFT_HAND)
				.setDisplayPos("thirdperson_righthand", HANDHELD_THIRD_PERSON_RIGHT_HAND)
				.setDisplayPos("thirdperson_lefthand", HANDHELD_THIRD_PERSON_LEFT_HAND);
	}

	public static void initItemModelsBro(ItemModelDispatcher dispatcher){
		for (Item item : modelsStandart.keySet()) {
			ItemModelStandard model = new ItemModelStandard(item);
			dispatcher.addDispatch(model);
			System.out.println("adding standart model for item '" + item.namespaceID + "'");
			if(modelsStandart.get(item)){
				setupWeaponModel(model);
			}
		}
		for (Item item : modelsWeapons.keySet()) {
			ItemModelWeapon model = new ItemModelWeapon(item, modelsWeapons.get(item));
			dispatcher.addDispatch(model);
			System.out.println("adding world model for item '" + item.namespaceID + "'");
			setupWeaponModel(model);
		}
		for (Item item : modelsStandartFullbright.keySet()) {
			ItemModelStandard model = new ItemModelStandard(item);
			dispatcher.addDispatch(model.setFullBright());
			System.out.println("adding world model for item '" + item.namespaceID + "'");
			if(modelsStandartFullbright.get(item)){
				setupWeaponModel(model);
			}
		}
	}

	@Override
	public void initEntityModels(EntityRendererDispatcher dispatcher) {

	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
