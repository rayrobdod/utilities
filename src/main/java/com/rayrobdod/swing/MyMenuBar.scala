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
package com.rayrobdod.swing

import javax.swing.JMenuItem
import java.awt.event.ActionListener

class MyMenuItem(title:String, mnemonic:Char, action:ActionListener)
			extends JMenuItem(title)
{
	setMnemonic(mnemonic)
	addActionListener(action)
}
