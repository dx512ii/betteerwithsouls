package dxii.betterwithsouls.mixin.accessor;


import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(value = Player.class, remap = false)
public interface IPlayerAccessor {

	@Invoker("damageEntity")
	void damageEntity(int dmg, DamageType dtype);

	@Invoker("alertWolves")
	void alertWolves(Mob attacker, boolean flag);


}
