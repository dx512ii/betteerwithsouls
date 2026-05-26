package dxii.bws;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class BWSConfig {
	private static final Toml TOML = new Toml("Better With Souls Configuration");
	public static final TomlConfigHandler CFG;

	static {
		int default_id_items = 25400;
		int default_id_blocks = 4700;

		TOML.addCategory("IDs")
			.addEntry("startItemID", "Default: " + default_id_items, default_id_items)
			.addEntry("startBlockID", "Default: " + default_id_blocks, default_id_blocks);

		CFG = new TomlConfigHandler(BWS.MOD_ID, TOML);
	}
}
