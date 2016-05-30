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

import net.minecraft.item.Item;

public class DurationRecipe extends Recipe
{
	/**
	 * the time that the crafting of this recipe should take to complete
	 */
	private int duration;

	public DurationRecipe(int duration)
	{
		super();
		this.duration = duration;
	}

	public DurationRecipe(Item input, Item output, int duration)
	{
		super(input, output);
		this.duration = duration;
	}

	public DurationRecipe(Item[] input, Item output, int duration)
	{
		super(input, output);
		this.duration = duration;
	}

	public DurationRecipe(Item input, Item[] output, int duration)
	{
		super(input, output);
		this.duration = duration;
	}

	public DurationRecipe(Item[] input, Item[] output, int duration)
	{
		super(input, output);
		this.duration = duration;
	}

	public int getDuration()
	{
		return duration;
	}
}
