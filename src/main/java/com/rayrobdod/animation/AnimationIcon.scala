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
package com.rayrobdod.animation

import java.awt.{Component, Graphics}

/**
 * An icon that will forward it's paintIcon calls to an animation.
 * This does not listen to the animation. Something else will have
 * to tell the component to repaint or whatever it needs to do.
 * @author Raymond Dodge
 * @version 18 May 2012
 * @version 22 May 2012 - now requires RenderableAnimation rather than Animation
 * @version 11 Jun 2012 - adding both addRepaintOnNextFrameListener methods
 */
class AnimationIcon(val animation:RenderableAnimation) extends javax.swing.Icon
{
	def paintIcon(c:Component, g:Graphics, x:Int, y:Int) =
			animation.paintCurrentFrame(c,g,x,y)
	def getIconWidth = animation.width
	def getIconHeight = animation.height
	
	/**
	 * Adds a NextFrameListener to the backing renderable animation
	 * whose only job is to call c.repaint()
	 */
	def addRepaintOnNextFrameListener(c:Component) {
		animation.addNextFrameListener(new NextFrameListener{
			def frameChanged(e:NextFrameEvent) {
				c.repaint()
			}
		})
	}
	
	def addRepaintOnNextFrameListener(c:Component, x:Int, y:Int) {
		animation.addNextFrameListener(new NextFrameListener{
			def frameChanged(e:NextFrameEvent) {
				c.repaint(x, y, getIconWidth, getIconHeight)
			}
		})
	}
}
