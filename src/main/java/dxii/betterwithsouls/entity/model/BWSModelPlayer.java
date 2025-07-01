//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dxii.betterwithsouls.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BWSModelPlayer extends BWSModelBiped {
	public Cube bipedLeftArmOverlay;
	public Cube bipedRightArmOverlay;
	public Cube bipedLeftLegOverlay;
	public Cube bipedRightLegOverlay;
	public Cube bipedBodyOverlay;

	public BWSModelPlayer(float expansion) {
		this(expansion, 0.0F);
	}

	public BWSModelPlayer(float expansion, float yRotOffset) {
		this.holdingLeftHand = false;
		this.holdingRightHand = false;
		this.sneaking = false;
		this.cloak = new Cube(0, 0, 64, 32);
		this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, expansion, true);
		this.ear = new Cube(24, 0, 64, 64);
		this.ear.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, expansion, true);
		this.bipedLeftArmOverlay = new Cube(48, 48, 64, 64);
		this.bipedLeftArmOverlay.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, expansion + 0.25F, true);
		this.bipedLeftArmOverlay.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedRightArmOverlay = new Cube(40, 32, 64, 64);
		this.bipedRightArmOverlay.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, expansion + 0.25F, true);
		this.bipedRightArmOverlay.setRotationPoint(-5.0F, 2.0F, 10.0F);
		this.bipedLeftLegOverlay = new Cube(0, 48, 64, 64);
		this.bipedLeftLegOverlay.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expansion + 0.25F, true);
		this.bipedLeftLegOverlay.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedRightLegOverlay = new Cube(0, 32, 64, 64);
		this.bipedRightLegOverlay.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expansion + 0.25F, true);
		this.bipedRightLegOverlay.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.bipedBodyOverlay = new Cube(16, 32, 64, 64);
		this.bipedBodyOverlay.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, expansion + 0.25F, true);
		this.bipedBodyOverlay.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.armLeft = new Cube(32, 48, 64, 64);
		this.armLeft.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, expansion, true);
		this.armLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.legLeft = new Cube(16, 48, 64, 64);
		this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expansion, true);
		this.legLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.head = new Cube(0, 0, 64, 64);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, expansion, true);
		this.head.setRotationPoint(0.0F, 0.0F + yRotOffset, 0.0F);
		this.hair = new Cube(32, 0, 64, 64);
		this.hair.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, expansion + 0.5F, true);
		this.hair.setRotationPoint(0.0F, 0.0F + yRotOffset, 0.0F);
		this.body = new Cube(16, 16, 64, 64);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, expansion, true);
		this.body.setRotationPoint(0.0F, 0.0F + yRotOffset, 0.0F);
		this.armRight = new Cube(40, 16, 64, 64);
		this.armRight.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, expansion, true);
		this.armRight.setRotationPoint(-5.0F, 2.0F + yRotOffset, 0.0F);
		this.legRight = new Cube(0, 16, 64, 64);
		this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expansion, true);
		this.legRight.setRotationPoint(-2.0F, 12.0F + yRotOffset, 0.0F);

		setupAnimatedLimb(this.head, "head");
		setupAnimatedLimb(this.body, "body");
		setupAnimatedLimb(this.armRight, "armR");
		setupAnimatedLimb(this.armLeft, "armL");
		setupAnimatedLimb(this.legRight, "legR");
		setupAnimatedLimb(this.legLeft, "legL");
	}

	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.render(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		GL11.glPushMatrix();
		this.bipedLeftArmOverlay.render(scale);
		this.bipedRightArmOverlay.render(scale);
		this.bipedLeftLegOverlay.render(scale);
		this.bipedRightLegOverlay.render(scale);
		this.bipedBodyOverlay.render(scale);
		GL11.glPopMatrix();
	}

	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		copyTranslation(this.legLeft, this.bipedLeftLegOverlay);
		copyTranslation(this.legRight, this.bipedRightLegOverlay);
		copyTranslation(this.armLeft, this.bipedLeftArmOverlay);
		copyTranslation(this.armRight, this.bipedRightArmOverlay);
		copyTranslation(this.body, this.bipedBodyOverlay);
	}

	public static void copyTranslation(Cube sourceCube, Cube destinationCube) {
		destinationCube.xRot = sourceCube.xRot;
		destinationCube.yRot = sourceCube.yRot;
		destinationCube.zRot = sourceCube.zRot;
		destinationCube.x = sourceCube.x;
		destinationCube.y = sourceCube.y;
		destinationCube.z = sourceCube.z;
	}
}
