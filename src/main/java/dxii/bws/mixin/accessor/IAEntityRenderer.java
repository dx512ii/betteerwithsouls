package dxii.bws.mixin.accessor;

import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityRenderer.class, remap = false)
public interface IAEntityRenderer {
	@Accessor("shadowOpacity")
	float getShadowOpacity();
	@Accessor("shadowOpacity")
	void setShadowOpacity(float newOpacity);

}
