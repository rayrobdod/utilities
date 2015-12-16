/*
	Copyright (c) 2013-2015, Raymond Dodge
	All rights reserved.
	 
	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions are met:
		* Redistributions of source code must retain the above copyright
		  notice, this list of conditions and the following disclaimer.
		* Redistributions in binary form must reproduce the above copyright
		  notice, this list of conditions and the following disclaimer in the
		  documentation and/or other materials provided with the distribution.
		* Neither the name "<PRODUCT NAME>" nor the names of its contributors
		  may be used to endorse or promote products derived from this software
		  without specific prior written permission.
	
	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
	ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
	DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
	DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
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
			
			serviceFileStream.close();
		}
		
		return allLines.toArray(new String[0]);
	}
}
