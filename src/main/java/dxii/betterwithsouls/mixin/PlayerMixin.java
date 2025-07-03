package dxii.betterwithsouls.mixin;


import dxii.betterwithsouls.BWSConfig;
import dxii.betterwithsouls.BWSOptions;
import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.interfaces.IPlayer;
import dxii.betterwithsouls.item.BWSItemArmor;
import dxii.betterwithsouls.item.ItemWeapon;
import dxii.betterwithsouls.util.DamageResistModule;
import dxii.betterwithsouls.util.DynamicLight;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumSleepStatus;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin extends Mob implements IPlayer {

	@Shadow
	public boolean isSwinging;

	@Unique
	public float swingProgressNew;
	@Unique
	public float swingSpeed = 1;
	@Unique
	public Player thisObject = (Player)(Object)this;
	@Unique
	public float loadDisplay;
	@Unique
	public float mana;

	@Override
	public void takeMana(int mana){
		this.mana -= mana;
		if(this.mana < 0){
			this.mana = 0;
		}
	}
	@Override
	public boolean hasMana(int mana){
		return this.mana >= mana;
	}


	@Override
	public float getLoadDisplay() {
		return loadDisplay;
	}

	@Unique
	public DynamicLight dyn;

	public PlayerMixin(World world) {
		super(world);
	}

	@Shadow
	protected float baseSpeed;
	@Shadow
	protected float baseFlySpeed;



	@Shadow
	public ContainerInventory inventory;

	@Inject(
		method = "swingItem",
		at = @At(value = "TAIL"))
	public void swingItem(CallbackInfo ci) {
		this.swingProgressNew = 0;
		this.isSwinging = true;
	}
	@Inject(
		method = "sleepInBedAt",
		at = @At(value = "RETURN"))
	public void bedObstructQOL(int x, int y, int z, CallbackInfoReturnable<EnumSleepStatus> cir){
		Minecraft mc = Minecraft.getMinecraft();
		ChunkCoordinates spawnCoordinates;
		ChunkCoordinates bedSpawnCoordinates;
		if (mc.thePlayer != null && !mc.isMultiplayerWorld()) {
			spawnCoordinates = mc.thePlayer.getPlayerSpawnCoordinate();
			if (spawnCoordinates != null) {
				bedSpawnCoordinates = Player.getValidBedSpawnCoordinates(mc.currentWorld, spawnCoordinates);
				if (bedSpawnCoordinates == null) {
					mc.thePlayer.sendTranslatedChatMessage("bed.isObstructed");
				}
			}
		}
	}
	@Inject(
		method = "dropPlayerItemWithRandomChoice",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;addStat(Lnet/minecraft/core/achievement/stat/Stat;I)V"))
	public void dropAdditional(ItemStack itemstack, boolean flag, CallbackInfo ci){
		if(itemstack.getItem() instanceof ItemWeapon){
			((ItemWeapon)itemstack.getItem()).holster(itemstack, thisObject.world, thisObject);
		}
	}

	@Inject(
		method = "<init>",
		at = @At(value = "TAIL"))
	public void plyInit(CallbackInfo ci) {
		dyn = BWSUtils.addDynamicLight(thisObject, 0, 0, -1, 0, -1);

		DamageResistModule dResists = ((IMob)thisObject).getMobResist();
	}



	/**
	 * @author	yap
	 * @reason	yappson
	 */
	@Overwrite
	public void damageEntity(int damage, DamageType damageType) {
		super.damageEntity(damage, damageType);
	}


	/**
	 * @author	yap
	 * @reason	yappson
	 */
	@Overwrite
	public void updateAI() {
		if (this.isSwinging) {
			this.swingProgressNew += this.swingSpeed;
			if (this.swingProgressNew >= 8) {
				this.swingProgressNew = 0;
				this.isSwinging = false;
				this.swingSpeed = 1;
			}
		} else {
			this.swingProgressNew = 0;
		}
		this.swingProgress = MathHelper.clamp(this.swingProgressNew / 8, 0, 1);

		updatePlayerLight();
		updatePlayerSpeed();
	}

	@Unique
	public void updatePlayerSpeed(){
		float weight = 0;
		for(ItemStack stack : this.inventory.armorInventory){
			if(stack != null && stack.getItem() instanceof BWSItemArmor){
				weight += ((BWSItemArmor)stack.getItem()).weight;
			}
		}
		float load = weight / BWSConfig.PLAYER_LOAD_DEFAULT;

		float moveSpeedMul = MathHelper.clamp(1.25f - (load*0.35f), 0.8f, 1.25f);


		this.loadDisplay = weight;
		this.baseSpeed = .1f*moveSpeedMul;
	}

	@Unique
	public void updatePlayerLight(){

		if(!(BWSOptions.dynLightEnabled.value && BWSOptions.dynLightPlayer.value) || dyn == null){
			return;
		}


		ItemStack stack = thisObject.getHeldItem();

		boolean emits = false;

		int olbrightness = dyn.brightness;
		int brightness = dyn.brightness;
		int radius = dyn.radius;



		if(stack != null){
			Block<?> block = thisObject.world.getBlock((int) thisObject.x-1, (int) thisObject.y, (int) thisObject.z-1);
			boolean blockIsWater = block != null && block.hasTag(BlockTags.IS_WATER);

			boolean lowEmit = !blockIsWater && stack.itemID == Blocks.TORCH_REDSTONE_ACTIVE.id()
				|| stack.itemID == Items.DUST_GLOWSTONE.id
				|| stack.itemID == Items.NETHERCOAL.id;
			boolean medEmit = !blockIsWater && stack.itemID == Blocks.TORCH_COAL.id()
				|| stack.itemID == Items.LANTERN_FIREFLY_GREEN.id
				|| stack.itemID == Items.LANTERN_FIREFLY_BLUE.id
				|| !blockIsWater && stack.itemID == Blocks.PUMPKIN_CARVED_ACTIVE.id()
				|| !blockIsWater && stack.itemID == Items.LANTERN_FIREFLY_ORANGE.id;
			boolean highEmit = !blockIsWater && stack.itemID == Items.BUCKET_LAVA.id
				|| stack.itemID == Blocks.GLOWSTONE.id();


			emits = lowEmit || medEmit || highEmit;
			if(lowEmit) {
				brightness = 12;
				radius = 4;
			}
			if(medEmit) {
				brightness = 14;
				radius = 6;
			}
			if(highEmit) {
				brightness = 20;
				radius = 8;
			}
		}

		dyn.radius = radius;
		dyn.brightness =  BWSOptions.dynLightPlayer.value && emits ? brightness : 0;
		dyn.markBlocksNearby(true);
	}



	@ModifyArg(
		method = "attackTargetEntityWithCurrentItem(Lnet/minecraft/core/entity/Entity;)V",
		at = @At(value = "INVOKE", target = "net/minecraft/core/entity/Entity.hurt (Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"), index = 1)
	public int entAttackMixin(int i) {
		return i > 2 ? i : 0;
	}

	@Inject(
		method = "tick()V",
		at = @At(value = "TAIL"))
	public void tickMixin(CallbackInfo ci) {
	}

	@Override
	public void bws$setSwingSpeed(float speed){
		this.swingSpeed = speed;
	}

}
