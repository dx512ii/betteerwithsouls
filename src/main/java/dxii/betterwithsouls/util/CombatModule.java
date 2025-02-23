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
	public int currentHoldType;

	public int hitTiming = -1;
	public int lastHitTiming = -1;
	public int hitDelay = -1;

	public int totalHoldTicks;

	public boolean holding;


	//public Vec3 oldLightBlockPos;

	public CombatModule(World world, PlayerLocal player){
		this.world = world;
		this.player = player;
	}


//	public void updateDynamicLight(){
//		if(world != null && player != null) {
//			int posx = (int) (player.x-1);
//			int posy = (int) (player.y);
//			int posz = (int) (player.z);
//
//			//System.out.println(oldLightBlockPos);
//
//			if (world.getBlockMaterial(posx, posy, posz) == Material.air) {
//				if(oldLightBlockPos != null){
//					world.setBlock((int) oldLightBlockPos.x, (int) oldLightBlockPos.y, (int) oldLightBlockPos.z, 0);
//				}else{
//					oldLightBlockPos = Vec3.getPermanentVec3(posx,posy, posz);
//				}
//				//world.setBlock(posx, posy, posz, Blocks.PUMPKIN_CARVED_ACTIVE.id());
//				world.setBlock(posx, posy, posz, BWSBlocks.dynamiclight_block.id());
//				oldLightBlockPos = Vec3.getPermanentVec3(posx,posy, posz);
//			}else{
//				world.setBlock((int) oldLightBlockPos.x, (int) oldLightBlockPos.y, (int) oldLightBlockPos.z, 0);
//			}
//		}
//	}

//	public int dynLightDelay;
//
//	public void updateBlocksForLightning(){
//		int rad = BWSConfig.playerLightRad;
//		int plyx = (int)this.player.x;
//		int plyy = (int)this.player.y;
//		int plyz = (int)this.player.z;
//
//
//		for(int x = -rad; x <= rad; x++){
//			for(int y = -rad; y <= rad; y++){
//				for(int z = -rad; z <= rad; z++){
//					this.world.markBlockNeedsUpdate(plyx - x, plyy - y, plyz - z);
//					this.world.markBlockNeedsUpdate(plyx + x, plyy + y, plyz + z);
//				}
//			}
//		}
//
//	}

	public void update(){
		if(this.player == null || this.world == null) {
			System.out.println("Combat Module: error!! either player or world is null!!");
			return;
		}
//
//		if(dynLightDelay != 0){
//			dynLightDelay--;
//		}
//		if(dynLightDelay == 0){
//			updateBlocksForLightning();
//			dynLightDelay = 1;
//		}

//		if(this.player != null && this.player.getPlayerSpawnCoordinate() != null) {
//			Vec3 vec1 = Vec3.getPermanentVec3(this.player.x, this.player.y, this.player.z);
//			Vec3 vec2 = Vec3.getPermanentVec3(
//				this.player.getPlayerSpawnCoordinate().x,
//				this.player.getPlayerSpawnCoordinate().y,
//				this.player.getPlayerSpawnCoordinate().z
//			);
//
//			int dist = (int) BWSUtils.vecDist(vec1, vec2);
//			System.out.println(dist);
//		}

		//this.worldObj.markBlockNeedsUpdate(x, y, z);
		if(this.hitDelay != -1){
			this.hitDelay--;
		}if(this.hitDelay < -1){
			this.hitDelay = -1;
		}

		if(this.hitTiming != -1){
			this.hitTiming--;
		}if(this.hitTiming < -1){
			this.hitTiming = -1;
		}

		if(!this.holding && (this.hitTiming == 0 || this.lastHitTiming == 0)){
			this.hitTiming = -1;
			this.lastHitTiming = -1;
			switch(currentAtkType) {
				case 0:
					currentWeapon.attack1(currentStack, this.world, this.player, true);
					if(currentWeapon.atk1hold){
						holding = false;
					}
					break;
				case 1:
					currentWeapon.attack2(currentStack, this.world, this.player, true);
					if(currentWeapon.atk2hold){
						holding = false;
					}
					break;
				case 2:
					currentWeapon.attack3(currentStack, this.world, this.player, true);
					if(currentWeapon.atk3hold){
						holding = false;
					}
					break;
			}
		}

		if(this.holding && this.currentHoldType != -1){
			this.totalHoldTicks++;
			switch(this.currentHoldType) {
				case 0:
					currentWeapon.hold1(currentStack, this.world, this.player, this.totalHoldTicks);
					break;
				case 1:
					currentWeapon.hold2(currentStack, this.world, this.player, this.totalHoldTicks);
					break;
				case 2:
					currentWeapon.hold3(currentStack, this.world, this.player, this.totalHoldTicks);
					break;
			}
		}
	}

	public void attackAttempt(int type, ItemWeapon wep, ItemStack stack){
		if( isAtacking() ){
			return;
		}

		this.currentWeapon = wep;
		this.currentStack = stack;
		this.currentAtkType = type;
		switch(type) {
			case 0:
				this.hitTiming = wep.atkTiming1;
				this.lastHitTiming = wep.atkTiming1;
				this.hitDelay = wep.atkDelay1;
				//System.out.println(this.hitDelay);
				if(lastHitTiming > 0) {
					wep.attack1(stack, this.world, this.player, false);
				}

				break;
			case 1:
				this.hitTiming = wep.atkTiming2;
				this.lastHitTiming = wep.atkTiming2;
				this.hitDelay = wep.atkDelay2;
				if(lastHitTiming > 0) {
					wep.attack2(stack, this.world, this.player, false);
				}
				break;
			case 2:
				this.hitTiming = wep.atkTiming3;
				this.lastHitTiming = wep.atkTiming3;
				this.hitDelay = wep.atkDelay3;
				if(lastHitTiming > 0) {
					wep.attack3(stack, this.world, this.player, false);
				}
				break;
		}
	}

	public void holdAttempt(int type, ItemWeapon wep, ItemStack stack){
		if(this.holding || isAtacking()){
			return;
		}
		this.currentWeapon = wep;
		this.currentStack = stack;
		this.currentHoldType = type;
		this.holding = true;



		switch(this.currentHoldType) {
			case 0:
				this.currentWeapon.hold1start(this.currentStack, this.world, this.player);
				this.hitDelay = wep.atkDelay1;
				break;
			case 1:
				this.currentWeapon.hold2start(this.currentStack, this.world, this.player);
				this.hitDelay = wep.atkDelay1;
				break;
			case 2:
				this.currentWeapon.hold3start(this.currentStack, this.world, this.player);
				this.hitDelay = wep.atkDelay1;
				break;
		}
	}



	public void holdRelease(){
		if(isAtacking()){
			return;
		}

		switch(this.currentHoldType) {
			case 0:
				attackAttempt(0, currentWeapon, currentStack);
				break;
			case 1:
				attackAttempt(1, currentWeapon, currentStack);
				break;
			case 2:
				attackAttempt(2, currentWeapon, currentStack);
				break;
		}
		this.holding = false;
		this.totalHoldTicks = 0;
	}

	public boolean isAtacking(){
		return this.hitDelay != -1;
	}

	public boolean canInteract(){
		//System.out.println("sas");
		return this.hitDelay == -1 && !this.holding;
	}
}
