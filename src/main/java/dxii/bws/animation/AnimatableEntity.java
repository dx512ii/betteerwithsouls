package dxii.bws.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface AnimatableEntity {
	Animation getCurrentAnimation();
	float getLastAnimSent();
	void setLastAnimSent(float newLast);
	void sendAnimation(Animation animation, boolean keepOnEqual);
	void sendNextAnimation(Animation animation);
	boolean isAnimating();
}
