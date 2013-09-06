package com.rayrobdod.tagprotocol.tag;

import java.util.List;
import java.util.Map;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import com.rayrobdod.boardGame.swingView.*;
import com.rayrobdod.jsonTilesheetViewer.CheckerboardURIMatcher;

public final class URLConnection extends java.net.URLConnection {
	private final String name;
	
	public URLConnection(URL u) {
		super(u);
		
		name = u.getAuthority() + ":" + u.getPath();
	}
	
	public void connect() throws IOException {
		this.connected = true;
	}
	
	public Object getContent() throws IOException {
		
		for ( TagResource i : TagResources.service() ) {
			if (i.name().equals(this.name)) {
				Object retVal = i.getContent();
				
				if (retVal == null) {
					return super.getContent();
				} else {
					return retVal;
				}
			}
		}
		
		throw new IOException("Unknown tag");
	}
	
	public InputStream getInputStream() throws IOException {
		
		for ( TagResource i : TagResources.service() ) {
			if (i.name().equals(this.name)) {
				InputStream retVal = i.getInputStream();
				
				if (retVal == null) {
					return super.getInputStream();
				} else {
					return retVal;
				}
			}
		}
		
		throw new IOException("Unknown tag");
	}
	
	public Map<String,List<String>> getHeaderFields() {
		
		for ( TagResource i : TagResources.service() ) {
			if (i.name().equals(this.name)) {
				Map<String,List<String>> retVal = i.getHeaderFields();
				
				if (retVal == null) {
					return super.getHeaderFields();
				} else {
					return retVal;
				}
			}
		}
		
		return super.getHeaderFields();
	}
}
