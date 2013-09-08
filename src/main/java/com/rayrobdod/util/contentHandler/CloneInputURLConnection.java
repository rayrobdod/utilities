package com.rayrobdod.util.contentHander;

import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.util.Map;
import java.util.List;
import java.security.Permission;


/**
 * A URLConnection that wraps a different URLConnection to allow calling getInputStream multiple times.
 *
 * With the exception of getInputStream, every method forwards its call to the parent URLConnction
 * 
 * The getInputStream intercepts the stream and copies the stream. Each invocation of getInputStream
 * returns a new InputStream with the exact same data, rather than all returning the same InputStream.
 */
public class CloneInputURLConnection extends URLConnection
{
	private final URLConnection parent;
	private byte[] inputStreamBytes;
	
	public CloneInputURLConnection(URLConnection parent) {
		super(parent.getURL());
		this.parent = parent;
	}
	
	
	public synchronized InputStream getInputStream() throws IOException {
		if (inputStreamBytes == null) {
			byte[] bytes = new byte[256];
			int bytesRead;
			InputStream toClone = parent.getInputStream();
			ByteArrayOutputStream cloner = new ByteArrayOutputStream();
			
			while ((bytesRead = toClone.read(bytes)) > 0) {
				cloner.write(bytes, 0, bytesRead);
			}
			inputStreamBytes = cloner.toByteArray();
		}
		
		return new ByteArrayInputStream(inputStreamBytes);
	}
	
	
	
	public void addRequestProperty(String key, String value) { parent.addRequestProperty(key, value); }
	public void connect() throws IOException { parent.connect(); }
	public boolean getAllowUserInteraction() { return parent.getAllowUserInteraction(); }
	public int getConnectTimeout() { return parent.getConnectTimeout(); }
	public Object getContent() throws IOException { return parent.getContent(); }
	public Object getContent(Class[] classes) throws IOException { return parent.getContent(classes); }
	public String getContentEncoding() { return parent.getContentEncoding(); }
	public int getContentLength() { return parent.getContentLength(); }
	public String getContentType() { return parent.getContentType(); }
	public long getDate() { return parent.getDate(); }
	public boolean getDefaultUseCaches() { return parent.getDefaultUseCaches(); }
	public boolean getDoInput() { return parent.getDoInput(); }
	public boolean getDoOutput() { return parent.getDoOutput(); }
	public long getExpiration() { return parent.getExpiration(); }
	public String getHeaderField(int n) { return parent.getHeaderField(n); }
	public String getHeaderField(String name) { return parent.getHeaderField(name); }
	public long getHeaderFieldDate(String name, long Default) { return parent.getHeaderFieldDate(name, Default); }
	public int getHeaderFieldInt(String name, int Default) { return parent.getHeaderFieldInt(name, Default); }
	public String getHeaderFieldKey(int n) { return parent.getHeaderFieldKey(n); }
	public Map<String,List<String>> getHeaderFields() { return parent.getHeaderFields(); }
	public long getIfModifiedSince() { return parent.getIfModifiedSince(); }
	public long getLastModified() { return parent.getLastModified(); }
	public OutputStream getOutputStream() throws IOException { return parent.getOutputStream(); }
	public Permission getPermission() throws IOException { return parent.getPermission(); }
	public int getReadTimeout() { return parent.getReadTimeout(); }
	public Map<String,List<String>> getRequestProperties() { return parent.getRequestProperties(); }
	public String getRequestProperty(String key) { return parent.getRequestProperty(key); }
	public URL getURL() { return parent.getURL(); }
	public boolean getUseCaches() { return parent.getUseCaches(); }
	public void setAllowUserInteraction(boolean allowuserinteraction) { parent.setAllowUserInteraction(allowuserinteraction); }
	public void setConnectTimeout(int timeout) { parent.setConnectTimeout(timeout); }
	public void setDefaultUseCaches(boolean defaultusecaches) { parent.setDefaultUseCaches(defaultusecaches); }
	public void setDoInput(boolean doinput) { parent.setDoInput(doinput); }
	public void setDoOutput(boolean dooutput) { parent.setDoOutput(dooutput); }
	public void setIfModifiedSince(long ifmodifiedsince) { parent.setIfModifiedSince(ifmodifiedsince); }
	public void setReadTimeout(int timeout) { parent.setReadTimeout(timeout); }
	public void setRequestProperty(String key, String value) { parent.setRequestProperty(key, value); }
	public void setUseCaches(boolean usecaches) { parent.setUseCaches(usecaches); }
	public String toString() { return parent.toString(); }
}
