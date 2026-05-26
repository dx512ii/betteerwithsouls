package dxii.bws.animation;

import net.minecraft.core.util.helper.MathHelper;
import org.useless.dragonfly.models.entity.BoneTransform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationBoneKeys {
	public String boneName;
	private final Map<Float, BoneTransform> transforms = new HashMap<>();
	private float totalDuration = 0;
	private final boolean overrideLoc;
	private final boolean overrideRot;

	public AnimationBoneKeys(String boneName){
		this(boneName, true, true);
	}
	public AnimationBoneKeys(String boneName, boolean overrideLoc, boolean overrideRot){
		this.boneName = boneName;
		this.overrideLoc = overrideLoc;
		this.overrideRot = overrideRot;
	}

	public float getTotalDuration(){
		return this.totalDuration;
	}

	public float[] getTimestampsAt(float atTime){
		List<Float> timeStamps = transforms.keySet().stream().toList();

		float stampCurrent = 0;
		float stampNext = 0;
		int last_i = 0;
		for(int i = 0; i < timeStamps.size(); ++i){
			stampCurrent = timeStamps.get(i);
			stampNext = timeStamps.get( Math.min(i+1, timeStamps.size()-1) );

			if((atTime >= stampCurrent) && atTime <= stampNext){
				break;
			}
		}

		return new float[]{stampCurrent, stampNext};
	}

	public static float getRatioBetweenFramesAt(float stampCurrent, float stampNext, float atTime){
		float stepInternal = atTime - stampCurrent;
		float stepTotal = stampNext - stampCurrent;

		return stepInternal/stepTotal;
	}

	public static void setupTransform(BoneTransform bone, double posX, double posY, double posZ, double rotX, double rotY, double rotZ, boolean overrideLoc, boolean overrideRot){
		if(overrideLoc) {

			bone.posX = posX;
			bone.posY = posY;
			bone.posZ = posZ;

		}else{

			if(posX != 0){
				bone.posX = posX;
			}
			if(posY != 0){
				bone.posY = posY;
			}
			if(posX != 0){
				bone.posZ = posZ;
			}

		}

		if(overrideRot) {

			bone.rotX = rotX;
			bone.rotY = rotY;
			bone.rotZ = rotZ;

		}else{

			if(rotX != 0){
				bone.rotX = rotX;
			}
			if(rotY != 0){
				bone.rotY = rotY;
			}
			if(rotX != 0){
				bone.rotZ = rotZ;
			}

		}
	}


	/**
	 * @param atTime - time reative to animation
	 * @return temporary transform which will be overwritten by some other call,
	 * use its values right away or store in another variable!
	 */
	static BoneTransform TEMP = new BoneTransform();
	public static BoneTransform getTransformAt(AnimationBoneKeys keys, float atTime){

		float[] timestamps = keys.getTimestampsAt(atTime);

		float stampCurrent = timestamps[0];
		float stampNext = timestamps[1];

		BoneTransform transformCurrent = keys.transforms.get( stampCurrent );
		BoneTransform transformNext = keys.transforms.get( stampNext );

		float lerpAmount = getRatioBetweenFramesAt(stampCurrent, stampNext, atTime);

		double posX;
		double posY;
		double posZ;

		double rotX;
		double rotY;
		double rotZ;

		if(lerpAmount <= 0 || !transformsDiffer(transformCurrent, transformNext)){
			posX = transformCurrent.posX;
			posY = transformCurrent.posY;
			posZ = transformCurrent.posZ;

			rotX = transformCurrent.rotX;
			rotY = transformCurrent.rotY;
			rotZ = transformCurrent.rotZ;
		} else if (lerpAmount >= 1) {
			posX = transformNext.posX;
			posY = transformNext.posY;
			posZ = transformNext.posZ;

			rotX = transformNext.rotX;
			rotY = transformNext.rotY;
			rotZ = transformNext.rotZ;
		}else{
			posX = MathHelper.lerp(transformCurrent.posX, transformNext.posX, lerpAmount);
			posY = MathHelper.lerp(transformCurrent.posY, transformNext.posY, lerpAmount);
			posZ = MathHelper.lerp(transformCurrent.posZ, transformNext.posZ, lerpAmount);

			rotX = MathHelper.lerp(transformCurrent.rotX, transformNext.rotX, lerpAmount);
			rotY = MathHelper.lerp(transformCurrent.rotY, transformNext.rotY, lerpAmount);
			rotZ = MathHelper.lerp(transformCurrent.rotZ, transformNext.rotZ, lerpAmount);
		}

		setupTransform(TEMP, posX, posY, posZ, rotX, rotY, rotZ, keys.overrideLoc, keys.overrideRot);

		return TEMP;
	}

	public void animateBone(BoneTransform bone, float atTime, float scale){
		float[] timestamps = getTimestampsAt(atTime);

		float stampCurrent = timestamps[0];
		float stampNext = timestamps[1];

		BoneTransform transformCurrent = transforms.get( stampCurrent );
		BoneTransform transformNext = transforms.get( stampNext );

		float lerpAmount = MathHelper.clamp(getRatioBetweenFramesAt(stampCurrent, stampNext, atTime), 0, 1);

		double posX = MathHelper.lerp(transformCurrent.posX, transformNext.posX*scale, lerpAmount);
		double posY = MathHelper.lerp(transformCurrent.posY, transformNext.posY*scale, lerpAmount);
		double posZ = MathHelper.lerp(transformCurrent.posZ, transformNext.posZ*scale, lerpAmount);

		double rotX = MathHelper.lerp(transformCurrent.rotX, transformNext.rotX*scale, lerpAmount);
		double rotY = MathHelper.lerp(transformCurrent.rotY, transformNext.rotY*scale, lerpAmount);
		double rotZ = MathHelper.lerp(transformCurrent.rotZ, transformNext.rotZ*scale, lerpAmount);

		setupTransform( bone, posX, posY, posZ, rotX, rotY, rotZ, this.overrideLoc, this.overrideRot);
	}

	public static boolean transformsDiffer(BoneTransform t1, BoneTransform t2){
		return
			t1.posX != t2.posX ||
			t1.posY != t2.posY ||
			t1.posZ != t2.posZ ||
			t1.rotX != t2.rotX ||
			t1.rotY != t2.rotY ||
			t1.rotZ != t2.rotZ
			;
	}

	public AnimationBoneKeys withPosKey(float time, double x, double y, double z) {
		return this.withPosKey(time, (float)x, (float)y, (float)z);
	}
	public AnimationBoneKeys withPosKey(float time, float x, float y, float z){
		if(!this.transforms.containsKey(0f)){
			this.transforms.put(0f, new BoneTransform());
		}

		if(!this.transforms.containsKey(time)){
			this.transforms.put(time, new BoneTransform());
		}
		if(this.totalDuration < time){
			this.totalDuration = time;
		}

		BoneTransform transform = this.transforms.get(time);

		transform.posX = x;
		transform.posY = y;
		transform.posZ = z;

		return this;
	}

	public AnimationBoneKeys withRotKey(float time, double x, double y, double z) {
		return this.withRotKey(time, (float)x, (float)y, (float)z);
	}
	public AnimationBoneKeys withRotKey(float time, float x, float y, float z){
		if(!this.transforms.containsKey(0f)){
			this.transforms.put(0f, new BoneTransform());
		}

		if(!this.transforms.containsKey(time)){
			this.transforms.put(time, new BoneTransform());
		}
		if(totalDuration < time){
			totalDuration = time;
		}

		BoneTransform transform = this.transforms.get(time);

		transform.rotX = MathHelper.toRadians(x);
		transform.rotY = MathHelper.toRadians(y);
		transform.rotZ = MathHelper.toRadians(z);

		return this;
	}
}
