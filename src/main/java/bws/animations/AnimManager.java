package bws.animations;

import java.util.ArrayList;
import java.util.List;

public class AnimManager {
	public Animation currentAnim;
	public Animation nextAnimation;

	public boolean animating = false;
	private int currentFrameTime;
	private int maxFrameTime;
	private boolean animVariant = true;

	private List<Frame> frames = new ArrayList<>();
	private int framePointer;
	//private double currentFrameSpeed;
	private Frame prevFrame;
	private Frame currentFrame;


	public int currentEventDiration;
	public EAnimationEvent currentEvent;

	public AnimManager(){

	}

	public void nextAnimation(Animation anim){
		this.nextAnimation = anim;
	}

	public void sendAnimation(Animation anim){
		if(anim == null){
			return;
		}

		this.currentAnim = anim;
		this.frames = anim.frames;
		this.resetAnimation();
		this.currentFrameTime = 0;
		this.currentFrame = frames.get(0);
		this.prevFrame = this.currentFrame;
		this.animating = true;
	}

	public void sendDiffAnimation(Animation anim, Animation anim2){
		Animation animV = animVariant ? anim : anim2;
		animVariant = !animVariant;

		this.sendAnimation(animV);
	}

	private void stopAnimationInternal(){
		if(currentAnim != null && this.nextAnimation != null) {
			this.sendAnimation(nextAnimation);
			return;
		}
		this.animating = false;
		this.currentAnim = null;
	}

	public void stopAnimation(){
		this.animating = false;
		this.currentAnim = null;
	}

	public void update(){
		if(this.currentAnim == null || !this.animating){ return; }

		if (currentFrameTime == 0) {
			if (currentFrame == null) {
				stopAnimation();
			}else {
				Frame cycle = cycleFrames();
				if(cycle == null){
					stopAnimationInternal();
				}else {
					this.currentFrameTime = cycle.frameTime;
					this.maxFrameTime = cycle.frameTime;
					if(this.currentFrame.getEvent() != null) {
						this.currentEvent = this.currentFrame.getEvent();
						this.currentEventDiration = this.currentFrame.eventDuration;
					}
				}
			}
		}else{
			currentFrameTime--;
		}


		if(currentEventDiration > 0){
			currentEventDiration--;
		}else{
			currentEvent = null;
		}

	}

	public EAnimationEvent getCurrentEvent(){
		return currentEvent;
	}

	public Frame cycleFrames(){
		if(frames.isEmpty()){
			System.out.println("AnimManager: "+this+" :THIS ANIMATION HAS NO FRAMES!!");
			return null;
		}
		Frame return_;
		if(this.framePointer < 0){
			if(this.currentAnim.loop) {
				//System.out.println("looping..");
				resetPointer();
			}else {
				return null;
			}
		}
		this.prevFrame = this.currentFrame;
		this.currentFrame = frames.get(this.framePointer);
		//currentFrameSpeed = this.currentFrame.frameTime;
		return_ = this.currentFrame;
		this.framePointer--;

		return return_;
	}

	public void resetAnimation(){
		this.prevFrame = frames.get(0);
		this.resetPointer();
	}

	private void resetPointer(){
		this.framePointer = frames.size()-1;
	}

	public boolean isLimbAnimated(String name){
		if(currentAnim == null){
			return false;
		}
		Key key = getCurrentKeyByName(name);
		return key != Key.NULL_KEY;
	}

	public float getXLerp(String name){
		if(this.currentAnim == null){
			return 0;
		}
		float y1 = getCurrentKeyByName(name).x;
		float y2 = getPrevKeyByName(name).x;
		float x1 = 0;
		float x2 = maxFrameTime+1;
		float x = currentFrameTime;
		return y1 + ((y2 - y1) /(x2 - x1))*(x - x1);
	}
	public float getYLerp(String name){
		if(this.currentAnim == null){
			return 0;
		}
		float y1 = getCurrentKeyByName(name).y;
		float y2 = getPrevKeyByName(name).y;
		float x1 = 0;
		float x2 = maxFrameTime+1;
		float x = currentFrameTime;

		return y1 + ((y2 - y1) /(x2 - x1))*(x - x1);
	}
	public float getZLerp(String name){
		if(this.currentAnim == null){
			return 0;
		}
		float y1 = getCurrentKeyByName(name).z;
		float y2 = getPrevKeyByName(name).z;
		float x1 = 0;
		float x2 = maxFrameTime+1;
		float x = currentFrameTime;

		return y1 + ((y2 - y1) /(x2 - x1))*(x - x1);
	}

	public float getRotXLerp(String name){
		if(this.currentAnim == null){
			return 0;
		}
		float y1 = getCurrentKeyByName(name).rotX;
		float y2 = getPrevKeyByName(name).rotX;
		float x1 = 0;
		float x2 = maxFrameTime+1;
		float x = currentFrameTime;

		return y1 + ((y2 - y1) /(x2 - x1))*(x - x1);
	}
	public float getRotYLerp(String name){
		if(this.currentAnim == null){
			return 0;
		}
		float y1 = getCurrentKeyByName(name).rotY;
		float y2 = getPrevKeyByName(name).rotY;
		float x1 = 0;
		float x2 = maxFrameTime+1;
		float x = currentFrameTime;

		return y1 + ((y2 - y1) /(x2 - x1))*(x - x1);
	}
	public float getRotZLerp(String name){
		if(this.currentAnim == null){
			return 0;
		}
		float y1 = getCurrentKeyByName(name).rotZ;
		float y2 = getPrevKeyByName(name).rotZ;
		float x1 = 0;
		float x2 = maxFrameTime+1;
		float x = currentFrameTime;

		return y1 + ((y2 - y1) /(x2 - x1))*(x - x1);
	}

	public Key getPrevKeyByName(String name){
		Key key = prevFrame.getKeyByName(name);
		return key != Key.NULL_KEY ? key : Key.NULL_KEY;
	}

	public Key getCurrentKeyByName(String name){
		Key key = currentFrame.getKeyByName(name);
		if(key != Key.NULL_KEY){
			return key;
		}
		return Key.NULL_KEY;
	}
}
