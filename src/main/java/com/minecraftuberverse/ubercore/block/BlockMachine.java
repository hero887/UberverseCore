
package com.minecraftuberverse.ubercore.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockMachine extends Block
{
	public BlockMachine(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return super.createTileEntity(world, state);
	}
}
