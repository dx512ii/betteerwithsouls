package dxii.bws.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class AnimationFrame {
	public AnimationFrame DUMMY = new AnimationFrame(-1);

	public List<AnimationBoneKeys> keys = new ArrayList<>();
	public List<AnimationBoneKeys> events = new ArrayList<>();
	private final float frameTime;

	public AnimationFrame(float frameTime){
		this.frameTime = frameTime;
	}

	public AnimationFrame withKey(AnimationBoneKeys key){
		keys.add(key);

		return this;
	}

	public float time(){
		return frameTime;
	}
}
