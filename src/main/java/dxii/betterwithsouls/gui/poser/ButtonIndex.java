package dxii.betterwithsouls.gui.poser;

import dxii.betterwithsouls.gui.ScreenPoser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;

public class ButtonIndex extends ButtonElement {

	public ScreenPoser parent;
	public int index = 0;

	public ButtonIndex(ScreenPoser parent, int id, int xPosition, int yPosition, int width, int height, String text) {
		super(id, xPosition, yPosition, width, height, text);
		this.parent = parent;
	}

	@Override
	public boolean mouseClicked(Minecraft mc, int mouseX, int mouseY) {
		return super.mouseClicked(mc, mouseX, mouseY);
	}
}
