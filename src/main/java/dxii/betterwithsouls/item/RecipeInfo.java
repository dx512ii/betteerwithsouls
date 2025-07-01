package dxii.betterwithsouls.item;

import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderBase;

public class RecipeInfo {//im just sick of recipe builder repetitive code
	public Item item;
	public String recipeID;
	public RecipeBuilderBase builder;

	public RecipeInfo(Item item, String recipeID, RecipeBuilderBase builder){
		this.item = item;
		this.recipeID = recipeID;
		this.builder = builder;
	}
}
