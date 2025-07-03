package dxii.betterwithsouls.util;

public class BlockingModule {
	public byte blockingTicks = 0;
	public DamageResistModule def = new DamageResistModule();
	public String blockSound = "";

	public boolean isBlocking(){
		return this.blockingTicks > 0;
	}
}
