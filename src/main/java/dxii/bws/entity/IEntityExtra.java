package dxii.bws.entity;

public interface IEntityExtra {
	void addCondition(EntityCondition cond, float duration, float meta1, float meta2, float meta3);
	void handleConditionExtra(EntityCondition cond);
}
