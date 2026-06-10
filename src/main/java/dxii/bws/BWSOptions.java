package dxii.bws;

import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.input.Keyboard;

public class BWSOptions {
	public static KeyBinding KEY_DODGE;
	public static KeyBinding KEY_ATTACK3;

	public static void init() {
		KEY_DODGE = GameSettings.register( new KeyBinding("key.dodge").setDefault(InputDevice.keyboard, Keyboard.KEY_LMENU) );
		KEY_ATTACK3 = GameSettings.register( new KeyBinding("key.attack3").setDefault(InputDevice.keyboard, Keyboard.KEY_F) );

		OptionsPage optionsPage = new OptionsPage(BWS.MOD_ID+".options", BWSItems.SOUL_TIER1_LIGHT.getDefaultStack());

		optionsPage
			.withComponent(
				new OptionsCategory(BWS.MOD_ID + ".options.keybinds")
					.withComponent(new KeyBindingComponent(KEY_DODGE))
					.withComponent(new KeyBindingComponent(KEY_ATTACK3))
			);

		OptionsPages.register(optionsPage);
	}
}
