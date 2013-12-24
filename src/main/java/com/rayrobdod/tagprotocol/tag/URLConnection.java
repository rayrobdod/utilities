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
package com.rayrobdod.tagprotocol.tag;

import java.util.List;
import java.util.Map;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;

/**
 * The URLConnection that lets URLs access the tag protocol.
 */
public final class URLConnection extends java.net.URLConnection {
	private final String name;
	private final TagResource resource;
	
	public URLConnection(URL u) {
		super(u);
		
		this.name = u.getAuthority() + ":" + u.getPath();
		
		TagResource resource = null;
		for ( TagResource i : TagResources.service() ) {
			if (i.name().equals(this.name)) {
				resource = i;
			}
		}
		this.resource = resource;
	}
	
	public void connect() throws IOException {
		this.connected = true;
	}
	
	public Object getContent() throws IOException {
		
		if (resource != null) {
			Object retVal = resource.getContent();
			
			if (retVal == null) {
				return super.getContent();
			} else {
				return retVal;
			}
		} else {
			throw new IOException("Unknown tag");
		}
	}
	
	public Object getContent(Class[] classes) throws IOException {
		
		if (resource != null) {
			Object retVal = resource.getContent();
			
			if (retVal == null) {
				return super.getContent(classes);
			} else {
				boolean matches = false;
				for ( Class c : classes ) {
					if (c.isInstance(retVal)) { matches = true; }
				}
				
				if (! matches) {
					return super.getContent(classes);
				} else {
					return retVal;
				}
			}
		} else {
			throw new IOException("Unknown tag");
		}
	}
	
	public InputStream getInputStream() throws IOException {
		
		if (resource != null) {
			InputStream retVal = resource.getInputStream();
			
			if (retVal == null) {
				return super.getInputStream();
			} else {
				return retVal;
			}
		} else {
			throw new IOException("Unknown tag");
		}
	}
	
    public String getHeaderField(String name) {
    	try {
    		List<String> list = this.getHeaderFields().get(name);
    		return list.get(list.size() - 1);
    	} catch ( NullPointerException e ) {
    		return null;
    	}
    }
	
	public Map<String,List<String>> getHeaderFields() {
		
		if (resource != null) {
			Map<String,List<String>> retVal = resource.getHeaderFields();
			
			if (retVal == null) {
				return super.getHeaderFields();
			} else {
				return retVal;
			}
		} else {
			return super.getHeaderFields();
		}
	}
}
