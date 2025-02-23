package dxii.betterwithsouls.mixin;


import dxii.betterwithsouls.BWSMain;
import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.anims.BipedHumanoidAnimations;
import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.interfaces.IPlayer;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.DamageResistModule;
import dxii.betterwithsouls.util.DynamicLight;
import dxii.betterwithsouls.util.animation.AnimManager;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
	public DynamicLight dyn;

	public PlayerMixin(World world) {
		super(world);
	}



	/**
	 * @author	yap
	 * @reason	yappson
	 */
	@Overwrite
	public void swingItem() {
		this.swingProgressNew = 0;
		this.isSwinging = true;
	}

	@Inject(
		method = "<init>",
		at = @At(value = "TAIL"))
	public void plyInit(CallbackInfo ci) {
		if(BWSMain.dynLightEnabled.value && BWSMain.dynLightPlayer.value) {
			dyn = BWSUtils.addDynamicLight(thisObject, 5, 20, -1, 0, 0);
		}
		DamageResistModule dResists = ((IMob)thisObject).bws$getMobResist();
		dResists.setDefence(BWSDamageTypes.SLASH, 10);
	}

//	/**
//	 * @author	yap
//	 * @reason	yappson
//	 */
//	@Overwrite
//	public boolean hurt(Entity attacker, int damage, DamageType type) {
//		((IMobAccessor)thisObject).setEntityAge(0);
//		if (thisObject.getHealth() <= 0) {
//			return false;
//		} else if (thisObject.gamemode.isPlayerInvulnerable()) {
//			return false;
//		} else {
//			if (thisObject.isPlayerSleeping() && !thisObject.world.isClientSide) {
//				thisObject.wakeUpPlayer(true, true);
//			}
//
//			if (attacker instanceof MobMonster || attacker instanceof ProjectileArrow) {
//				switch (thisObject.world.getDifficulty()) {
//					case PEACEFUL:
//						damage = 0;
//						break;
//					case EASY:
//						damage = damage / 3 + 1;
//						break;
//					case HARD:
//						damage = damage * 3 / 2;
//				}
//			}
//
//			Entity blamedAttacker = attacker;
//			if (damage == 0 && !(attacker instanceof MobSnowman)) {
//				return false;
//			} else {
//				if (attacker instanceof ProjectileArrow && ((ProjectileArrow)attacker).owner != null) {
//					blamedAttacker = ((ProjectileArrow)attacker).owner;
//				}
//
//				if (blamedAttacker instanceof Mob) {
//					((IPlayerAccessor)thisObject).alertWolves((Mob)blamedAttacker, false);
//				}
//
//				thisObject.addStat(StatList.damageTakenStat, damage);
//				if (attacker != null) {
//					thisObject.addStat(StatList.mobEncounterStats.get(EntityDispatcher.idForClass(attacker.getClass())), 1);
//				}
//
//				return ((IMob)thisObject).bws$hurt(attacker, damage, type);
//			}
//		}
//	}


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
		updatePlayerLight();

		((Mob)(Object)this).swingProgress = MathHelper.clamp(this.swingProgressNew / 8, 0, 1);
	}

	@Unique
	public void updatePlayerLight(){

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
				|| stack.itemID == Blocks.PUMPKIN_CARVED_ACTIVE.id()
				|| stack.itemID == Items.LANTERN_FIREFLY_ORANGE.id;
			boolean highEmit = !blockIsWater && stack.itemID == Items.BUCKET_LAVA.id
				|| stack.itemID == Blocks.GLOWSTONE.id();


			emits = lowEmit || medEmit || highEmit;
			if(lowEmit) {
				brightness = 12;
				radius = 3;
			}
			if(medEmit) {
				brightness = 17;
				radius = 5;
			}
			if(highEmit) {
				brightness = 20;
				radius = 8;
			}
		}

		dyn.brightness =  BWSMain.dynLightPlayer.value && emits ? brightness : 0;
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
