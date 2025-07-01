package dxii.betterwithsouls.gui;

import dxii.betterwithsouls.gui.poser.ButtonIndex;
import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.util.animation.Animation;
import dxii.betterwithsouls.util.animation.AnimationRegistry;
import dxii.betterwithsouls.util.animation.BipedLimbInfo;
import dxii.betterwithsouls.util.animation.BipedPoseInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ScreenPoser extends Screen {

	ScreenPoseLimb limbScreen;


	public ButtonElement reset;
	public ButtonElement dump;
	public ButtonElement rotR;
	public ButtonElement rotL;
	public ButtonElement plus;
	public ButtonElement minus;

	public Mob currentModel;
	public BipedPoseInfo currentPose;

	private float zoom = 60;
	private float rot = 0;
	private float rotV = 0;

	public ScreenPoser() {
		this(null);
	}
	public ScreenPoser(Screen parent) {
		super(parent);
		this.mc = Minecraft.getMinecraft();

		this.setRegistryModel(0);
	}


	public void setRegistryModel(int index){
		if(!AnimationRegistry.INSTANCE.classes.isEmpty()) {
			if(AnimationRegistry.INSTANCE.classes.size()-1 < index){
				return;
			}

			this.setModel(AnimationRegistry.INSTANCE.classes.get(index));
			if(this.currentModel != null) {
				this.currentPose = AnimationRegistry.INSTANCE.getPoseByClass(this.currentModel.getClass());
				this.updateModelPose();

				setLimb(index);
			}else{
				System.out.println("Animator error!!!! Current model is null!!");
				this.limbScreen = new ScreenPoseLimb(this, null, "null", 40, 35);
				this.limbScreen.init();
			}
		}else{
			System.out.println("Animator error!!!! Render a model to add it to a list!!");

			this.limbScreen = new ScreenPoseLimb(this,null, "null", 40, 35);
			this.limbScreen.init();
		}
	}

	public void setLimb(int index){
		this.limbScreen = new ScreenPoseLimb(this, this.currentPose.limbs.get(index), this.currentPose.limbs.get(index).name, 40, 35);
		this.limbScreen.init();
		//hack to make limb panel render again
		Minecraft.getMinecraft().gameWindow.toggleFullscreen();
		Minecraft.getMinecraft().gameWindow.toggleFullscreen();
	}
	public void updateModelPose(){
		resetPose();
		Animation anim = Animation.fromPoseInfo(this.currentPose);
		this.sendModelAnim(anim);
		this.updateCurrentAnimManager();
	}
	private void setModel(Class<? extends Mob> clazz){
		this.currentModel = (Mob)EntityDispatcher.createEntityInWorld(clazz, null);
	}

	public void drawModel() {
		if(currentModel == null){
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f1 = this.zoom;
		int j = 160;
		int k = (this.height) / 2 + 120;
		GL11.glEnable(32826);
		GL11.glEnable(2903);
		GL11.glEnable(2929);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(j), (float)(k)-this.rotV, 50.0F);
		GL11.glScalef(-f1, f1, f1);

		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(this.rotV, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(this.rot, 0.0F, 1.0F, 0.0F);
		Lighting.enableLight();
		GL11.glTranslatef(0.0F, this.mc.thePlayer.heightOffset, 0.0F);
		EntityRenderDispatcher.instance.viewLerpYaw = 180.0F;
		EntityRenderer.renderShadows = false;

		this.updateCurrentAnimManager();
		EntityRenderDispatcher.instance.renderEntityWithPosYaw(Tessellator.instance, currentModel, 0.0, 0.0, 0.0, 0.0F, 1.0F);

		EntityRenderer.renderShadows = true;
		GL11.glPopMatrix();
		Lighting.disable();
		GL11.glDisable(32826);
	}

	public void sendModelAnim(Animation anim){
		if(currentModel == null){
			return;
		}
		((IEntity)currentModel).bws$sendEntityAnim(anim);
	}
	public void updateCurrentAnimManager(){
		if(currentModel == null){
			return;
		}
		((IEntity)currentModel).bws$getAnimManager().update();
	}
	public void resetPose(){
		if(currentModel == null){
			return;
		}
		((IEntity)currentModel).bws$getVMManager().stopAnimation();
	}

	@Override
	public void init() {
		super.init();
		this.limbScreen.font = this.font;
		int offset = 115;
		reset = new ButtonElement(1,
			offset,
			(int) (this.height*.75f),
			40, 20, "RESET");
		this.buttons.add(reset);
		dump = new ButtonElement(1,
			50+offset,
			(int) (this.height*.75f),
			40, 20, "DUMP");
		this.buttons.add(dump);

		rotR = new ButtonElement(1,
			50+offset,
			(int) (this.height*.75f)-20,
			40, 20, "->");
		this.buttons.add(rotR);
		rotL = new ButtonElement(1,
			offset,
			(int) (this.height*.75f)-20,
			40, 20, "<-");
		this.buttons.add(rotL);

		this.updateLimbButtons();
		this.updateModelButtons();
	}
	public List<ButtonIndex> limbButtons = new ArrayList<>();
	public List<ButtonIndex> modelButtons = new ArrayList<>();
	public void updateLimbButtons(){
		if(this.currentPose !=  null) {

			for(ButtonElement btn : limbButtons){
				if(btn != null) {
					this.buttons.remove(btn);
				}
			}
			int counter = 0;
			for (BipedLimbInfo limb : this.currentPose.limbs) {
				ButtonIndex btn = this.addButton( this.width / 2, 20 * counter, 40, 20, limb.name);
				btn.index = counter;
				this.limbButtons.add(btn);
				counter++;
			}
		}
	}
	public void updateModelButtons(){
		if(!AnimationRegistry.INSTANCE.classes.isEmpty()) {
			for(ButtonElement btn : modelButtons){
				if(btn != null) {
					this.buttons.remove(btn);
				}
			}

			int counter = 0;
			for (Class<? extends Mob> mob : AnimationRegistry.INSTANCE.classes) {
				ButtonIndex btn = this.addButton( this.width-100, 20 * counter, 100, 20, mob.getSimpleName());
				btn.index = counter;
				this.modelButtons.add(btn);
				counter++;
			}
		}
	}

	public ButtonIndex addButton(int xpos, int ypos, int witdh, int height, String text){
		ButtonIndex btn = new ButtonIndex(this, 1,
			xpos,
			ypos,
			witdh, height, text);
		this.buttons.add(btn);

		return btn;
	}

	@Override
	protected void buttonClicked(ButtonElement button) {
		super.buttonClicked(button);

		if(button == this.rotL){
			this.rot -= 15;
		}
		if(button == this.rotR){
			this.rot += 15;
		}

		if(button == this.dump){
			this.currentPose.dump();
		}
		if(button == this.reset){
			this.currentPose.reset();
			zoom = 60;
			rot = 0;
			rotV = 0;
			this.updateModelPose();
		}

		for(ButtonIndex btn : limbButtons){
			if(btn != null && btn == button) {
				this.setLimb(btn.index);
			}
		}
		for(ButtonIndex btn : modelButtons){
			if(btn != null && btn == button) {
				setRegistryModel(btn.index);
			}
		}
	}

	@Override
	public void mouseClicked(int mx, int my, int buttonNum) {
		if (buttonNum == 0) {
			for (ButtonElement button : this.buttons) {
				if (button.mouseClicked(this.mc, mx, my) && button.playSound) {
					this.clickedButton = button;
					this.mc.sndManager.playSound("random.click", SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
					if (button.listener != null) {
						button.listener.listen(button);
					} else {
						this.buttonClicked(button);
					}
				}
			}
		}

		this.limbScreen.mouseClicked(mx, my, buttonNum);
	}

	@Override
	public void render(int mx, int my, float partialTick) {
		super.render(mx, my, partialTick);
		this.limbScreen.render(mx, my, partialTick);


		float speed = 1;

		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.rot -= speed*2;

		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.rot += speed*2;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.rotV -= speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.rotV += speed;
		}
		rot = normalizeAngle(rot);

		this.rotV = MathHelper.clamp(this.rotV, -20, 60);

		drawModel();
	}

	public float normalizeAngle(float ang){
		if(ang > 360){
			ang = 0;
		}
		if(ang < -360){
			ang = 0;
		}
		return ang;
	}

}
