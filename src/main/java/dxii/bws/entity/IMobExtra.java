package dxii.bws.entity;

import org.jetbrains.annotations.Nullable;

public interface IMobExtra {
	void takeDamageInfo(DamageInfo dinfo);
	void setResistAgainst(DamageTypeBWS dtype, float def);
	float getResistAgainst(DamageTypeBWS dtype);

	void setMaxPoise(int newPoise);
	void setPoise(int newPoise);

	void raiseBlock(DamageResistModule resists, float duration);
	void raiseBlock(DamageResistModule resists, float duration, @Nullable String sound);
	DamageResistModule getBlocking();
}
