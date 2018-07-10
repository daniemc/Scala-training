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

  test("a failure can be recovered [recover]") {
    val div1 = divide(12, 0).recover {    
      case e: Exception => "Division Error"
    }

    assert(Success("Division Error") == div1)
  }

  test("a failure can be recovered [recoverWith]") {

    // with recoverWith i must be explicit with the return type 
    val div1 = divide(12, 0).recoverWith {    
      case e: Exception => Success("Division Error")
    }

    val div2 = divide(12, 0).recoverWith {
      case e: Exception => divide(0, 1)
    }

    assert(Success("Division Error") == div1)
    assert(Success(0) == div2)
  }

  test("a try can be converted to option") {
    val tryToOpt1 = divide(4, 2)
    val tryToOpt2 = divide(4, 0)

    val opt1 = tryToOpt1.toOption
    val opt2 = tryToOpt2.toOption

    assert(Some(2) == opt1)
    assert(None == opt2)
  }
  
  test("you can chain success in for comprenhension") {
    val divSum = for {
      x <- divide(8, 8)
      y <- divide(8, 4)
      z <- divide(8, 2)
    } yield x + y + z

    assert(Success(7) == divSum)
  }

  test("if on element in a for comph is failure, the result us Failure") {
    val divSum = for {
      x <- divide(8, 8)
      y <- divide(8, 0)
      z <- divide(8, 2)
    } yield x + y + z

    assert(divSum.isFailure)

  }
  
  // testing monads laws

  test("law 1 : left identity") {

    // if you have a value
    val num: Int = 1

    // and a box (Try) that takes the same type of the value (Int)
    // and put the value inside the box
    val tryNum: Try[Int] = Try(num)

    // and you have a function that takes the same type of the value (Int)
    // and returns the same type of the box (Try: [Success/None])
    val sum = (x: Int) => Success(x + 1)

    // then, doing flatMap to the box (Try tryNum) 
    // or applying it to the function (sum)
    assert(tryNum.flatMap(sum) == sum(num))

    // should return the same result
    println("Law 1 : Left identity")
    println(tryNum.flatMap(sum))
    println(sum(num))
  }

  test("law 2 : right identity") {

    // if you have a value
    val num: Int = 1

    // and a box (Try) that takes the same type of the value (Int)
    // and put the value inside the box
    val tryNum: Try[Int] = Try(num)
    

    // and you have a function that takes the same type of value 
    // and wraps it in the same kind of box untouched
    assert(tryNum.flatMap(x => Success(x)) == tryNum)

    // then after flatMapping that function on your box should not change it
    println("Law 2 : Right identity")
    println(tryNum.flatMap(x => Success(x)))
    println(tryNum)

  }

  test("law 3 : associativity") {
    // if you have a value
    val num: Int = 1

    // and a box (Try) that takes the same type of the value (Int)
    // and put the value inside the box
    val tryNum: Try[Int] = Try(num)

    // and a chain of functions that operates on it (monad)
    val sum = (x: Int) => Success(x + 1)
    val subs = (x: Int) => Success(x - 1)

    // then it should not matter how you nest the flatMappings of those functions.
    assert(
      tryNum
        .flatMap(sum)
        .flatMap(subs) 
      == 
      tryNum
        .flatMap(x => sum(x)
          .flatMap(subs)
        )
    )

    // it should return the same
    println("Law 3 : Associativity")
    println(
      tryNum
        .flatMap(sum)
        .flatMap(subs) 
    )
    println(
      tryNum
        .flatMap(x => sum(x)
          .flatMap(subs)
        )
    )
  }

  

}
