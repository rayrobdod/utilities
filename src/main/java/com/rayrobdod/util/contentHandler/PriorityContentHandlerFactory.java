package com.rayrobdod.util.contentHander;

import java.net.ContentHandlerFactory;
import java.net.ContentHandler;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A ContentHandlerFactory that will aggregate ContentHandlers and respond to a
 * createContentHandler by sending the aggregation and letting them fight over 
 * what they're doing  
 */
public final class PriorityContentHandlerFactory implements ContentHandlerFactory {
	
	private final Map<String, SortedSet<ContentHandlerWithPriority>> children;
	
	
	private PriorityContentHandlerFactory() {
		children = new HashMap<String, SortedSet<ContentHandlerWithPriority>>();
	}
	
	public static final PriorityContentHandlerFactory instance =
			new PriorityContentHandlerFactory();
	
	public synchronized void addContentHandler(String mime, int priority, ContentHandler handler) {
		if (handler == null) throw new NullPointerException();
		
		SortedSet<ContentHandlerWithPriority> old = children.get(mime);
		
		if (old == null) {old = new TreeSet<ContentHandlerWithPriority>();}
		
		old.add(new ContentHandlerWithPriority(priority, handler));
		children.put(mime, old);
	}
	
	public synchronized void removeContentHandler(String mime, int priority, ContentHandler handler) {
		SortedSet<ContentHandlerWithPriority> old = children.get(mime);
		
		if (old != null) {
			old.remove(new ContentHandlerWithPriority(priority, handler));
			children.put(mime, old);
		}
	}
	
	
	public PriorityContentHandler createContentHandler(String mime) {
		SortedSet<ContentHandlerWithPriority> a1 = children.get(mime);
		SortedSet<ContentHandlerWithPriority> a2 = children.get(mime.split("/")[0] + "/*");
		SortedSet<ContentHandlerWithPriority> a3 = children.get("*/*");
		
		SortedSet<ContentHandlerWithPriority> a = new TreeSet<ContentHandlerWithPriority>();
		if (a1 != null) a.addAll(a1);
		if (a2 != null) a.addAll(a2);
		if (a3 != null) a.addAll(a3);
		
		List<ContentHandler> b = new ArrayList<ContentHandler>(a.size());
		for ( ContentHandlerWithPriority c : a ) {
			b.add(c.handler);
		}
		
		return new PriorityContentHandler(b);
	}
	
	public String acceptHeader() {
		if (children.isEmpty()) {
			return "";
		} else {
			StringBuilder b = new StringBuilder();
		
			for (Map.Entry<String, SortedSet<ContentHandlerWithPriority>> e : children.entrySet()) {
				if (! e.getValue().isEmpty()) {
					b.append( e.getKey() );
					b.append( ";q=" );
					b.append( e.getValue().first().priority * 0.01 );
					b.append( "," );
				}
			}
			return b.substring(0, b.length() - 1);
		}
	}
	
	
	private final class ContentHandlerWithPriority implements Comparable<ContentHandlerWithPriority> {
		private int priority;
		private ContentHandler handler;
		
		public ContentHandlerWithPriority(int priority, ContentHandler handler) {
			this.priority = priority;
			this.handler = handler;
		}
		
		public int compareTo(ContentHandlerWithPriority other) {
			if (other.priority != this.priority) {
				return other.priority - this.priority;
			} else {
				return other.handler.getClass().getName().compareTo(
					this.handler.getClass().getName());
			}
		}
		
		public boolean equals(Object other) {
			if (other instanceof ContentHandlerWithPriority) {
				ContentHandlerWithPriority other2 = (ContentHandlerWithPriority) other;
				
				return (other2.priority == this.priority &&
						other2.handler == this.handler);
			} else {
				return false;
			}
		}
	}
	
}
