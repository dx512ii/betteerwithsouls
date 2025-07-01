package dxii.betterwithsouls.util;

import net.minecraft.core.util.helper.DamageType;

import java.util.HashMap;
import java.util.Map;

//basically holds some variables so i dont have a bloat of classes and interfaces
public class DamageResistModule {
	//aight, now i kinda understand why some variables get private or protected attribute
	//sometimes you want to access a function but you get a bloat of variables dropped from the context menu
	//thats alright to set a getter or smth

	private Map<DamageType, Float> resists = new HashMap<>();
	private Map<DamageType, Float> weaknesses = new HashMap<>();

	private int PushResist = 1;
	private int poise = 0;

	public void setPushResist(int pushResist) {
		PushResist = pushResist;
	}
	public int getPushResist() {
		return PushResist;
	}

	public void setPoise(int poise) {
		this.poise = poise;
	}
	public int getPoise() {
		return this.poise;
	}

	public static float clamp(float num){
		return num > 0 && num < 0.5 ? 1 : num;
	}

	public void copyWithMultiplier(DamageResistModule module, float mul){
		if(module == null){
			return;
		}

		this.resists = new HashMap<>(module.resists);
		for (DamageType type : resists.keySet()) {
			float def = resists.get(type)*mul;
			resists.put(type, def);
		}
		this.weaknesses = new HashMap<>(module.weaknesses);
		for (DamageType type : weaknesses.keySet()) {
			float weak = weaknesses.get(type)*mul;
			weaknesses.put(type, weak);
		}

	}

	public void setDefence(DamageType type, float def){
		this.resists.put(type, def);
	}

	public float getDefence(DamageType type){
		if(type == null){
			return 0;
		}
		if(this.resists.get(type) == null){
			return 0;
		}
		return this.resists.get(type);
	}

	/**
	 * sets weakness multiplier ( dmg * (this + 1) ), can be set to less than
	 * 0, if you want a defence multiplier
	 */
	public void setWeakness(DamageType type, float weak){
		this.weaknesses.put(type, weak);
	}

	public float getWeakMulAgainstEDamage(DamageType type){
		if(type == null){
			return 0;
		}
		if(this.weaknesses.get(type) == null){
			return 0;
		}

		return this.weaknesses.get(type);
	}

}
