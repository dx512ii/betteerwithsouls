package dxii.betterwithsouls.entity.model;


import dxii.betterwithsouls.interfaces.IModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelZombieTest extends ModelBiped {

	public ModelZombieTest() {
		this.parts(0, 0.0F);
	}

	public void parts(float var1, float var2) {
		this.holdingLeftHand = false;
		this.holdingRightHand = false;
		this.sneaking = false;
//		this.cloak = new Cube(0, 0);
//		this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, var1);
//		this.ear = new Cube(24, 0);
//		this.ear.addBox(-4.0F, -7.0F, -1.0F, 7, 7, 1, var1);y
//		this.head = new Cube(0, 0);
//		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1);
//		this.head.setRotationPoint(0.0F, 0.0F + var2, 0.0F);
//		this.hair = new Cube(32, 0);
//		this.hair.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1 + 0.5F);
//		this.hair.setRotationPoint(0.0F, 0.0F + var2, 0.0F);
//		this.body = new Cube(16, 16);
//		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, var1);
//		this.body.setRotationPoint(0.0F, 0.0F + var2, 0.0F);
//		this.armRight = new Cube(40, 16);
//		this.armRight.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, var1);
//		this.armRight.setRotationPoint(-5.0F, 2.0F + var2, 0.0F);
//		this.armLeft = new Cube(40, 16);
//		this.armLeft.mirror = true;
//		this.armLeft.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, var1);
//		this.armLeft.setRotationPoint(5.0F, 2.0F + var2, 0.0F);
//		this.legRight = new Cube(0, 16);
//		this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
//		this.legRight.setRotationPoint(-2.0F, 12.0F + var2, 0.0F);
//		this.legLeft = new Cube(0, 16);
//		this.legLeft.mirror = true;
//		this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
//		this.legLeft.setRotationPoint(2.0F, 12.0F + var2, 0.0F);
//		//System.out.println("updated parts!!");
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.render(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
	}

	private int debugDelay;
	@Override
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {

		if (debugDelay != 0) {
			debugDelay--;
		} else {
			dumpLimbs();
			debugDelay = 3333;
		}


		super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
//
//		this.head.x = 0;
//		this.head.y = 0;
//		this.head.z = -2f;
//
//		this.body.x = -.3f;
//		this.body.y = 0;
//		this.body.z = -1;
//		this.body.xRot = MathHelper.toRadians(7);
//		this.body.yRot = MathHelper.toRadians(17);
//		this.body.zRot = 0;
//
//		this.armRight.x = armRight.x;
//		this.armRight.y = armRight.y;
//		this.armRight.z = armRight.z+1;
//		this.armRight.xRot = MathHelper.toRadians(-162);
//		this.armRight.yRot = MathHelper.toRadians(-1);
//		this.armRight.zRot = MathHelper.toRadians(-16);
//
//		this.armLeft.x = armLeft.x;
//		this.armLeft.y = armLeft.y;
//		this.armLeft.z = armLeft.z-2;
//		this.armLeft.xRot = MathHelper.toRadians(-39);
//		this.armLeft.yRot = MathHelper.toRadians(12);
//		this.armLeft.zRot = MathHelper.toRadians(15);
//
//		((IModel)this).setItemRot(25);
//		((IModel)this).setItemTranslation(0, -.1, .1);
	}

	public void dumpLimbs(){
		System.out.println("POSE DUMP: ");
		System.out.println("-------------------------------------------------");
		System.out.println("head:    | pos(" + head.x+"f, " + head.y+"f, " + head.z+"f )");
		System.out.println("         | rot("+MathHelper.toDegrees(head.xRot)+"f, "+MathHelper.toDegrees(head.yRot)+"f, "+MathHelper.toDegrees(head.zRot) + "f )");
		System.out.println("-------------------------------------------------");
		System.out.println("body:    | pos(" + body.x+"f, " + body.y+"f, " + body.z+"f )");
		System.out.println("         | rot("+MathHelper.toDegrees(body.xRot)+"f, "+MathHelper.toDegrees(body.yRot)+"f, "+MathHelper.toDegrees(body.zRot) + "f )");
		System.out.println("-------------------------------------------------");
		System.out.println("armRight:| pos(" + armRight.x+"f, " + armRight.y+"f, " + armRight.z+"f )");
		System.out.println("         | rot("+MathHelper.toDegrees(armRight.xRot)+"f, "+MathHelper.toDegrees(armRight.yRot)+"f, "+MathHelper.toDegrees(armRight.zRot) + "f )");
		System.out.println("-------------------------------------------------");
		System.out.println("armLeft: | pos(" + armLeft.x+"f, " + armLeft.y+"f, " + armLeft.z+"f )");
		System.out.println("         | rot("+MathHelper.toDegrees(armLeft.xRot)+"f, "+MathHelper.toDegrees(armLeft.yRot)+"f, "+MathHelper.toDegrees(armLeft.zRot) + "f )");
		System.out.println("-------------------------------------------------");
		System.out.println("legRight:| pos(" + legRight.x+"f, " + legRight.y+"f, " + legRight.z+"f )");
		System.out.println("         | rot("+MathHelper.toDegrees(legRight.xRot)+"f, "+MathHelper.toDegrees(legRight.yRot)+"f, "+MathHelper.toDegrees(legRight.zRot) + "f )");
		System.out.println("-------------------------------------------------");
		System.out.println("legLeft: | pos(" + legLeft.x+"f, " + legLeft.y+"f, " + legLeft.z+"f )");
		System.out.println("         | rot("+MathHelper.toDegrees(legLeft.xRot)+"f, "+MathHelper.toDegrees(legLeft.yRot)+"f, "+MathHelper.toDegrees(legLeft.zRot) + "f )");
		System.out.println("-------------------------------------------------");
		System.out.println("weapon:  | pos= " + ((IModel)this).getItemTranslation());
		System.out.println("         | rot= "+((IModel)this).getItemRot()+"f, ");
		System.out.println("-------------------------------------------------");
		System.out.println("POSE DUMP END");
	}
}
