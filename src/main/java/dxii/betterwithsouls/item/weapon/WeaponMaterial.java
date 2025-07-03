package dxii.betterwithsouls.item.weapon;

public class WeaponMaterial {

	public static final WeaponMaterial WOOD = new WeaponMaterial()
		.withDurability(128)
		.withAttackSound("shield.swing")
		.withWooshSound("shield.swing")
		.withBlockSound("block.wood")
		.withHitSoundWorld("block.wood")
		;
	public static final WeaponMaterial STONE = new WeaponMaterial()
		.withDurability(384)
		.withAttackSound("shield.swing")
		.withWooshSound("shield.swing")
		.withBlockSound("block.blade3")
		.withHitSoundWorld("block.blade3")
		;
	public static final WeaponMaterial IRON = new WeaponMaterial()
		.withDurability(800)
		.withAttackSound("sword.swing")
		.withWooshSound("shield.swing")
		.withBlockSound("block.blade3")
		.withHitSoundWorld("block.blade3")
		;


	public int durability;
	public String attackSound = "";
	public String wooshSound = "";
	public String blockSound = "";
	public String hitSoundWorld = "";

	public WeaponMaterial withDurability(int dur){
		this.durability = dur;

		return this;
	}

	public WeaponMaterial withAttackSound(String snd){
		this.attackSound = snd;
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
	public WeaponMaterial withHitSoundWorld(String snd){
		this.hitSoundWorld = snd;
		return this;
	}
}
