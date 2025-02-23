package dxii.betterwithsouls.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ICube {
	String getName();
	void setName(String name);
	void decompile();

	void setOffset(float x, float y, float z);
	float getOffsetX();
	float getOffsetY();
	float getOffsetZ();

	/**
	 * add box to a cube using pos, rot, scale and pivot pos directly from blockbench cube, (bedrock entity model)
	 * this thing will do all needed adjustments
	 */
	void addBoxBlockbench(float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, int pivotX, int pivotY, int pivotZ, float expandAmount);
}
