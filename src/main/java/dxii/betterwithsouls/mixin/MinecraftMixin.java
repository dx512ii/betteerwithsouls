package dxii.betterwithsouls.mixin;



import dxii.betterwithsouls.BWSOptions;
import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.interfaces.IMinecraft;
import dxii.betterwithsouls.item.ItemWeapon;
import dxii.betterwithsouls.util.CombatModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.input.controller.ControllerInput;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin implements IMinecraft {

	@Unique
	public Vec3 itemDummyPos = Vec3.getPermanentVec3(0, 0, 0);
	@Unique
	public Vec3 itemDummyRot = Vec3.getPermanentVec3(0, 0, 0);


	@Override
	public Vec3 getItemDummyPos() {
		return this.itemDummyPos;
	}

	@Override
	public void addToItemDummyPos(double x, double y, double z) {
		this.itemDummyPos.x = this.itemDummyPos.x+x;
		this.itemDummyPos.y = this.itemDummyPos.y+y;
		this.itemDummyPos.z = this.itemDummyPos.z+z;
	}

	@Override
	public Vec3 getItemDummyRot() {
		return this.itemDummyRot;
	}

	@Override
	public void addToItemDummyRot(double x, double y, double z) {
		this.itemDummyRot.x = this.itemDummyRot.x+x;
		this.itemDummyRot.y = this.itemDummyRot.y+y;
		this.itemDummyRot.z = this.itemDummyRot.z+z;
	}

	@Override
	public void resetItemDummy() {
		this.itemDummyPos.x = 0;
		this.itemDummyPos.y = 0;
		this.itemDummyPos.z = 0;
		this.itemDummyRot.x = 0;
		this.itemDummyRot.y = 0;
		this.itemDummyRot.z = 0;
	}

	@Override
	public CombatModule getCombatModule() {
		return cmodule;
	}

	@Shadow
	public boolean inGameHasFocus;
	@Shadow
	public HitResult objectMouseOver;
	@Shadow
	public GameSettings gameSettings;
	@Shadow
	public PlayerController playerController;
	@Shadow
	public PlayerLocal thePlayer;
	@Shadow
	public WorldClient currentWorld;
	@Shadow
	public ControllerInput controllerInput;

	@Unique
	public CombatModule cmodule = new CombatModule(this.thePlayer);

	@Unique
	public int interactDelay;

	@Inject(
		method = "clickMouse",
		at = @At(value = "HEAD"), cancellable = true)
	public void combatThingie(int clickType, boolean attack, boolean repeat, CallbackInfo ci){
		if(BWSUtils.playerHoldsWeapon(this.thePlayer) != null){
			ci.cancel();
			if(this.objectMouseOver != null){
				if (this.objectMouseOver.hitType == HitResult.HitType.ENTITY) {
					if (clickType == 1) {
						this.playerController.interact(this.thePlayer, this.objectMouseOver.entity);
					}
				} else if (clickType == 1 && interactDelay <= 0 && this.objectMouseOver.hitType == HitResult.HitType.TILE) {
					boolean atk1 = this.gameSettings.keyAttack.isPressed() || this.controllerInput != null && this.controllerInput.buttonRightTrigger.isPressed();
					if(atk1 || !cmodule.canAttack()){
						return;
					}
					int blockX = this.objectMouseOver.x;
					int blockY = this.objectMouseOver.y;
					int blockZ = this.objectMouseOver.z;
					Side side = this.objectMouseOver.side;
					double yPlaced = this.objectMouseOver.location.y - (double)this.objectMouseOver.y;
					double xPlaced;
					if (side.getAxis() == Axis.X) {
						xPlaced = this.objectMouseOver.location.x - (double)this.objectMouseOver.x;
					} else if (side.getAxis() == Axis.Z) {
						xPlaced = this.objectMouseOver.location.z - (double)this.objectMouseOver.z;
					} else {
						xPlaced = this.objectMouseOver.location.x - (double)this.objectMouseOver.x;
					}

					interactDelay = 4;

					ItemStack stack = this.thePlayer.inventory.getCurrentItem();
					if (this.playerController.useOrPlaceItemStackOnTile(this.thePlayer, this.currentWorld, stack, blockX, blockY, blockZ, side, xPlaced, yPlaced)) {
						this.playerController.swingItem(true);
					}
					cmodule.attackAttempt(1, BWSUtils.playerHoldsWeapon(this.thePlayer));
				}
			}
		}
	}

	@Inject(
		method = "runTick()V",
		at = @At(value = "HEAD"))
	public void tickMixin(CallbackInfo ci){
		if(interactDelay > 0){
			interactDelay--;
		}
		if(interactDelay > 0){
			interactDelay--;
		}
		cmodule.update();
		if(this.inGameHasFocus) {
			boolean atk1 = this.gameSettings.keyAttack.isPressed() || this.controllerInput != null && this.controllerInput.buttonRightTrigger.isPressed();
			boolean atk2 = this.gameSettings.keyInteract.isPressed() || this.controllerInput != null && this.controllerInput.buttonLeftTrigger.isPressed();
			boolean parry = BWSOptions.keyParry.isPressed();
			if (this.thePlayer != null && BWSUtils.playerHoldsWeapon(this.thePlayer) != null) {
				ItemWeapon wep = BWSUtils.playerHoldsWeapon(this.thePlayer);

				if (atk1) {
					cmodule.attackAttempt(0, wep);
				}
				if (atk2) {
					cmodule.attackAttempt(1, wep);
				}
				if (parry) {
					cmodule.attackAttempt(2, wep);
				}
			}
		}
	}

	@Inject(
		method = "respawn",
		at = @At(value = "TAIL"))
	public void respawnAdditional(boolean multiplayer, int targetDimension, CallbackInfo ci){
		this.cmodule.respawn(currentWorld, thePlayer);
	}
	@Inject(
		method = "changeWorld(Lnet/minecraft/client/world/WorldClient;Ljava/lang/String;Lnet/minecraft/core/entity/player/Player;)V",
		at = @At(value = "TAIL"))
	public void changeWorldAdditional(WorldClient world, String loadingTitle, Player player, CallbackInfo ci){
		this.cmodule.respawn(currentWorld, thePlayer);
	}


	@Inject(
		method = "mineBlocks",
		at = @At(value = "HEAD"), cancellable = true)
	public void miningNah(int i, boolean flag, CallbackInfo ci){
		if(this.thePlayer.getHeldItem() != null && this.thePlayer.getHeldItem().getItem() instanceof ItemWeapon){
			ci.cancel();
		}
	}
	@Redirect(
		method = "checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.currentItemLocked ()Z")
	)
	private boolean hotbarLock1(ContainerInventory inv){
		return inv.currentItemLocked() || !this.cmodule.canAttack();
	}
	@Redirect(
		method = "handleControllerInput()V",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.currentItemLocked ()Z",ordinal = 0)
	)
	private boolean hotbarLock2(ContainerInventory inv){
		return inv.currentItemLocked() || !this.cmodule.canAttack();
	}
	@Redirect(
		method = "handleControllerInput()V",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.currentItemLocked ()Z",ordinal = 1)
	)
	private boolean hotbarLock3(ContainerInventory inv){
		return inv.currentItemLocked() || !this.cmodule.canAttack();
	}
	@ModifyArg(
		method ="checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.changeCurrentItem (I)V"
			, ordinal = 1), index = 0
	)
	public int hotbarLock4(int i){
		return this.cmodule.canAttack() ? i : 0;
	}
	@ModifyArg(
		method ="checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.changeCurrentItem (I)V"
			, ordinal = 2), index = 0
	)
	public int hotbarLock5(int i){
		return this.cmodule.canAttack() ? i : 0;
	}
	@Redirect(
		method ="checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/client/option/KeyBinding.isPressEvent (Lnet/minecraft/client/input/InputDevice;)Z"
			,ordinal = 21)
	)
	public boolean hotbarLock7(KeyBinding key, InputDevice eventInputDevice){
		return key.isPressEvent(eventInputDevice) && this.cmodule.canAttack();
	}
	@Redirect(
		method ="checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/client/option/KeyBinding.isPressEvent (Lnet/minecraft/client/input/InputDevice;)Z"
			,ordinal = 22)
	)
	public boolean hotbarLock8(KeyBinding key, InputDevice eventInputDevice){
		return key.isPressEvent(eventInputDevice) && this.cmodule.canAttack();
	}
	@ModifyArg(
		method ="runTick()V",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.changeCurrentItem (I)V"
		), index = 0
	)
	public int hotbarLock9(int i){
		return this.cmodule.canAttack() ? i : 0;
	}


}


