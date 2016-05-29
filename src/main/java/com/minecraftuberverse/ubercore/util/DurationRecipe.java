
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
