package dxii.betterwithsouls.mixin;


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
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin implements IMinecraft {

	@Shadow
	public boolean inGameHasFocus;
	@Shadow
	private int mouseTicksRan;
	@Shadow
	private int ticksRan;
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
	public WorldRenderer worldRenderer;
	@Shadow
	public ControllerInput controllerInput;
	@Shadow
	public boolean isGamePaused;

	@Unique
	public CombatModule combatModule = new CombatModule(currentWorld, thePlayer);

	/**
	 * @author me
	 * @reason for the love of god
	 */
	@Overwrite
	public void clickMouse(int clickType, boolean attack, boolean repeat){
		if(
			this.thePlayer.getHeldItem() != null
			&& this.thePlayer.getHeldItem().getItem() instanceof ItemWeapon
		){

			ItemWeapon wep = (ItemWeapon) this.thePlayer.getHeldItem().getItem();

			if(clickType == 0){
				if(wep.atk1hold){
					combatModule.holdAttempt(0, wep, this.thePlayer.getHeldItem());
				}
			}
			else if(objectMouseOver != null){
					boolean flag = true;
					int blockX = this.objectMouseOver.x;
					int blockY = this.objectMouseOver.y;
					int blockZ = this.objectMouseOver.z;
					Side side = this.objectMouseOver.side;
					double yPlacedx = this.objectMouseOver.location.y - (double) this.objectMouseOver.y;
					double xPlacedx;
					if (side.getAxis() == Axis.X) {
						xPlacedx = this.objectMouseOver.location.x - (double) this.objectMouseOver.x;
					} else if (side.getAxis() == Axis.Z) {
						xPlacedx = this.objectMouseOver.location.z - (double) this.objectMouseOver.z;
					} else {
						xPlacedx = this.objectMouseOver.location.x - (double) this.objectMouseOver.x;
					}

					ItemStack stackx = this.thePlayer.inventory.getCurrentItem();
					int numItemsInStackx = stackx == null ? 0 : stackx.stackSize;
					if (this.playerController.useItemOn(this.thePlayer, this.currentWorld, stackx, blockX, blockY, blockZ, side, xPlacedx, yPlacedx)) {
						flag = false;
						this.playerController.swingItem(false);
					}else if(wep.atk2hold){
						combatModule.holdAttempt(1, wep, this.thePlayer.getHeldItem());
					}
					if (stackx == null) {
						return;
					}
					if (stackx.stackSize <= 0) {
						this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.getCurrentItemIndex()] = null;
					} else if (stackx.stackSize != numItemsInStackx) {
						this.worldRenderer.itemRenderer.resetEquippedProgress();
					}

					if (flag) {
						ItemStack itemstack = this.thePlayer.inventory.getCurrentItem();
						if (itemstack != null && this.playerController.useItem(this.thePlayer, this.currentWorld, itemstack)) {
							this.worldRenderer.itemRenderer.resetEquippedProgress();
						}
					}
				}else if(wep.atk2hold){
					combatModule.holdAttempt(1, wep, this.thePlayer.getHeldItem());
				}


		}else {//standart click logic
			defaultClick(clickType, attack, repeat);
		}
	}




	@Unique
	public void defaultClick(int clickType, boolean attack, boolean repeat){
		this.mouseTicksRan = this.ticksRan;
		boolean flag = true;
		if (this.objectMouseOver == null) {
			if (this.thePlayer.world != null || this.gameSettings.easyBridge.value && clickType == 1 && !(this.thePlayer.xRot < 45.0F)) {
				List<AABB> cubes = this.thePlayer.world.getCubes(this.thePlayer, this.thePlayer.bb.cloneMove(0.0, -1.0, 0.0));
				if (!cubes.isEmpty()) {
					AABB cube = cubes.get(0);
					label104:
					if (cube != null) {
						int blockX = MathHelper.floor(this.thePlayer.x);
						int blockY = MathHelper.floor(this.thePlayer.y - (double) this.thePlayer.heightOffset - 0.25);
						int blockZ = MathHelper.floor(this.thePlayer.z);
						Direction playerDirection = Direction.getHorizontalDirection(this.thePlayer);
						Side side;
						switch (playerDirection) {
							case NORTH:
								side = Side.NORTH;
								break;
							case SOUTH:
								side = Side.SOUTH;
								break;
							case WEST:
								side = Side.WEST;
								break;
							case EAST:
								side = Side.EAST;
								break;
							default:
								break label104;
						}

						double yPlaced = 0.5;
						double xPlaced = 0.5;
						ItemStack stack = this.thePlayer.inventory.getCurrentItem();
						int numItemsInStack = stack == null ? 0 : stack.stackSize;
						if (this.playerController.useItemOn(this.thePlayer, this.currentWorld, stack, blockX, blockY, blockZ, side, xPlaced, yPlaced)) {
							this.playerController.swingItem(true);
						}

						if (stack != null) {
							if (stack.stackSize <= 0) {
								this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.getCurrentItemIndex()] = null;
							} else if (stack.stackSize != numItemsInStack) {
								this.worldRenderer.itemRenderer.updateEquippedItem();
							}
						}
					}
				}
			}

			if (clickType == 0 && attack) {
				this.playerController.swingItem(true);
			}
		} else if (this.objectMouseOver.hitType == HitResult.HitType.ENTITY) {
			if (clickType == 0 && attack) {
				this.playerController.swingItem(true);
				this.playerController.attack(this.thePlayer, this.objectMouseOver.entity);
			}

			if (clickType == 1 && this.playerController.interact(this.thePlayer, this.objectMouseOver.entity)) {
				flag = false;
			}
		} else if (this.objectMouseOver.hitType == HitResult.HitType.TILE) {
			int blockX = this.objectMouseOver.x;
			int blockY = this.objectMouseOver.y;
			int blockZ = this.objectMouseOver.z;
			Side side = this.objectMouseOver.side;
			double yPlacedx = this.objectMouseOver.location.y - (double) this.objectMouseOver.y;
			double xPlacedx;
			if (side.getAxis() == Axis.X) {
				xPlacedx = this.objectMouseOver.location.x - (double) this.objectMouseOver.x;
			} else if (side.getAxis() == Axis.Z) {
				xPlacedx = this.objectMouseOver.location.z - (double) this.objectMouseOver.z;
			} else {
				xPlacedx = this.objectMouseOver.location.x - (double) this.objectMouseOver.x;
			}

			if (clickType == 0) {
				this.playerController.startDestroyBlock(blockX, blockY, blockZ, this.objectMouseOver.side, xPlacedx, yPlacedx, repeat);
				if (this.thePlayer.getHeldObject() == null) {
					this.playerController.swingItem(true);
				}
			} else {
				ItemStack stackx = this.thePlayer.inventory.getCurrentItem();
				int numItemsInStackx = stackx == null ? 0 : stackx.stackSize;
				if (this.playerController.useItemOn(this.thePlayer, this.currentWorld, stackx, blockX, blockY, blockZ, side, xPlacedx, yPlacedx)) {
					flag = false;
					this.playerController.swingItem(true);
				}

				if (stackx == null) {
					return;
				}

				if (stackx.stackSize <= 0) {
					this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.getCurrentItemIndex()] = null;
				} else if (stackx.stackSize != numItemsInStackx) {
					this.worldRenderer.itemRenderer.resetEquippedProgress();
				}
			}
		}

		if (flag && clickType == 1) {
			ItemStack itemstack = this.thePlayer.inventory.getCurrentItem();
			if (itemstack != null && this.playerController.useItem(this.thePlayer, this.currentWorld, itemstack)) {
				this.worldRenderer.itemRenderer.resetEquippedProgress();
			}
		}
	}



	@Inject(
		method = "runTick()V",
		at = @At(value = "HEAD"))
	public void tickMixin(CallbackInfo ci){
		if(!this.isGamePaused && this.thePlayer != null && this.currentWorld != null) {

			combatModule.update();
			combatModule.player = this.thePlayer;
			combatModule.world = this.currentWorld;

			boolean mouse1 = this.gameSettings.keyAttack.isPressed() || this.controllerInput != null && this.controllerInput.buttonRightTrigger.isPressed();
			boolean mouse2 = this.gameSettings.keyInteract.isPressed() || this.controllerInput != null && this.controllerInput.buttonLeftTrigger.isPressed();

			if (!combatModule.holding && this.thePlayer.getHeldItem() != null) {
				if(this.thePlayer.getHeldItem().getItem() instanceof ItemWeapon) {
					ItemWeapon wep = (ItemWeapon) this.thePlayer.getHeldItem().getItem();

					if (this.inGameHasFocus && mouse1 && !wep.atk1hold) {
						combatModule.attackAttempt(0, wep, this.thePlayer.getHeldItem());
					} else if (this.inGameHasFocus && mouse2 && !wep.atk2hold) {
						if (this.objectMouseOver != null && this.objectMouseOver.entity != null) {
							if (!this.playerController.interact(this.thePlayer, this.objectMouseOver.entity)) {
								combatModule.attackAttempt(1, wep, this.thePlayer.getHeldItem());
							}
						} else {
							combatModule.attackAttempt(1, wep, this.thePlayer.getHeldItem());
						}
					}
				}
			}
			if (combatModule.holding && (!mouse1 && combatModule.currentHoldType == 0 || !mouse2 && combatModule.currentHoldType == 1) ) {
				combatModule.holdRelease();
			}
		}
	}


	//hotbar locking when player attacks
	@Redirect(
		method = "checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.currentItemLocked ()Z")
	)
	private boolean hotbarLock1(ContainerInventory inv){
		return inv.currentItemLocked() || !this.combatModule.canInteract();
	}
	@Redirect(
		method = "handleControllerInput()V",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.currentItemLocked ()Z",ordinal = 0)
	)
	private boolean hotbarLock2(ContainerInventory inv){
		return inv.currentItemLocked() || !this.combatModule.canInteract();
	}
	@Redirect(
		method = "handleControllerInput()V",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.currentItemLocked ()Z",ordinal = 1)
	)
	private boolean hotbarLock3(ContainerInventory inv){
		return inv.currentItemLocked() || !this.combatModule.canInteract();
	}
	@ModifyArg(
		method ="checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.changeCurrentItem (I)V"
			, ordinal = 1), index = 0
	)
	public int hotbarLock4(int i){
		return this.combatModule.canInteract() ? i : 0;
	}
	@ModifyArg(
		method ="checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.changeCurrentItem (I)V"
			, ordinal = 2), index = 0
	)
	public int hotbarLock5(int i){
		return this.combatModule.canInteract() ? i : 0;
	}
	@Redirect(
		method ="checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/client/option/KeyBinding.isPressEvent (Lnet/minecraft/client/input/InputDevice;)Z"
			,ordinal = 21)
	)
	public boolean hotbarLock7(KeyBinding key, InputDevice eventInputDevice){
		return key.isPressEvent(eventInputDevice) && this.combatModule.canInteract();
	}
	@Redirect(
		method ="checkBoundInputs(Lnet/minecraft/client/input/InputDevice;)Z",
		at = @At(value = "INVOKE", target = "net/minecraft/client/option/KeyBinding.isPressEvent (Lnet/minecraft/client/input/InputDevice;)Z"
			,ordinal = 22)
	)
	public boolean hotbarLock8(KeyBinding key, InputDevice eventInputDevice){
		return key.isPressEvent(eventInputDevice) && this.combatModule.canInteract();
	}
	@ModifyArg(
		method ="runTick()V",
		at = @At(value = "INVOKE", target = "net/minecraft/core/player/inventory/container/ContainerInventory.changeCurrentItem (I)V"
		), index = 0
	)
	public int hotbarLock9(int i){
		return this.combatModule.canInteract() ? i : 0;
	}

}


