package dxii.betterwithsouls.util;

import dxii.betterwithsouls.enums.EDamageType;
import net.minecraft.core.util.helper.DamageType;

//basically holds some variables so i dont have a bloat of classes and interfaces
public class DamageResistModule {
	//aight, now i kinda understand why some variables get private or protected attribute
	//sometimes you want to access a function but you get a bloat of variables dropped from the context menu
	//thats alright to set a getter or smth


	private int PushResist = 1;

	private int ThrustResist = 0;
	private int StrikeResist = 0;
	private int SlashResist = 0;
	private int MagicResist = 0;
	private int PoisonResist = 0;
	private int BloodResist = 0;
	private int LightningResist = 0;
	private int DarkResist = 0;
	private int FireResist = 0;

	private double ThrustWeakness = 0;
	private double StrikeWeakness = 0;
	private double SlashWeakness = 0;
	private double MagicWeakness = 0;
	private double PoisonWeakness = 0;
	private double BloodWeakness= 0;
	private double LightningWeakness = 0;
	private double DarkWeakness = 0;
	private double FireWeakness = 0;

	public void setPushResist(int pushResist) {
		PushResist = pushResist;
	}

	public int getPushResist() {
		return PushResist;
	}

	public void setDefence(DamageType type, int def){
		if (type == BWSDamageTypes.THRUST) {
			this.ThrustResist = def;
		}else if(type == BWSDamageTypes.STRIKE){
			this.StrikeResist = def;
		}else if(type == BWSDamageTypes.SLASH){
			this.SlashResist = def;
		}else if(type == BWSDamageTypes.MAGIC){
			this.MagicResist = def;
		}else if(type == BWSDamageTypes.POISON){
			this.PoisonResist = def;
		}else if(type == BWSDamageTypes.BLOOD){
			this.BloodResist = def;
		}else if(type == BWSDamageTypes.LIGHTNING){
			this.LightningResist = def;
		}else if(type == BWSDamageTypes.DARK){
			this.DarkResist = def;
		}else if(type == BWSDamageTypes.FIRE){
			this.FireResist = def;
		}
	}

	public int getDefence(DamageType type){
		if (type == BWSDamageTypes.THRUST) {
			return this.ThrustResist;
		}else if(type == BWSDamageTypes.STRIKE){
			return this.StrikeResist;
		}else if(type == BWSDamageTypes.SLASH){
			return this.SlashResist;
		}else if(type == BWSDamageTypes.MAGIC){
			return this.MagicResist;
		}else if(type == BWSDamageTypes.POISON){
			return this.PoisonResist;
		}else if(type == BWSDamageTypes.BLOOD){
			return this.BloodResist;
		}else if(type == BWSDamageTypes.LIGHTNING){
			return this.LightningResist;
		}else if(type == BWSDamageTypes.DARK){
			return this.DarkResist;
		}else if(type == BWSDamageTypes.FIRE){
			return this.FireResist;
		}else{
			return 0;
		}
	}

	/**
	 * sets weakness multiplier ( dmg * (this + 1) ), can be set to less than
	 * 0, if you want a defence multiplier
	 */
	public void setWeakness(DamageType type, int weak){
		if (type == BWSDamageTypes.THRUST) {
			this.ThrustWeakness = weak;
		}else if(type == BWSDamageTypes.STRIKE){
			this.StrikeWeakness = weak;
		}else if(type == BWSDamageTypes.SLASH){
			this.SlashWeakness = weak;
		}else if(type == BWSDamageTypes.MAGIC){
			this.MagicWeakness = weak;
		}else if(type == BWSDamageTypes.POISON){
			this.PoisonWeakness = weak;
		}else if(type == BWSDamageTypes.BLOOD){
			this.BloodWeakness = weak;
		}else if(type == BWSDamageTypes.LIGHTNING){
			this.LightningWeakness = weak;
		}else if(type == BWSDamageTypes.DARK){
			this.DarkWeakness = weak;
		}else if(type == BWSDamageTypes.FIRE){
			this.FireWeakness = weak;
		}
	}

	public double getWeakMulAgainstEDamage(DamageType type){
		if (type == BWSDamageTypes.THRUST) {
			return this.ThrustWeakness;
		}else if(type == BWSDamageTypes.STRIKE){
			return this.StrikeWeakness;
		}else if(type == BWSDamageTypes.SLASH){
			return this.SlashWeakness;
		}else if(type == BWSDamageTypes.MAGIC){
			return this.MagicWeakness;
		}else if(type == BWSDamageTypes.POISON){
			return this.PoisonWeakness;
		}else if(type == BWSDamageTypes.BLOOD){
			return this.BloodWeakness;
		}else if(type == BWSDamageTypes.LIGHTNING){
			return this.LightningWeakness;
		}else if(type == BWSDamageTypes.DARK){
			return this.DarkWeakness;
		}else if(type == BWSDamageTypes.FIRE){
			return this.FireWeakness;
		}else{
			return 0;
		}
	}

}
