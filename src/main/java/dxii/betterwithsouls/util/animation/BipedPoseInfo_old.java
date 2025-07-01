package dxii.betterwithsouls.util.animation;


public class BipedPoseInfo_old {
	public BipedLimbInfo head = new BipedLimbInfo();

	public BipedLimbInfo body = new BipedLimbInfo();

	public BipedLimbInfo armR = new BipedLimbInfo();
	public BipedLimbInfo armL = new BipedLimbInfo();

	public BipedLimbInfo legR = new BipedLimbInfo();
	public BipedLimbInfo legL = new BipedLimbInfo();

	public BipedLimbInfo weapon = new BipedLimbInfo();

	public void resetAll(){
		this.head.reset();
		this.body.reset();
		this.armR.reset();
		this.armL.reset();
		this.legR.reset();
		this.legL.reset();

		this.weapon.reset();
	}

	public void dump(){
		System.out.println("POSE DUMP: ");
		System.out.println("-------------------------------------------------");
		this.head.dump();
		this.body.dump();
		this.armL.dump();
		this.armR.dump();
		this.legL.dump();
		this.legR.dump();
		this.weapon.dump();
		System.out.println("-------------------------------------------------");
		System.out.println("POSE DUMP END");
	}

}
