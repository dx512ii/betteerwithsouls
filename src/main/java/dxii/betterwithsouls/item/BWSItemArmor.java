package dxii.betterwithsouls.item;

import dxii.betterwithsouls.item.armor.BWSArmorMaterial;
import dxii.betterwithsouls.util.BWSDamageTypes;
import dxii.betterwithsouls.util.DamageResistModule;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DamageType;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

public class BWSItemArmor extends BWSModItem  implements IArmorItem {
	public BWSItemArmor(String name, BWSArmorMaterial material, int id, int armorPiece) {
		super(name, id);
		this.armorPiece = armorPiece;

		this.material = material;
		this.texture = material.texture;
		this.tooltip = material.tooltip;
		this.resists.copyWithMultiplier(material.resists, BWSArmorMaterial.ARMOR_PIECE_RESIST_MODIFIERS[armorPiece]);

		this.weight = BWSArmorMaterial.ARMOR_PIECE_RESIST_MODIFIERS[armorPiece]*material.weight;
		this.weight = (float) (Math.round(weight * 1E4) / 1E4);

		this.setMaxDamage((int)(ARMOR_PIECE_DURABILITY_MODIFIERS[armorPiece] * material.durability));
	}

	public DamageResistModule resists = new DamageResistModule();
	public int armorPiece;

	public float weight;

	public String texture;
	public BWSArmorMaterial material;
	public String tooltip;


	@Override
	public String getTranslatedDescription(ItemStack itemstack) {
		StringBuilder tooltip = new StringBuilder();
		tooltip.append("\n§nweight:=").append(this.weight+"\n ");

		tooltip.append("§8").append(I18n.getInstance().translateDescKey(this.tooltip)).append("\n");
		tooltip.append("§5§ndefence:").append("\n");
		for(DamageType type : BWSDamageTypes.values){
			if(this.resists.getDefence(type) != 0) {
				float value = this.resists.getDefence(type);
				value = (float) (Math.round(value * 1E4) / 1E4);

				tooltip.append(I18n.getInstance().translateDescKey(type.getLanguageKey()))
					.append(":=").append("§8").append(value)
					.append("\n");
			}
		}
		tooltip.append("§e§nweakness:").append("\n");
		for(DamageType type : BWSDamageTypes.values){
			if(this.resists.getWeakMulAgainstEDamage(type) != 0) {
				float value = this.resists.getWeakMulAgainstEDamage(type);
				value = (float) (Math.round(value * 1E4) / 1E4);

				tooltip.append(I18n.getInstance().translateDescKey(type.getLanguageKey()))
					.append(":=").append("§8").append(value)
					.append("\n");
			}
		}

		return super.getTranslatedDescription(itemstack)+tooltip;
	}

	@Override
	public @Nullable ArmorMaterial getArmorMaterial() {
		return null;
	}

	@Override
	public int getArmorPiece() {
		return this.armorPiece;
	}


}
