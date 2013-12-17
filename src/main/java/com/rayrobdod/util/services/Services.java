package com.rayrobdod.util.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * A class with a single static method that reads all lines in a ServiceLoader style
 * file.
 * @author Raymond Dodge
 * @version 2013 Dec 17 - using proper classloaders
 */
public class Services
{
	private final static String PREFIX = "META-INF/services/";
	
	private Services(){}

	public static String[] readServices(String service) throws java.io.IOException, java.net.URISyntaxException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		return Services.readServices(service, cl);
	}

	
	/**
	 * Reads all lines in all Service style classes with the specificed name.
	 *
	 * Anything on a line after a '#' is ignored. An empty line is ignored.
	 * Anything else is recorded and returned.
	 */
	public static String[] readServices(String service, ClassLoader loader) throws java.io.IOException, java.net.URISyntaxException
	{
		final String fullPath = PREFIX + service;
		final Enumeration<URL> listOfFiles;
		if (loader == null) {
			listOfFiles = ClassLoader.getSystemResources(fullPath);
		} else {	
			listOfFiles = loader.getResources(fullPath);
		}
		List<String> allLines = new ArrayList<String>();
		
		while (listOfFiles.hasMoreElements())
		{
			final URL serviceFileURL = listOfFiles.nextElement();
			final InputStream serviceFileStream = serviceFileURL.openStream();
			
			Iterable<String> lines = new Iterable<String>() {
				public Iterator<String> iterator() {
					return new java.util.Scanner(serviceFileStream).useDelimiter("\n");
				}
			};
			
			for (String line : lines)
			{
				final String trimLine = line.trim();
				
				String usedPart = "";
				if (trimLine.contains("#"))
					usedPart = trimLine.split("#")[0].trim();
				else
					usedPart = trimLine;
				
				if (!usedPart.isEmpty()) {allLines.add(usedPart);}
			}
		}
		
		return allLines.toArray(new String[0]);
	}
}
