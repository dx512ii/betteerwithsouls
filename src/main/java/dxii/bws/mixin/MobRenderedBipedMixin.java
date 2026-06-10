package dxii.bws.mixin;

import dxii.bws.BWS;
import dxii.bws.animation.AnimatableEntity;
import dxii.bws.animation.Animation;
import dxii.bws.animation.AnimatableModel;
import dxii.bws.mixin.accessor.IAEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.render.camera.EntityCameraFirstPerson;
import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.useless.dragonfly.models.entity.StaticEntityModel;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererBiped.class, remap = false)
public class MobRenderedBipedMixin<T extends Mob> implements AnimatableModel {
	@Unique
	public MobRendererBiped<T> self = (MobRendererBiped<T>)(Object)this;

	@Unique
	List<String> animatableBones = new ArrayList<>();

	@Override
	public List<String> getAnimatableBones() {
		return animatableBones;
	}

	@Override
	public void addBoneToAnimate(String name) {
		animatableBones.add(name);
	}

	@Inject(
		method = "setupAnimations",
		at = @At(value = "RETURN"))
	public void animate(@NotNull T entity, @Nullable StaticEntityModel model, float partialTick, int layer, CallbackInfoReturnable<StaticEntityModel> cir){
		if(model == null){
			return;
		}

		// its kinda hard to comprehend how these patterns work but aah whatever
		if( !(entity instanceof AnimatableEntity aent) ){
			System.out.println("animation mixin failed for '" + entity + "', reason: not instance of animatable");
			return;
		}

		if(aent.isAnimating()){
			Animation anim = aent.getCurrentAnimation();

			float atTime = BWS.curtime() - aent.getLastAnimSent();
			if(anim.getPlayMode() == Animation.PlayMode.LOOP && atTime >= anim.getTotalDuration()){
				aent.setLastAnimSent(BWS.curtime());
			}

			Animation.animate(model, anim, BWS.curtime() - aent.getLastAnimSent());
		}

		if(showPlayerPartly(entity, model, partialTick)){
			((IAEntityRenderer)(self)).setShadowOpacity(0);
		}else{
			((IAEntityRenderer)(self)).setShadowOpacity(1);
		}
	}


	@Unique
	public boolean showPlayerPartly(@NotNull T entity, @Nullable StaticEntityModel model, float partialTick){
		if(model == null){
			return false;
		}
		if( !(self instanceof MobRendererPlayer) ){
			return false;
		}
		if(!BWS.playerHoldsWeapon(Minecraft.getMinecraft().thePlayer)){
			return false;
		}
		if( !(Minecraft.getMinecraft().activeCamera instanceof EntityCameraFirstPerson) ){
			return false;
		}
		if(Minecraft.getMinecraft().currentScreen instanceof ScreenInventory){
			return false;
		}



		float bodyYaw = this.getBodyYaw_(entity, partialTick);
		float headYaw = this.getHeadYaw_(entity, partialTick) - bodyYaw;
		float headPitch = this.getHeadPitch_(entity, partialTick);

		BWS.setupViewmodelAnimation(model, bodyYaw, headYaw, headPitch);

		return true;
	}

	@Unique
	protected float getHeadPitch_(@NotNull T entity, float partialTick) {
		return MathHelper.lerp(entity.xRotO, entity.xRot, partialTick) * MathHelper.DEG_TO_RAD;
	}


	@Unique
	private float getBodyYaw_(@NotNull T entity, float partialTick) {
		return MathHelper.lerp(entity.yBodyRotO, entity.yBodyRot, partialTick) * MathHelper.DEG_TO_RAD;
	}

	@Unique
	protected float getHeadYaw_(@NotNull T entity, float partialTick) {
		return MathHelper.lerp(entity.yRotO, entity.yRot, partialTick) * MathHelper.DEG_TO_RAD;
	}


}
