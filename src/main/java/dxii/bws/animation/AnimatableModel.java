package dxii.bws.animation;

import java.util.List;

public interface AnimatableModel {
	List<String> getAnimatableBones();
	void addBoneToAnimate(String name);
}
