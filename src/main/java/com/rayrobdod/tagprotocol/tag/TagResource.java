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
	 * @return an object if the tag refers to an object, or null if the tag refers to an InputStream.
	 */
	Object getContent() throws IOException;
	
	/**
	 * @return an InputStream if the tag refers to an InputStream, or null if the tag refers to an Object.
	 */
	InputStream getInputStream() throws IOException;
	
	Map<String,List<String>> getHeaderFields();
}
