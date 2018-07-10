package co.com.s4n.testingscala.monads

import org.scalatest._

class Either extends FunSuite {

  test("an either can be right") {
    var rightEither = Right(1)
    assert(rightEither.isRight)
  }

  test("an either can be left") {
    var leftEither = Left(1)
    assert(leftEither.isLeft)
  }

  def div(num1: Int, num2: Int) = {
    if (num2 == 0) Left("Can't divide by 0") 
    else Right(num1/num2)
  }

  test("testing with a function that can return right or left") {
    var div1 = div(4, 2)
    var div2 = div(2, 0)

    assert(div1.isRight)
    assert(div2.isLeft)
    assert(Right(2) == div1)
    assert(Left("Can't divide by 0") == div2)
  }

  test("you can use match with either") {
    var div1 = div(4, 2)

    div1 match {
      case Right(value) => assert(2 == value)
      case Left(err) => assert("Can't divide by 0" == err)
    }
  }

  test("difference between either and option") {
    // in option you can't use none to contain value
    assertDoesNotCompile("val optNone = None(2)")
    // and as you could see, with either you can
    assertCompiles("val optNone = Left(2)")
  }

}