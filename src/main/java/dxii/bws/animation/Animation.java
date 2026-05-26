package dxii.bws.animation;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

import java.util.*;


public class Animation {
	public enum PlayMode{
		PLAY_ONCE,
		LOOP,
		HOLD
	}


	private final ArrayList<AnimationBoneKeys> keys = new ArrayList<>();
	public final Map<Float, AnimationEvent> events = new HashMap<>();
	private float totalDuration = 0;
	private PlayMode playMode = PlayMode.PLAY_ONCE;


	private static final List<Animation> animsRegistry = new ArrayList<>();
	public int id;

	public Animation(){
		this.id = animsRegistry.size();
		animsRegistry.add(this);
	}

	public static Animation getByID(int id){
		if(id > animsRegistry.size()-1){
			return null;
		}
		return animsRegistry.get(id);
	}

	public Animation setPlayMode(PlayMode mode){
		this.playMode = mode;

		return this;
	}
	public PlayMode getPlayMode(){
		return this.playMode;
	}

	/**
	 *it is ESSENTIAL that you put frames consequentially, from smallest time to biggest, or else the animation will break horribly
	 */
	public Animation putBone(AnimationBoneKeys frame){
		this.keys.add(frame);
		if(totalDuration < frame.getTotalDuration()){
			totalDuration = frame.getTotalDuration();
		}

		return this;
	}

	public Animation withEvent(float time, AnimationEvent event){
		this.events.put(time, event);

		return this;
	}

	public Animation copyEventsFrom(@NotNull Animation anim){
		for(float time : anim.events.keySet()){
			this.events.put(time, anim.events.get(time));
		}

		return this;
	}

	public float getTotalDuration(){
		return this.totalDuration;
	}

	/**
	 * animate the model directly
	 * @param model the model
	 * @param atTime time since the animation started relative to curtime (example: curtime - lastAnimStarted)
	 */
	public static void animate(@Nullable StaticEntityModel model, Animation anim, float atTime){
		animate(model, anim, atTime, 1);
	}
	public static void animate(@Nullable StaticEntityModel model, Animation anim, float atTime, float scale){
		if(model == null){
			return;
		}

		for(AnimationBoneKeys keys : anim.keys){
			BoneTransform bone = model.getTransform(keys.boneName);

			if(anim.playMode == PlayMode.HOLD) {
				atTime = Math.min(atTime, anim.getTotalDuration());
			}

			keys.animateBone(bone, atTime, scale);
		}
	}

	@Nullable public static BoneTransform getTransformByNameAt(String name, Animation anim, float atTime){
		for(AnimationBoneKeys keys : anim.keys){
			if(Objects.equals(keys.boneName, name)){
				return AnimationBoneKeys.getTransformAt(keys, atTime);
			}
		}

		return null;
	}

	/*
		if time >= total_time then
			return nil
		  end

		  local current_frame_time = 0
		  local next_frame_time = total_time

		  local current_frame
		  local next_frame

		  for i = 1, #frame_times do

			current_frame_time = frame_times[i]
			if #frame_times >= i+1 then
			  next_frame_time = frame_times[i+1]
			end

			if time > current_frame_time and time <= next_frame_time then
			  current_frame = frames[i]
			  next_frame = frames[i+1]

			  break
			end

		  end

		  local frame_delta_internal = time - current_frame_time
		  local frame_delta_total = next_frame_time - current_frame_time
		  local lerp_factor = frame_delta_internal/frame_delta_total
	 */

	/*
	BoneTransform TEMP = new BoneTransform();
	public void animateByName(BoneTransform bone, String limbName, float timeAt){
		if(timeAt >= this.totalTime){
			return;
		}

		float currentFrameTime = 0;
		float nextFrameTime;

		AnimationFrame currentFrame = null;
		AnimationFrame nextFrame = null;

		for(int i = 0; i < frames.size(); i++){
			currentFrameTime = frame_times.get(i);
			nextFrameTime = frame_times.get(i+1);

			if(timeAt > currentFrameTime && timeAt <= nextFrameTime){
				currentFrame = frames.get(i);
				nextFrame = frames.get(i+1);

				break;
			}
		}

		if(currentFrame == null && nextFrame == null){
			return;
		}

		BoneTransform currentFrameTransform = null;
		BoneTransform nextFrameTransform = null;

		if(currentFrame != null){
			currentFrameTransform = currentFrame.getBoneTransform(limbName);
		}if(currentFrame != null){
			nextFrameTransform = nextFrame.getBoneTransform(limbName);
		}

		float framesDeltaTotal = timeAt - currentFrameTime;
		float framesDeltaInternal = this.totalTime;
		float lerpFactor = framesDeltaInternal/framesDeltaTotal;

		double posX = MathHelper.lerp(currentFrameTransform != null ? currentFrameTransform.posX : 0, nextFrameTransform != null ? nextFrameTransform.posX : 0, lerpFactor);
		double posY = MathHelper.lerp(currentFrameTransform != null ? currentFrameTransform.posY : 0, nextFrameTransform != null ? nextFrameTransform.posY : 0, lerpFactor);
		double posZ = MathHelper.lerp(currentFrameTransform != null ? currentFrameTransform.posZ : 0, nextFrameTransform != null ? nextFrameTransform.posZ : 0, lerpFactor);

		if(posX != 0 || posY != 0 || posZ != 0){
			bone.posX = posX;
			bone.posY = posY;
			bone.posZ = posZ;
		}

		double rotX = MathHelper.lerp(currentFrameTransform != null ? currentFrameTransform.rotX : 0, nextFrameTransform != null ? nextFrameTransform.rotX : 0, lerpFactor);
		double rotY = MathHelper.lerp(currentFrameTransform != null ? currentFrameTransform.rotY : 0, nextFrameTransform != null ? nextFrameTransform.rotY : 0, lerpFactor);
		double rotZ = MathHelper.lerp(currentFrameTransform != null ? currentFrameTransform.rotZ : 0, nextFrameTransform != null ? nextFrameTransform.rotZ : 0, lerpFactor);

		if(rotX != 0 || rotY != 0 || rotZ != 0){
			bone.rotX = rotX;
			bone.rotY = rotY;
			bone.rotZ = rotZ;
		}

		bone.visible = currentFrameTransform == null || currentFrameTransform.visible;
	}
	*/
}
