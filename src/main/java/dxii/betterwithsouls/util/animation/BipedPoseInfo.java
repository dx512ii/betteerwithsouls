package dxii.betterwithsouls.util.animation;

import dxii.betterwithsouls.interfaces.ICube;
import net.minecraft.client.render.model.Cube;

import java.util.ArrayList;
import java.util.List;

public class BipedPoseInfo {
	public List<BipedLimbInfo> limbs = new ArrayList<>();

	public BipedPoseInfo(List<Cube> cubes){
		for(Cube cube : cubes){
			String name = ((ICube)cube).getName();
			limbs.add(new BipedLimbInfo(name));
		}
		limbs.add(new BipedLimbInfo("weapon"));
	}


	public void reset(){
		for(BipedLimbInfo limb : limbs){
			if(limb != null){
				limb.reset();
			}
		}
	}

	public void dump(){
		System.out.println("POSE DUMP: ");
		System.out.println("-------------------------------------------------");
		System.out.print("new Animation(true)\n" +
			"\t\t\t.withFrame(new Frame(5)");
		System.out.print("\n");
		for(BipedLimbInfo limb : limbs){
			if(limb != null){
				limb.dump();
			}
		}
		System.out.print(")");
		System.out.print("\n");
		System.out.println("-------------------------------------------------");
		System.out.println("POSE DUMP END");
	}

	public BipedLimbInfo getLimbByName(String name){
		for(BipedLimbInfo limb : limbs){
			if(limb != null && limb.name == name){
				return limb;
			}
		}

		return null;
	}

	public void addPosByName(String name, float x, float y, float z){
		getLimbByName(name).addPos(x, y, z);
	}

	public void addRot(String name, float x, float y, float z){
		getLimbByName(name).addRot(x, y, z);
	}
}
