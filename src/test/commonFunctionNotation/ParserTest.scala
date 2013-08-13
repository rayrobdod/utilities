package com.rayrobdod.testing.commonFunctionNotation

import com.rayrobdod.commonFunctionNotation.Parser.{parse => cfnParse}
import org.scalatest.{FlatSpec, BeforeAndAfter}
import scala.collection.immutable.Map
import java.text.ParseException

/**

org.scalatest.tools.Runner -oW -s com.rayrobdod.testing.commonFunctionNotation.ParserTest

 * @author Raymond Dodge
 * @version 04 Jul 2012
 * @version 05 Jul 2012 - it must "parse nested multi-param functions" in
 */
class ParserTest extends FlatSpec
{
	val piValue = 3.14159
	
	val functions = Map(
		"Pi" -> {() => piValue},
		"Neg" -> {(a:Int) => -a},
		"Add" -> {(a:Int, b:Int) => a + b}
	)
	
	behavior of "CommonFunctionNotation Parser"
	
	info("""Given functions: 
    Pi => 3.14159
    Neg(a:Int) => -a
    Add(a:Int, b:Int) => a + b""")
	
	it must "parse integers" in
	{
		val item = 100
		val result = cfnParse(item.toString, functions)
		
		info("Since this is Java, it turns into an Integer")
		assert(result === item)
	}
	
	it must "parse negative integers" in
	{
		val item = -42
		val result = cfnParse(item.toString, functions)
		
		info("Since this is Java, it turns into an Integer")
		assert(result === item)
	}
	
	it must "parse decimal values" in
	{
		val item = .33333
		val result = cfnParse(item.toString, functions)
		
		info("Since this is Java, it turns into a Double")
		assert(result === item)
	}
	
	it must "parse integers with whitespace" in
	{
		val item = -42
		val result = cfnParse("  " + item.toString + "\n", functions)
		
		assert(result === item)
	}
	
	it must "parse constant values without parenthesis" in
	{
		val result = cfnParse("Pi", functions)
		
		assert(result === piValue)
	}
	
	it must "parse constant values with whitespace" in
	{
		val result = cfnParse("  Pi  ", functions)
		
		assert(result === piValue)
	}
	
	ignore should "parse constant values with parenthesis" in
	{
		val result = cfnParse("Pi()", functions)
		
		assert(result === piValue)
	}
	
	it must "parse one-parameter functions" in
	{
		val result = cfnParse("Neg(10)", functions)
		
		assert(result === -10)
	}
	
	it must "parse two-parameter functions" in
	{
		val result = cfnParse("Add(2,3)", functions)
		
		assert(result === 5)
	}
	
	it must "not call two-parameter functions with one parameter" in
	{
		intercept[ParseException]{
			val result = cfnParse("Add(2)", functions)
		}
	}
	
	it must "not call two-parameter functions with three parameters" in
	{
		intercept[ParseException]{
			val result = cfnParse("Add(2,3,4)", functions)
		}
	}
	
	it must "parse nested functions" in
	{
		val result = cfnParse("Add(25,Neg(25))", functions)
		
		assert(result === 0)
	}
	
	it must "parse nested multi-param functions" in
	{
		val result = cfnParse("Add(Add(1,2),Add(3,4))", functions)
		
		assert(result === 10)
	}
	
	it must "parse deeply nested functions" in
	{
		val result = cfnParse("Neg(Neg(Neg(Neg(5))))", functions)
		
		assert(result === 5)
	}
	
	it must "parse nested functions with whitespace" in
	{
		val result = cfnParse("  Add(  25  ,  Neg(  25  )  )  ", functions)
		
		assert(result === 0)
	}
	
	it should "assert that functions recieve parameters of the right type" in
	{
		intercept[ClassCastException]{
			cfnParse("Neg(15.5)", functions)
		}
	}
	
	it must "not allow whitespace between a function name and a parenthesis" in
	{
		intercept[ParseException]{
			cfnParse("Neg  (12)", functions)
		}
	}
	
	it must "not call a function which is not exposed" in
	{
		intercept[java.util.NoSuchElementException]{
			cfnParse("Mult(6,5)", functions)
		}
	}
}
