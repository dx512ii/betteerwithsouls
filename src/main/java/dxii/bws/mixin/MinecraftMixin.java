package dxii.bws.mixin;

import dxii.bws.BWS;
import dxii.bws.interfaces.IMinecraftExtra;
import dxii.bws.item.ItemWeapon;
import dxii.bws.modules.AttackModule;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin implements IMinecraftExtra {
	@Inject(
		method = "run",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;screenshotListener()V"))
	public void bwsRun(CallbackInfo ci){
		BWS.globalTick();
	}

	@Unique
	public Minecraft self = (Minecraft)(Object)this;

	// attack modules for primary, secondary and tertiary attacks
	@Unique
	public AttackModule attackModule1 = new AttackModule();
	@Unique
	public AttackModule attackModule2 = new AttackModule();
	@Unique
	public AttackModule attackModule3 = new AttackModule();

	@Override
	public boolean playerIsAttacking() {
		return !attackModule1.canAttack() || !attackModule2.canAttack() || !attackModule3.canAttack();
	}

	@Unique
	public void handleWeaponMouseClick(int type){
		if(playerIsAttacking()) {
			return;
		}


		ItemWeapon wep = BWS.playerGetWeapon(self.thePlayer);
		if(wep == null){
			return;
		}

		switch(type){
			case 0: {
				wep.attack1(self.thePlayer, self.thePlayer.world, false);
				attackModule1.performAttack(1, wep.attackTiming1);
			}
			case 1: {
				wep.attack2(self.thePlayer, self.thePlayer.world, false);
				attackModule2.performAttack(1, wep.attackTiming2);
			}
		}
	}

	@Inject(
		method = "runTick",
		at = @At(value = "HEAD"))
	public void tickExtra(CallbackInfo ci){
		if(BWS.playerHoldsWeapon(self.thePlayer)) {
			if (attackModule1.shouldAttackTimed()) {
				ItemWeapon curWep = BWS.playerGetWeapon(self.thePlayer);
				if (curWep != null) curWep.attack1(self.thePlayer, self.thePlayer.world, true);
			}
			if (attackModule2.shouldAttackTimed()) {
				ItemWeapon curWep = BWS.playerGetWeapon(self.thePlayer);
				if (curWep != null) curWep.attack2(self.thePlayer, self.thePlayer.world, true);
			}
			if (attackModule3.shouldAttackTimed()) {
				ItemWeapon curWep = BWS.playerGetWeapon(self.thePlayer);
				if (curWep != null) curWep.attack3(self.thePlayer, self.thePlayer.world, true);
			}
		}
	}


	@Inject(
		method = "clickMouse",
		at = @At(value = "HEAD"), cancellable = true)
	public void mouseClickIntervention(int clickType, boolean attack, boolean repeat, CallbackInfo ci){
//		BWS.animtest(self.thePlayer);

		if(BWS.playerHoldsWeapon(self.thePlayer)){
			handleWeaponMouseClick(clickType);
			ci.cancel();
		}
	}
}
