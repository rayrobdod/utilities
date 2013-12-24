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

import java.net.URL;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.MalformedURLException;

public final class Handler extends java.net.URLStreamHandler {

	private static final String urlChar = "[\\w\\-\\_\\.\\!\\~\\*\\'\\(\\)\\%\\&\\=\\/]+";
	private static final String datePattern = "\\d\\d\\d\\d(?:-\\d\\d(?:-\\d\\d)?)?";
	public static final Pattern tagPattern = Pattern.compile("(("+urlChar+"),"+datePattern+"):("+urlChar+")(?:\\?("+urlChar+"))?" );
		
	
	
	protected int getDefaultPort() {return -1;}

	public java.net.URLConnection openConnection(URL u) throws IOException {
		return new URLConnection(u);
	}
	
	public void parseURL(URL u, String spec, int start, int limit) {
		
		final Matcher tagMatcher = tagPattern.matcher(spec.substring(start, limit));
		final String ref1 = spec.substring(limit);
		final String ref2 = (ref1.equals("") ? null : ref1);
		
		
		if (tagMatcher.matches()) {
			this.setURL(u, "tag",
					/* host */ tagMatcher.group(2),
					/* port */ -1,
					/* authority */ tagMatcher.group(1),
					/* userInfo */ null,
					/* path */ tagMatcher.group(3),
					/* query */ tagMatcher.group(4),
					/* ref */ ref2
            );
        } else {
        	// throw new MalformedURLException();
        	
        	
			this.setURL(u, "tag",
					/* host */ null,
					/* port */ -1,
					/* authority */ null,
					/* userInfo */ null,
					/* path */ null,
					/* query */ null,
					/* ref */ null
            );
        }
	}
	
	public String toExternalForm(URL u) {
		return "tag:" + u.getAuthority() + ":" + u.getPath() +
				(u.getQuery() != null ? "?" + u.getQuery() : "") +
				(u.getRef()   != null ? "#" + u.getRef() : "");
	}
}

