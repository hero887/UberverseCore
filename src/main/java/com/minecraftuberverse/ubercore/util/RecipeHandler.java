/*******************************************************************************
 * UberCore - A modification of Minecraft created with Minecraft Forge serving as a library for other mods
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

package com.minecraftuberverse.ubercore.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeHandler<E extends Recipe>
{
	private Collection<E> recipes;

	public RecipeHandler()
	{
		recipes = new ArrayList<>();
	}

	public void register(E recipe)
	{
		recipes.add(recipe);
	}

	public E getMatchingRecipe(ItemStack... itemStacks)
	{
		Map<Item, Integer> items = new HashMap<>();
		for (ItemStack stack : itemStacks)
		{
			Item item = stack.getItem();
			int count = stack.stackSize;
			if (!items.containsKey(item)) items.put(item, count);
			else items.put(item, items.get(item) + count);
		}

		for (E recipe : recipes)
		{
			if (recipe.containsRequiredInput(items)) return recipe;
		}
		return null;
	}

	public boolean hasMatchingRecipe(ItemStack... itemStacks)
	{
		return this.getMatchingRecipe(itemStacks) != null;
	}

	public boolean isValidInput(ItemStack stack)
	{
		Item item = stack.getItem();
		for (E e : recipes)
		{
			if (e.isValidInput(item)) return true;
		}
		return false;
	}
}
