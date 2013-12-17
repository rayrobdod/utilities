package com.rayrobdod.testing.util.services

import com.rayrobdod.util.services.Services.{readServices}
import java.util.ServiceConfigurationError
import org.scalatest.{FunSpec, BeforeAndAfter}
import scala.collection.JavaConversions.enumerationAsScalaIterator
import scala.collection.JavaConversions.iterableAsScalaIterable

/**
 * @author Raymond Dodge
 * @version 2013 Dec 17
 */
class ServicesTest extends FunSpec
{
	describe ("java.util.ServiceLoader") {
		it ("Should return an empty list for a non-existant file") {
			val value = java.util.ServiceLoader.load(classOf[ServiceConfigurationError])
			assertResult(0)(value.toSeq.length)
		}
		it ("Should read a line from a file") {
			val value = java.util.ServiceLoader.load(classOf[ServicesTest])
			assertResult(1)(value.toSeq.length)
		}
	}
	describe ("The readServices method") {
		it ("Should return an empty list for a non-existant file") {
			val serviceName = classOf[ServiceConfigurationError].getName()
			assertResult(0)(readServices(serviceName).length)
		}
		it ("Should read a line from a file") {
			val serviceName = classOf[ServicesTest].getName()
			assertResult(1)(readServices(serviceName).length)
		}
		it ("Should return an empty file, return an empty list") {
			val serviceName = "com.rayrobdod.testing.util.services.ServicesTest1"
			assertResult(0)(readServices(serviceName).length)
		}
		it ("Should return a list of elements, one per line in the file") {
			val serviceName = "com.rayrobdod.testing.util.services.ServicesTest2"
			val result = Seq("first", "second", "third")
			
			assertResult(result.toList)(readServices(serviceName).toList)
		}
		it ("Should ignore comments and blank lines") {
			val serviceName = "com.rayrobdod.testing.util.services.ServicesTest3"
			val result = Seq("first", "second", "third")
			
			assertResult(result.toList)(readServices(serviceName).toList)
		}
	}
}
