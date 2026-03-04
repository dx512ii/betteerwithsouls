package bws.animations;

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

	public static Key createEmptyKey(String limbName){
		return new Key(limbName, 0, 0, 0, 0, 0, 0, true, true).additiveLocation();
	}
	public static Key createKey(String limbName, double x, double y, double z, double rotX, double rotY, double rotZ, boolean location, boolean rotation){
		return new Key(limbName, (float)x, (float)y, (float)z, (float)rotX, (float)rotY, (float)rotZ, location, rotation);
	}
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

	public Key(String limbName, float x, float y, float z, boolean location){
		this.limbName = limbName;

		this.location = location;
		this.rotation = false;

		this.x = x;
		this.y = y;
		this.z = z;

		this.rotX = 0;
		this.rotY = 0;
		this.rotZ = 0;
	}

	public Key additiveRotation(){
		this.additiveRot = true;
		return this;
	}

	public Key additiveLocation(){
		this.additiveLoc = true;
		return this;
	}

	public boolean hasLoc(){
		return this.x!=0 || this.y!=0 || this.z!=0;
	}
	public boolean hasRot(){
		return this.rotX!=0 || this.rotY!=0 || this.rotZ!=0;
	}
}
