package com.rayrobdod.util.contentHander;

import java.net.ContentHandler;
import java.net.URLConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A ContentHandler that contains a list of ContentHandlers and will
 * respond to requests using the first appropriate ContentHandler
 * it finds.
 */
public final class PriorityContentHandler extends ContentHandler {
	
	private final List<ContentHandler> children;
	
	public PriorityContentHandler(List<ContentHandler> children) {
		this.children = children;
	}
	
	public Object getContent(URLConnection urlc) throws IOException {
		if (this.children.isEmpty())
			return null;
		else
			return this.children.get(0).getContent(urlc);
	}
	
	public Object getContent(final URLConnection urlc2, final Class[] classes) throws IOException {
		final CloneInputURLConnection urlc = new CloneInputURLConnection(urlc2);
		final ArrayList<Object> results = new ArrayList<Object>(children.size()); 
		for (int i = 0; i < children.size(); i++) {
			results.add(children.get(i).getContent(urlc, classes));
		}
		results.removeAll(java.util.Collections.singleton(null));
		
		java.util.Collections.sort(results, new java.util.Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				int rank1 = -1;
				int rank2 = -1;
				
				for ( int i = 0; i < classes.length; i++ ) {
					if (-1 == rank1 && classes[i].isInstance(o1)) {
							rank1 = i;
					}
					if (-1 == rank2 && classes[i].isInstance(o2)) {
							rank2 = i;
					}
				}
				
				return rank1 - rank2;
			}
		});
		
		return (results.isEmpty() ? null : results.get(0));
	}
}
