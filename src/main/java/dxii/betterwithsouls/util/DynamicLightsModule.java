package dxii.betterwithsouls.util;

import dxii.betterwithsouls.BWSMain;
import dxii.betterwithsouls.BWSUtils;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class DynamicLightsModule {

	public List<DynamicLight> lights = new ArrayList();
	private int updDelayTemp;


	public void update(){
		if(!BWSMain.dynLightEnabled.value){
			for (DynamicLight light : lights) {
				if (light == null || light.removed) {
					lights.remove(light);
					return;
				} else {
					light.update();
				}
			}

			return;
		}

		if(updDelayTemp != 0){
			updDelayTemp--;
		}
		if(updDelayTemp == 0) {
			updDelayTemp = BWSMain.dynLightRate.getValueIndex()+1;
			for (DynamicLight light : lights) {
				if (light == null || light.removed) {
					lights.remove(light);
					return;
				} else {
					light.update();
				}
			}
		}
	}

	public void addLight(DynamicLight dyn){
		lights.add(dyn);
	}

	public void modifyPlayerLight(int radius, int brightness){
		DynamicLight dyn = lights.get(0);
		dyn.radius = radius;
		dyn.brightness = brightness;
	}

	public float getBrightness(int x, int y, int z){
		if(!BWSMain.dynLightEnabled.value){
			return 0;
		}
		float finalBright = 0;

		for(DynamicLight light : lights){
			double brightness = light.brightness;

			if(brightness == 0){
				continue;
			}

			double rad = light.radius;
			double radsqr = rad*rad;
			double dist;
			Vec3 vec1 = Vec3.getPermanentVec3(light.x, light.y, light.z);
			Vec3 vec2 = Vec3.getPermanentVec3(x, y, z);
			dist = radsqr - MathHelper.clamp(BWSUtils.vecDistSquared(vec1, vec2), 0, radsqr);
			finalBright = Math.max((float) ((dist / radsqr) * brightness), finalBright);
		}

		return finalBright;
	}

}
