package dxii.betterwithsouls.util;

public class KeyFrame {

	private String limbName;

	private int x;
	private int y;
	private int z;

	private float rotX;
	private float rotY;
	private float rotZ;

	public KeyFrame(String limbName, int x, int y, int z, float rotX, float rotY, float rotZ){
		this.limbName = limbName;

		this.x = x;
		this.y = y;
		this.z = z;

		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

	public void setLimbName(String name){
		this.limbName = name;
	}

	public void setCoords(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
