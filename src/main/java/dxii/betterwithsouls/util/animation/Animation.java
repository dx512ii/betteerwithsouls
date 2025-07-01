package dxii.betterwithsouls.util.animation;

import java.util.ArrayList;
import java.util.List;

public class Animation {
	public final List<Frame> frames = new ArrayList<>();
	public boolean loop;
	public int[] actionFrames;


	public Animation(boolean loop){
		this.loop = loop;
	}

	public Animation withFrame(Frame frame){
		frames.add(0, frame);
		return this;
	}

	public Frame firstFrame(){
		return this.frames.get(0);
	}

	public int getDuration(){
		int dur = 0;
		for(Frame frame : frames){
			dur += frame.frameTime;
		}
		return dur;
	}

	public static Animation fromPoseInfo(BipedPoseInfo info){
		Animation anim = new Animation(true);
		Frame pose = new Frame(1);
		anim.withFrame(pose);
		for(BipedLimbInfo limb : info.limbs){
			//limb.dump();
			pose.withKey(new Key(limb.name, limb.posX, limb.posY, limb.posZ, limb.rotX, limb.rotY, limb.rotZ, limb.hasLoc(), limb.hasRot()).additiveLocation());
		}
		//System.out.println("-----------------------");

		return anim;
	}
}
