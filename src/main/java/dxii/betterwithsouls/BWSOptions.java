package dxii.betterwithsouls;

import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import org.lwjgl.input.Keyboard;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

public class BWSOptions {

	public static OptionsPage optionsPage;
	public static OptionRange dynLightRate;
	public static OptionBoolean dynLightEnabled;
	public static OptionBoolean dynLightPlayer;
	public static OptionBoolean dynLightExplosions;
	public static KeyBinding keyDodge;
	public static KeyBinding keyParry;

	public static void afterGameStart() {
		optionsPage = new OptionsPage(MOD_ID+".options", BWSItems.SACRIFICE_RING_RARE.getDefaultStack());
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
					.withComponent(new KeyBindingComponent(keyDodge))
					.withComponent(new KeyBindingComponent(keyParry))
			);
	}

	public static void optionsInit(GameSettings gs){
		//credits to big sir for showing me how to implement mod's settings
		dynLightRate = new OptionRange(gs, MOD_ID+ ".dynlightsrate", 2, 5);
		dynLightEnabled = new OptionBoolean(gs, MOD_ID+ ".dynlightsenabled", false);
		dynLightPlayer = new OptionBoolean(gs, MOD_ID+ ".dynlightsplayer", true);
		dynLightExplosions = new OptionBoolean(gs, MOD_ID+ ".dynlightsexpl", false);
		keyDodge = new KeyBinding("key.dodge").setDefault(InputDevice.keyboard, Keyboard.KEY_LMENU);
		keyParry = new KeyBinding("key.parry").setDefault(InputDevice.keyboard, Keyboard.KEY_F);
	}
}
