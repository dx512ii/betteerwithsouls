package dxii.betterwithsouls.mixin;


import dxii.betterwithsouls.BWSMain;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin {

	@Unique
	public OptionRange dynLightsRate;
	@Unique
	public OptionBoolean dynLightsEnabled;
	@Unique
	public OptionBoolean dynLightPlayer;
	@Unique
	public OptionBoolean dynLightExplosions;

	@Unique
	public GameSettings thisObject = (GameSettings) (Object)this;

	@Inject(
		method = "<init>",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameSettings;loadOptions()V"))
	public void settingsMixin(CallbackInfo ci) {
		BWSMain.optionsInit(thisObject);
		dynLightsRate = BWSMain.dynLightRate;
		dynLightsEnabled = BWSMain.dynLightEnabled;
		dynLightPlayer = BWSMain.dynLightPlayer;
		dynLightExplosions = BWSMain.dynLightExplosions;
	}

}
