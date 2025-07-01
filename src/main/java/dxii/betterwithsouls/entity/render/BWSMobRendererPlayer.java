//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dxii.betterwithsouls.entity.render;

import dxii.betterwithsouls._BWSMain;
import dxii.betterwithsouls.entity.model.BWSModelBiped;
import dxii.betterwithsouls.entity.model.BWSModelPlayer;
import dxii.betterwithsouls.entity.model.BWSModelPlayerSlim;
import dxii.betterwithsouls.interfaces.IModel;
import dxii.betterwithsouls.item.BWSItemArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.ImageParser;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.PlayerSkinParser;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.camera.EntityCamera;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.client.render.model.ModelPlayerSlim;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemQuiver;
import net.minecraft.core.item.ItemQuiverEndless;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.BlocksContainer;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BWSMobRendererPlayer extends BWSMobRenderer<Player> {
	private BWSModelBiped modelBipedMain;
	private final BWSModelPlayer modelThick;
	private final BWSModelPlayer modelSlim;
	private final BWSModelBiped modelArmorChestplate;
	private final BWSModelBiped modelArmor;
	private BlocksContainer container = null;
	private RenderBlocks containerRenderBlock = null;

	public BWSMobRendererPlayer() {
		super(new BWSModelPlayer(0.0F), 0.5F);
		this.modelBipedMain = (BWSModelBiped)this.mainModel;
		this.modelArmorChestplate = new BWSModelBiped(1.0F);
		this.modelArmor = new BWSModelBiped(0.5F);
		this.modelThick = new BWSModelPlayer(0.0F);
		this.modelSlim = new BWSModelPlayerSlim(0.0F);
	}

	protected void renderSpecials(Player player, float partialTick) {
		if (Minecraft.getMinecraft().thePlayer.getGamemode() == Gamemode.spectator && player.getGamemode() == Gamemode.spectator) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
		} else {
			if (player.getGamemode() == Gamemode.spectator && Minecraft.getMinecraft().thePlayer.getGamemode() != Gamemode.spectator) {
				return;
			}

			if (player.getGamemode() == Gamemode.spectator) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.0F);
			}
		}

		ItemStack itemstack = player.inventory.armorItemInSlot(3);
		if (itemstack != null) {
			GL11.glPushMatrix();
			this.modelBipedMain.head.translateTo(0.0625F);
			float scale;
			if (itemstack.itemID < Blocks.blocksList.length && (BlockModelDispatcher.getInstance().getDispatch(Blocks.getBlock(itemstack.itemID))).shouldItemRender3d()) {
				scale = 0.625F;
				GL11.glTranslatef(0.0F, -0.25F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(scale, -scale, scale);
				ItemModelDispatcher.getInstance().getDispatch(itemstack).renderItem(Tessellator.instance, this.renderDispatcher.itemRenderer, player, itemstack);
			} else if (!(itemstack.getItem() instanceof BWSItemArmor) && (!(itemstack.getItem() instanceof IArmorItem) || ((IArmorItem)itemstack.getItem()).getArmorPiece() != 3)) {
				GL11.glEnable(2884);
				scale = 0.625F;
				GL11.glTranslatef(0.0F, -0.8F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
				GL11.glScalef(scale, scale, scale);
				ItemModelDispatcher.getInstance().getDispatch(itemstack).renderItemInWorld(Tessellator.instance, player, itemstack, player.getBrightness(partialTick), 1.0F, true);
				GL11.glDisable(2884);
			}

			GL11.glPopMatrix();
		}

		boolean renderCape = this.bindDownloadableTexture("https://api.betterthanadventure.net/capes?username=" + player.username, (String)null, (ImageParser)null);
		if (!renderCape) {
			renderCape = this.bindDownloadableTexture(player.capeURL, null, null);
		}

		float bodyAngle;
		if (renderCape) {
			GL11.glPushMatrix();
			GL11.glEnable(3042);
			float yawOff;
			if (Minecraft.getMinecraft().thePlayer.getGamemode() == Gamemode.spectator && player.getGamemode() == Gamemode.spectator) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
			} else if (player.getGamemode() == Gamemode.spectator) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.0F);
			} else {
				yawOff = !Global.accessor.isFullbrightEnabled() && !LightmapHelper.isLightmapEnabled() ? player.getBrightness(partialTick) : 1.0F;
				GL11.glColor4f(yawOff, yawOff, yawOff, 1.0F);
			}

			GL11.glTranslatef(0.0F, 0.0F, 0.125F);
			yawOff = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO) * partialTick;
			bodyAngle = 5.0F;
			if (player.isSneaking()) {
				bodyAngle += 25.0F;
			}

			double _xd = player.vehicle instanceof Entity ? ((Entity)player.vehicle).xd : MathHelper.lerp(player.xdO, player.xd, (double)partialTick);
			double _yd = Math.min(player.vehicle instanceof Entity ? ((Entity)player.vehicle).yd : MathHelper.lerp(player.ydO, player.yd, (double)partialTick), 0.0);
			double _zd = player.vehicle instanceof Entity ? ((Entity)player.vehicle).zd : MathHelper.lerp(player.zd0, player.zd, (double)partialTick);
			double vel = -1.0 / (3.0 * Math.hypot(_xd, _zd) + 1.0) + 1.0;
			double moveAng = Math.atan2(_xd, _zd);
			double yawRad = Math.toRadians((double)yawOff);
			double multiplier = 1.0 - Math.abs((Math.cos(yawRad) + 1.0 - (Math.cos(moveAng) + 1.0) + 2.0) / 2.0 - 1.0);
			player.wobbleTimer += (float)((double)((float)player.tickCount + partialTick - (float)player.lastRenderTick) / (30.0 - 29.0 * MathHelper.clamp(vel, 0.0, 1.0)));
			player.lastRenderTick = player.tickCount;
			double wobble = Math.sin((double)player.wobbleTimer) * (1.5 + 4.5 * vel * multiplier);
			GL11.glRotatef((float)MathHelper.clamp(MathHelper.clamp((double)bodyAngle + vel * 100.0 * multiplier, (double)bodyAngle, 100.0) + wobble - _yd * 60.0, 0.0, 180.0), 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			this.modelBipedMain.renderCloak(0.0625F);
			GL11.glPopMatrix();
		}

		ItemStack itemstack1 = player.inventory.getCurrentItem();
		if (itemstack1 != null) {
			GL11.glPushMatrix();
			this.modelBipedMain.armRight.translateTo(0.0625F);

			Vec3 loc = modelBipedMain.getItemTranslation();
			GL11.glTranslated(loc.x, loc.y, loc.z);
			Vec3 rot = modelBipedMain.getItemRot();

			GL11.glRotated(rot.z, 0, 0, 1);
			GL11.glRotated(rot.y, 0, 1, 0);
			GL11.glRotated(rot.x, 1, 0, 0);

			BlockModel.setRenderBlocks(this.renderDispatcher.itemRenderer.renderBlocksInstance);
			ItemModelDispatcher.getInstance().getDispatch(itemstack1).renderItemThirdPerson(Tessellator.instance, this.renderDispatcher.itemRenderer, player, itemstack1, true);

			GL11.glPopMatrix();
		}

		if (player.getHeldObject() != null) {
			bodyAngle = player.walkAnimSpeedO + (player.walkAnimSpeed - player.walkAnimSpeedO) * partialTick;
			float walkProgress = player.walkAnimPos - player.walkAnimSpeed * (1.0F - partialTick);
			GL11.glTranslatef(0.0F, MathHelper.cos(walkProgress / 2.0F * 2.0F / 3.0F) / 26.0F * bodyAngle + 0.1F, 0.0F);
			this.drawHeldObject(player, partialTick);
		}

	}

	public void drawFirstPersonHand(Player player, boolean isLeft) {
		this.mainModel = player.slimModel ? this.modelSlim : this.modelThick;
		this.modelBipedMain = player.slimModel ? this.modelSlim : this.modelThick;
		this.modelBipedMain.onGround = 0.0F;
		this.modelBipedMain.isRiding = false;
		this.modelBipedMain.setupAnimation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		if (isLeft) {
			this.modelBipedMain.armLeft.render(0.0625F);
		} else {
			this.modelBipedMain.armRight.render(0.0625F);
		}

		if (this.modelBipedMain instanceof BWSModelPlayer) {
			if (isLeft) {
				((BWSModelPlayer)this.modelBipedMain).bipedLeftArmOverlay.render(0.0625F);
			} else {
				((BWSModelPlayer)this.modelBipedMain).bipedRightArmOverlay.render(0.0625F);
			}
		}

	}

	public void drawHeldObject(Player player, float partialTick) {
		if (player.getHeldObject() instanceof CarriedBlock) {
			CarriedBlock carriedBlock = (CarriedBlock)player.getHeldObject();
			if (this.container == null || this.container.world != player.world) {
				this.container = new BlocksContainer(player.world);
				this.containerRenderBlock = new RenderBlocks(this.container);
			}

			Tessellator tessellator = Tessellator.instance;
			Minecraft mc = Minecraft.getMinecraft();
			TextureRegistry.blockAtlas.bind();
			Lighting.disable();
			GL11.glPushMatrix();
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(3042);
			GL11.glDisable(2884);
			if (mc.isAmbientOcclusionEnabled()) {
				GL11.glShadeModel(7425);
			} else {
				GL11.glShadeModel(7424);
			}

			GL11.glScalef(0.55F, -0.55F, 0.55F);
			GL11.glTranslatef(0.0F, -0.75F, -0.75F);
			int blockX = MathHelper.floor(player.x);
			int blockY = MathHelper.floor(player.y) + 1;
			int blockZ = MathHelper.floor(player.z);
			BlockModel.setRenderBlocks(this.containerRenderBlock);
			tessellator.startDrawingQuads();
			tessellator.setTranslation((double)(-blockX) - 0.5, (double)(-blockY) - 0.5, (double)(-blockZ) - 0.5);
			this.container.setLightReferenceEntity(player);
			this.container.setBlock(blockX, blockY, blockZ, carriedBlock.blockId, carriedBlock.metadata, carriedBlock.entity);
			((BlockModel<?>)BlockModelDispatcher.getInstance().getDispatch(carriedBlock.block())).renderNoCulling(Tessellator.instance, blockX, blockY, blockZ);
			tessellator.draw();
			tessellator.setTranslation(0.0, 0.0, 0.0);
			this.container.setLightReferenceEntity((Entity)null);
			this.container.clear();
			TileEntityRenderer<TileEntity> renderer = TileEntityRenderDispatcher.instance.getRenderer(carriedBlock.entity);
			if (renderer != null && carriedBlock.entity != null) {
				carriedBlock.entity.worldObj = player.world;
				renderer.doRender(tessellator, carriedBlock.entity, -0.5, -0.5, -0.5, partialTick);
				carriedBlock.entity.worldObj = null;
			}

			GL11.glPopMatrix();
			GL11.glEnable(2896);
			GL11.glEnable(16384);
			GL11.glEnable(16385);
			GL11.glEnable(2903);
		}

	}

	protected void renderSpecials(Tessellator tessellator, Player entity, double d, double d1, double d2) {
		if (Minecraft.getMinecraft().thePlayer.getGamemode() == Gamemode.spectator || entity.getGamemode() != Gamemode.spectator) {
			if (Minecraft.getMinecraft().gameSettings.immersiveMode.drawNames() && this.renderDispatcher.camera != null) {
				if (this.renderDispatcher.camera instanceof EntityCamera && ((EntityCamera)this.renderDispatcher.camera).mob == entity) {
					return;
				}

				float f = 1.6F;
				float f1 = 0.01666667F * f;
				float f2 = (float)this.renderDispatcher.camera.distanceTo(entity);
				float f3 = entity.isSneaking() ? 32.0F : 64.0F;
				if (f2 < f3) {
					String s = entity.getDisplayName();
					if (!entity.isSneaking()) {
						if (entity.isPlayerSleeping()) {
							this.renderLivingLabel(tessellator, entity, s, d, d1 - 1.5, d2, 64, false);
						} else {
							this.renderLivingLabel(tessellator, entity, s, d, d1, d2, 64, false);
						}
					} else {
						Font font = this.getFont();
						GL11.glPushMatrix();
						GL11.glTranslatef((float)d + 0.0F, (float)d1 + 2.3F, (float)d2);
						GL11.glNormal3f(0.0F, 1.0F, 0.0F);
						GL11.glRotatef(-this.renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
						GL11.glRotatef(this.renderDispatcher.viewLerpPitch, 1.0F, 0.0F, 0.0F);
						GL11.glScalef(-f1, -f1, f1);
						GL11.glDisable(2896);
						GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
						GL11.glDepthMask(false);
						GL11.glEnable(3042);
						GL11.glBlendFunc(770, 771);
						GL11.glDisable(3553);
						tessellator.startDrawingQuads();
						int i = font.getStringWidth(s) / 2;
						tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
						tessellator.addVertex((double)(-i - 1), -1.0, 0.0);
						tessellator.addVertex((double)(-i - 1), 8.0, 0.0);
						tessellator.addVertex((double)(i + 1), 8.0, 0.0);
						tessellator.addVertex((double)(i + 1), -1.0, 0.0);
						tessellator.draw();
						GL11.glEnable(3553);
						GL11.glDepthMask(true);
						font.drawString(s, -font.getStringWidth(s) / 2, 0, 553648127);
						GL11.glEnable(2896);
						GL11.glDisable(3042);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glPopMatrix();
					}
				}
			}

		}
	}

	protected void setupScale(Player entity, float partialTick) {
		if (Minecraft.getMinecraft().thePlayer.getGamemode() == Gamemode.spectator && entity.getGamemode() == Gamemode.spectator) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
		} else if (entity.getGamemode() == Gamemode.spectator) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.0F);
		}

		float scale = 0.9375F;
		GL11.glScalef(scale, scale, scale);
		if (entity.isDwarf()) {
			GL11.glScalef(0.6F, 0.5F, 0.6F);
		}

	}

	protected boolean prepareArmor(Player entity, int layer, float partialTick) {
		if (Minecraft.getMinecraft().thePlayer.getGamemode() == Gamemode.spectator && entity.getGamemode() == Gamemode.spectator) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
			GL11.glEnable(3042);
		} else {
			if (entity.getGamemode() == Gamemode.spectator && Minecraft.getMinecraft().thePlayer.getGamemode() != Gamemode.spectator) {
				return false;
			}

			if (entity.getGamemode() == Gamemode.spectator) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.0F);
			}
		}

		ItemStack itemstack = entity.inventory.armorItemInSlot(3 - layer);
		if (itemstack != null) {
			if(itemstack.getItem() instanceof BWSItemArmor){

				BWSItemArmor armorItem = (BWSItemArmor)itemstack.getItem();
				BWSModelBiped modelBiped;
				this.bindTexture(String.format("/assets/"+ _BWSMain.MOD_ID +"/textures/armor/%s_%d.png", armorItem.texture, layer != 2 ? 1 : 2));
				modelBiped = layer != 2 ? this.modelArmorChestplate : this.modelArmor;
				modelBiped.head.visible = layer == 0;
				modelBiped.hair.visible = layer == 0;
				modelBiped.body.visible = layer == 1 || layer == 2;
				modelBiped.armRight.visible = layer == 1;
				modelBiped.armLeft.visible = layer == 1;
				modelBiped.legRight.visible = layer == 2 || layer == 3;
				modelBiped.legLeft.visible = layer == 2 || layer == 3;
				this.setArmorModel(modelBiped);
				return true;

			}else {
				Item item = itemstack.getItem();
				IArmorItem armorItem = (IArmorItem) item;
				if (armorItem.getArmorPiece() == 3 - layer) {
					BWSModelBiped modelBiped;
					if (item instanceof ItemQuiver) {
						this.bindTexture("/assets/minecraft/textures/armor/quiver.png");
						modelBiped = this.modelArmorChestplate;
						modelBiped.body.visible = layer == 1 || layer == 2;
						this.setArmorModel(modelBiped);
						return true;
					}

					if (item instanceof ItemQuiverEndless) {
						this.bindTexture("/assets/minecraft/textures/armor/quiver_golden.png");
						modelBiped = this.modelArmorChestplate;
						modelBiped.body.visible = layer == 1 || layer == 2;
						this.setArmorModel(modelBiped);
						return true;
					}

					if (item == Items.ARMOR_BOOTS_ICESKATES) {
						this.bindTexture("/assets/minecraft/textures/armor/skates.png");
						modelBiped = this.modelArmorChestplate;
						modelBiped.legRight.visible = layer == 2 || layer == 3;
						modelBiped.legLeft.visible = layer == 2 || layer == 3;
						this.setArmorModel(modelBiped);
						return true;
					}

					if (armorItem.getArmorMaterial() != null) {
						this.bindTexture(String.format("/assets/%s/textures/armor/%s_%d.png", armorItem.getArmorMaterial().identifier.namespace(), armorItem.getArmorMaterial().identifier.value(), layer != 2 ? 1 : 2));
						modelBiped = layer != 2 ? this.modelArmorChestplate : this.modelArmor;
						modelBiped.head.visible = layer == 0;
						modelBiped.hair.visible = layer == 0;
						modelBiped.body.visible = layer == 1 || layer == 2;
						modelBiped.armRight.visible = layer == 1;
						modelBiped.armLeft.visible = layer == 1;
						modelBiped.legRight.visible = layer == 2 || layer == 3;
						modelBiped.legLeft.visible = layer == 2 || layer == 3;
						this.setArmorModel(modelBiped);
						return true;
					}
				}
			}
		}

		return false;
	}

	protected void renderAdditional(Player entity, float partialTick) {
		this.renderSpecials(entity, partialTick);
	}

	protected void setupRotations(Player entity, float ticksExisted, float bodyYaw, float partialTick) {
		if (entity.isAlive() && entity.isPlayerSleeping()) {
			GL11.glRotatef(entity.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.getMaxDeathRotation(entity), 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
		} else {
			super.setupRotations(entity, ticksExisted, bodyYaw, partialTick);
		}

	}

	protected void translateModel(Player entity, double x, double y, double z) {
		if (entity.isAlive() && entity.isPlayerSleeping()) {
			super.translateModel(entity, x + (double)entity.sleepOffX, y + (double)entity.sleepOffY, z + (double)entity.sleepOffZ);
		} else {
			super.translateModel(entity, x, y, z);
		}

	}

	public void render(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);


		if(false){
			if(mainModel != null) {
				((BWSModelBiped) mainModel).head.visible = false;
				((BWSModelBiped) mainModel).hair.visible = false;
			}
			if(modelThick != null) {
				(modelThick).head.visible = false;
				(modelThick).hair.visible = false;
			}
			if(modelSlim != null) {
				(modelSlim).head.visible = false;
				(modelSlim).hair.visible = false;
			}
			if(overlayModel != null) {
				((BWSModelBiped) overlayModel).head.visible = false;
				((BWSModelBiped) overlayModel).hair.visible = false;
			}
			if(armorModel != null) {
				((BWSModelBiped) armorModel).head.visible = false;
				((BWSModelBiped) armorModel).hair.visible = false;
			}
			if(modelArmorChestplate != null) {
				modelArmorChestplate.head.visible = false;
				modelArmorChestplate.hair.visible = false;
			}
		}

		this.mainModel = entity.slimModel ? this.modelSlim : this.modelThick;
		this.modelBipedMain = entity.slimModel ? this.modelSlim : this.modelThick;
		ItemStack itemstack = entity.inventory.getCurrentItem();
		this.modelArmorChestplate.holdingLarge = this.modelArmor.holdingLarge = this.modelBipedMain.holdingLarge = entity.getHeldObject() != null;
		this.modelArmorChestplate.holdingRightHand = this.modelArmor.holdingRightHand = this.modelBipedMain.holdingRightHand = itemstack != null;
		BWSModelBiped var10002 = this.modelBipedMain;
		this.modelArmorChestplate.holdingRightHand = this.modelArmor.holdingRightHand = var10002.holdingRightHand |= entity.getHeldObject() != null;
		var10002 = this.modelBipedMain;
		this.modelArmorChestplate.holdingLeftHand = this.modelArmor.holdingLeftHand = var10002.holdingLeftHand |= entity.getHeldObject() != null;
		this.modelArmorChestplate.sneaking = this.modelArmor.sneaking = this.modelBipedMain.sneaking = entity.isSneaking();
		this.modelArmorChestplate.isRiding = this.modelArmor.isRiding = this.modelBipedMain.isRiding = entity.isPassenger();
		double d3 = y - (double)entity.heightOffset;
		if (entity.isSneaking() && !(entity instanceof PlayerLocal)) {
			d3 -= 0.125;
		}

		super.render(tessellator, entity, x, d3, z, yaw, partialTick);
		this.modelArmorChestplate.holdingLarge = this.modelArmor.holdingLarge = this.modelBipedMain.holdingLarge = false;
		this.modelArmorChestplate.sneaking = this.modelArmor.sneaking = this.modelBipedMain.sneaking = false;
		this.modelArmorChestplate.holdingRightHand = this.modelArmor.holdingRightHand = this.modelBipedMain.holdingRightHand = false;
		this.modelArmorChestplate.holdingLeftHand = this.modelArmor.holdingLeftHand = this.modelBipedMain.holdingLeftHand = false;
		GL11.glDisable(3042);
	}

	public void postRender(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick) {
		if (entity.getGamemode() != Gamemode.spectator) {
			super.postRender(tessellator, entity, x, y, z, yaw, partialTick);
		}
	}

	public void loadEntityTexture(Player entity) {
		this.bindDownloadableTexture(entity.skinURL, entity.getEntityTexture(), PlayerSkinParser.instance);
	}

	protected float limbSway(Player entity, float partialTick) {
		return super.limbSway(entity, partialTick);
	}
}
