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

import scala.collection.immutable.Map
import java.awt.{Component, Graphics}

/**
 * A decorator for animtion that allows switching between several other animations at will.
 * 
 * @author Raymond Dodge
 * @version 18 May 2011 - moved from {@code com.rayrobdod.boardGame} to {@code com.rayrobdod.animation}
 * @version 22 May 2012 - not extends and uses RenderableAnimation rather than Animation
 */
class StateMachineAnimation(val stateFrames:Map[Object, RenderableAnimation]) extends RenderableAnimation
{
	var currentState:RenderableAnimation = stateFrames.head._2
	var currentImage:Int = 0;
	
	def changeState(state:Object) {
		currentState = stateFrames(state);
	}
	
	def currFrameLength = currentState.currFrameLength
	def incrementFrame = currentState.incrementFrame
	def width = currentState.width
	def height = currentState.height
	
	def paintCurrentFrame(c:Component, g:Graphics, x:Int, y:Int) =
			currentState.paintCurrentFrame(c,g,x,y)
}
