package com.rayrobdod.util.services;

import java.util.ServiceConfigurationError;
import static com.rayrobdod.util.services.Services.readServices;
import java.io.InputStream;

/**
 * A ServiceLoader-like that loads the input streams for resources 
 * from a service file
 * 
 * This should be able to get past the security manager, but without Paths,
 * nothing would be allowed to refer to anything else, which may not be feasable.
 * @author Raymond Dodge
 * @version 2013 Dec 08
 */
public final class ResourcesAsStreamServiceLoader implements Iterable<InputStream>
{
	private final String serviceName;
	
	public ResourcesAsStreamServiceLoader(String serviceName)
	{
		this.serviceName = serviceName;
	}
	
	public java.util.Iterator<InputStream> iterator()
	{
		return new Iterator();
	}
	
	private class Iterator implements java.util.Iterator<InputStream>
	{
		private int current = 0;
		private final String[] readLines;
		
		public Iterator()
		{
			try
			{
				readLines = readServices(serviceName);
			}
			catch (java.io.IOException e)
			{
				throw new ServiceConfigurationError("", e);
			}
			catch (java.net.URISyntaxException e)
			{
				throw new ServiceConfigurationError("", e);
			}
		}
		
		public InputStream next() throws java.util.NoSuchElementException
		{
			if (!hasNext()) throw new java.util.NoSuchElementException();
			
			// TODO: more checks
				String returnString = readLines[current];
				InputStream returnURL = ClassLoader.getSystemResourceAsStream(returnString);
				
				if (returnURL == null) throw new ServiceConfigurationError(
					"Service " + serviceName + " pointed at nonexistant resource: " +
					returnString);
				
				current++;
				return returnURL;
		}
		
		public boolean hasNext()
		{
			return current < readLines.length;
		}
		
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException("Cannot remove from a Service");
		}
	}
}
