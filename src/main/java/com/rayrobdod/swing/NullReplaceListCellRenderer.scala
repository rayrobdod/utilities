package com.rayrobdod.swing

import javax.swing.{ListCellRenderer, JList}

/**
 * A decorator ListCellRenderer that intercepts a null value and replaces it with something else
 * 
 * @author Raymond Dodge
 * @version 15 Jun 2012
 * @todo rewrite in Java?
 */
class NullReplaceListCellRenderer[A](val backing:ListCellRenderer[A], val nullReplacement:A) extends ListCellRenderer[A]
{
	override def getListCellRendererComponent(list:JList[_ <: A], value:A,
						index:Int, isSelected:Boolean, cellHasFocus:Boolean):java.awt.Component =
	{
		backing.getListCellRendererComponent(list,
				if (value == null) {nullReplacement} else {value},
//				Option(value).getOrElse(nullReplacement),
				index, isSelected, cellHasFocus)
	}
}
