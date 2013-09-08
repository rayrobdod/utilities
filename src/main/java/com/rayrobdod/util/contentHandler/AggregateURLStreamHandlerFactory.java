package com.rayrobdod.util.contentHander;

import java.net.URLStreamHandlerFactory;
import java.net.URLStreamHandler;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A AggregateURLStreamHandlerFactory that will aggregate URLStreamHandlerFactory and respond to a
 * createURLStreamHandler by returning the highest-priority URLStreamHandler for that protocol
 */
public final class AggregateURLStreamHandlerFactory implements URLStreamHandlerFactory {
	
	private final Map<String, SortedSet<URLStreamHandlerWithPriority>> children;
	
	
	private AggregateURLStreamHandlerFactory() {
		children = new HashMap<String, SortedSet<URLStreamHandlerWithPriority>>();
	}
	
	public static final AggregateURLStreamHandlerFactory instance =
			new AggregateURLStreamHandlerFactory();
	
	public synchronized void addURLStreamHandler(String protocol, int priority, URLStreamHandler handler) {
		if (handler == null) throw new NullPointerException();
		
		SortedSet<URLStreamHandlerWithPriority> old = children.get(protocol);
		
		if (old == null) {old = new TreeSet<URLStreamHandlerWithPriority>();}
		
		old.add(new URLStreamHandlerWithPriority(priority, handler));
		children.put(protocol, old);
	}
	
	public synchronized void removeURLStreamHandler(String protocol, int priority, URLStreamHandler handler) {
		SortedSet<URLStreamHandlerWithPriority> old = children.get(protocol);
		
		if (old != null) {
			old.remove(new URLStreamHandlerWithPriority(priority, handler));
			children.put(protocol, old);
		}
	}
	
	
	public URLStreamHandler createURLStreamHandler(String protocol) {
		if (children.get(protocol) != null && !children.get(protocol).isEmpty()) {
			return children.get(protocol).first().handler;
		} else {
			return null;
//			throw new java.net.MalformedURLException("unknown protocol: " + protocol);
		}
	}
	
	
	private final class URLStreamHandlerWithPriority implements Comparable<URLStreamHandlerWithPriority> {
		private final int priority;
		private final URLStreamHandler handler;
		
		public URLStreamHandlerWithPriority(int priority, URLStreamHandler handler) {
			this.priority = priority;
			this.handler = handler;
		}
		
		public int compareTo(URLStreamHandlerWithPriority other) {
			if (other.priority != this.priority) {
				return other.priority - this.priority;
			} else {
				return other.handler.getClass().getName().compareTo(
					this.handler.getClass().getName());
			}
		}
		
		public boolean equals(Object other) {
			if (other instanceof URLStreamHandlerWithPriority) {
				URLStreamHandlerWithPriority other2 = (URLStreamHandlerWithPriority) other;
				
				return (other2.priority == this.priority &&
						other2.handler == this.handler);
			} else {
				return false;
			}
		}
	}
	
}
