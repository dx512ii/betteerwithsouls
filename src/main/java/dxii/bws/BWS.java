package dxii.bws;

import dxii.bws.animation.AnimatableEntity;
import dxii.bws.entity.DamageInfo;
import dxii.bws.entity.DamageResistModule;
import dxii.bws.entity.DamageTypeBWS;
import dxii.bws.interfaces.IEntityBWS;
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
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.ModelEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BWS implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ModelEntrypoint {
	public static final int MAX_WEAPONS_REINFORCEMENT = 10;
	public static int getReinforceSoulTier(int current){
		float ratio = (float)current / (float)MAX_WEAPONS_REINFORCEMENT;
		if(ratio < .2){
			return 0;
		}
		if(ratio >= .2){
			return 1;
		}
		if(ratio >= .5){
			return 2;
		}

		return 0;
	}
	public static int getReinforceSteelCount(int current){
		float ratio = (float)current / (float)MAX_WEAPONS_REINFORCEMENT;
		if(ratio < .2){
			return 1;
		}
		if(ratio >= .2){
			return 2;
		}
		if(ratio >= .5){
			return 4;
		}

		return 1;
	}


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
		BWSOptions.init();
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

	public static int CalculateDamageForMob(Mob self, DamageInfo dinfo, DamageResistModule resistModule, DamageResistModule blockResist){
		int dmgBase = dinfo.getDamage();
		DamageTypeBWS dtype = dinfo.getDamageType();
		int dmg = dmgBase;

		dmg = (int)DamageResistModule.calculateDamage(dmg, resistModule.getResist(dtype));
		if(blockResist != null){
			dmg = (int)DamageResistModule.calculateDamage(dmg, blockResist.getResist(dtype));
		}

		if(self instanceof IEntityBWS selfBWS){
			if(resistModule.damagePoise(dmg) <= 0){
				selfBWS.stun();
			}
		}

		return dmg;
	}

	public static void setupViewmodelAnimation(@Nullable StaticEntityModel model, float bodyYaw, float headYaw, float headPitch){
		BoneTransform waist = model.getTransform("waist");
		BoneTransform body = model.getTransform("body");

		// waist.visible = false;
		waist.rotY = headYaw; // + 30*MathHelper.DEG_TO_RAD;
		body.rotX += MathHelper.clamp(
			headPitch - 15 * MathHelper.DEG_TO_RAD,
			-60 * MathHelper.DEG_TO_RAD,
			60 * MathHelper.DEG_TO_RAD
		);
		body.posY += -5;
		if(body.rotX < 0){
			body.posY += Math.abs(body.rotX)*10;
		}
		body.posZ += -6 + body.rotX*10;


		model.getTransform("rightLeg").visible = false;
		model.getTransform("leftLeg").visible = false;

		body.visible = false;
		model.getTransform("head").visible = false;

		model.getTransform("rightArm").visible = true;
		model.getTransform("leftArm").visible = true;
	}

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
