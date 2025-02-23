package dxii.betterwithsouls;


import dxii.betterwithsouls.entity.ZombieTest;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.collection.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class BWSMain implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "betterwithsouls";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static OptionsPage optionsPage;
	public static OptionRange dynLightRate;
	public static OptionBoolean dynLightEnabled;
	public static OptionBoolean dynLightPlayer;
	public static OptionBoolean dynLightExplosions;
//	public static KeyBinding keyDodge;
//	public static KeyBinding keyParry;

	@Override
	public void beforeGameStart() {
		new BWSModels();

		EntityHelper.createEntity(ZombieTest.class, NamespaceID.getPermanent(MOD_ID, "zombdbi"), "zondbi");

	}

    @Override
    public void onInitialize() {
        LOGGER.info("Better With Souls initialized.");

		BWSItems.initItems();
		BWSBlocks.initBlocks();

    }

	@Override
	public void onRecipesReady() {
		BWSRecipes.initRecipes();
	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void afterGameStart() {
		optionsPage = new OptionsPage(MOD_ID+".options", BWSItems.TOMAHAWK_STONE.getDefaultStack());
		OptionsPages.register(optionsPage);

		optionsPage
			.withComponent(
				new OptionsCategory(MOD_ID + ".category.dynlights")
					.withComponent(new BooleanOptionComponent(dynLightEnabled))
					.withComponent(new BooleanOptionComponent(dynLightPlayer))
					.withComponent(new BooleanOptionComponent(dynLightExplosions))
					.withComponent(new ToggleableOptionComponent<>(dynLightRate))
			).withComponent(
				new OptionsCategory(MOD_ID + ".category.keybinds")
//					.withComponent(new KeyBindingComponent(keyDodge))
//					.withComponent(new KeyBindingComponent(keyParry))
			);
	}

	public static void optionsInit(GameSettings gs){
		//credits to big sir for showing me how to implement mod's settings
		dynLightRate = new OptionRange(gs, MOD_ID+ ".dynlightsrate", 2, 5);
		dynLightEnabled = new OptionBoolean(gs, MOD_ID+ ".dynlightsenabled", false);
		dynLightPlayer = new OptionBoolean(gs, MOD_ID+ ".dynlightsplayer", true);
		dynLightExplosions = new OptionBoolean(gs, MOD_ID+ ".dynlightsexpl", false);
	}

	@Override
	public void beforeClientStart() {
		try {
			TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.guiSpriteAtlas, true);
		} catch (Exception var2) {
			LOGGER.warn("Failed to fully initialize assets, some issue may occur!", var2);
		}
	}

	@Override
	public void afterClientStart() {

	}
}
