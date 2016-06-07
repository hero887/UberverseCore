/*******************************************************************************
 * UberCore - A modification of Minecraft(R) created with Minecraft Forge(R) serving as a library for other modifications
 * Copyright (C) 2016  Wim Creuwels, a.k.a. Lewis_McReu and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package com.minecraftuberverse.ubercore.tileentity;

import java.util.HashMap;
import java.util.Map;

import com.minecraftuberverse.ubercore.util.DurationRecipe;
import com.minecraftuberverse.ubercore.util.Recipe;
import com.minecraftuberverse.ubercore.util.RecipeHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Lewis_McReu
 */
public abstract class TileEntityMachine extends TileEntity implements IUpdatePlayerListBox
{
	public static final String NBTKEY_PROGRESS = "progress";

	private static final Map<String, RecipeHandler<Recipe>> recipeHandlers = new HashMap<>();

	public static RecipeHandler<Recipe> getRecipeHandler(String key)
	{
		return recipeHandlers.get(key);
	}

	public static int getInputStackLimit(String key)
	{
		return recipeHandlers.get(key).getInputStackLimit();
	}

	public static int getOutputStackLimit(String key)
	{
		return recipeHandlers.get(key).getInputStackLimit();
	}

	public static RecipeHandler<Recipe> registerMachine(String key, int inputStackLimit, int outputStackLimit)
	{
		RecipeHandler<Recipe> recipeHandler = new RecipeHandler<>(inputStackLimit,
				outputStackLimit);
		RecipeHandler<Recipe> r = recipeHandlers.putIfAbsent(key, recipeHandler);
		if (r == null) return recipeHandler;
		else return null;
	}

	private Recipe activeRecipe;

	private int timeLeft;

	/**
	 * whether the process is done
	 */
	private boolean ready;

	/**
	 * This method adds the passed stack to the inventory after checks that the
	 * stack is valid input; calls
	 * 
	 * @param stack
	 *            the stack to add to the inventory
	 */
	public void addInput(ItemStack stack)
	{
		if (getRecipeHandler(getRecipeHandlerKey())
				.isValidInput(stack)) insertItemStackIntoInventory(stack);
	}

	/**
	 * Method that actually adds an itemstack to the inventory Called in
	 * {@link #addInput(ItemStack)}
	 * 
	 * @param stack
	 *            the stack to insert into the inventory
	 */
	protected abstract boolean insertItemStackIntoInventory(ItemStack stack);

	/**
	 * @return the itemstacks currently in the inventory but doesn't remove them
	 */
	public abstract ItemStack[] getInput();

	/**
	 * This method should effectively empty the inventory and return all
	 * contents
	 * 
	 * @return the itemstacks currently in the inventory
	 */
	public abstract ItemStack[] removeInput();

	/**
	 * This method returns whatever the output of the active recipe would be, it
	 * doesn't actually remove anything from the inventory
	 * 
	 * @return the output of the active recipe, if there is one and if the
	 *         recipe is done, otherwise returns null
	 */
	public ItemStack[] getOutput()
	{
		if (ready)
		{
			ready = false;
			return activeRecipe != null ? activeRecipe.getOutputAsItemStacks() : null;
		}
		else return null;
	}

	public abstract void setOutput(ItemStack[] itemStacks);

	public abstract ItemStack[] removeOutput();

	public Recipe getActiveRecipe()
	{
		return activeRecipe;
	}

	/**
	 * Finds a possible recipe within the available inputs and stores it in
	 * {@link #activeRecipe} Also sets {@link #timeLeft} to the recipes'
	 * duration
	 */
	protected final void selectActiveRecipe()
	{
		activeRecipe = getRecipeHandler(getRecipeHandlerKey()).getMatchingRecipe(getInput());
		if (hasActiveRecipe() && activeRecipe instanceof DurationRecipe) timeLeft = ((DurationRecipe) activeRecipe)
				.getDuration();
	}

	public boolean hasActiveRecipe()
	{
		return activeRecipe != null;
	}

	@Override
	public void update()
	{
		if (!ready && !hasActiveRecipe()) selectActiveRecipe();
		if (hasActiveRecipe() && !activeRecipe.containsRequiredInput(getInput()))
		{
			activeRecipe = null;
			timeLeft = 0;
			return;
		}
		if (hasActiveRecipe() && activeRecipe instanceof DurationRecipe && !ready)
		{
			if (timeLeft <= 0)
			{
				ready = true;
				setOutput(activeRecipe.getOutputAsItemStacks());
			}
			else timeLeft--;
		}
	}

	public boolean isReady()
	{
		return ready;
	}

	protected void setReady(boolean ready)
	{
		this.ready = ready;
	}

	/**
	 * This method must return a value if you wish to make use of the provided
	 * functionality
	 * 
	 * @return the key used for the recipe handler of any instances of the
	 *         actual type
	 */
	protected abstract String getRecipeHandlerKey();

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger(NBTKEY_PROGRESS, timeLeft);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.timeLeft = compound.getInteger(NBTKEY_PROGRESS);
	}
}
