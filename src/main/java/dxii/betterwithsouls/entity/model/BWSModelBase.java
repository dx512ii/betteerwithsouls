package dxii.betterwithsouls.entity.model;

import dxii.betterwithsouls.entity.BWSMob;
import dxii.betterwithsouls.interfaces.ICube;
import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.util.animation.AnimManager;
import dxii.betterwithsouls.util.animation.AnimationRegistry;
import dxii.betterwithsouls.util.animation.BipedPoseInfo;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

public class BWSModelBase extends ModelBase {
	public float onGround;
	public boolean isRiding = false;

	public Vec3 itemRotation = Vec3.getPermanentVec3(0, 0, 0);
	public Vec3 itemTranslation = Vec3.getPermanentVec3(0, 0, 0);

	public List<Cube> animatedLimbs = new ArrayList<>();
	AnimManager currentManager;

	@Override
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		animateLimbs(this.currentManager);
	}

	private boolean firstTick = true;
	public void renderCustom(Mob mob) {
		if(firstTick){
			currentManager = ((IEntity)mob).bws$getAnimManager();
			System.out.println(mob.getClass().getSimpleName()+": attempt to add to registry...");
			if((mob instanceof BWSMob && ((BWSMob)mob).debug)){
				BipedPoseInfo pose = new BipedPoseInfo(this.animatedLimbs);
				AnimationRegistry.INSTANCE.addAnimatable(mob.getClass(), pose);
			}

			this.firstTick = false;
		}

	}

	@Unique
	public void animateLimbs(AnimManager manager){
		if(manager == null){
			return;
		}
//		System.out.println("animatin'...");

		for(Cube limb : animatedLimbs){
			String name = ((ICube)limb).getName();
			if(manager.isLimbAnimated(name)){
				boolean location = manager.getCurrentKeyByName(name).location;
				boolean rotation = manager.getCurrentKeyByName(name).rotation;

				if(location){
					((ICube)limb).setOffset(
						manager.getXLerp(name),
						manager.getYLerp(name),
						manager.getZLerp(name));
				}else{
					((ICube)limb).setOffset(0, 0, 0);
				}
				if(rotation){
					limb.xRot = MathHelper.toRadians(manager.getRotXLerp(name))+.00001f;
					limb.yRot = MathHelper.toRadians(manager.getRotYLerp(name))+.00001f;
					limb.zRot = MathHelper.toRadians(manager.getRotZLerp(name))+.00001f;
					boolean additiveRotation = manager.getCurrentKeyByName(name).additiveRot;
					if(additiveRotation){
						((ICube)limb).setRotationOffset(
							manager.getRotXLerp(name),
							manager.getRotYLerp(name),
							manager.getRotZLerp(name));
					}else{
						((ICube)limb).setRotationOffset(0, 0, 0);
					}
				}
			}else{
				((ICube)limb).setOffset(0, 0, 0);
			}
		}

		if(manager.isLimbAnimated("weapon")){
			boolean location = manager.getCurrentKeyByName("weapon").location;
			boolean rotation = manager.getCurrentKeyByName("weapon").rotation;

			if(location){
				this.itemTranslation.x = manager.getXLerp("weapon");
				this.itemTranslation.y = manager.getYLerp("weapon");
				this.itemTranslation.z = manager.getZLerp("weapon");
			}else{
				this.itemTranslation.x = 0;
				this.itemTranslation.y = 0;
				this.itemTranslation.z = 0;
			}
			if(rotation){
				this.itemRotation.x = manager.getRotXLerp("weapon");
				this.itemRotation.y = manager.getRotYLerp("weapon");
				this.itemRotation.z = manager.getRotZLerp("weapon");
			}else{
				this.itemRotation.x = 0;
				this.itemRotation.y = 0;
				this.itemRotation.z = 0;
			}
		}else{
			this.itemTranslation.x = 0;
			this.itemTranslation.y = 0;
			this.itemTranslation.z = 0;

			this.itemRotation.x = 0;
			this.itemRotation.y = 0;
			this.itemRotation.z = 0;
		}
	}


	public void setupAnimatedLimb(Cube cube, String name){
		((ICube)cube).setName(name);
		this.animatedLimbs.add(cube);
	}

	public void resetAnimatedLimbs() {
		this.animatedLimbs.clear();
	}

	public Vec3 getItemRot() {
		return this.itemRotation;
	}

	public void setItemRot(Vec3 rot) {
		this.itemRotation = rot;
	}

	public Vec3 getItemTranslation() {
		return this.itemTranslation;
	}

	public void setItemTranslation(double x, double y, double z) {
		this.itemTranslation.x = x;
		this.itemTranslation.y = y;
		this.itemTranslation.z = z;
	}

	public void modifyBox(Cube cube, float minX, float minY, float minZ, int sizeX, int sizeY, int sizeZ, float expandAmount, boolean flipBottomUV) {
		((ICube)cube).decompile();
		cube.addBox(minX, minY, minZ, sizeX, sizeY, sizeZ, expandAmount);
	}


	/**
	 * add box to a cube using pos, rot, scale and pivot pos directly from blockbench, (bedrock entity model)
	 * this thing will do all needed adjustments
	 */
	public void addBoxBlockbench(Cube cube, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, int pivotX, int pivotY, int pivotZ, float expandAmount){
		//just to make code more readable and get rid of these goddamn casts (they look ugly)
		((ICube)cube).addBoxBlockbench(posX, posY, posZ, sizeX, sizeY, sizeZ, pivotX, pivotY, pivotZ, expandAmount);
	}
}
