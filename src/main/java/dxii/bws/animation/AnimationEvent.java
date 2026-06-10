package dxii.bws.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class AnimationEvent {
	public enum Type{
		/**BASE EVENT
		 * <p>
		 * meta1 - velX
		 * <p>
		 * meta2 - velY
		 * <p>
		 * meta3 - velZ
		 */
		PUSH,
		PUSH_LOOK,
		/**BASE EVENT
		 * <p>
		 * stringMeta - sound name
		 * <p>
		 * meta1 - volume
		 * <p>
		 * meta2 - pitch
		 * <p>
		 * meta3 - pitch variation
		 */
		PLAY_SOUND,
		/**BASE EVENT
		 * <p>
		 * stringMeta - soundName
		 * <p>
		 * meta1 - posX
		 * <p>
		 * meta2 - posY
		 * <p>
		 * meta3 - posZ
		 * <p>
		 * meta4 - volume
		 * <p>
		 * meta5 - pitch mul
		 * <p>
		 * meta6 - pitch variation
		 */
		PLAY_SOUND_POS,
		/**BASE EVENT
		 * * <p>
		 * meta1 - cond enum id
		 * * <p>
		 * meta2 - duration in seconds
		 *
		 */
		ADD_COND,
		// sync body y rot with head
		SYNC_BODY_ROT_Y

	}

	/**
	 * amount of times an event should execute
	 */
	public int executionsAmount;
	public Type type;

	public String stringMeta;

	public float meta1;
	public float meta2;
	public float meta3;

	public float meta4;
	public float meta5;
	public float meta6;

	public float meta7;
	public float meta8;
	public float meta9;

	public AnimationEvent(Type type, int executionsAmount) {
		this(type, executionsAmount, "", 0,0,0, 0,0,0, 0,0,0);
	}

	public AnimationEvent(Type type, int executionsAmount, float meta1, float meta2, String stringMeta) {
		this(type, executionsAmount, stringMeta, meta1, meta2, 1, 1,1,1, 1,1,1);
	}
	public AnimationEvent(Type type, int executionsAmount, float meta1, float meta2, float meta3, String stringMeta) {
		this(type, executionsAmount, stringMeta, meta1, meta2, meta3, 1,1,1, 1,1,1);
	}

	public AnimationEvent(Type type, int executionsAmount, float meta1, float meta2, float meta3, float meta4, float meta5, float meta6, String stringMeta) {
		this(type, executionsAmount, "", meta1, meta2, meta3, meta4, meta5, meta6, 1,1,1);
	}

	public AnimationEvent(Type type, int executionsAmount, String stringMeta, float meta1, float meta2, float meta3, float meta4, float meta5, float meta6, float meta7, float meta8, float meta9){
		this.stringMeta = stringMeta;

		this.meta1 = meta1;
		this.meta2 = meta2;
		this.meta3 = meta3;

		this.type = type;
		this.executionsAmount = executionsAmount;
	}

	/**
	 * intended to be one-time fire reference for some base event
	 */
	public static class Scheduled{
		/**
		 * base event reference
		 */
		public AnimationEvent eventRef;
		/**
		 * time at which the event will start firing until its ticks count is depleted
		 */
		public float timeScheduled;
		public float ticks;

		public boolean removed(){
			return this.ticks <= 0;
		}

		public Scheduled(AnimationEvent eventRef, float timeScheduled, int ticks){
			this.eventRef = eventRef;
			this.timeScheduled = timeScheduled;
			this.ticks = ticks;
		}
	}

}
