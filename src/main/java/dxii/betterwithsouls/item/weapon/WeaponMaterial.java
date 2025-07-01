package dxii.betterwithsouls.item.weapon;

public class WeaponMaterial {

	public static final WeaponMaterial WOOD = new WeaponMaterial()
		.withDurability(64)
		.withSwingSound("sword.swing")
		.withWooshSound("shield.swing")
		.withBlockSound("block.blade3")
		;
	public static final WeaponMaterial STONE = new WeaponMaterial()
		.withDurability(128)
		.withSwingSound("sword.swing")
		.withWooshSound("shield.swing")
		.withBlockSound("block.blade3")
		;
	public static final WeaponMaterial IRON = new WeaponMaterial()
		.withDurability(384)
		.withSwingSound("sword.swing")
		.withWooshSound("shield.swing")
		.withBlockSound("block.blade3")
		;


	public int durability;
	public String swingSound;
	public String wooshSound;
	public String blockSound;

	public WeaponMaterial withDurability(int dur){
		this.durability = dur;
		return this;
	}

	public WeaponMaterial withSwingSound(String snd){
		this.swingSound = snd;
		return this;
	}
	public WeaponMaterial withWooshSound(String snd){
		this.wooshSound = snd;
		return this;
	}
	public WeaponMaterial withBlockSound(String snd){
		this.blockSound = snd;
		return this;
	}
}
