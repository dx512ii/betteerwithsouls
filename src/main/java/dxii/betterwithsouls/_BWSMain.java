package dxii.betterwithsouls;


import dxii.betterwithsouls.client.ParticleDamageText;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.entity.particle.ParticleDispatcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.sound.SoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class _BWSMain implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "betterwithsouls";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void beforeGameStart() {
		new BWSEntitiesAndModels();
	}

    @Override
    public void onInitialize() {
        LOGGER.info("Better With Souls initialized.");

		BWSItems.initItems();
		BWSBlocks.initBlocks();
		ParticleDispatcher.getInstance().addDispatch("damage_text", (world, x, y, z, motionX, motionY, motionZ, data) ->
			new ParticleDamageText(data, world, x, y, z, motionX, motionY, motionZ, 1 ));

		SoundRepository.registerNamespace(MOD_ID);

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
		BWSOptions.afterGameStart();
	}

	@Override
	public void beforeClientStart() {
		try {
			TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.guiSpriteAtlas, true);
			TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.particleAtlas, true);
		} catch (Exception var2) {
			LOGGER.warn("Failed to fully initialize assets, some issue may occur!", var2);
		}
	}

	@Override
	public void afterClientStart() {

	}
}
