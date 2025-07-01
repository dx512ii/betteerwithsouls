package dxii.betterwithsouls.gui;

import dxii.betterwithsouls.interfaces.IMinecraft;
import dxii.betterwithsouls.util.animation.BipedLimbInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.core.util.phys.Vec3;

public class ScreenItemDummy extends Screen {

	public ScreenItemDummy(Screen parent) {
		super(parent);
	}

	public ScreenItemDummy() {
		this(null);

		this.mc = Minecraft.getMinecraft();
	}

	public Minecraft mc;

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

	public ButtonElement dump;
	public ButtonElement reset;

//"ᐯ"
	@Override
	public void init() {
		super.init();

		int buttonSmallW = 20;
		int buttonSmallH = 20;


		posX = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2,
			(int)(this.height*0.2)-buttonSmallH/2,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(posX);
		posXMinus = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2,
			(int)(this.height*0.2)+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(posXMinus);

		posY = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2+buttonSmallW*2,
			(int)(this.height*0.2)-buttonSmallH/2,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(posY);
		posYMinus = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2+buttonSmallW*2,
			(int)(this.height*0.2)+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(posYMinus);

		posZ = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2+buttonSmallW*4,
			(int)(this.height*0.2)-buttonSmallH/2,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(posZ);
		posZMinus = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2+buttonSmallW*4,
			(int)(this.height*0.2)+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(posZMinus);

		//ROT
		rotX = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2,
			(int)(this.height*0.5)-buttonSmallH/2,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(rotX);
		rotXMinus = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2,
			(int)(this.height*0.5)+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(rotXMinus);

		rotY = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2+buttonSmallW*2,
			(int)(this.height*0.5)-buttonSmallH/2,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(rotY);
		rotYMinus = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2+buttonSmallW*2,
			(int)(this.height*0.5)+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(rotYMinus);

		rotZ = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2+buttonSmallW*4,
			(int)(this.height*0.5)-buttonSmallH/2,
			buttonSmallW, buttonSmallH, "+");
		this.buttons.add(rotZ);
		rotZMinus = new ButtonElement(1,
			(int)(this.width*0.2)-buttonSmallW/2+buttonSmallW*4,
			(int)(this.height*0.5)+buttonSmallH,
			buttonSmallW, buttonSmallH, "-");
		this.buttons.add(rotZMinus);

		dump = new ButtonElement(1,
			(int)(this.width*0.2),
			(int)(this.height*0.7)+buttonSmallH,
			buttonSmallW*2, buttonSmallH, "DUMP");
		this.buttons.add(dump);
		reset = new ButtonElement(1,
			(int)(this.width*0.4),
			(int)(this.height*0.7)+buttonSmallH,
			buttonSmallW*2, buttonSmallH, "RESET");
		this.buttons.add(reset);

	}

	@Override
	protected void buttonClicked(ButtonElement button) {
		if(button == this.posX){
			((IMinecraft)mc).addToItemDummyPos(.05, 0, 0);
		}else if(button == this.posXMinus){
			((IMinecraft)mc).addToItemDummyPos(-.05, 0, 0);
		}else if(button == this.posY){
			((IMinecraft)mc).addToItemDummyPos(0, .05, 0);
		}else if(button == this.posYMinus){
			((IMinecraft)mc).addToItemDummyPos(0, -.05, 0);
		}else if(button == this.posZ){
			((IMinecraft)mc).addToItemDummyPos(0, -0, .05);
		}else if(button == this.posZMinus){
			((IMinecraft)mc).addToItemDummyPos(0, -0, -.05);
		}

		else if(button == this.rotX){
			((IMinecraft)mc).addToItemDummyRot(5, -0, 0);
		}else if(button == this.rotXMinus){
			((IMinecraft)mc).addToItemDummyRot(-5, -0, 0);
		}else if(button == this.rotY){
			((IMinecraft)mc).addToItemDummyRot(0, 5, 0);
		}else if(button == this.rotYMinus){
			((IMinecraft)mc).addToItemDummyRot(0, -5, 0);
		}else if(button == this.rotZ){
			((IMinecraft)mc).addToItemDummyRot(0, 0, 5);
		}else if(button == this.rotZMinus){
			((IMinecraft)mc).addToItemDummyRot(0, -0, -5);
		}else if(button == this.dump){
			Vec3 pos = ((IMinecraft)mc).getItemDummyPos();
			Vec3 rot = ((IMinecraft)mc).getItemDummyRot();
			System.out.println("-----------------DUMP:-------------------");
			System.out.print(".withFrame(new Frame(0)");
			System.out.print("\n");

			String build = "\t\t.withKey(Key.createKey(" + '"' + "weapon" + '"' + ", " +
				BipedLimbInfo.formatDouble(pos.x) + ", " + BipedLimbInfo.formatDouble(pos.y) + ", " + BipedLimbInfo.formatDouble(pos.z) +
				", " + BipedLimbInfo.formatDouble(rot.x) + ", " + BipedLimbInfo.formatDouble(rot.y) + ", " + BipedLimbInfo.formatDouble(rot.z) +
				", " + true + ", " + true +
				") ))";

			System.out.print(build);
			System.out.print("\n");

			System.out.println("-----------------------------------------");
		}else if(button == this.reset){
			((IMinecraft)mc).resetItemDummy();
		}
	}

	@Override
	public void render(int mx, int my, float partialTick) {
		super.render(mx, my, partialTick);
		int color = 0x0000;

		this.font.drawString("!!DUMMY ITEM PANEL!!", (int) (this.width*0.4), (int) (this.height*0.03), color);
		this.font.drawString("pos(xyz):", (int) (this.width*0.2)-60, (int) (this.height*0.22), 0xffffff);
		this.font.drawString("rot(xyz):", (int) (this.width*0.2)-60, (int) (this.height*0.52), 0xffffff);
	}
}
