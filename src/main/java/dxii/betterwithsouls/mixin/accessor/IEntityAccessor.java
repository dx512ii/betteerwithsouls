package dxii.betterwithsouls.mixin.accessor;

import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(value = Entity.class, remap = false)
public interface IEntityAccessor {

	@Accessor("random")
	Random getRandom();

	@Invoker("markHurt")
	void markHurt_();

}
