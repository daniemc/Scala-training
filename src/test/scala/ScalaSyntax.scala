import org.scalatest._;

class ScalaSyntax extends FunSuite {

  test("this is a test") {
    var x = 1
    assert(x == 1)
  }

  test("var & val types can be inferred") {
    var x = 1
    var z = "str"
    
    assert(x == 1)
    assert(z == "str")
  }

  test("inferred values are hard typed") {
    var x = 1
    var z = "str"

    assert(x !== "1")
    assertDoesNotCompile("z = 2")
  }

  test("you can assign type if you want to") {
    var x : Int = 1 + 2
    var z : String = "Hi"

    assert(3 === x)
    assert("Hi" == z)
  }

  test("var can be changed after declaration") {
    var num = 1;
    num = 2
    num = 3
    assert(num == 3)
  }

  test("val can't be changed after declaration") { 
    val num2 = 1
    assertDoesNotCompile("num2 = 3")
  }

  test("named inline function declaration") {
    val add = (num1 : Int, num2 : Int) => num1 + num2
    assert(4 == add(2, 2))
  }

  test("anonymous inline function declaration") {  

    //function doesn't have name     
    (num : Int) => num % 2 == 0

    assertCompiles("(num : Int) => num % 2 == 0")
  }

  test("named block function declaration") {
    
    val add = (num1 : Int, num2 : Int) => {
      num1 + num2
    }   

    assert(4 == add(2, 2))
  }

  test("can't use return statement in function block") {
    val badBlock = (num1 : Int, num2 : Int) => {    
      assertDoesNotCompile("return num1 + num2")      
    }
  }

  test("function without params") {
    val anyNum = () => 27
    var anyStr = () => "yep!"

    assert(27 == anyNum())
    assert("yep!" == anyStr())
  }  

  test("methods definition") {
    //yes, methods are a little different than functions

    // block methods def
    def add(num1 : Int, num2 : Int) : Int = {
      num1 + num2
    }

    // inline methods def
    def substract(num1 : Int, num2 : Int) : Int = num1 - num2

    assert(5 == add(2, 3))
    assert(2 == substract(5, 3))
  
  }

  test("multi parameter methods") {
    
    def addAndThenDivide(numAdd1 : Int, numAdd2 : Int)(numDivider : Int) = (numAdd1 + numAdd2) / numDivider

    def concat(str1 : String)(str2 : String)(str3 : String)(str4 : String) = {
      str1 + " " + str2 + " " + str3 + " " + str4
    }

    val result = addAndThenDivide(10, 10)(2)
    var result2 = concat("Hi,")("you")("can")("concat")

    assert(10 == result)
    assert("Hi, you can concat" == result2)
  }

  test("object tests") {
    
    object myObj {

      // I can define vars inside objects
      var num1 = 1
      var num2 = 1

      // I can define function inside objects 
      def myObjFunc(num3 : Int, num4 : Int) = {
        val res = num1 + num2 + num3 + num4

        // doesn't need return statement
        res
      }

      def anotherFunc(num5 : Int, num6 : Int) = {
        val res = num5 + num6
        res
      }
    }

    // I don't need create object with 'new' word
    // I can call functions directly with dot notation
    assert(6 === myObj.myObjFunc(2, 2))

    // I can call a function inside a object without dot notation (note the parentheses)
    assert(2 === (myObj anotherFunc(1, 1)))
  }

  test("scala classes") {

    // I can pass params to classes
    class myClass(num : Int){

      // def function with param & use class param to return value
      def myFunc1(num2 : Int) = num + num2
      def myFunc2(num3 : Int) = num + num3

      // def function without params
      def myFunc3 = num + 3
      def myFunc4 = num + 4

    }

    // I need the word 'new' to create class instance
    var classInst = new myClass(1);

    var result1 = classInst.myFunc1(1);
    var result2 = classInst.myFunc2(2);
    var result3 = classInst.myFunc3;
    var result4 = classInst.myFunc4;

    assert(2 == result1)
    assert(3 == result2)
    assert(4 == result3)
    assert(5 == result4)

  } 

  test("scala classes default value params") {

    // note that you need to add 'var' 
    class DefaultClass(var num1 : Int = 0, var num2 : Int = 5)
    
    class DefaultClass2(num1 : Int = 0, num2 : Int = 5)

    var mydef = new DefaultClass
    var mydef2 = new DefaultClass2

    assert(0 == mydef.num1)
    assert(5 == mydef.num2)

    // without var can't access value, compile error
    assertDoesNotCompile("0 == mydef2.num1")
  }

  test("class state can be changed") {

    // if I don't want to, I don't need to pass params to classes
    class otherClass{
      var num = 0;

      def func(numIn : Int) = num + numIn 
      def func2(numIn2 : Int) = num + numIn2 

      def changeNumFunction(numToreplace : Int) = {
        num = numToreplace
      }
    }

    var myOtherClass = new otherClass()
    var anotherResult1 = myOtherClass.func(1)

    // can change it directly
    myOtherClass.num = 2
    var anotherResult2 = myOtherClass.func2(1)

    // can change it by a function
    myOtherClass.changeNumFunction(5)

    //without dot notation (note without parentheses)
    var anotherResult3 = myOtherClass func(5)

    assert(1 == anotherResult1)
    assert(3 == anotherResult2)
    assert(10 == anotherResult3)

  }

  test("case classes are good to model immutable data") {
    case class Dog(name : String, age : Int)

    // doen't need 'new' word to instantiate case class
    val myDog = Dog("no have", 0)

    assert("no have" == myDog.name)
  }

  test("can't change dog attr because val def ") {
    case class Dog(name : String, age : Int)

    val myDog = Dog("no have", 0)

    assertDoesNotCompile("myDog.age = 5")
  }

  case class Cat(name : String, age : Int){
    var inName = name
    var inage = age
  }

  test("changin case classes state") {

    // you can change case class with var , but God is watching you
    val myCat = Cat("no have", 0)
    assert("no have" == myCat.inName)
    myCat.inName = "new name"
    assert("new name" == myCat.inName)
    
    // you can't change case class params any way
    assertDoesNotCompile("myCat.age = 5")
       
  }

  test("can copy case class and change params in new class") {
    val myCat = Cat("no have", 0)

    val myCopyCat = myCat.copy(myCat.name, 5)
    assert(myCopyCat.age == 5)  
  }

  //traits can hold definitions 
  trait Trait1 {
    def def1(num : Int) : Int
  }

  trait Trait2 {
    def def2(str : String) : String
  }

  test("traits 1") {    

    // classes can extends traits and implements its definitions
    class Class1 extends Trait1 with Trait2 {
      override def def1(num : Int) = num + 5
      override def def2(str : String) = "Hi, " + str
    }

    var result = new Class1
    assert(10 == result.def1(5))
    assert("Hi, there" == result.def2("there"))
  }

  test("traits 2") {   

      // traits should be implemented
      class Class2 extends Trait1 with Trait2 {
        override def def1(num : Int) = num + 5
        override def def2(str : String) = ???        
      }
      
      var result2 = new Class2
      result2.def1(2)
      
      // when call a method not implemented in class, 
      // it fires a throwable
      assertThrows[NotImplementedError] {
        result2.def2("again")
      }

  }

  test("scala matching") {
    def matchTest( num : Int) : String = num match {
      case 100 => "hundred"
      case 200 => "two hundred"
      case 300 => {
        var str = "here can goes anything"
        str = "three hundred"
        // no return statement needed
        str
      }
      case 400 => {
        var str = "here goes anything"
        str = "four hundred"
        // no return statement needed, but i can be explicit
        return str
      }
      case _ => "default"
    }

    val matchResult1 = matchTest(200)
    val matchResult2 = matchTest(300)
    val matchResult21 = matchTest(400)
    val matchResult3 = matchTest(50)
    val matchResult4 = matchTest(500)

    assertResult("two hundred")(matchResult1)
    assertResult("three hundred")(matchResult2)
    assertResult("four hundred")(matchResult21)
    assertResult("default")(matchResult3)
    assertResult("default")(matchResult4)
  }





}
