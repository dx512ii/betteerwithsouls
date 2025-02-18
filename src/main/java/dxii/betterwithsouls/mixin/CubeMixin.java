package dxii.betterwithsouls.mixin;

import dxii.betterwithsouls.interfaces.ICube;
import net.minecraft.client.render.model.Cube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = Cube.class, remap = false)
public class CubeMixin implements ICube {
	@Unique
	public String name;
	@Unique
	public Cube thisObject = (Cube)(Object)this;

	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void addBoxBlockbench(float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, int pivotX, int pivotY, int pivotZ, float expandAmount){
		thisObject.addBox(posX+pivotX, Math.abs(posY)-sizeY+pivotY, posZ-pivotZ, sizeX, sizeY, sizeZ, expandAmount);
		thisObject.setRotationPoint(-pivotX, pivotY, -pivotZ);
	}
}
