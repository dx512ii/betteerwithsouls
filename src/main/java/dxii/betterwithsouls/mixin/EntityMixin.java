package dxii.betterwithsouls.mixin;

import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.util.animation.AnimManager;
import dxii.betterwithsouls.util.animation.Animation;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public class EntityMixin implements IEntity {

	//animation
	@Unique
	public AnimManager animManager = new AnimManager();
	@Unique
	public AnimManager VMAnimManager = new AnimManager();
	@Unique
	boolean canBeParried = false;

	@Override
	public void bws$sendEntityAnim(Animation anim) {
		if(anim==null){return;}
		this.animManager.sendAnimation(anim);
	}

	@Override
	public void bws$sendEntityDiffAnim(Animation anim, Animation anim2) {
		if(anim==null || anim2==null){return;}
		this.animManager.sendDiffAnimation(anim, anim2);
	}

	@Override
	public AnimManager bws$getAnimManager() {
		return this.animManager;
	}
	@Override
	public AnimManager bws$getVMManager() {
		return this.VMAnimManager;
	}
	@Override
	public void setCanBeParried(boolean yea) {
		this.canBeParried = true;
	}
	@Override
	public boolean canBeParried() {
		return this.canBeParried;
	}


	@Inject(
		method = "baseTick",
		at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;firstTick:Z")
	)
	public void tickMixin(CallbackInfo ci) {
		this.animManager.update();
		this.VMAnimManager.update();
	}
}
