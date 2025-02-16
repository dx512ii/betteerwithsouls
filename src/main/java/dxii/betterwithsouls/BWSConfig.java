package dxii.betterwithsouls;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

//props to calamitycage and his terrapon, for introducing me to 7.3 changes
public class BWSConfig {
	public static final int playerLightRad = 5;
	public static final int playerLightBrightness = 20;

	private static final Toml TOML = new Toml("Better With Souls Configuration");
	public static final TomlConfigHandler CFG;

	static {
		TOML.addCategory("IDs")
			.addEntry("startItemID", "Default: 25400", 25400)
			.addEntry("startBlockID", "Default: 4700", 4700);

		CFG = new TomlConfigHandler(BWSMain.MOD_ID, TOML);
	}
}
