package dxii.betterwithsouls.interfaces;

import dxii.betterwithsouls.util.CombatModule;
import net.minecraft.core.util.phys.Vec3;

public interface IMinecraft {
	Vec3 getItemDummyPos();
	void addToItemDummyPos(double x, double y, double z);
	Vec3 getItemDummyRot();
	void addToItemDummyRot(double x, double y, double z);
	void resetItemDummy();
	CombatModule getCombatModule();
}
