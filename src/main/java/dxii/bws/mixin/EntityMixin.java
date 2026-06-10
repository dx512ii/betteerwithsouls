package dxii.bws.mixin;

import dxii.bws.BWS;
import dxii.bws.animation.AnimEventHandler;
import dxii.bws.animation.AnimatableEntity;
import dxii.bws.animation.Animation;
import dxii.bws.animation.AnimationEvent;
import dxii.bws.entity.EntityCondition;
import dxii.bws.entity.IEntityExtra;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Global;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.sound.SoundCategory;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(value = Entity.class, remap = false)
public class EntityMixin implements AnimatableEntity, AnimEventHandler, IEntityExtra {

	@Unique
	private static final Random rand = new Random();
	@Unique
	Entity self = (Entity)(Object)this;

	@Unique
	Animation currentAnim;
	@Unique
	Animation nextAnim;

	@Unique
	float lastAnimSent;
	@Unique
	List<AnimationEvent.Scheduled> animationEvents = new ArrayList<>();
	@Unique
	List<EntityCondition.Scheduled> conds = new ArrayList<>();
	@Unique
	List<EntityCondition> activeConditions = new ArrayList<>();


	@Override
	public Animation getCurrentAnimation() {
		return this.currentAnim;
	}

	@Override
	public float getLastAnimSent() {
		return this.lastAnimSent;
	}

	@Override
	public void setLastAnimSent(float newLast) {
		this.lastAnimSent = newLast;
	}

	@Override
	public boolean isAnimating() {
		if(this.currentAnim == null){
			return false;
		}

		if(this.currentAnim.getPlayMode() == Animation.PlayMode.HOLD || this.currentAnim.getPlayMode() == Animation.PlayMode.LOOP){
			return true;
		}

		return BWS.curtime() < this.lastAnimSent + this.currentAnim.getTotalDuration();
	}


	@Override
	public void sendAnimation(Animation animation, boolean keepOnEqual) {
		if(animation == null){
			this.currentAnim = null;
			this.lastAnimSent = -1;

			return;
		}

		if(keepOnEqual && animation == this.currentAnim){
			return;
		}

		if(Global.DEBUG_MODE) {
			System.out.println("animation sent for '" + self + "': " + animation);
			System.out.println("duration: " + animation.getTotalDuration());
		}

		this.currentAnim = animation;


		for(float eventTime : animation.events.keySet()){
			AnimationEvent event = animation.events.get(eventTime);

			if(Global.DEBUG_MODE) System.out.println("	with event '" + event.type + "'");

			animationEvents.add( new AnimationEvent.Scheduled(event, BWS.curtime() + eventTime, event.executionsAmount) );
		}


		this.lastAnimSent = BWS.curtime();
	}

	@Override
	public void sendNextAnimation(Animation animation) {
		this.nextAnim = animation;
	}

	@Unique
	public float lastCleanup = 0;

	@Inject(
		method = "baseTick",
		at = @At(value = "HEAD"))
	public void handleAnimEvents(CallbackInfo ci){
		if( !(self instanceof AnimEventHandler) ){
			return;
		}

		if(!isAnimating() && this.nextAnim != null){
			sendAnimation(this.nextAnim, false);
			this.nextAnim = null;
		}

		float ct = BWS.curtime();

		// iterate through every scheduled event and check if it should be fired
		for(AnimationEvent.Scheduled event : this.animationEvents){
			if(ct >= event.timeScheduled && event.ticks > 0){
				if(Global.DEBUG_MODE) System.out.println(self+": firing animation event '" + event.eventRef.type + "'");

				handleAnimationEvent_base(event.eventRef);
				((AnimEventHandler)(self)).handleAnimationEvent(event.eventRef);

				--event.ticks;
			}
		}
		for(EntityCondition.Scheduled scond : conds){
			if(scond.removed()){
				return;
			}

			EntityCondition type = scond.cond;
			activeConditions.clear();
			handleCondition_base(type);
		}


		if(ct > lastCleanup + 2){
			this.animationEvents.removeIf(AnimationEvent.Scheduled::removed);
			this.conds.removeIf(EntityCondition.Scheduled::removed);

			lastCleanup = ct;
		}
	}

	@Unique
	public void handleCondition_base(@NonNull EntityCondition cond){
		if(!hasCondition(cond)){
			activeConditions.add(cond);
		}

		switch(cond){
			case POISON -> {

			}
			case MOTION_CONSTANT -> {

			}
			case INVULN_DODGE -> {

			}
		}
	}
	@Unique
	public void handleAnimationEvent_base(@NonNull AnimationEvent event){
		switch (event.type){
			case PLAY_SOUND -> {
				self.world.playSoundAtEntity(null, self, event.stringMeta, event.meta1, rand.nextFloat() * event.meta3*0.4F + event.meta2*.85f);
			}
			case PLAY_SOUND_POS -> {
				self.world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, self.x + event.meta1, self.y + event.meta2, self.z + event.meta3, event.stringMeta, event.meta4, rand.nextFloat() * event.meta6*0.4F + event.meta5*.85f);
			}
		}

		//mob specific
		if(self instanceof Mob mself){

			switch (event.type){
				case SYNC_BODY_ROT_Y -> {
					mself.yBodyRot = mself.yRot;
				}
			}
		}
	}

	@Override
	public void handleAnimationEvent(AnimationEvent event) {
	}

	@Override
	public void addCondition(EntityCondition cond, float duration, float meta1, float meta2, float meta3) {
		conds.add(new EntityCondition.Scheduled(cond, BWS.curtime(), duration, meta1, meta2, meta3));
	}

	@Override
	public boolean hasCondition(EntityCondition cond) {
		return activeConditions.contains(cond);
	}

	@Override
	public void removeCondition(EntityCondition cond) {
		if(!hasCondition(cond)){
			return;
		}
	}

	@Override
	public void handleConditionExtra(EntityCondition cond) {
	}
}
