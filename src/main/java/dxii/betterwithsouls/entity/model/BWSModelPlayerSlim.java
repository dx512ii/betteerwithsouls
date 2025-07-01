//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dxii.betterwithsouls.entity.model;

import dxii.betterwithsouls.entity.model.BWSModelPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;

@Environment(EnvType.CLIENT)
public class BWSModelPlayerSlim extends BWSModelPlayer {
	public BWSModelPlayerSlim(float f) {
		this(f, 0.0F);
	}

	public BWSModelPlayerSlim(float expansion, float yRotOffset) {
		super(expansion, yRotOffset);
		this.bipedLeftArmOverlay = new Cube(48, 48, 64, 64);
		this.bipedLeftArmOverlay.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, expansion + 0.25F);
		this.bipedLeftArmOverlay.setRotationPoint(5.0F, 2.5F, 0.0F);
		this.bipedRightArmOverlay = new Cube(40, 32, 64, 64);
		this.bipedRightArmOverlay.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, expansion + 0.25F);
		this.bipedRightArmOverlay.setRotationPoint(-5.0F, 2.5F, 10.0F);
		this.armLeft = new Cube(32, 48, 64, 64);
		this.armLeft.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, expansion);
		this.armLeft.setRotationPoint(5.0F, 2.5F, 0.0F);
		this.armRight = new Cube(40, 16, 64, 64);
		this.armRight.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, expansion);
		this.armRight.setRotationPoint(-5.0F, 2.5F + yRotOffset, 0.0F);

		setupAnimatedLimb(this.head, "head");
		setupAnimatedLimb(this.body, "body");
		setupAnimatedLimb(this.armRight, "armR");
		setupAnimatedLimb(this.armLeft, "armL");
		setupAnimatedLimb(this.legRight, "legR");
		setupAnimatedLimb(this.legLeft, "legL");
	}
}
