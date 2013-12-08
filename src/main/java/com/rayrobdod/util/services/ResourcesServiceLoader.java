package com.rayrobdod.util.services;

import java.util.ServiceConfigurationError;
import static com.rayrobdod.util.services.Services.readServices;

import java.net.URL;

/**
 * A ServiceLoader-like that loads the url names for resources 
 * from a service file
 * @author Raymond Dodge
 * @version 18 Jul 2012
 * @version 2013 Dec 08 - moving former capacity to ResourcesAsPathServiceLoader
 */
public final class ResourcesServiceLoader implements Iterable<URL>
{
	private final String serviceName;
	
	public ResourcesServiceLoader(String serviceName)
	{
		this.serviceName = serviceName;
	}
	
	public java.util.Iterator<URL> iterator()
	{
		return new Iterator();
	}
	
	private class Iterator implements java.util.Iterator<URL>
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
		
		public URL next() throws java.util.NoSuchElementException
		{
			if (!hasNext()) throw new java.util.NoSuchElementException();
			
			// TODO: more checks
				String returnString = readLines[current];
				java.net.URL returnURL = ClassLoader.getSystemResource(returnString);
				
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
