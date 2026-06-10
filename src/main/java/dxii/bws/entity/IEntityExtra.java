package dxii.bws.entity;

public interface IEntityExtra {
	void addCondition(EntityCondition cond, float duration, float meta1, float meta2, float meta3);
	boolean hasCondition(EntityCondition cond);
	void removeCondition(EntityCondition cond);
	void handleConditionExtra(EntityCondition cond);
}
