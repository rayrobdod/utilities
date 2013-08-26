/*
	Honestly, this being independently invented is a thousand times
	more likely than someone finding this. On that note:
	
	Copyright (C) 2013 Raymond Dodge
	
	        DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
                    Version 2, December 2004 

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 

  0. You just DO WHAT THE FUCK YOU WANT TO.
*/
package com.rayrobdod.swing;

import javax.swing.Icon;
import java.awt.Component;
import java.awt.Graphics;

/**
 * @author Raymond Dodge
 * @version 2013 Jun 11
 */
class TextOverlayIcon(below:Icon, text:String) extends Icon {
	val font = java.awt.Font.decode(java.awt.Font.SERIF + " BOLD 10")
	
	def getIconHeight() = below.getIconHeight
	def getIconWidth()  = below.getIconWidth
	def paintIcon(c:Component, g:Graphics, x:Int, y:Int) {
		below.paintIcon(c,g,x,y)
		
		val oldFont = g.getFont
		g.setFont(font)
		
		val metrics = g.getFontMetrics
		// newX = x
		// newY = y + getIconHeight
		val newX = x + getIconWidth - metrics.stringWidth(text) - 2
		val newY = y + metrics.getHeight()/2 + 2
		
		g.drawString(text, newX, newY)
		
		g.setFont(oldFont)
	}
}
