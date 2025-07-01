package dxii.betterwithsouls.util.animation;


import net.minecraft.core.entity.Mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnimationRegistry {
	public static AnimationRegistry INSTANCE = new AnimationRegistry();

	public AnimationRegistry(){

	}

	public final ArrayList<Class<? extends Mob>> classes = new ArrayList<>();
	public final Map<Class<? extends Mob>, BipedPoseInfo> poses = new HashMap<>();

	public void addAnimatable(Class<? extends Mob> clazz, BipedPoseInfo pose){
		System.out.println("AnimRegistry: adding class - '"+clazz.getSimpleName()+"'");
		for(BipedLimbInfo limb : pose.limbs){
			System.out.println("limb: "+limb.name);
		}
		classes.add(clazz);
		poses.put(clazz, pose);
	}

	public BipedPoseInfo getPoseByClass(Class<? extends Mob> clazz) {
		return poses.get(clazz);
	}


}
