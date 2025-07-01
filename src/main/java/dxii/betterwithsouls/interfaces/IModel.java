package dxii.betterwithsouls.interfaces;

import net.minecraft.client.render.model.Cube;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.phys.Vec3;

import java.lang.reflect.InvocationTargetException;

public interface IModel {
	void setupAnimatedLimb(Cube cube, String name);
	void resetAnimatedLimbs();

	void modifyBox(Cube cube, float minX, float minY, float minZ, int sizeX, int sizeY, int sizeZ, float expandAmount, boolean flipBottomUV);
	void renderCustom(Mob mob);

	Vec3 getItemRot();
	void setItemRot(Vec3 rot);

	Vec3 getItemTranslation();
	void setItemTranslation(double x, double y, double z);
}
