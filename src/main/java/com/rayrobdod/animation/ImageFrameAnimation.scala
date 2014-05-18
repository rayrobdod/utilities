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

import scala.collection.immutable.Seq
import java.awt.Image
import java.awt.{Component, Graphics}
import com.rayrobdod.util.BlitzAnimImage

/**
 * @author Raymond Dodge
 * @version 2014 May 18
 */
final class ImageFrameAnimation(
			frames:Seq[_ <: Image],
			frameLengths:Seq[Int],
			repeat:Boolean = false
) extends RenderableAnimation
{
	def this(frames:Seq[_ <: Image], frameLength:Int, repeat:Boolean)
	{
		this(frames, Seq.fill(frames.length){frameLength}, repeat);
	}
	def this(frames:BlitzAnimImage, frameLengths:Seq[Int], repeat:Boolean)
	{
		this(Seq.empty ++ frames.getImages, frameLengths, repeat);
	}
	def this(frames:BlitzAnimImage, frameLength:Int, repeat:Boolean)
	{
		this(Seq.empty ++ frames.getImages, frameLength, repeat);
	}
	
	private var currentFrame = 0;
	
	private val frames2:Seq[Image] = frames
	frames2.forall{_.getWidth(null) == frames(0).getWidth(null)}
	frames2.forall{_.getHeight(null) == frames(0).getHeight(null)}
	
	override val width = frames(0).getWidth(null);
	override val height = frames(0).getHeight(null);
	override def paintCurrentFrame(c:Component, g:Graphics, x:Int, y:Int) = {
		g.drawImage(frames(currentFrame), x, y, null)
	}
	override def currFrameLength = frameLengths(currentFrame)
	
	override def incrementFrame() {
		currentFrame = currentFrame + 1
		if (repeat) {
			currentFrame = currentFrame % frames.length
		} else {
			running = currentFrame < frames.length
		}
	}
	
}
