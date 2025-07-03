package dxii.betterwithsouls.item;



import dxii.betterwithsouls.BWSUtils;
import dxii.betterwithsouls.enums.EReinforcementType;
import dxii.betterwithsouls.interfaces.IEntity;
import dxii.betterwithsouls.interfaces.IMob;
import dxii.betterwithsouls.interfaces.IPlayer;
import dxii.betterwithsouls.interfaces.IReinforceable;
import dxii.betterwithsouls.item.weapon.moveset.AnimationMoveset;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.DamageInfo;
import dxii.betterwithsouls.util.DamageResistModule;
import dxii.betterwithsouls.util.animation.Animation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

import java.util.List;


/*
DXII'S WEAPON BASE
IT WORKS, I LIKE IT, IT CAN DO COOLDOWNS, LMB/RMB ATTACKS AND 3RD ONE SPECIAL
WITH KEYBIND, FEEL FREE TO TAKE IT IF YOU LIKE IT, IT IS CONNECTED DIRECTLY TO MOUSE INPUT
(CHECK 'MinecraftMixin_old')
 */

public class ItemWeapon extends BWSModItem {
	//attacks delay and hold
	public int atkDelay1 = 0;
	public int atkDelay2 = 0;
	public int atkDelay3 = 0;

	public int atkTiming1 = 0;
	public int atkTiming2 = 0;
	public int atkTiming3 = 0;


	public ItemWeapon(String name, int id) {
		super(name, id);
		this.maxStackSize = 1;
	}

	public float getModelOffsetX(){
		return 0;
	}
	public float getModelOffsetY(){
		return 0;
	}

	//weapon stats
	public DamageInfo dinfo = new DamageInfo();
	public int damage = 1;
	public float meleeRange = 3;
	public DamageType dtype = BWSDamageTypes.SLASH;
	public EReinforcementType reinforcementType = EReinforcementType.NONE;

	public AnimationMoveset moveset;

	public int manaCost;

	public ItemWeapon withAttackTimigs(int timing1, int timing2, int timing3){
		this.atkTiming1 = timing1;
		this.atkTiming2 = timing2;
		this.atkTiming3 = timing3;
			return this;
	}
	public ItemWeapon withAttackDelays(int delay1, int delay2, int delay3){
		this.atkDelay1 = delay1;
		this.atkDelay2 = delay2;
		this.atkDelay3 = delay3;
			return this;
	}
	public ItemWeapon withStats(DamageType type, int damage, float range){
		this.dtype = type;
		this.damage = damage;
		this.meleeRange = range;
		return this;
	}
	public ItemWeapon withMoveset(AnimationMoveset moveset){
		this.moveset = moveset;
		return this;
	}


	private final AABB hitBox = AABB.getPermanentBB(
		0,
		0,
		0,
		0,
		0,
		0
	);


	@Override
	public String getTranslatedName(ItemStack itemstack) {
		String upgrade = "";
		if(this.reinforcementType != EReinforcementType.NONE){
			upgrade = " +"+getReinforcement(itemstack);
		}
		return I18n.getInstance().translateKey(itemstack.getItemKey() + ".name")+upgrade;
	}
	@Override
	public String getTranslatedDescription(ItemStack itemstack) {
		return I18n.getInstance().translateKey(itemstack.getItemKey() + ".desc") + "\n§0☠:"+this.getWeaponDamage(itemstack);
	}


	public void deploy(ItemStack itemstack, World world, Player entityplayer){
		this.sendPlayerAnim(entityplayer, moveset.getPlayerIdleAnimation());
		this.sendVMAnim(entityplayer, moveset.getIdleAnimation());
	}
	public void holster(ItemStack itemstack, World world, Player entityplayer){
		this.stopPlayerAnims(entityplayer);
	}

	/**
	all three attacks has timed and non-timed execution
	change atkTiming* to change delay between these two executions,
	at 0 delay only timed one will be executed
	*/
	public void attack1(ItemStack itemstack, World world, Player entityplayer, boolean timed){
	}

	//secondary
	/**
	 all three attacks has timed and non-timed execution
	 change atkTiming* to change delay between these two executions,
	 at 0 delay only timed one will be executed
	 */
	public void attack2(ItemStack itemstack, World world, Player entityplayer, boolean timed){

	}

	//parry
	/**
	 all three attacks has timed and non-timed execution
	 change atkTiming* to change delay between these two executions,
	 at 0 delay only timed one will be executed
	 */
	public void attack3(ItemStack itemstack, World world, Player entityplayer, boolean timed){

	}

	public int getWeaponDamage(ItemStack stack){
		byte reinforce = getReinforcement(stack);

		switch(this.reinforcementType){
			case NORMAL:
				return (int) (this.damage + this.damage*.1*reinforce);
			case UNIQUE:
				return (int) (this.damage + this.damage*.5*reinforce);
			default:
				return this.damage;
		}
	}



	public void meleeAttack(Mob attacker, DamageInfo dinfo, float range, float dot, String hitsound, String worldHitSound, boolean heavySound){
		if(attacker.world == null){
			return;
		}

		boolean hitsmb = false;
		World world = attacker.world;

		hitBox.minX = attacker.x - range;
		hitBox.minY = attacker.y - range - attacker.getHeadHeight();
		hitBox.minZ = attacker.z - range;
		hitBox.maxX = attacker.x + range;
		hitBox.maxY = attacker.y + range + attacker.getHeadHeight();
		hitBox.maxZ = attacker.z + range;

		List<Mob> attacked = world.getEntitiesWithinAABB(Mob.class, hitBox);

		for (Mob mob : attacked) {
			if (mob == attacker) {
				continue;
			}
			if (mob.isAlive() && BWSUtils.canReachEntity(attacker, mob, range)
				&& mob.canEntityBeSeen(attacker)
				&& BWSUtils.getDotToEntity(attacker, mob, false) <= dot
			) {
				((IMob) mob).receiveDamageInfo(dinfo);
				hitsmb = true;
			}
		}
		//there should be sound but meh
		Vec3 startpos = attacker.getPosition(1, true);
		Vec3 viewvec = attacker.getViewVector(1);
		viewvec.x *= range;
		viewvec.y *= range;
		viewvec.z *= range;
		Vec3 endpos = startpos.add(viewvec.x, viewvec.y, viewvec.z);

		HitResult trace = attacker.world.checkBlockCollisionBetweenPoints(startpos, endpos, false, false, true );
		if(trace != null && trace.side != Side.NONE){
			playSound(attacker, worldHitSound, heavySound, 0.3f, 1);
		}
		if(hitsmb){
			world.playSoundAtEntity(attacker, attacker, hitsound, 0.25F, 1.2F / (BWSUtils.rand.nextFloat() * 0.4F + 1.0F));
		}
	}
	public void raiseGuard(Mob mob, DamageResistModule blockResist, String blockSound){
		((IMob)mob).raiseGuard(blockResist, blockSound);
	}

	public void playSound(Entity entity, String sound, boolean heavy, float volmul, float pitchmul){
		if(entity.world == null){
			System.out.println("WeaponMelee ERROR: attempt to play sound, entity's world is NULL!!!");
			return;
		}
		entity.world.playSoundAtEntity(entity, entity, sound, 0.5F*volmul, 1.2F / (itemRand.nextFloat() * 0.4F + (heavy ? 1.5f : 1.0F)*pitchmul));
	}

	@Override
	public boolean beforeDestroyBlock(World world, ItemStack itemStack, int blockId, int x, int y, int z, Side side, Player player) {
		return false;
	}

	public void sendPlayerAnim(Player player, Animation anim){
		((IEntity)player).bws$sendEntityAnim(anim);
	}
	public void sendPlayerNextAnim(Player player, Animation anim){
		((IEntity)player).bws$getAnimManager().nextAnimation(anim);
	}
	public void sendPlayerDiffAnim(Player player, Animation anim, Animation anim2){
		((IEntity)player).bws$sendEntityDiffAnim(anim, anim2);
	}
	public void stopPlayerAnims(Player player){
		((IEntity)player).bws$getAnimManager().stopAnimation();
	}
	public void sendVMAnim(Player player, Animation anim){
		((IEntity)player).bws$getVMManager().sendAnimation(anim);
	}
	public void sendVMNextAnim(Player player, Animation anim){
		((IEntity)player).bws$getVMManager().nextAnimation(anim);
	}
	public void sendVMDiffAnim(Player player, Animation anim, Animation anim2){
		((IEntity)player).bws$getVMManager().sendDiffAnimation(anim, anim2);
	}

	public void adjustSwingSpeed(Player player, float speed){
		((IPlayer) player).bws$setSwingSpeed(speed);
	}

	public static byte getReinforcement(ItemStack stack){
		return ((IReinforceable)(Object)stack).bws$getReinforcement();
	}

}
