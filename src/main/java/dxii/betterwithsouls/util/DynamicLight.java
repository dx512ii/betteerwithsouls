package dxii.betterwithsouls.util;


import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;

public class DynamicLight {
	public boolean removed;
	public static final DynamicLight NULL_LIGHT = new DynamicLight(0, 0, 0, null).setBrightness(0);
	//coords & offsets
	public int x;
	public int xO;
	public int y;
	public int yO;
	public int z;
	public int zO;

	/**
	 *lesser radius - less lag (it updates all blocks in radius)
	 */
	public int radius;

	/**
	 * dont set this more that 31, or it will go out of array bounds and shit itself >=[
	 */
	public int brightness;

	public boolean fading;

	public World world;
	public Entity entity;

	public DynamicLight(int x, int y, int z, World world){
		this.entity = null;
		fading = true;
		this.x = x + xO;
		this.y = y + yO;
		this.z = z + zO;
		this.world = world;
	}

	public DynamicLight(Entity ent, World world){
		this.entity = ent;
		this.x = (int)entity.x + xO;
		this.y = (int)entity.y + yO;
		this.z = (int)entity.z + zO;
		this.world = world;
	}

	public void update(){
		if(this.removed){
			return;
		}
		if(entity != null){
			this.x = (int)entity.x + xO;
			this.y = (int)entity.y + yO;
			this.z = (int)entity.z + zO;

			if(this.entity.xd != 0 ||
			this.entity.yd != 0 ||
			this.entity.zd != 0){
				markBlocksNearby(false);
			}
		}else if(!fading){
			this.remove();
		}else{
			if(this.radius == 0){
				this.remove();
			}else {
				markBlocksNearby(false);
				this.radius--;
			}
		}


	}

	public DynamicLight setBrightness(int brightness){
		this.brightness = brightness;
		return this;
	}

	public void markBlocksNearby(boolean force){
		int rad = this.radius;

		if(this.brightness <= 0 && !force){
			return;
		}

		int plyx = this.x;
		int plyy = this.y;
		int plyz = this.z;

		if(this.entity != null) {
			plyx = (int) this.entity.x;
			plyy = (int) this.entity.y;
			plyz = (int) this.entity.z;
		}

		for(int ix = -rad; ix <= rad; ix++){
			for(int iy = -rad; iy <= rad; iy++){
				for(int iz = -rad; iz <= rad; iz++){
					this.world.markBlockNeedsUpdate(plyx - ix, plyy - iy, plyz - iz);
					this.world.markBlockNeedsUpdate(plyx + ix, plyy + iy, plyz + iz);
				}
			}
		}


	}

	public void remove(){
		markBlocksNearby(false);
		this.removed = true;
	}
}
