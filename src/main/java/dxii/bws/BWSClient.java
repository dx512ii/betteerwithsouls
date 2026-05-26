package dxii.bws;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.sound.SoundRepository;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;

import static dxii.bws.BWS.MOD_ID;
import static dxii.bws.BWS.LOGGER;

@Environment(EnvType.CLIENT)
public class BWSClient implements ClientModInitializer, ClientStartEntrypoint {
	@Override
	public void beforeClientStart() {
		SoundRepository.namespaceAdded(MOD_ID);

		try {
			TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.worldAtlas, false);
		} catch (URISyntaxException | IOException e) {
			LOGGER.error("Failed to initialize textures!");
		}
	}

	@Override
	public void afterClientStart() {
		//achievements

		//MobInfoRegistry shit
	}

	@Override
	public void onInitializeClient() {
		LOGGER.info("Better With Souls client init!");
	}
}
