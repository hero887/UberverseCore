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
public class Recipe {
	private Map<Item, Integer> input;
	private Map<Item, Integer> output;

	public Recipe() {
		this.input = new HashMap<>();
		this.output = new HashMap<>();
	}

	public Recipe(Item input, Item output) {
		this();
		addInput(input);
		addOutput(output);
	}

	public Recipe(Item[] input, Item output) {
		this();
		addInput(input);
		addOutput(output);
	}

	public Recipe(Item input, Item[] output) {
		this();
		addInput(input);
		addOutput(output);
	}

	public Recipe(Item[] input, Item[] output) {
		this();
		addInput(input);
		addOutput(output);
	}

	public Recipe addInput(Item... items) {
		for (Item item : items) {
			if (!input.containsKey(item))
				input.put(item, 1);
			else
				input.put(item, input.get(item) + 1);
		}
		return this;
	}

	public Recipe addOutput(Item... items) {
		for (Item item : items) {
			if (!output.containsKey(item))
				output.put(item, 1);
			else
				output.put(item, output.get(item) + 1);
		}
		return this;
	}

	public boolean isValidInput(Item item) {
		return input.containsKey(item);
	}

	public boolean containsRequiredInput(Map<Item, Integer> items) {
		if (items.size() < input.size())
			return false;
		for (Entry<Item, Integer> item : items.entrySet()) {
			if (!input.containsKey(item.getKey()) || input.get(item.getKey()) > item.getValue())
				return false;
		}

		return true;
	}

	public boolean containsRequiredInput(ItemStack[] itemStacks) {
		Map<Item, Integer> items = convertItemStacksToMap(itemStacks);
		if (items.size() < input.size())
			return false;
		for (Entry<Item, Integer> item : items.entrySet()) {
			if (!input.containsKey(item.getKey()) || input.get(item.getKey()) > item.getValue())
				return false;
		}

		return true;
	}

	public static Map<Item, Integer> convertItemStacksToMap(ItemStack[] itemStacks) {
		Map<Item, Integer> items = new HashMap<>();
		for (ItemStack stack : itemStacks) {
			Item item = stack.getItem();
			int count = stack.stackSize;
			if (!items.containsKey(item))
				items.put(item, count);
			else
				items.put(item, items.get(item) + count);
		}
		return items;
	}

	public ItemStack[] getInputAsItemStacks() {
		Collection<ItemStack> out = new ArrayList<>();
		for (Entry<Item, Integer> e : input.entrySet()) {
			Item item = e.getKey();
			int count = e.getValue();
			ItemStack dummy = new ItemStack(item);
			while (count > item.getItemStackLimit(dummy)) {
				count -= item.getItemStackLimit(dummy);
				out.add(new ItemStack(item, item.getItemStackLimit(dummy)));
			}
			if (count > 0) {
				out.add(new ItemStack(item, count));
			}
		}
		return out.toArray(new ItemStack[0]);
	}

	public ItemStack[] getOutputAsItemStacks() {
		Collection<ItemStack> out = new ArrayList<>();
		for (Entry<Item, Integer> e : output.entrySet()) {
			Item item = e.getKey();
			int count = e.getValue();
			ItemStack dummy = new ItemStack(item);
			while (count > item.getItemStackLimit(dummy)) {
				count -= item.getItemStackLimit(dummy);
				out.add(new ItemStack(item, item.getItemStackLimit(dummy)));
			}
			if (count > 0) {
				out.add(new ItemStack(item, count));
			}
		}
		return out.toArray(new ItemStack[0]);
	}

}
