package dxii.bws;

import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.input.Keyboard;
import turniplabs.halplibe.util.OptionsInitEntrypoint;

public class BWSOptions implements OptionsInitEntrypoint {
	public static KeyBinding KEY_DODGE;

	@Override
	public void initOptions(GameSettings settings) {
		KEY_DODGE = GameSettings.register( new KeyBinding("key.dodge").setDefault(InputDevice.keyboard, Keyboard.KEY_LMENU) );

		OptionsPage optionsPage = new OptionsPage(BWS.MOD_ID+".options", BWSItems.ACC_RING_IRON.getDefaultStack());
		OptionsPages.register(optionsPage);

		optionsPage
			.withComponent(
				new OptionsCategory(BWS.MOD_ID + ".category.keybinds")
					.withComponent(new KeyBindingComponent(KEY_DODGE))
			);
	}
}
