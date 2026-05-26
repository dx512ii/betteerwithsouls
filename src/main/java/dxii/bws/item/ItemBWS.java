package dxii.bws.item;

import dxii.bws.BWS;
import dxii.bws.BWSItems;
import dxii.bws.BWSModels;
import net.minecraft.core.item.Item;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.HalpLibe;

public class ItemBWS extends Item {
	public ItemBWS(@NotNull String translationKey) {
		this(translationKey, BWSItems.next_id());
	}
	public ItemBWS(@NotNull String translationKey, int id) {
		super(translationKey, BWS.MOD_ID+":item/" + ( translationKey.replace(".", "_") ), id);
	}

	public ItemBWS withWeaponModel(){
		return this.withWeaponModel(false);
	}
	public ItemBWS withWeaponModel(boolean usesWorld){
		if(HalpLibe.isClient){
			BWSModels.modelsWeapons.put(this, usesWorld);
		}

		return this;
	}
	public ItemBWS withStandartModel(){
		return withStandartModel(false);
	}
	public ItemBWS withStandartModel(boolean weapon){
		if(HalpLibe.isClient){
			BWSModels.modelsStandart.put(this, weapon);
		}

		return this;
	}
	public ItemBWS withStandartFullbrightModel(){
		return this.withStandartFullbrightModel(false);
	}
	public ItemBWS withStandartFullbrightModel(boolean weapon){
		if(HalpLibe.isClient){
			BWSModels.modelsStandartFullbright.put(this, weapon);
		}

		return this;
	}
}
