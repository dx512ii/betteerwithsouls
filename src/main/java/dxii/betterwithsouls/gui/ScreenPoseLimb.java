package dxii.betterwithsouls.gui;

import dxii.betterwithsouls.util.animation.BipedLimbInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.render.Font;

public class ScreenPoseLimb extends Screen {

	public BipedLimbInfo currentLimb;
	String name = "null";
	int offsetx=40;
	int offsety=40;

	public Font font;

	public ButtonElement posX;
	public ButtonElement posXMinus;
	public ButtonElement posY;
	public ButtonElement posYMinus;
	public ButtonElement posZ;
	public ButtonElement posZMinus;

	public ButtonElement rotX;
	public ButtonElement rotXMinus;
	public ButtonElement rotY;
	public ButtonElement rotYMinus;
	public ButtonElement rotZ;
	public ButtonElement rotZMinus;

	public ButtonElement reset;


	public ScreenPoseLimb() {
		this(null);
	}
	public ScreenPoseLimb(Screen parent) {
		super(parent);
	}
	public ScreenPoser parent;


	public ScreenPoseLimb(ScreenPoser parent, BipedLimbInfo limb, String name, int xOffset, int yOffset) {
		this(null);
		this.parent = parent;
		this.mc = Minecraft.getMinecraft();
		this.currentLimb = limb;
		this.offsetx = xOffset;
		this.offsety = yOffset;
		this.name = name;
	}



//"ᐯ"
	@Override
	public void init() {
		super.init();

		int buttonSmallW = 10;
		int buttonSmallH = 10;


		posX = new ButtonElement(1,
			offsetx -buttonSmallW/2,
			offsety-buttonSmallH,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(posX);
		posXMinus = new ButtonElement(1,
			offsetx -buttonSmallW/2,
			offsety+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(posXMinus);

		posY = new ButtonElement(1,
			offsetx -buttonSmallW/2+buttonSmallW*2,
			offsety-buttonSmallH,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(posY);
		posYMinus = new ButtonElement(1,
			offsetx -buttonSmallW/2+buttonSmallW*2,
			offsety+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(posYMinus);

		posZ = new ButtonElement(1,
			offsetx -buttonSmallW/2+buttonSmallW*4,
			offsety-buttonSmallH,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(posZ);
		posZMinus = new ButtonElement(1,
			offsetx -buttonSmallW/2+buttonSmallW*4,
			offsety+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(posZMinus);

		//ROT
		rotX = new ButtonElement(1,
			offsetx -buttonSmallW/2,
			offsety+50-buttonSmallH,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(rotX);
		rotXMinus = new ButtonElement(1,
			offsetx -buttonSmallW/2,
			offsety+50+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(rotXMinus);

		rotY = new ButtonElement(1,
			offsetx -buttonSmallW/2+buttonSmallW*2,
			offsety+50-buttonSmallH,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(rotY);
		rotYMinus = new ButtonElement(1,
			offsetx -buttonSmallW/2+buttonSmallW*2,
			offsety+50+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(rotYMinus);

		rotZ = new ButtonElement(1,
			offsetx -buttonSmallW/2+buttonSmallW*4,
			offsety+50-buttonSmallH,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(rotZ);
		rotZMinus = new ButtonElement(1,
			offsetx -buttonSmallW/2+buttonSmallW*4,
			offsety+50+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(rotZMinus);

		reset = new ButtonElement(1,
			offsetx,
			offsety+25,
			(int) (buttonSmallW*3.5f), buttonSmallH+2, "reset");
		this.buttons.add(reset);

	}

	@Override
	protected void buttonClicked(ButtonElement button) {
		float posStep = .05f;
		float rotStep = 5;

		if(currentLimb == null){return;}

		if(button == this.posX){
			this.currentLimb.addPos(posStep, 0, 0);
			this.parent.updateModelPose();
		}else if(button == this.posXMinus){
			this.currentLimb.addPos(-posStep, 0, 0);
			this.parent.updateModelPose();
		}else if(button == this.posY){
			this.currentLimb.addPos(0, posStep, 0);
			this.parent.updateModelPose();
		}else if(button == this.posYMinus){
			this.currentLimb.addPos(0, -posStep, 0);
			this.parent.updateModelPose();
		}else if(button == this.posZ){
			this.currentLimb.addPos(0, 0, posStep);
			this.parent.updateModelPose();
		}else if(button == this.posZMinus){
			this.currentLimb.addPos(0, 0, -posStep);
			this.parent.updateModelPose();
		}else if(button == this.rotX){
			this.currentLimb.addRot(rotStep, 0, 0);
			this.parent.updateModelPose();
		}else if(button == this.rotXMinus){
			this.currentLimb.addRot(-rotStep, 0, 0);
			this.parent.updateModelPose();
		}else if(button == this.rotY){
			this.currentLimb.addRot(0, rotStep, 0);
			this.parent.updateModelPose();
		}else if(button == this.rotYMinus){
			this.currentLimb.addRot(0, -rotStep, 0);
			this.parent.updateModelPose();
		}else if(button == this.rotZ){
			this.currentLimb.addRot(0, 0, rotStep);
			this.parent.updateModelPose();
		}else if(button == this.rotZMinus){
			this.currentLimb.addRot(0, 0, -rotStep);
			this.parent.updateModelPose();
		}else if(button == this.reset){
			this.currentLimb.reset();
			this.parent.updateModelPose();
		}
	}

	@Override
	public void render(int mx, int my, float partialTick) {
		if(this.mc == null ||this.font == null){
			return;
		}
		if (this.mc.currentWorld != null) {
			int color = this.mc.gameSettings.guiBackgroundColor.value.getARGB();
			this.drawGradientRect(offsetx-35, offsety-30, offsetx+50, offsety+75, color, color);
		} else {
			this.renderTexturedBackground();
		}

		super.render(mx, my, partialTick);

		this.font.drawString(this.name, offsetx-30, offsety-25, 0xffffff);
		this.font.drawString("pos:", offsetx-30, offsety, 0xaaaaaa);
		this.font.drawString("rot:", offsetx-30, offsety+50, 0xaaaaaa);

		if(currentLimb != null) {
			this.font.drawString(String.valueOf(currentLimb.posX), offsetx - 6, offsety + 2, 0xaaaaaa);
			this.font.drawString(String.valueOf(currentLimb.posY), offsetx + 13, offsety + 2, 0xaaaaaa);
			this.font.drawString(String.valueOf(currentLimb.posZ), offsetx + 33, offsety + 2, 0xaaaaaa);

			this.font.drawString(String.valueOf(currentLimb.rotX), offsetx - 6, offsety + 51, 0xaaaaaa);
			this.font.drawString(String.valueOf(currentLimb.rotY), offsetx + 13, offsety + 51, 0xaaaaaa);
			this.font.drawString(String.valueOf(currentLimb.rotZ), offsetx + 33, offsety + 51, 0xaaaaaa);
		}else{
			this.font.drawString("null", offsetx - 6, offsety + 2, 0xaaaaaa);
			this.font.drawString("null", offsetx + 13, offsety + 2, 0xaaaaaa);
			this.font.drawString("null", offsetx + 33, offsety + 2, 0xaaaaaa);

			this.font.drawString("null", offsetx - 6, offsety + 51, 0xaaaaaa);
			this.font.drawString("null", offsetx + 13, offsety + 51, 0xaaaaaa);
			this.font.drawString("null", offsetx + 33, offsety + 51, 0xaaaaaa);

		}
	}
}
