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

import javax.swing.ListCellRenderer;
import javax.swing.JList;
import java.awt.Component;

/**
 * A decorator ListCellRenderer that intercepts a null value and replaces it with something else
 * 
 * @author Raymond Dodge
 * @version 15 Jun 2012
 * @version 2013 Aug 13 - rewrote in Java
 */
public final class NullReplaceListCellRenderer<E> implements ListCellRenderer<E>
{
	private final ListCellRenderer<E> backing;
	private final E nullReplacement;

	public NullReplaceListCellRenderer(ListCellRenderer<E> backing, E nullReplacement) {
		this.backing = backing;
		this.nullReplacement = nullReplacement;
	}

	public Component getListCellRendererComponent(JList<? extends E> list, E value,
						int index, boolean isSelected, boolean cellHasFocus)
	{
		return backing.getListCellRendererComponent(list,
				(value == null ? nullReplacement : value),
				index, isSelected, cellHasFocus);
	}
}
