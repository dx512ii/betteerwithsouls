package dxii.bws;

import dxii.bws.animation.AnimatableEntity;
import dxii.bws.animation.AnimationsLibrary_humanoid;
import dxii.bws.interfaces.IInventoryExtra;
import dxii.bws.interfaces.IMinecraftExtra;
import dxii.bws.item.ItemWeapon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.ModelEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BWS implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ModelEntrypoint {
	public static final String MOD_ID = HalpLibe.registerMod("betterwithsouls");
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int ITEM_ID_START = 16000;
	public static int ENTITY_ID_START = 16000;


	public static final boolean CLIENT = FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT);
	public static final boolean SERVER = FabricLoader.getInstance().getEnvironmentType().equals(EnvType.SERVER);
	private static long startupTime = 0;
	private static float curTime = 0;
	private static float curTimeIngame = 0;

	private static float lastTick = 0;

	public static void globalTick(){
		curTime = (float)( (System.currentTimeMillis() - startupTime) * .001 );

		if(!Minecraft.getMinecraft().isGamePaused){
			float diff = curTime - lastTick;
			curTimeIngame += diff;
		}

		lastTick = curTime;
	}

	public static float curtime(){
		return curtime(true);
	}
	public static float curtime(boolean pauseSensetive){
		return pauseSensetive ? curTimeIngame : curTime;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Better With Souls initialized.");
		startupTime = System.currentTimeMillis();

		BWSItems.init();
	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void initBlockModels(BlockModelDispatcher blockModelDispatcher) {

	}

	@Override
	public void initItemModels(ItemModelDispatcher itemModelDispatcher) {

	}

	@Override
	public void initEntityModels(EntityRendererDispatcher entityRendererDispatcher) {
		//entityRendererDispatcher.assignRenderer();
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher tileEntityRenderDispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher blockColorDispatcher) {

	}


	// UTIL

	public static boolean playerHoldsWeapon(Player ply){
		if(ply == null){
			return false;
		}
		if (ply.getHeldItem() == null){
			return false;
		}
		return ply.getHeldItem().getItem() instanceof ItemWeapon;
	}
	public static ItemWeapon playerGetWeapon(Player ply){
		if(ply == null){
			return null;
		}

		ItemStack held = ply.getHeldItem();
		if ( held == null || !(held.getItem() instanceof ItemWeapon) ){
			return null;
		}
		return (ItemWeapon)(held.getItem());
	}
	public static ItemWeapon itemAsWeapon(ItemStack stack){
		if ( stack == null || !(stack.getItem() instanceof ItemWeapon) ){
			return null;
		}
		return (ItemWeapon)(stack.getItem());
	}
	public static ItemWeapon itemAsWeapon(Item item){
		if (!(item instanceof ItemWeapon)){
			return null;
		}
		return (ItemWeapon)(item);
	}

	public static AnimatableEntity asAnimatable(Entity ent){
		return (AnimatableEntity)ent;
	}
	public static IMinecraftExtra getMinecraftExtra(){
		return ((IMinecraftExtra) Minecraft.getMinecraft());
	}
	public static ItemStack[] getAccessories(ContainerInventory inv){
		return ((IInventoryExtra)inv).getAccessories();
	}
}
