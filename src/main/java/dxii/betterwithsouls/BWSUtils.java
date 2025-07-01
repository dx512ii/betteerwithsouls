package dxii.betterwithsouls;

import dxii.betterwithsouls.enums.EAccBonus;
import dxii.betterwithsouls.interfaces.ICube;
import dxii.betterwithsouls.interfaces.IInventory;
import dxii.betterwithsouls.interfaces.IWorld;
import dxii.betterwithsouls.item.ItemAccessory;
import dxii.betterwithsouls.util.DynamicLight;
import net.minecraft.client.render.model.Cube;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

import java.util.Random;

public class BWSUtils {

	public static final Random rand = new Random();

	public static int dmgAgainstResist(int dmg, int resist){
		return MathHelper.clamp(dmg - resist, 0, dmg);
	}

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
	public static double clampSize(double num, double min, double max){
		double retNum = Math.abs(num);
		retNum = MathHelper.clamp(retNum, min, max);
		return num < 0 ? -retNum : retNum;
	}

	/*
	ENTITY STUFF
	 */
	public static Vec3 getEntityViewVec(Entity ent){
		float pitch;
		float yaw;
		float xzLen;
		float x;

		float xrot = MathHelper.clamp(ent.xRot, -45, 45);

		pitch = MathHelper.cos(-ent.yRot * 0.017453292F - 3.1415927F);
		yaw = MathHelper.sin(-ent.yRot * 0.017453292F - 3.1415927F);
		xzLen = -MathHelper.cos(-xrot * 0.017453292F);
		x = MathHelper.sin(-xrot * 0.017453292F);
		return Vec3.getTempVec3(yaw * xzLen, x, pitch * xzLen);
	}

	public static boolean canReachEntity(Entity ent1, Entity ent2, double distance){
		double d = ent1.x - ent2.x;
		double d1 = ent1.z - ent2.z;
		float f = MathHelper.sqrt(d * d + d1 * d1);

		double f1 = d / (double)f;
		double f2 = d1 / (double)f;


		f1 *= -ent2.bbWidth*.5;
		f2 *= -ent2.bbWidth*.5;

		double entX = ent2.x-f1;
		double entZ = ent2.z-f2;
		
		boolean dist = distance * distance >= ent1.distanceToSqr(entX, ent2.y+ent2.bbHeight, entZ);
		boolean dist2 = distance * distance >= ent1.distanceToSqr(entX, ent2.y-ent2.bbHeight, entZ);
		boolean dist3 = distance * distance >= ent1.distanceToSqr(entX, ent2.y, entZ);

		return dist || dist2 || dist3;
	}

	public static double getEntitiesLookDot(Entity ent1, Entity ent2){
		return DotProduct(getEntityViewVec(ent1), getEntityViewVec(ent2));
	}

	public static boolean getEntitiesFacing(Entity ent1, Entity ent2){
		return getEntitiesLookDot(ent1, ent2) < -0.6;
	}

	public static double getDotToEntity(Mob entLooking, Entity entLooked, boolean ignorey){
		Vec3 dirToEntLooked = entLooking.getPosition(1, true).add( -entLooked.x, -entLooked.y, -entLooked.z).add( 0, -entLooked.bbHeight*.5, 0);
		Vec3 viewvec = getEntityViewVec(entLooking);
		if(ignorey){
			viewvec.y = 0;
			viewvec.normalize();
		}

		return DotProduct(viewvec, dirToEntLooked.normalize());
	}

	public static boolean isEntityInFront(Mob entLooking, Entity entLooked){

		return getDotToEntity(entLooking, entLooked, true) <= -0.5;
	}
	public static boolean isEntityInFrontWide(Mob entLooking, Entity entLooked){
		return getDotToEntity(entLooking, entLooked, true) < -0.31;
	}

	/*
	DYNAMIC LIGHTS
	 */
	public static DynamicLight addDynamicLight(Entity ent, int radius, int brightness, int xOffset, int yOffset, int zOffset){
		if(ent.world == null){
			return DynamicLight.NULL_LIGHT;
		}

		if(((IWorld)ent.world).bws$getDynLights() != null) {
			System.out.println("player dynamic light init!");

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


	public static void addBoxBlockbench(Cube cube, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, int pivotX, int pivotY, int pivotZ, float expandAmount){
		//just to make code more readable and get rid of these goddamn casts (they look ugly)
		((ICube)cube).addBoxBlockbench(posX, posY, posZ, sizeX, sizeY, sizeZ, pivotX, pivotY, pivotZ, expandAmount);
	}

	public static boolean playerHasAccessoryEffect(Player player, EAccBonus bonus){
		boolean hasEffect = false;

		ItemStack[] accInv = ((IInventory) ((player).inventory)).bws$getAccInv();
		if (accInv[0] != null && ((ItemAccessory) (accInv[0].getItem())).bonus == bonus) {
			hasEffect = true;
		} else if (accInv[1] != null && ((ItemAccessory) (accInv[1].getItem())).bonus == bonus) {
			hasEffect = true;
		} else if (accInv[2] != null && ((ItemAccessory) (accInv[2].getItem())).bonus == bonus) {
			hasEffect = true;
		} else if (accInv[3] != null && ((ItemAccessory) (accInv[3].getItem())).bonus == bonus) {
			hasEffect = true;
		}

		return hasEffect;
	}

}
