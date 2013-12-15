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
package com.rayrobdod.commonFunctionNotation

import java.util.regex.{Pattern, Matcher}
import scala.collection.immutable.Map
import java.text.ParseException

/**
 * An object that holds a method to parse commonFunctionNotation strings
 * 
 * @author Raymond Dodge
 * @version 04 Jul 2012
 * @version 05 Jul 2012 - parser understands nesting and that there are multiple levels of commas
 */
object Parser
{
	val functionName = "[A-Za-z_][A-Za-z0-9_]*"
	
	val functionPattern:Pattern = Pattern.compile("\\s*(" + functionName + ")\\(([A-Za-z0-9_\\.\\s,\\(\\)]*)\\)\\s*")
	val constantPattern:Pattern = Pattern.compile("\\s*(" + functionName + ")\\s*")
	val integerPattern:Pattern = Pattern.compile("\\s*(-?\\d+)\\s*")
	val numericPattern:Pattern = Pattern.compile("\\s*(-?\\d*\\.\\d*)\\s*")
	
	/**
	 * Parses the string into an object using a series of function calls
	 * 
	 * @param s the string to parse
	 * @param functions a map of function names to functions, by which I mean Function0,
	 			Function1, Function2, etc.
	 * @throws ParseException if an invalid pattern is detected
	 * @throws NoSuchElementException if a function not in the map of functions is called
	 		// @todo change to NoSuchFunctionException ?
	 * @throws ClassCastException if a function is called with variables of the wrong type
	 */
	def parse(s:String, functions:Map[String,Any]):Any = {
		
		val integerMatcher:Matcher = integerPattern.matcher(s)
		val numberMatcher:Matcher = numericPattern.matcher(s)
		val functionMatcher:Matcher = functionPattern.matcher(s)
		val constantMatcher:Matcher = constantPattern.matcher(s)
		
		if (integerMatcher.matches())
		{
			java.lang.Integer.parseInt(integerMatcher.group(1))
		}
		else if (numberMatcher.matches())
		{
			java.lang.Double.parseDouble(numberMatcher.group(1))
		}
		else if (constantMatcher.matches())
		{
			functions(constantMatcher.group(1)) match {
				case x:Function0[_] => x.apply
			}
		}
		else if (functionMatcher.matches())
		{
			val functionName = functionMatcher.group(1)
			val function = functions(functionName)
			
			val paramsString = functionMatcher.group(2)
			val paramsSeq:Seq[String] = {
				//paramsString.split(',')
				val splits:Seq[Int] = -1 +: paramsString.zipWithIndex.foldLeft( ((0,Seq.empty[Int])) ){(levelSplits:(Int,Seq[Int]), charIndex:(Char, Int)) => 
					val (level, split) = levelSplits
					val (char, index) = charIndex 
					
					char match{
						case '(' => (( level + 1, split ))
						case ')' => (( level - 1, split ))
						case ',' => if (level == 0) {(( level, split :+ index))} else levelSplits
						case _ => levelSplits
					}
				}._2 :+ paramsString.length
				
				splits.zip(splits.tail).map({(a:Int, b:Int) => paramsString.substring(a+1,b)}.tupled)
			}
			val params = paramsSeq.map{parse(_, functions)}
			
			val paramsWanted = function match {
				case x:Function0[_] => 0
				case x:Function1[_,_] => 1
				case x:Function2[_,_,_] => 2
				case x:Function3[_,_,_,_] => 3
			}
			
			if (paramsWanted != params.length)
			{
				throw new ParseException(functionName + " wanted " + paramsWanted +
						" paramters, but " + params.length + " parameters were given", functionMatcher.start(2))
			}
			
			try
			{
				function match {
					case x:Function0[_] => x.apply
					case x:Function1[Any,_] => x.apply(params(0))
					case x:Function2[Any,Any,_] => x.apply(params(0), params(1))
					case x:Function3[Any,Any,Any,_] => x.apply(params(0), params(1), params(2))
					//TODO: higher order stuff
				}
			} catch {
				case e:IndexOutOfBoundsException => throw new AssertionError("Earlier length check failed", e)
				case e:ClassCastException => throw e
			}
		}
		else
		{
			throw new ParseException("Command not understood: " + s, 0)
		}
	}
}
