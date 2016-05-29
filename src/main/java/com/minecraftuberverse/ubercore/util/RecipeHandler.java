
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
