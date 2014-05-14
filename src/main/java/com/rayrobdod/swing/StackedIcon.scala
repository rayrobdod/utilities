/*
	Honestly, this being independently invented is a thousand times
	more likely than someone finding this. On that note:
	
	Copyright (C) 2014 Raymond Dodge
	
	        DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
                    Version 2, December 2004 

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 

  0. You just DO WHAT THE FUCK YOU WANT TO.
*/
package com.rayrobdod.swing

import scala.collection.immutable.Seq
import javax.swing.Icon
import java.awt.{Component, Graphics}

/**
 * Displays a series of icons one on top of another
 * @since 2014 May 13
 */
final class StackedIcon(values:Seq[Icon]) extends Icon {
	def paintIcon(c:Component, g:Graphics, x:Int, y:Int) = {
		values.foreach{ _.paintIcon(c,g,x,y) }
	}
	def getIconWidth = values.map{_.getIconWidth}.max
	def getIconHeight = values.map{_.getIconHeight}.max
}
