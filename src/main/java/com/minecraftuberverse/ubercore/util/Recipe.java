
package com.minecraftuberverse.ubercore.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * 
 * A general purpose recipe with the possibilities for multiple inputs and
 * outputs
 * 
 * @author Lewis_McReu
 *
 */
public class Recipe
{
	private Map<Item, Integer> input;
	private Map<Item, Integer> output;

	public Recipe()
	{
		this.input = new HashMap<>();
		this.output = new HashMap<>();
	}

	public Recipe(Item input, Item output)
	{
		this();
		addInput(input);
		addOutput(output);
	}

	public Recipe(Item[] input, Item output)
	{
		this();
		addInput(input);
		addOutput(output);
	}

	public Recipe(Item input, Item[] output)
	{
		this();
		addInput(input);
		addOutput(output);
	}

	public Recipe(Item[] input, Item[] output)
	{
		this();
		addInput(input);
		addOutput(output);
	}

	public Recipe addInput(Item... items)
	{
		for (Item item : items)
		{
			if (!input.containsKey(item)) input.put(item, 1);
			else input.put(item, input.get(item) + 1);
		}
		return this;
	}

	public Recipe addOutput(Item... items)
	{
		for (Item item : items)
		{
			if (!output.containsKey(item)) output.put(item, 1);
			else output.put(item, output.get(item) + 1);
		}
		return this;
	}

	public boolean isValidInput(Item item)
	{
		return input.containsKey(item);
	}

	public boolean containsRequiredInput(Map<Item, Integer> items)
	{
		if (items.size() < input.size()) return false;
		for (Entry<Item, Integer> item : items.entrySet())
		{
			if (!input.containsKey(item.getKey()) || input.get(item.getKey()) > item
					.getValue()) return false;
		}

		return true;
	}

	public ItemStack[] getOutputAsItemStacks()
	{
		Collection<ItemStack> out = new ArrayList<>();
		for (Entry<Item, Integer> e : output.entrySet())
		{
			Item item = e.getKey();
			int count = e.getValue();
			while (count > item.getItemStackLimit())
			{
				count -= item.getItemStackLimit();
				out.add(new ItemStack(item, item.getItemStackLimit()));
			}
			if (count > 0)
			{
				out.add(new ItemStack(item, count));
			}
		}
		return out.toArray(new ItemStack[0]);
	}

}
