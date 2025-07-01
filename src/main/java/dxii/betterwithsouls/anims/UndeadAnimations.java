package dxii.betterwithsouls.anims;

import dxii.betterwithsouls.util.animation.Animation;
import dxii.betterwithsouls.util.animation.Frame;
import dxii.betterwithsouls.util.animation.Key;

public class UndeadAnimations {

	public static final Animation zweiIdle = new Animation(true)
		.withFrame(new Frame(0)
			.withKey(Key.createKey("body", 0.0, 0.0, 0.05, -5.0, 0.0, 0.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.0, 0.0, 0.0, -55.0, -20.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armL", -0.1, 0.0, -0.15, -80.0, 60.0, 0.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.0, -0.05, 0.15, -35.0, 0.0, 0.0, true, true).additiveLocation() )
		);

	public static final Animation shortswordIdle = new Animation(true)
		.withFrame(new Frame(15)

			.withKey(new Key("head", 0.0f, 0.0f, -0.05f, 0.0f, 0.0f, 0.0f, true, false).additiveLocation() )
			.withKey(new Key("body", 0.0f, 0.0f, -0.05f, 5f, 3f, 0.0f, true, true).additiveLocation() )
			.withKey(new Key("armL", 0.0f, 0, 0f, -15.0f, -5.0f, -10.0f, true, true).additiveRotation().additiveLocation() )
			.withKey(new Key("armR", 0.0f, 0, 0f, -10.0f, 10.0f, 10.0f, true, true).additiveLocation() )

		).withFrame(new Frame(35)

			.withKey(new Key("head", 0.0f, 0.0f, -0.05f, 0.0f, 0.0f, 0.0f, true, false).additiveLocation() )
			.withKey(new Key("body", 0.0f, 0.0f, -0.05f, 5f, 4.0f, 0.0f, true, true).additiveLocation() )
			.withKey(new Key("armL", 0.0f, 0, 0f, -15.0f, -5.0f, -10.0f, true, true).additiveRotation().additiveLocation() )
			.withKey(new Key("armR", 0.0f, 0, 0f, -10.0f, 9, 10, true, true).additiveLocation() )

		).withFrame(new Frame(24)

			.withKey(new Key("head", 0.0f, 0.0f, -0.05f, 0.0f, 0.0f, 0.0f, true, false).additiveLocation() )
			.withKey(new Key("body", 0.0f, 0.0f, -0.05f, 5, 4.0f, 0.0f, true, true).additiveLocation() )
			.withKey(new Key("armL", 0.0f, 0, 0f, -15.0f, -5.0f, -10.0f, true, true).additiveRotation().additiveLocation() )
			.withKey(new Key("armR", 0.0f, 0, 0f, -10.0f, 9, 10, true, true).additiveLocation() )

		).withFrame(new Frame(15)

			.withKey(new Key("head", 0.0f, 0.0f, -0.05f, 0.0f, 0.0f, 0.0f, true, false).additiveLocation() )
			.withKey(new Key("body", 0.0f, 0.0f, -0.05f, 5.0f, 5.0f, 0.0f, true, true).additiveLocation() )
			.withKey(new Key("armL", 0.0f, 0, 0f, -15.0f, -5.0f, -10.0f, true, true).additiveRotation().additiveLocation() )
			.withKey(new Key("armR", 0.0f, 0, 0f, -10.0f, 10.0f, 10.0f, true, true).additiveLocation() )

		);

	public static final Animation shortswordAttack = new Animation(false)
		.withFrame(new Frame(3)

			.withKey(new Key("body", 0.0f, 0.0f, 0.05f, -5.0f, 10.0f, 0.0f, true, true) )
			.withKey(new Key("armL", -0.05f, 0.05f, -0.15f, -50.0f, 30.0f, 0.0f, true, true).additiveLocation() )
			.withKey(new Key("armR", 0.0f, 0.0f, 0.015f, -155.0f, 20.0f, -10.0f, true, true).additiveLocation() )
			.withKey(new Key("weapon", 0.0f, 0.0f, -0.3f, 20.0f, 0.0f, 0.0f, true, true) )

		).withFrame(new Frame(5)

			.withKey(Key.createKey("head", 0.0, 0.0, 0.1, 0.0, 0.0, 10.0, true, true).additiveLocation() )
			.withKey(Key.createKey("body", 0.05, 0.0, 0.1, -10.0, 10.0, 0.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armL", 0.0, 0.0, 0.0, -70.0, 35.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.05, -.1, 0.15, -190.0, 25.0, 0.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.0, 0.2, -0.4, 25.0, 0.0, 0.0, true, true).additiveLocation() )

		).withFrame(new Frame(1)

			.withKey(Key.createKey("head", 0.05, 0.0, -.1, 10.0, 0.0, -5.0, true, true).additiveRotation() )
			.withKey(Key.createKey("body", 0.05, 0.0, -0.1, 10.0, -15.0, 0.0, true, true) )
			.withKey(Key.createKey("armL", 0.0, 0.0, 0.0, 35.0, -10.0, -5.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.15, 0.05, -0.3, -45.0, -10.0, -25.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.0, 0.3, -0.5, 25.0, 0.0, 0.0, true, true) )

		).withFrame(new Frame(3)

			.withKey(Key.createKey("head", 0.1, 0.05, -0.2, 35.0, -5.0, 15.0, true, true).additiveLocation() )
			.withKey(Key.createKey("body", 0.1, 0.05, -0.25, 20.0, -15.0, 5.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armL", 0.0, 0.0, 0.0, 30.0, -25.0, -5.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.15, 0.05, -0.3, 10.0, 0.0, -25.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.1, .1, -0.5, 20.0, 0.0, 0.0, true, true).additiveLocation() )

		).withFrame(new Frame(4)

			.withKey(Key.createKey("head", 0.15, 0.1, -0.35, 35.0, -5.0, 15.0, true, true).additiveLocation() )
			.withKey(Key.createKey("body", 0.15, 0.1, -0.35, 25.0, -15.0, 5.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armL", 0.1, 0.1, -0.2, 35.0, -25.0, -10.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.2, 0.15, -0.45, -10.0, 0.0, -30.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.1, 0.3, -0.7, 20.0, 0.0, 0.0, true, true).additiveLocation() )

		).withFrame( shortswordIdle.firstFrame() );

	public static final Animation stun = new Animation(false)
		.withFrame(new Frame(0)

			.withKey(Key.createKey("armL", 0.0, 0.0, 0.0, 5.0, 0.0, -15.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.0, 0.0, 0.0, 0.0, 15.0, 15.0, false, true).additiveLocation() )

		).withFrame(new Frame(5)

			.withKey(Key.createKey("head", 0.0, 0.0, 0.0, 35.0, 15.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("body", 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armL", 0.0, 0.0, -0.05, -5.0, 0.0, -5.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.0, 0.0, 0.05, 30.0, 15.0, 15.0, true, true).additiveLocation() )

		).withFrame(new Frame(4)

			.withKey(Key.createKey("head", 0.0, 0.0, 0.0, 45.0, 5.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armL", 0.0, 0.0, 0.0, 5.0, 0.0, -5.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.0, 0.0, 0.0, 15.0, 10.0, 0.0, false, true).additiveLocation() )
		).withFrame(shortswordIdle.firstFrame().setFrameTime(4));

	public static final Animation parried = new Animation(false)
		.withFrame(new Frame(1)

			.withKey(Key.createKey("head", 0.0, 0.0, 0.0, 20.0, -5.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("body", 0.0, 0.0, 0.0, 0.0, -10.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armL", -0.05, 0.0, .1, 15.0, -20.0, 0.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.05, 0.05, -.1, -45.0, -10.0, -15.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.0, 0.0, -0.3, 25.0, 0.0, 0.0, true, true).additiveLocation() )

		).withFrame(new Frame(3)

			.withKey(Key.createKey("head", 0.0, 0.0, 0.0, 30.0, 10.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("body", 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armL", 0.0, 0.05, -0.05, 20.0, -10.0, -10.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.05, 0.0, 0, -150.0, 35.0, -10.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.1, 0.0, -0.3, 25.0, 0.0, 0.0, true, true).additiveLocation() )

		).withFrame(new Frame(3)

			.withKey(Key.createKey("head", 0.0, 0.0, 0.0, 35.0, 10.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("body", 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, false, true).additiveLocation() )
			.withKey(Key.createKey("armL", 0.0, 0.05, -0.05, 0.0, -10.0, -10.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.05, 0.0, 0, -65.0, 110.0, -10.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.1, 0.0, -0.3, 25.0, 0.0, 0.0, true, true).additiveLocation() )

		).withFrame(new Frame(8)

			.withKey(Key.createKey("head", 0.0, 0.0, -0.05, 0, 10.0, 0.0, true, true).additiveLocation() )
			.withKey(Key.createKey("body", 0.0, 0.0, -0.05, 5.0, 0.0, 0.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armL", 0.0, 0.05, -0.05, 0.0, -10.0, -10.0, true, true).additiveLocation() )
			.withKey(Key.createKey("armR", 0.0, 0.05, -0.05, -25.0, 40.0, -5.0, true, true).additiveLocation() )
			.withKey(Key.createKey("weapon", 0.0, 0.1, -0.3, 25.0, 0.0, 0.0, true, true).additiveLocation() )

		).withFrame(shortswordIdle.firstFrame().setFrameTime(13));

}
