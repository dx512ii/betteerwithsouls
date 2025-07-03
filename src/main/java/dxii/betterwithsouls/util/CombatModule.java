package dxii.betterwithsouls.util;

import dxii.betterwithsouls.item.ItemWeapon;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class CombatModule {

	public PlayerLocal player;
	public World world;

	public ItemWeapon currentWeapon;
	public ItemStack currentStack;
	public int currentAtkType;

	public int hitTiming = -1;
	public int lastHitTiming = -1;
	public int hitDelay = -1;



	public CombatModule(PlayerLocal player){
		if(player != null && player.world != null) {
			this.world = player.world;
			this.player = player;
		}
	}

	public void respawn(World world, PlayerLocal newplayer){
		this.world = world;
		this.player = newplayer;
	}

	boolean firstTick = true;
	public void update(){
		if(firstTick && (this.player == null || this.world == null)) {
			System.out.println("Combat Module: error!! either player or world is null!!");
			firstTick = false;
			return;
		}

		if(this.hitDelay != -1){
			this.hitDelay--;
		}

		if(this.hitDelay < -1){
			this.hitDelay = -1;
		}

		if(this.hitTiming != -1){
			this.hitTiming--;
		}if(this.hitTiming < -1){
			this.hitTiming = -1;
		}

		if(this.hitTiming == 0){
			switch(currentAtkType) {
				case 0:
					currentWeapon.attack1(this.currentStack, this.world, this.player, true);
					break;
				case 1:
					currentWeapon.attack2(this.currentStack, this.world, this.player, true);
					break;
				case 2:
					currentWeapon.attack3(this.currentStack, this.world, this.player, true);
					break;
			}
		}

	}

	public void attackAttempt(int type, ItemWeapon wep){
		if( isAtacking() ){
			return;
		}
		this.currentWeapon = wep;
		this.currentStack = this.player.getHeldItem();
		this.currentAtkType = type;
		switch(type) {
			case 0:
				this.hitTiming = wep.atkTiming1;
				this.lastHitTiming = wep.atkTiming1;
				this.hitDelay = wep.atkDelay1;
				if(lastHitTiming > 0 || wep.atkTiming1 == 0) {
					wep.attack1(this.currentStack, this.world, this.player, false);
				}

				break;
			case 1:
				this.hitTiming = wep.atkTiming2;
				this.lastHitTiming = wep.atkTiming2;
				this.hitDelay = wep.atkDelay2;
				if(lastHitTiming > 0 || wep.atkTiming2 == 0) {
					wep.attack2(this.currentStack, this.world, this.player, false);
				}
				break;
			case 2:
				this.hitTiming = wep.atkTiming3;
				this.lastHitTiming = wep.atkTiming3;
				this.hitDelay = wep.atkDelay3;
				if(lastHitTiming > 0 || wep.atkTiming3 == 0) {
					wep.attack3(this.currentStack, this.world, this.player, false);
				}
				break;
		}
	}

	public boolean isAtacking(){
		return this.hitDelay != -1;
	}

	public boolean canAttack(){
		return this.hitDelay == -1;
	}
}
