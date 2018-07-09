package co.com.s4n.testingscala.monads

import org.scalatest._

class Option extends FunSuite {

  test("ways to create an option") {
    val option1 = Some(1)
    val option2 = Some{ 1 }    

    assert(Some(1) == option1)
    assert(Some(1) == option2)   

  }

  test("you can define an Option empty") {
    var opt = Some()

    // you can, but isn't the better way and will be deprecated
    assert(Some() == opt)
  }

  test("the better way to define an empty option is with None") {
    var opt = None

    assert(None == opt)
  }

  test("it's not safe get option value with 'get' '") {
    val opt1 = Some()
    val opt2 = None

    assertThrows[NoSuchElementException] {      
      opt2.get
    }

    // for any reason, this doesn't fail and get () as value
    val res = opt1.get
    assert(() == res)
    println(s"equals to $res")
  }

  // function to cast a string to Int or get none if not posible
  def toInt(str: String) = {
    try {
      Some(Integer.parseInt(str.trim))
    } catch {
      case e : Exception => None
    }
  }

  test("safe way to access to option value") {
    val strNum = toInt("1").getOrElse(0)
    val strNoNum = toInt("NAN").getOrElse(0)
    
    assert(1 == strNum)
    assert(0 == strNoNum)
  
  }

  test("you can use match in option value") {
    val strNum1 = toInt("1")
    val strNum2 = toInt("NAN")

    val strRes1 = strNum1 match {
      case Some(i) => s"this is $i"
      case None => "strNum couldn't be parsed"
    }

    val strRes2 = strNum2 match {
      case Some(i) => s"this is $i"
      case None => "strNum couldn't be parsed"
    }

    assert(strRes1 == "this is 1")
    assert(strRes2 == "strNum couldn't be parsed")

  }

  test("you can use foreach in option") {
    toInt("1").foreach{ i =>
        println(s"Got an int: $i")
    }

    assert(true)
  }

  test("you can use option over collections") {
    val strList = List("1", "Ja", "2", "No", "3")

    val optList = strList.map(toInt)

    assert(List(Some(1), None, Some(2), None, Some(3)) == optList)

    // whe can remove None values
    assert(List(1, 2, 3) == optList.flatten)

    // or use directly flatMap
    assert(List(1, 2, 3) == strList.flatMap(toInt))
  }

  test("you can know if a option is defined or not") {
    val strList = List("1", "Ja", "2", "No", "3")

    val optList = strList.map(toInt)

    assert(optList(0).isDefined)
    assert(false == optList(1).isDefined)
  }

  test("for comprenhension in Option") {
    val num1 = Some(10)
    val num2 = Some(17)

    val res = for {
      x <- num1
      y <- num2
    } yield x + y

    assert(Some(27) == res)
  }

  test("use map and flatMap with option") {
    val str1 = Some("Bla")
    val str2 = Some("Hi")

    val resStr1 = str1.map(str => str + "ck")
    val resStr2 = str2.flatMap(str => Option(str + ", Scala"))

    assert(Some("Black") == resStr1)
    assert(Some("Hi, Scala") == resStr2)
  }

  test("you can filter an option") {
    val num1 = Some(2)
    val num2 = Some(4)

    val resNum1 = num1.filter(num => num < 3)
    val resNum2 = num2.filter(_ < 3)

    assert(Some(2) == resNum1)
    assert(None == resNum2)
  }

  test("you can use fold with option") {
    val num1 = Some(10)

    val res = num1.fold(0)(num => num  + 7)
    
    assert(17 == res) 

    // for any reason the default value doesn't affect the result
    val res2 = num1.fold(6546)(num => num  + 7)

    assert(17 == res2)

    // and doesn't compile with acummulator
    assertDoesNotCompile("num1.fold(10)((accum, el) => accum + el)")

  }


}