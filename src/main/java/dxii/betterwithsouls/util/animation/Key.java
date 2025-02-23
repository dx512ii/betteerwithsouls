package dxii.betterwithsouls.util.animation;

public class Key {

	public static Key NULL_KEY = new Key("null", 0, 0, 0, 0,0, 0, false, false);

	public String limbName;

	public boolean additiveLoc = false;
	public boolean additiveRot = false;
	public boolean location;
	public boolean rotation;

	public float x;
	public float y;
	public float z;

	public float rotX;
	public float rotY;
	public float rotZ;

	public Key(String limbName, float x, float y, float z, float rotX, float rotY, float rotZ, boolean location, boolean rotation){
		this.limbName = limbName;

		this.location = location;
		this.rotation = rotation;

		this.x = x;
		this.y = y;
		this.z = z;

		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}


	public Key additiveRotation(){
		this.additiveRot = true;
		return this;
	}

	public Key additiveLocation(){
		this.additiveLoc = true;
		return this;
	}
}
