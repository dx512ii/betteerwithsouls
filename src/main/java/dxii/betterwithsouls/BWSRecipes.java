package dxii.betterwithsouls;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;

import static dxii.betterwithsouls.BWSMain.MOD_ID;

public class BWSRecipes {

	public static void initRecipes(){
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("stone_sword");
		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				" S ",
				" S ",
				" I ")
			.addInput('S', "minecraft:cobblestones")
			.addInput('I', Items.STICK)
			.create("shortsword_stone", BWSItems.SHORTSWORD_STONE.getDefaultStack());
	}

}
