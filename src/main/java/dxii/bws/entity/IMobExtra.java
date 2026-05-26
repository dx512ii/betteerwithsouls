package dxii.bws.entity;

public interface IMobExtra {
	void takeDamageInfo(DamageInfo dinfo);
	void setResistAgainst(DamageTypeBWS dtype, float def);
	float getResistAgainst(DamageTypeBWS dtype);
}
