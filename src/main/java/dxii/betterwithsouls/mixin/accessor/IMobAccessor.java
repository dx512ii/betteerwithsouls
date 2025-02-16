package dxii.betterwithsouls.mixin.accessor;

import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(value = Mob.class, remap = false)
public interface IMobAccessor {

	@Accessor("entityAge")
	int getEntityAge();

	@Accessor("lastDamage")
	int lastDamage();

	@Accessor("entityAge")
	void setEntityAge(int newage);

}
