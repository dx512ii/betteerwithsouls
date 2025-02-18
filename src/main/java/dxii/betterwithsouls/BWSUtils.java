package dxii.betterwithsouls;

import dxii.betterwithsouls.interfaces.IWorld;
import dxii.betterwithsouls.util.DynamicLight;
import net.minecraft.client.render.model.Cube;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

import javax.annotation.Nullable;

import static net.minecraft.core.enums.LightLayer.Block;

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

	/*
	MATH & VECTORS
	 */
	public static double vecDist(Vec3 vec1, Vec3 vec2){
		return Vec3.getPermanentVec3(vec1.x - vec2.x, vec1.y - vec2.y, vec1.z - vec2.z).length();
	}
	public static double vecDistSquared(Vec3 vec1, Vec3 vec2){
		return abs(((vec1.x - vec2.x)*(vec1.x - vec2.x) + (vec1.y - vec2.y)*(vec1.y - vec2.y) + (vec1.z - vec2.z)*(vec1.z - vec2.z)) );
	}
	public static double DotProduct(Vec3 Vec1, Vec3 Vec2){
		return Vec1.x * Vec2.x + Vec1.y * Vec2.y + Vec1.z * Vec2.z;
	}
	public static double abs(double i){
		return i < 0 ? -i : i;
	}

	/*
	ENTITY STUFF
	 */

	public static double getEntitiesLookDot(Mob ent1, Mob ent2){
		return DotProduct(ent1.getLookAngle(), ent2.getLookAngle());
	}

	public static boolean getEntitiesFacing(Mob ent1, Mob ent2){
		return getEntitiesLookDot(ent1, ent2) < -0.6;
	}

	public static double getDotToEntity(Mob entLooking, Entity entLooked){
		Vec3 dirToEntLooked = entLooking.getPosition(1, false).add( -entLooked.x, -entLooked.y, -entLooked.z);

		return DotProduct(entLooking.getLookAngle(), dirToEntLooked.normalize());
	}

	public static boolean isEntityInFront(Mob entLooking, Entity entLooked){
		return getDotToEntity(entLooking, entLooked) < -0.5;
	}

	/*
	DYNAMIC LIGHTS
	 */
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
