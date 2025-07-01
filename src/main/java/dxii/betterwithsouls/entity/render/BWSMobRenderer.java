package dxii.betterwithsouls.entity.render;

import com.mojang.logging.LogUtils;
import dxii.betterwithsouls.entity.model.BWSModelBase;
import dxii.betterwithsouls.entity.model.BWSModelBiped;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.Global;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;

@Environment(EnvType.CLIENT)
public class BWSMobRenderer<T extends Mob> extends MobRenderer<T> {
	private static final Logger LOGGER = LogUtils.getLogger();
	protected BWSModelBase mainModel;
	protected BWSModelBase armorModel;
	protected BWSModelBase overlayModel;
	protected String overlayTexture;
	private final Minecraft mc;

	public BWSMobRenderer(BWSModelBase model, float shadowSize) {
		super(model, shadowSize);
		this.mainModel = model;
		this.shadowSize = shadowSize;
		this.mc = Minecraft.getMinecraft();
	}

	public void setArmorModel(BWSModelBase armor) {
		this.armorModel = armor;
	}

	public void setOverlayModel(BWSModelBase modelbase, String texture) {
		this.overlayModel = modelbase;
		this.overlayTexture = texture;
	}

	public void render(Tessellator tessellator, T entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		this.mainModel.onGround = this.getSwingProgress(entity, partialTick);
		if (this.armorModel != null) {
			this.armorModel.onGround = this.mainModel.onGround;
		}

		if (this.overlayModel != null) {
			this.overlayModel.onGround = this.mainModel.onGround;
		}

		this.mainModel.isRiding = entity.isPassenger();
		if (this.armorModel != null) {
			this.armorModel.isRiding = this.mainModel.isRiding;
		}

		if (this.overlayModel != null) {
			this.overlayModel.isRiding = this.mainModel.isRiding;
		}

		try {
			float bodyYaw = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTick;
			float headYaw = entity.yRotO + (entity.yRot - entity.yRotO) * partialTick;
			float headPitch = entity.xRotO + (entity.xRot - entity.xRotO) * partialTick;
			this.translateModel(entity, x, y, z);
			float limbSway = this.limbSway(entity, partialTick);
			this.setupRotations(entity, limbSway, bodyYaw, partialTick);
			float scale = 0.0625F;
			GL11.glEnable(32826);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			this.setupScale(entity, partialTick);
			GL11.glTranslatef(0.0F, -24.0F * scale - 0.0078125F, 0.0F);
			float walkSpeed = entity.walkAnimSpeedO + (entity.walkAnimSpeed - entity.walkAnimSpeedO) * partialTick;
			float walkProgress = entity.walkAnimPos - entity.walkAnimSpeed * (1.0F - partialTick);
			if (walkSpeed > 1.0F) {
				walkSpeed = 1.0F;
			}

			this.loadEntityTexture(entity);
			GL11.glEnable(3008);
			this.mainModel.setLivingAnimations(entity, walkProgress, walkSpeed, partialTick);
			this.mainModel.renderCustom(entity);
			this.mainModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
			if (this.overlayModel != null) {
				this.overlayModel.setLivingAnimations(entity, walkProgress, walkSpeed, partialTick);
				this.bindTexture(this.overlayTexture);
				this.overlayModel.renderCustom(entity);
				this.overlayModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
			}

			if (this.armorModel != null) {
				this.armorModel.setLivingAnimations(entity, walkProgress, walkSpeed, partialTick);
			}

			for (int renderPass = 0; renderPass < 4; renderPass++) {
				if (this.prepareArmor(entity, renderPass, partialTick)) {
					this.armorModel.setLivingAnimations(entity, walkProgress, walkSpeed, partialTick);
					this.armorModel.renderCustom(entity);
					this.armorModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					GL11.glDisable(3042);
					GL11.glEnable(3008);
				}
			}

			this.renderAdditional(entity, partialTick);
			float brightness = entity.getBrightness(partialTick);
			if (Global.accessor.isFullbrightEnabled() || LightmapHelper.isLightmapEnabled()) {
				brightness = 1.0F;
			}

			int argb = this.getOverlayColor(entity, brightness, partialTick);
			if ((argb >> 24 & 0xFF) > 0 || entity.hurtTime > 0 || entity.deathTime > 0) {
				GL11.glDisable(3553);
				GL11.glDisable(3008);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glDepthFunc(514);
				if (entity.hurtTime > 0 || entity.deathTime > 0) {
					GL11.glColor4f(brightness, 0.0F, 0.0F, 0.4F);
					this.mainModel.renderCustom(entity);
					this.mainModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					if (this.overlayModel != null) {
						this.overlayModel.renderCustom(entity);
						this.overlayModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					}

					for (int k = 0; k < 4; k++) {
						if (this.prepareArmor(entity, k, partialTick)) {
							GL11.glColor4f(brightness, 0.0F, 0.0F, 0.4F);
							this.armorModel.renderCustom(entity);
							this.armorModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
						}
					}
				}

				if ((argb >> 24 & 0xFF) > 0) {
					float r = (float)(argb >> 16 & 0xFF) / 255.0F;
					float g = (float)(argb >> 8 & 0xFF) / 255.0F;
					float b = (float)(argb & 0xFF) / 255.0F;
					float a = (float)(argb >> 24 & 0xFF) / 255.0F;
					GL11.glColor4f(r, g, b, a);
					this.mainModel.renderCustom(entity);
					this.mainModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					if (this.overlayModel != null) {
						this.overlayModel.renderCustom(entity);
						this.overlayModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					}

					for (int l = 0; l < 4; l++) {
						if (this.prepareArmor(entity, l, partialTick)) {
							GL11.glColor4f(r, g, b, a);
							this.armorModel.renderCustom(entity);
							this.armorModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
						}
					}
				}

				GL11.glDepthFunc(515);
				GL11.glDisable(3042);
				GL11.glEnable(3008);
				GL11.glEnable(3553);
			}

			GL11.glDisable(32826);
		} catch (Exception var25) {
			LOGGER.error("Render exception in class '{}'!", this.getClass().getSimpleName(), var25);
		}

		GL11.glEnable(2884);
		GL11.glPopMatrix();
		this.renderSpecials(tessellator, entity, x, y, z);
	}

	protected void translateModel(T entity, double x, double y, double z) {
		GL11.glTranslatef((float)x, (float)y, (float)z);
	}

	protected void setupRotations(T entity, float ticksExisted, float bodyYaw, float partialTick) {
		GL11.glRotatef(180.0F - bodyYaw, 0.0F, 1.0F, 0.0F);
		if (entity.deathTime > 0) {
			float rotationProgress = ((float)entity.deathTime + partialTick - 1.0F) / 20.0F * 1.6F;
			rotationProgress = MathHelper.sqrt_float(rotationProgress);
			if (rotationProgress > 1.0F) {
				rotationProgress = 1.0F;
			}

			GL11.glRotatef(rotationProgress * this.getMaxDeathRotation(entity), 0.0F, 0.0F, 1.0F);
		}
	}

	protected float getSwingProgress(T entity, float partialTick) {
		return entity.getSwingProgress(partialTick);
	}

	protected float limbSway(T entity, float partialTick) {
		return (float)entity.tickCount + partialTick;
	}

	protected void renderAdditional(T entity, float f) {
	}

	protected boolean prepareArmor(T entity, int layer, float partialTick) {
		return false;
	}

	protected float getMaxDeathRotation(T entity) {
		return 90.0F;
	}

	protected int getOverlayColor(T entity, float brightness, float partialTick) {
		return 0;
	}

	protected void setupScale(T entity, float partialTick) {
	}

	protected void renderSpecials(Tessellator tessellator, T entity, double d, double d1, double d2) {
		if (this.mc.canRenderEntityLabel()) {
			this.renderLivingLabel(tessellator, entity, Integer.toString(entity.id), d, d1, d2, 64, false);
		} else if (!entity.nickname.isEmpty()) {
			this.renderLivingLabel(tessellator, entity, entity.getDisplayName(), d, d1, d2, 64, true);
		}
	}

	protected void renderLivingLabel(Tessellator tessellator, T entity, String s, double d, double d1, double d2, int maxDistance, boolean depthTest) {
		float f = (float)this.renderDispatcher.camera.distanceTo(entity);
		if (!(f > (float)maxDistance)) {
			Font font = this.getFont();
			float f1 = 1.6F;
			float f2 = 0.026666671F;
			GL11.glPushMatrix();
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + entity.getHeadHeight() + 0.8F, (float)d2);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-this.renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.renderDispatcher.viewLerpPitch, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(-0.026666671F, -0.026666671F, 0.026666671F);
			GL11.glDisable(2896);
			GL11.glDepthMask(false);
			if (!depthTest) {
				GL11.glDisable(2929);
			}

			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			byte byte0 = 0;
			GL11.glDisable(3553);
			tessellator.startDrawingQuads();
			int j = font.getStringWidth(s) / 2;
			tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
			tessellator.addVertex((-j - 1), (-1 + byte0), 0.0);
			tessellator.addVertex((-j - 1), (8 + byte0), 0.0);
			tessellator.addVertex((j + 1), (8 + byte0), 0.0);
			tessellator.addVertex((j + 1), (-1 + byte0), 0.0);
			tessellator.draw();
			GL11.glEnable(3553);
			font.drawString(s, -font.getStringWidth(s) / 2, byte0, 553648127);
			if (!depthTest) {
				GL11.glEnable(2929);
			}

			GL11.glDepthMask(true);
			font.drawString(s, -font.getStringWidth(s) / 2, byte0, 16777215);
			GL11.glEnable(2896);
			GL11.glDisable(3042);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}

	public void loadEntityTexture(T entity) {
		if (!Minecraft.getMinecraft().gameSettings.mobVariants.value) {
			this.bindTexture(entity.getDefaultEntityTexture());
		} else {
			this.bindTexture(entity.getEntityTexture());
		}
	}
}
