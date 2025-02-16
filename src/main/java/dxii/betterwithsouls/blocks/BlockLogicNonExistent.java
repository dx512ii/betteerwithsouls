package dxii.betterwithsouls.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;

public class BlockLogicNonExistent extends BlockLogic {
	public BlockLogicNonExistent(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
		return null;
	}

	@Override
	public AABB getBlockBoundsFromState(WorldSource world, int x, int y, int z) {
		return AABB.getTemporaryBB(0, 0, 0, 0, 0, 0);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}


}
