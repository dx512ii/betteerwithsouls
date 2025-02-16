package dxii.betterwithsouls;

import dxii.betterwithsouls.interfaces.IWorld;
import dxii.betterwithsouls.mixin.accessor.IGuiAccessor;
import dxii.betterwithsouls.mixin.accessor.IScreenAccessor;
import dxii.betterwithsouls.mixin.accessor.IScreenContainerAccessor;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.DynamicLight;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;

public class BWSUtils {

	public static void pushRelative(Entity entity, float right, float up, float forward, float amount) {
		System.out.println(right);
		System.out.println(up);
		System.out.println(forward);

		float f3 = MathHelper.sqrt_float(right * right + forward * forward);
		if (f3 < 0.01f) {
			return;
		}
		if (f3 < 1.0f) {
			f3 = 1.0f;
		}
		f3 = amount / f3;
		float sinYaw = MathHelper.sin(entity.yRot * 3.141593f / 180.0f);
		float cosYaw = MathHelper.cos(entity.yRot * 3.141593f / 180.0f);
		entity.xd += ((right *= f3) * cosYaw - (forward *= f3) * sinYaw);
		entity.yd += up;
		entity.zd += (forward * cosYaw + right * sinYaw);
	}

	public static double vecDist(Vec3 vec1, Vec3 vec2){
		return Vec3.getPermanentVec3(vec1.x - vec2.x, vec1.y - vec2.y, vec1.z - vec2.z).length();
	}
	public static double vecDistSquared(Vec3 vec1, Vec3 vec2){
		return abs(((vec1.x - vec2.x)*(vec1.x - vec2.x) + (vec1.y - vec2.y)*(vec1.y - vec2.y) + (vec1.z - vec2.z)*(vec1.z - vec2.z)) );
	}

	public static double abs(double i){
		return i < 0 ? -i : i;
	}

	public static DynamicLight addDynamicLight(Entity ent, int radius, int brightness, int xOffset, int yOffset, int zOffset){
		if(((IWorld)ent.world).bws$getDynLights() != null) {
			DynamicLight dyn = new DynamicLight(ent, ent.world);
			dyn.x = (int) ent.x;
			dyn.y = (int) ent.y;
			dyn.z = (int) ent.z;

			dyn.xO = xOffset;
			dyn.yO = yOffset;
			dyn.zO = zOffset;

			dyn.entity = ent;
			dyn.world = ent.world;
			dyn.radius = radius;
			dyn.brightness = brightness;

			((IWorld) ent.world).bws$getDynLights().addLight(dyn);

			return dyn;
		}
		return null;
	}

	public static DynamicLight addDynamicLightFading(World world, int radius, int brightness, int x, int y, int z){
		if(((IWorld)world).bws$getDynLights() != null) {
			DynamicLight dyn = new DynamicLight(x, y, z, world);

			dyn.radius = radius;
			dyn.brightness = brightness;

			((IWorld) world).bws$getDynLights().addLight(dyn);

			return dyn;
		}
		return null;
	}

	public static void modifyPlayerLight(World world, int radius, int brightness){
		if(((IWorld)world).bws$getDynLights() != null) {
			((IWorld)world).bws$getDynLights().modifyPlayerLight(radius, brightness);
		}
	}

}
