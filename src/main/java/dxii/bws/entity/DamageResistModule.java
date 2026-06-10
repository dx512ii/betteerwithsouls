package dxii.bws.entity;

import net.minecraft.core.util.helper.MathHelper;

import java.util.HashMap;
import java.util.Map;

//basically holds some variables so i dont have a bloat of classes and interfaces
public class DamageResistModule {
	//aight, now i kinda understand why some variables get private or protected attribute
	//sometimes you want to access a function but you get a bloat of variables dropped from the context menu
	//thats alright to set a getter or smth

	private Map<DamageTypeBWS, Float> resists = new HashMap<>();

	private int PushResist = 1;
	private int poiseMax = 0;
	private int poise = 0;
	public String blockSound;

	public void setPushResist(int pushResist) {
		PushResist = pushResist;
	}
	public int getPushResist() {
		return PushResist;
	}

	public int getPoise() {
		return this.poise;
	}
	public int damagePoise(int dmg) {
		if(this.poise - dmg <= 0){
			this.poise = this.poiseMax;

			return 0;
		}

		return this.poise -= dmg;
	}

	public void setMaxPoise(int newPoise) {
		this.poiseMax = newPoise;
	}
	public void setPoise(int newPoise) {
		this.poise = newPoise;
	}

	public void copyWithMultiplier(DamageResistModule module, float mul){
		if(module == null){
			return;
		}
		this.resists = new HashMap<>(module.resists);
		for (DamageTypeBWS type : resists.keySet()) {
			float weak = resists.get(type)*mul;
			resists.put(type, weak);
		}

	}

	public void setResist(DamageTypeBWS type, float weak){
		this.resists.put(type, weak);
	}

	public float getResist(DamageTypeBWS type){
		if(this.resists.get(type) == null){
			return 0;
		}

		return this.resists.get(type);
	}

	public static float calculateDamage(int atk, float def){
		/*
			function calculate_atk_def(atk, def)
			  if def <= 0 then return atk end

			  local dmg = clamp(atk / (def+atk), 0, 1) * atk

			  return math.max(dmg, 1)
			end
		 */

		if(def <= 0){
			return atk;
		}
		float a = (float)atk;

		return Math.max( MathHelper.clamp(a / (def + a), 0, 1) * a, 1 );
	}

}
