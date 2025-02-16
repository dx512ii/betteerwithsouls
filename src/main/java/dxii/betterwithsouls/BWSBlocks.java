package dxii.betterwithsouls;

import dxii.betterwithsouls.blocks.BlockLogicNonExistent;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import turniplabs.halplibe.helper.BlockBuilder;

import static dxii.betterwithsouls.BWSMain.MOD_ID;

public class BWSBlocks {
	private static int startID = BWSConfig.CFG.getInt("IDs.startBlockID");
	private static int nextID() {
		return startID++;
	}

	//public static Block<?> dynamiclight_block;

	public static void initBlocks(){
//		BlockBuilder dynamiclight_builder = new BlockBuilder(MOD_ID)
//			.setLightOpacity(0)
//			.setResistance(-1f)
//			.setLuminance(15)
//			.setUnbreakable()
//			.addTags(BlockTags.PREVENT_MOB_SPAWNS);
//		dynamiclight_block = dynamiclight_builder
//			.build("block.dynamiclight", "block/dynamiclight_block", nextID(), b -> new BlockLogicNonExistent(b, Material.air));
	}
}
