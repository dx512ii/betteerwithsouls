package dxii.bws.entity;

import java.util.HashMap;
import java.util.Map;

//basically holds some variables so i dont have a bloat of classes and interfaces
public class DamageResistModule {
	//aight, now i kinda understand why some variables get private or protected attribute
	//sometimes you want to access a function but you get a bloat of variables dropped from the context menu
	//thats alright to set a getter or smth

	private Map<DamageTypeBWS, Float> weaknesses = new HashMap<>();

	private int PushResist = 1;
	private int poise = 0;

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
		return this.poise -= dmg;
	}

	public void setPoise(int newPoise) {
		this.poise = newPoise;
	}

	public void copyWithMultiplier(DamageResistModule module, float mul){
		if(module == null){
			return;
		}
		this.weaknesses = new HashMap<>(module.weaknesses);
		for (DamageTypeBWS type : weaknesses.keySet()) {
			float weak = weaknesses.get(type)*mul;
			weaknesses.put(type, weak);
		}

	}

	/**
	 * < 0 - resist
	 * <p>
	 * > 0 - weakness
	 */
	public void setResist(DamageTypeBWS type, float weak){
		this.weaknesses.put(type, weak);
	}

	public float getResist(DamageTypeBWS type){
		if(this.weaknesses.get(type) == null){
			return 0;
		}

		return this.weaknesses.get(type);
	}

}
