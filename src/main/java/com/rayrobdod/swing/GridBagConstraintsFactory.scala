/*
	Copyright (c) 2012-2013, Raymond Dodge
	All rights reserved.
	
	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions are met:
		* Redistributions of source code must retain the above copyright
		  notice, this list of conditions and the following disclaimer.
		* Redistributions in binary form must reproduce the above copyright
		  notice, this list of conditions and the following disclaimer in the
		  documentation and/or other materials provided with the distribution.
		* Neither the name "Image Manipulator" nor the names of its contributors
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

package com.rayrobdod.swing

import java.awt.{GridBagConstraints, Insets}

/**
 * A factory for [[java.awt.GridBagConstraints]].
 * 
 * This uses the new method of GridBagConstraints; the main difference is that this has default parameters.
 * 
 * @version 2013 Jan 29
 * @note worth having a val for a default GridBagConstraints?
 */
object GridBagConstraintsFactory {
	def apply(
			gridx:Int = GridBagConstraints.RELATIVE,
			gridy:Int = GridBagConstraints.RELATIVE,
			gridwidth:Int = 1,
			gridheight:Int = 1,
			weightx:Double = 0,
			weighty:Double = 0,
			anchor:Int = GridBagConstraints.CENTER,
			fill:Int = GridBagConstraints.NONE,
			insets:Insets = new Insets(0,0,0,0),
			ipadx:Int = 0,
			ipady:Int = 0
	) = new GridBagConstraints(
			gridx, gridy, gridwidth, gridheight,
			weightx, weighty, anchor, fill, insets, ipadx, ipady
	)
}
