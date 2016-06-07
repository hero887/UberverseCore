package com.minecraftuberverse.ubercore.util;

import org.apache.logging.log4j.Level;

import net.minecraftforge.fml.common.FMLLog;

/**
 * @author Lewis_McReu
 */
public class Logger
{
	private final String reference;

	public Logger(String reference)
	{
		this.reference = reference;
	}

	public void log(Level logLevel, Object object)
	{
		FMLLog.log(reference, logLevel, String.valueOf(object));
	}

	public void all(Object object)
	{
		log(Level.ALL, object);
	}

	public void debug(Object object)
	{
		log(Level.DEBUG, object);
	}

	public void error(Object object)
	{
		log(Level.ERROR, object);
	}

	public void fatal(Object object)
	{
		log(Level.FATAL, object);
	}

	public void off(Object object)
	{
		log(Level.OFF, object);
	}

	public void info(Object object)
	{
		log(Level.INFO, object);
	}

	public void warn(Object object)
	{
		log(Level.WARN, object);
	}

	public void trace(Object object)
	{
		log(Level.TRACE, object);
	}
}
