package com.rayrobdod.tagprotocol.tag;

import java.io.InputStream;
import java.util.Map;
import java.util.List;

public final class TagResources {
	private TagResources() {}
	
	public static java.lang.Iterable<TagResource> service() {
		return java.util.ServiceLoader.load(TagResource.class);
	}
}
