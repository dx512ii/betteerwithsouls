package bws.animations;

import java.text.DecimalFormat;

public class BipedLimbInfo {
	public String name;

	public float posX;
	public float posY;
	public float posZ;

	public float rotX;
	public float rotY;
	public float rotZ;


	public BipedLimbInfo(){}

	public BipedLimbInfo(String name){
		this.name = name;
	}


	public void addPos(float x, float y, float z){
		this.posX += x;
		this.posY += y;
		this.posZ += z;

		normalize();
	}

	public float normalize(float num){
		if(num == -7.4505806E-9){
			num = 0;
		}
		return num;
	}
	public void normalize(){
		this.posX = formatFloat(this.posX);
		this.posY = formatFloat(this.posY);
		this.posZ = formatFloat(this.posZ);
		this.rotX = formatFloat(this.rotX);
		this.rotY = formatFloat(this.rotY);
		this.rotZ = formatFloat(this.rotZ);
	}

	public void addRot(float x, float y, float z){
		this.rotX += x;
		this.rotY += y;
		this.rotZ += z;

		normalize();
	}

	public void reset(){
		this.posX = 0;
		this.posY = 0;
		this.posZ = 0;

		this.rotX = 0;
		this.rotY = 0;
		this.rotZ = 0;
	}

	public static double formatDouble(double num){
		return formatFloat((float) num);
	}

	public static float formatFloat(float num){
		DecimalFormat df = new DecimalFormat("#.##");
		df.setMaximumFractionDigits(3);
		String formatted = df.format(num);
		formatted= formatted.replace(",", ".");
		return Float.parseFloat(formatted);
	}

	public void dump(){
		if(this.posX==0 && this.posY==0 && this.posZ==0 && this.rotX==0 && this.rotY==0 && this.rotZ==0){
			return;
		}

		//.withKey(new Key("weapon", 0.0f, 0.0f, -0.4f, 40.0f, 0.0f, 0.0f, true, true ))

		StringBuilder build = new StringBuilder(1024);
		build.append("\t\t\t\t.withKey(Key.createKey(").append('"').append(this.name).append('"').append(", ");

		boolean loc = hasLoc();
		boolean rot = hasRot();

		build.append(formatFloat(this.posX)).append(", ").append(formatFloat(this.posY)).append(", ").append(formatFloat(this.posZ));
		build.append(", ").append(formatFloat(this.rotX)).append(", ").append(formatFloat(this.rotY)).append(", ").append(formatFloat(this.rotZ));

		build.append(", ").append(loc).append(", ").append(rot);

		build.append(").additiveLocation() )");

		System.out.print(build.toString());
		System.out.print("\n");
	}

	public boolean hasLoc(){
		return this.posX!=0 || this.posY!=0 || this.posZ!=0;
	}
	public boolean hasRot(){
		return this.rotX!=0 || this.rotY!=0 || this.rotZ!=0;
	}
}
