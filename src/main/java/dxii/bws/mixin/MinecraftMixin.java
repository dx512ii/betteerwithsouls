package dxii.bws.mixin;

import dxii.bws.BWS;
import dxii.bws.BWSOptions;
import dxii.bws.interfaces.IMinecraftExtra;
import dxii.bws.item.ItemWeapon;
import dxii.bws.modules.AttackModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.item.ItemStack;
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


	@Inject(
		method = "runTick",
		at = @At(value = "HEAD"))
	public void tickExtra(CallbackInfo ci){
		if(BWS.playerHoldsWeapon(self.thePlayer)) {
			ItemWeapon curWep = BWS.playerGetWeapon(self.thePlayer);

			if(curWep == null){
				return;
			}

			ItemStack wepStack = self.thePlayer.getHeldItem();

			if(!self.isGamePaused && !playerIsAttacking()) {
				boolean atkpress1 = GameSettings.KEY_ATTACK.isPressed();
				boolean atkpress2 = GameSettings.KEY_INTERACT.isPressed();
				boolean atkpress3 = BWSOptions.KEY_ATTACK3.isPressed();

				if(attackModule1.attackHeld != atkpress1){
					curWep.attackBeginStop1(self.thePlayer, self.thePlayer.world, atkpress1);
				}
				if(attackModule2.attackHeld != atkpress2){
					curWep.attackBeginStop2(self.thePlayer, self.thePlayer.world, atkpress2);
				}
				if(attackModule3.attackHeld != atkpress3){
					curWep.attackBeginStop3(self.thePlayer, self.thePlayer.world, atkpress3);
				}

				attackModule1.attackHeld = atkpress1;
				attackModule2.attackHeld = atkpress2;
				attackModule3.attackHeld = atkpress3;

				if (atkpress1) {
					curWep.attack1(self.thePlayer, self.thePlayer.world, wepStack, false);
					attackModule1.performAttack(curWep.attackDelay1, curWep.attackTiming1);
				}
				if (atkpress2) {
					curWep.attack2(self.thePlayer, self.thePlayer.world, wepStack, false);
					attackModule2.performAttack(curWep.attackDelay2, curWep.attackTiming2);
				}
				if (atkpress3) {
					curWep.attack3(self.thePlayer, self.thePlayer.world, wepStack, false);
					attackModule3.performAttack(curWep.attackDelay3, curWep.attackTiming3);
				}
			}

			if (attackModule1.shouldAttackTimed()) {
				curWep.attack1(self.thePlayer, self.thePlayer.world, wepStack, true);
			}
			if (attackModule2.shouldAttackTimed()) {
				curWep.attack2(self.thePlayer, self.thePlayer.world, wepStack, true);
			}
			if (attackModule3.shouldAttackTimed()) {
				curWep.attack3(self.thePlayer, self.thePlayer.world, wepStack, true);
			}


		}
	}


	@Inject(
		method = "clickMouse",
		at = @At(value = "HEAD"), cancellable = true)
	public void mouseClickIntervention(int clickType, boolean attack, boolean repeat, CallbackInfo ci){
		if(BWS.playerHoldsWeapon(self.thePlayer)){
			ci.cancel();
		}
	}
}
