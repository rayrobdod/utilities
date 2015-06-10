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
import java.awt.Color;

/**
 * @author Raymond Dodge
 * @version 11 Jun 2012
 * @version 28 Oct 2012 - implementing toString.
 * @version 2013 Jun 03 - implementing getIconColor.
 * @version 2015 Jun 09
 */
public final class SolidColorIcon implements Icon
{
	private final int width;
	private final int height;
	private final Color color;
	
	public SolidColorIcon(Color color, int width, int height)
	{
		this.color = color;
		this.width = width;
		this.height = height;
	}
	
	public int getIconWidth() {return width;}
	public int getIconHeight() {return height;}
	public Color getIconColor() {return color;}
	
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	public String toString() {
		return this.getClass().getName() + "[" +
		        color + ", " +
				"w=" + width + ", " +
				"h=" + height + "]";
	}
	
	protected boolean canEquals(Object other) {
		return other instanceof SolidColorIcon;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof SolidColorIcon) {
			SolidColorIcon other2 = (SolidColorIcon) other;
			if (other2.canEquals(this)) {
				return other2.getIconColor().equals(this.getIconColor()) &&
						other2.getIconWidth() == this.getIconWidth() &&
						other2.getIconHeight() == this.getIconHeight();
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.getIconColor().hashCode() +
			31 * (this.getIconWidth() +
			31 * this.getIconHeight());
	}
}
