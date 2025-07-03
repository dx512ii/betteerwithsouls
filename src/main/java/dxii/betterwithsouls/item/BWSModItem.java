package dxii.betterwithsouls.item;

import dxii.betterwithsouls.BWSItems;
import dxii.betterwithsouls.BWSRecipes;
import dxii.betterwithsouls._BWSMain;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderBase;

//made this class just in case all my mod items would need smth
public class BWSModItem extends Item {
	public BWSModItem(String name, int id) {
		super(name, _BWSMain.MOD_ID + ":item/"+name, id);

		this.name = name;
	}

	String name;

	public BWSModItem withRecipe(RecipeBuilderBase builder){
		BWSRecipes.recipes.add(new RecipeInfo(this, this.name, builder));

		return this;
	}

	public BWSModItem withStandartModel(){
		BWSItems.standartModels.add(this);
		return this;
	}

	public BWSModItem withWeaponModel(boolean big){
		if(big){
			BWSItems.weaponModelsBig.add(this);
		}else{
			BWSItems.weaponModels.add(this);
		}
		return this;
	}



}
