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

import scala.collection.immutable.{Seq, Nil}
import java.lang.System.{currentTimeMillis => currentTime}

/**
 * A controler for an animation. Subclasses determine the model.
 *
 * 
 * @author Raymond Dodge
 * @version 2014 May 13
 */
abstract class Animation extends Runnable
{
	/** the listeners to notify when the frame changes */
	private var frameChangeListeners:Seq[NextFrameListener] = Nil
	/** the listeners to notify when the animation ends */
	private var endAnimationListeners:Seq[AnimationEndedListener] = Nil
	/** the time the previous frame change happened */
	private var lastFrameChange:Long = _
	
	var paused:Boolean = false
	def running:Boolean = _running
	protected def running_=(x:Boolean) = {_running = x}
	private var _running = false
	
	/** The length of time that the current frame should exist for */
	def currFrameLength:Int
	/** Advances to the next frame. */
	def incrementFrame():Any
	
	final def addNextFrameListener(l:NextFrameListener) {
		frameChangeListeners = l +: frameChangeListeners
	}
	final def addAnimationEndedListener(l:AnimationEndedListener) {
		endAnimationListeners = l +: endAnimationListeners
	}
	
	final protected def notifyListenersOfFrameChange(e:NextFrameEvent) =
	{
		frameChangeListeners.foreach{_.frameChanged(e)}
	}
	
	final protected def notifyListenersOfEndAnimation(e:AnimationEndedEvent) =
	{
		endAnimationListeners.foreach{_.animationEnded(e)}
	}
	
	final def run():Unit = {
		running = true;
		
		while (running)
		{
			lastFrameChange = currentTime()
			
			while (!paused) {
				while (lastFrameChange + currFrameLength - currentTime() > 0)
				{
					Thread.sleep(lastFrameChange + currFrameLength - currentTime())
				}
				
				lastFrameChange += currFrameLength
				incrementFrame()
				notifyListenersOfFrameChange(NextFrameEvent(Animation.this))
			}
		}
		
		notifyListenersOfEndAnimation(AnimationEndedEvent(Animation.this))
	}
}

trait NextFrameListener extends java.util.EventListener
{
	def frameChanged(e:NextFrameEvent)
}

case class NextFrameEvent(src:Animation)
		extends java.util.EventObject(src)
{
	override def getSource:Animation = src
}

trait AnimationEndedListener extends java.util.EventListener
{
	def animationEnded(e:AnimationEndedEvent)
}

case class AnimationEndedEvent(src:Animation)
		extends java.util.EventObject(src)
{
	override def getSource:Animation = src
}