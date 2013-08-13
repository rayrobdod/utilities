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
