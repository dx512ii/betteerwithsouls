package dxii.betterwithsouls.anims;

import dxii.betterwithsouls.util.animation.Animation;
import dxii.betterwithsouls.util.animation.Frame;
import dxii.betterwithsouls.util.animation.Key;

public class ViewModelAnimations {
	public static final Animation SHORTSWORD_SWING1 = new Animation(false)
		.withFrame(new Frame(0)
			.withKey(new Key("weapon", .15f, .1f, -0.05f, 20, 10, -85, true, true )))

		.withFrame(new Frame(1)
			.withKey(new Key("weapon", .05f, .05f, -0.85f, 64, 60, -150, true, true )))

		.withFrame(new Frame(1)
			.withKey(new Key("weapon", -.75f, .15f, -0.8f, 20, 120, -125, true, true )))

		.withFrame(new Frame(2)
			.withKey(new Key("weapon", -.35f, -0, -0.3f, -40, 130, -65, true, true )))

		.withFrame(new Frame(4)
			.withKey(new Key("weapon", -0, -0, -0, -0, 0, -0, true, true )))
		;
	public static final Animation SHORTSWORD_SWING2 = new Animation(false)
				.withFrame(new Frame(0)
					.withKey(new Key("weapon", -0.55f, 0, -1.05f, -40.0f, -15.0f, 60.0f, true, true )))
		.withFrame(new Frame(1)
					.withKey(new Key("weapon", -0.05f, 0.1f, -0.6f, -135.0f, -15.0f, -75.0f, true, true )))
		.withFrame(new Frame(1)
					.withKey(new Key("weapon", 0.3f, 0.1f, 0.15f, -140.0f, 20.0f, -115.0f, true, true )))
		.withFrame(new Frame(4)
					.withKey(new Key("weapon", 0, 0, 0, -0, 0, -0, true, true )))

	;
	public static final Animation GENERIC_block = new Animation(false)
		.withFrame(new Frame(0)
			.withKey(Key.createKey("weapon", -.2, 0.0, -.64, -50.0, 80.0, -30.0, true, true) ))
		.withFrame(new Frame(6)
			.withKey(Key.createEmptyKey("weapon") ))
		;

	public static final Animation SHORTSWORD_PARRY = new Animation(false)
		.withFrame(new Frame(0)
			.withKey(new Key("weapon", -0.05f, 0.1f, -0.34f , -80.0f, -5.0f, 45.0f, true, true )))
		.withFrame(new Frame(0)
			.withKey(new Key("weapon", -0.05f, 0.1f, -0.34f , -80.0f, -5.0f, 45.0f, true, true )))
		.withFrame(new Frame(0)
			.withKey(new Key("weapon", 0.1f, 0.65f, -0.2f, -60.0f, -20.0f, 20.0f, true, true )))
		.withFrame(new Frame(1)
			.withKey(new Key("weapon", 0.1f, 0.65f, 0.1f, 5.0f, -25.0f, -15.0f, true, true )))
		.withFrame(new Frame(4)
			.withKey(new Key("weapon", 0, 0, 0, 0, -0, -0, true, true )))

		;

	public static final Animation GREATSWORD_slash = new Animation(false)
		.withFrame(new Frame(0)
			.withKey(Key.createKey("weapon", -0.05, -.1, 0.05, -25.0, 0.0, -20.0, true, true) ))
		.withFrame(new Frame(2)
			.withKey(Key.createKey("weapon", 0.15, .35, 0.0, 35.0, 0.0, 0.0, true, true) ))
		.withFrame(new Frame(1)
			.withKey(Key.createKey("weapon", -.35, .15, -0.5, -70.0, 5.0, -100.0, true, true) ))
		.withFrame(new Frame(1)
			.withKey(Key.createKey("weapon", -.89, -.2, -.8, -125.0, -20.0, -95.0, true, true) ))
		.withFrame(new Frame(5)
			.withKey(Key.createKey("weapon", -.35, -.1, -.3, -80.0, 0.0, -45.0, true, true) ))
		.withFrame(new Frame(4)
			.withKey(Key.createEmptyKey("weapon") ))
		;
	public static final Animation GREATSWORD_thrust = new Animation(false)
		.withFrame(new Frame(0)
			.withKey(Key.createKey("weapon", -0, -0, 0, -0, 0.0, -0, true, true) ))
		.withFrame(new Frame(2)
			.withKey(Key.createKey("weapon", -.1, -.4, 0.25, -45.0, -40.0, -70.0, true, true) ))
		.withFrame(new Frame(1)
			.withKey(Key.createKey("weapon", 0.5, -.1, -.85, -100.0, -45.0, 20.0, true, true) ))
		.withFrame(new Frame(3)
			.withKey(Key.createKey("weapon", 0.25, -0.0, -.9, -145.0, -40.0, 70.0, true, true) ))
		.withFrame(new Frame(6)
			.withKey(Key.createKey("weapon", .35, -0.0, -.3, -145.0, -30.0, 70.0, true, true) ))
		.withFrame(new Frame(5)
			.withKey(Key.createEmptyKey("weapon") ))
		;

}
