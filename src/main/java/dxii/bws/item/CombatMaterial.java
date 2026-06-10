package dxii.bws.item;

public class CombatMaterial {
	public String impactSound;
	public float pitch;

	public CombatMaterial(String impactSound, float pitch){
		this.impactSound = impactSound;
		this.pitch = pitch;
	}

	public static final CombatMaterial WOOD_LIGHT = new CombatMaterial("minecraft:step.wood", 1f);
}
