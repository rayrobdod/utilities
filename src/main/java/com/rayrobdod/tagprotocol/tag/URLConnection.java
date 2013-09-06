package com.rayrobdod.tagprotocol.tag;

import java.util.List;
import java.util.Map;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;

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
