package dxii.bws.entity;

public enum EntityCondition {
	NONE,

	INVULN_DODGE,

	/**
	 * constantly moving in the same direction, which is defined by meta 2,3,4
	 */
	DODGING,

	POISON,
	;

	public static class Scheduled {
		EntityCondition cond = EntityCondition.NONE;

		float timeScheduled;
		float duration;

		float meta1;
		float meta2;
		float meta3;

		public Scheduled(EntityCondition cond, float timeScheduled, float duration, float meta1, float meta2, float meta3){
			this.cond = cond;
			this.timeScheduled = timeScheduled;
			this.duration = duration;
			this.meta1 = meta1;
			this.meta2 = meta2;
			this.meta3 = meta3;
		}
	}
}
