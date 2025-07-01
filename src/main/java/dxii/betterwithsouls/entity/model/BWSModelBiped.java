package dxii.betterwithsouls.entity.model;

import dxii.betterwithsouls.entity.BWSMob;
import dxii.betterwithsouls.interfaces.ICube;
import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.util.animation.AnimManager;
import dxii.betterwithsouls.util.animation.AnimationRegistry;
import dxii.betterwithsouls.util.animation.BipedPoseInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class BWSModelBiped extends BWSModelBase {
	public Cube head;
	public Cube hair;
	public Cube body;
	public Cube armRight;
	public Cube armLeft;
	public Cube legRight;
	public Cube legLeft;
	public Cube ear;
	public Cube cloak;
	public boolean holdingLeftHand;
	public boolean holdingRightHand;
	public boolean holdingLarge = false;
	public boolean sneaking;

	public BWSModelBiped() {
		this(0.0F);
	}

	public BWSModelBiped(float expansion) {
		this(expansion, 0.0F);
	}

	public BWSModelBiped(float expansion, float yRotOffset) {
		this.holdingLeftHand = false;
		this.holdingRightHand = false;
		this.sneaking = false;
		this.cloak = new Cube(0, 0);
		this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, expansion);
		this.ear = new Cube(24, 0);
		this.ear.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, expansion);
		this.head = new Cube(0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, expansion);
		this.head.setRotationPoint(0.0F, 0.0F + yRotOffset, 0.0F);


		this.hair = new Cube(32, 0);
		this.hair.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, expansion + 0.5F);
		this.hair.setRotationPoint(0.0F, 0.0F + yRotOffset, 0.0F);
		this.body = new Cube(16, 16);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, expansion);
		this.body.setRotationPoint(0.0F, 0.0F + yRotOffset, 0.0F);

		this.armRight = new Cube(40, 16);
		this.armRight.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, expansion);
		this.armRight.setRotationPoint(-5.0F, 2.0F + yRotOffset, 0.0F);

		this.armLeft = new Cube(40, 16);
		this.armLeft.mirror = true;
		this.armLeft.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, expansion);
		this.armLeft.setRotationPoint(5.0F, 2.0F + yRotOffset, 0.0F);

		this.legRight = new Cube(0, 16);
		this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expansion);
		this.legRight.setRotationPoint(-2.0F, 12.0F + yRotOffset, 0.0F);

		this.legLeft = new Cube(0, 16);
		this.legLeft.mirror = true;
		this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expansion);
		this.legLeft.setRotationPoint(2.0F, 12.0F + yRotOffset, 0.0F);


		setupAnimatedLimb(this.head, "head");
		setupAnimatedLimb(this.body, "body");
		setupAnimatedLimb(this.armRight, "armR");
		setupAnimatedLimb(this.armLeft, "armL");
		setupAnimatedLimb(this.legRight, "legR");
		setupAnimatedLimb(this.legLeft, "legL");
	}

	@Override
	public void setLivingAnimations(Mob mob, float limbSwing, float limbYaw, float partialTick) {
		this.currentManager = ((IEntity)mob).bws$getAnimManager();
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		this.head.render(scale);
		this.body.render(scale);
		this.armRight.render(scale);
		this.armLeft.render(scale);
		this.legRight.render(scale);
		this.legLeft.render(scale);
		this.hair.render(scale);
	}

	@Override
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.head.yRot = headYaw / (float) (180.0 / Math.PI);
		this.head.xRot = headPitch / (float) (180.0 / Math.PI);
		this.hair.yRot = this.head.yRot;
		this.hair.xRot = this.head.xRot;
		if (this.holdingLarge) {
			this.armRight.xRot = MathHelper.cos(limbSwing / 2.0F * 0.6662F) * 2.0F * limbYaw * 0.125F - 0.65F;
			this.armLeft.xRot = MathHelper.cos(limbSwing / 2.0F * 0.6662F) * 2.0F * limbYaw * 0.125F - 0.65F;
		} else {
			this.armRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbYaw * 0.5F;
			this.armLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbYaw * 0.5F;
		}

		this.armRight.zRot = 0.0F;
		this.armLeft.zRot = 0.0F;
		this.legRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
		this.legLeft.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw;
		this.legRight.yRot = 0.0F;
		this.legLeft.yRot = 0.0F;
		if (this.isRiding) {
			this.armRight.xRot += -0.6283185F;
			this.armLeft.xRot += -0.6283185F;
			this.legRight.xRot = -1.256637F;
			this.legLeft.xRot = -1.256637F;
			this.legRight.yRot = 0.3141593F;
			this.legLeft.yRot = -0.3141593F;
		}

		if (this.holdingLeftHand) {
			this.armLeft.xRot = this.armLeft.xRot * 0.5F - 0.3141593F;
		}

		if (this.holdingRightHand) {
			this.armRight.xRot = this.armRight.xRot * 0.5F - 0.3141593F;
		}

		this.armRight.yRot = 0.0F;
		this.armLeft.yRot = 0.0F;
		if (this.onGround > -9990.0F) {
			float f6 = this.onGround;
			this.body.yRot = MathHelper.sin(MathHelper.sqrt_float(f6) * (float) Math.PI * 2.0F) * 0.2F;
			this.armRight.z = MathHelper.sin(this.body.yRot) * 5.0F;
			this.armRight.x = -MathHelper.cos(this.body.yRot) * 5.0F;
			this.armLeft.z = -MathHelper.sin(this.body.yRot) * 5.0F;
			this.armLeft.x = MathHelper.cos(this.body.yRot) * 5.0F;
			this.armRight.yRot = this.armRight.yRot + this.body.yRot;
			this.armLeft.yRot = this.armLeft.yRot + this.body.yRot;
			this.armLeft.xRot = this.armLeft.xRot + this.body.xRot;
			f6 = 1.0F - this.onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			float f7 = MathHelper.sin(f6 * (float) Math.PI);
			float f8 = MathHelper.sin(this.onGround * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
			this.armRight.xRot = (float)((double)this.armRight.xRot - ((double)f7 * 1.2 + (double)f8));
			this.armRight.yRot = this.armRight.yRot + this.body.yRot * 2.0F;
			this.armRight.zRot = MathHelper.sin(this.onGround * (float) Math.PI) * -0.4F;
		}

		if (this.sneaking) {
			this.body.xRot = 0.5F;
			this.legRight.xRot -= 0.0F;
			this.legLeft.xRot -= 0.0F;
			this.armRight.xRot += 0.4F;
			if (this.holdingLarge) {
				this.armRight.xRot -= 0.4F;
				this.armLeft.xRot -= 0.5F;
			}

			this.legRight.z = 4.0F;
			this.legLeft.z = 4.0F;
			this.legRight.y = 9.0F;
			this.legLeft.y = 9.0F;
			this.head.y = 1.0F;
			this.hair.y = 1.0F;
		} else {
			this.body.xRot = 0.0F;
			this.legRight.z = 0.0F;
			this.legLeft.z = 0.0F;
			this.legRight.y = 12.0F;
			this.legLeft.y = 12.0F;
			this.head.y = 0.0F;
			this.hair.y = 0.0F;
		}

		this.armRight.zRot = this.armRight.zRot + MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
		this.armLeft.zRot = this.armLeft.zRot - (MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F);
		this.armRight.xRot = this.armRight.xRot + MathHelper.sin(limbPitch * 0.067F) * 0.05F;
		this.armLeft.xRot = this.armLeft.xRot - MathHelper.sin(limbPitch * 0.067F) * 0.05F;

		super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
	}

	public void renderCloak(float scale) {
		this.cloak.render(scale);
	}
}
