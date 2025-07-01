package dxii.betterwithsouls;

import dxii.betterwithsouls.item.RecipeInfo;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.util.ArrayList;
import java.util.List;

import static dxii.betterwithsouls._BWSMain.MOD_ID;

public class BWSRecipes {

	public static final List<RecipeInfo> recipes = new ArrayList<>();

	public static void initRecipes(){
		//removing vanilla recepies i dont need lol
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("carve_pumpkin_sword");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("stone_sword");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("wooden_sword");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("iron_sword");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("golden_sword");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("diamond_sword");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("steel_sword");

		removeArmor("leather");
		removeArmor("iron");
		removeArmor("diamond");
		removeArmor("steel");
		removeArmor("golden");

		BWSItems.ARMOR_WOODEN_HELMET.withRecipe(RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"SSS",
				"S S",
				"   ")
			.addInput('S', "minecraft:logs")
		);
		BWSItems.ARMOR_WOODEN_CHESTPLATE.withRecipe(RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"S S",
				"SSS",
				"SSS")
			.addInput('S', "minecraft:logs")
		);
		BWSItems.ARMOR_WOODEN_PANTS.withRecipe(RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"SSS",
				"S S",
				"S S")
			.addInput('S', "minecraft:logs")
		);
		BWSItems.ARMOR_WOODEN_BOOTS.withRecipe(RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"S S",
				"S S",
				"   ")
			.addInput('S', "minecraft:logs")
		);

		for(RecipeInfo info : recipes){
			processRecipeInfo(info);
		}
	}

	public static void removeArmor(String type){
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe(type+"_helmet");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe(type+"_chestplate");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe(type+"_leggings");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe(type+"_boots");
	}

	public static void processRecipeInfo(RecipeInfo info){
		info.builder.create(info.recipeID, info.item.getDefaultStack());
	}

}
