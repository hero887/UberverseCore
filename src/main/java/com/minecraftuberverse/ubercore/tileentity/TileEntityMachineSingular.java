package com.minecraftuberverse.ubercore.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This class provides an implementation for the case where the tile entity only
 * holds a single stack for both input and output
 * 
 * @author Lewis_McReu
 *
 */
public abstract class TileEntityMachineSingular extends TileEntityMachine
{
	public static final String NBTKEY_CONTENT = "content";

	private ItemStack content = null;

	public ItemStack getContent()
	{
		return content;
	}

	private void setContent(ItemStack stack)
	{
		content = stack;
	}

	@Override
	public ItemStack[] getInput()
	{
		return new ItemStack[] { content };
	}

	@Override
	protected boolean insertItemStackIntoInventory(ItemStack stack)
	{
		boolean hasNoContent = content == null;
		if (hasNoContent) setContent(stack);
		return hasNoContent;
	}

	@Override
	public ItemStack[] removeInput()
	{
		ItemStack[] out = new ItemStack[] { getContent() };
		setContent(null);
		return out;
	}

	@Override
	public void setOutput(ItemStack[] output)
	{
		setContent(output[0]);
	}

	@Override
	public ItemStack[] removeOutput()
	{
		return removeInput();
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		if (content != null) compound.setTag(NBTKEY_CONTENT,
				content.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.content = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(NBTKEY_CONTENT));
	}
}
