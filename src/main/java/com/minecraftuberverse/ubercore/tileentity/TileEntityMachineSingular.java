package com.minecraftuberverse.ubercore.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This class provides an implementation for the case where the tile entity only
 * holds a single stack for both input and output
 * 
 * @author Lewis_McReu
 *
 */
public abstract class TileEntityMachineSingular extends TileEntityMachine implements IInventory
{
	public static final String NBTKEY_CONTENT = "content";

	private ItemStack content = null;

	public ItemStack getContent()
	{
		return content;
	}

	protected void setContent(ItemStack stack)
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

	public int getSizeInventory()
	{
		return 1;
	}

	public ItemStack getStackInSlot(int index)
	{
		return content;
	}

	public ItemStack decrStackSize(int index, int count)
	{
		return null;
	}

	public ItemStack getStackInSlotOnClosing(int index)
	{
		return null;
	}

	public void setInventorySlotContents(int index, ItemStack stack)
	{
		content = stack;
	}

	public int getInventoryStackLimit()
	{
		return 1;
	}

	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return false;
	}

	public void openInventory(EntityPlayer player)
	{
	}

	public void closeInventory(EntityPlayer player)
	{
	}

	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return getRecipeHandler(getRecipeHandlerKey()).isValidInput(stack);
	}

	public int getField(int id)
	{
		return 0;
	}

	public void setField(int id, int value)
	{
	}

	public int getFieldCount()
	{
		return 0;
	}

	public void clear()
	{
		content = null;
	}
}
