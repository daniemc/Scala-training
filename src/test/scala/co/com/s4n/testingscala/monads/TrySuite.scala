package co.com.s4n.testingscala.monads

import org.scalatest._

import scala.util.{Failure, Success, Try}

class TrySuite extends FunSuite {

  // functions for examples

  def divide(num1: Int, num2: Int) = {
    Try(num1 / num2)
  }

  test("try definition") {
    val num1 = Try(1)

    assert(num1.isSuccess)
    assert(Success(1) == num1)
  }

  test("try failed") {
    val fail = Try(3 / 0)

    assert(fail.isFailure)
  }

  test("you can use match with try") {
    // Success
    val div = divide(12, 3)

    val res = div match {
      case Success(value) => value
      case Failure(e)     => 0
    }

    assert(4 == res)

    // Failure
    val div1 = divide(12, 0)

    val res1 = div1 match {
      case Success(value) => value
      case Failure(e) => 0
    }

    assert(0 == res1)
  }

  test("you can get the Try value") {
    var res = divide(12, 3)
    var num = res.get

    assert(4 == num)
  }

  test("same as option, you can get the Try value safely") {
    var res = divide(12, 0)
    var num = res.getOrElse(0)

    assert(0 == num)
  }

  test("you can get the failure message") {
    var res = divide(12, 0)

    // get it with match
    var msg = res match {
      case Success(v) => v
      case Failure(m) => s"The message is -> $m"
    }

    println(msg)
    assert("The message is -> java.lang.ArithmeticException: / by zero" == msg)

    // get it with toString method
    var msg2 = if (res.isFailure) res.toString else "No error"

    println(msg2)
    assert("Failure(java.lang.ArithmeticException: / by zero)" == msg2)
  }

  test("a failure can be recovered too") {
    // val div1 = divide(12, 0).recover{}
  }

}
