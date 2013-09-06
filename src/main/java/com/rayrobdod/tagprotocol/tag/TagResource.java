package com.rayrobdod.tagprotocol.tag;

import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.List;

public interface TagResource {
	/**
	 * Returns the authority/path pair identifying this resource
	 */
	String name();
	
	/**
	 * @return An object representing this tag
	 */
	Object getContent() throws IOException;
	
	/**
	 * @return An object representing this tag in the requested format
	 */
	//Object getContent(Class[] classes) throws IOException;
	
	/**
	 * @return an InputStream representing this tag
	 */
	InputStream getInputStream() throws IOException;
	
	Map<String,List<String>> getHeaderFields();
}
