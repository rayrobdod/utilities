/*
	Copyright (c) 2013, Raymond Dodge
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

import java.util.ServiceConfigurationError;
import static com.rayrobdod.util.services.Services.readServices;

/**
 * A ServiceLoader-like that loads the classes of the services rather than the
 * services themselves.
 * @author Raymond Dodge
 * @version 2013 Dec 17 - using proper classloaders
 */
public class ClassServiceLoader<A> implements Iterable<Class<? extends A>>
{
	private final Class<A> clazz;
	private final String serviceName;
	private final ClassLoader loader;
	
	private static ClassLoader defaultClassLoader() {return Thread.currentThread().getContextClassLoader();}
	public ClassServiceLoader(Class<A> clazz) {this(clazz, clazz.getName(), defaultClassLoader());}
	public ClassServiceLoader(Class<A> clazz, String serviceName) {this(clazz, serviceName, defaultClassLoader());}
	public ClassServiceLoader(Class<A> clazz, ClassLoader loader) {this(clazz, clazz.getName(), loader);}
	
	public ClassServiceLoader(Class<A> clazz, String serviceName, ClassLoader loader) 
	{
		this.clazz = clazz;
		this.serviceName = serviceName;
		this.loader = loader;
	}
	
	public java.util.Iterator<Class<? extends A>> iterator()
	{
		return new Iterator();
	}
	
	private class Iterator implements java.util.Iterator<Class<? extends A>>
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
		
		public Class<? extends A> next()
		{
			if (!hasNext()) throw new java.util.NoSuchElementException();
			
			try
			{
				Class<?> returnValue;
				if (loader == null) {
					returnValue = Class.forName(readLines[current]);
				} else {	
					returnValue = loader.loadClass(readLines[current]);
				}
				current++;
				return returnValue.asSubclass(clazz);
			}
			catch (ClassNotFoundException e)
			{
				throw new ServiceConfigurationError("Class not found", e);
			}
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
