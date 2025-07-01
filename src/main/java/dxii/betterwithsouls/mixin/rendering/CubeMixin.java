package dxii.betterwithsouls.mixin.rendering;

import dxii.betterwithsouls.interfaces.ICube;
import net.minecraft.client.render.model.Cube;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = Cube.class, remap = false)
public class CubeMixin implements ICube {

	@Shadow
	private boolean compiled = false;

	@Unique
	public String name = "null";
	@Unique
	public Cube thisObject = (Cube)(Object)this;

	@Unique
	float xo;
	@Unique
	float yo;
	@Unique
	float zo;

	@Unique
	float rotXo;
	@Unique
	float rotYo;
	@Unique
	float rotZo;

	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void decompile() {
		this.compiled = false;
	}

	@Override
	public void setOffset(float x, float y, float z) {
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}
	@Override
	public void setRotationOffset(float x, float y, float z) {
		this.rotXo = x;
		this.rotYo = y;
		this.rotZo = z;
	}


	@Override
	public void addBoxBlockbench(float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, int pivotX, int pivotY, int pivotZ, float expandAmount){
		thisObject.addBox(posX+pivotX, Math.abs(posY)-sizeY+pivotY, posZ-pivotZ, sizeX, sizeY, sizeZ, expandAmount);
		thisObject.setRotationPoint(-pivotX, pivotY, -pivotZ);
	}


	@ModifyArgs(
		method = "render",
		at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal=0 )
	)
	public void offsetCube(Args args){
		args.set(0, (float)args.get(0)+xo);
		args.set(1, (float)args.get(1)+yo);
		args.set(2, (float)args.get(2)+zo);
	}
	@ModifyArgs(
		method = "render",
		at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal=2 )
	)
	public void offsetCube2(Args args){
		args.set(0, (float)args.get(0)+xo);
		args.set(1, (float)args.get(1)+yo);
		args.set(2, (float)args.get(2)+zo);
	}


}
