import org.scalatest._;

class MyFirstScalaTest extends FlatSpec {

  // function deff
  def toUpper( upperList : List[String] ) = upperList.map(str => str.toUpperCase)
  def functionAsParam ( anyList : List[String], myFunc : String => String ) =  anyList.map(myFunc)
  var aList  = List("d", "a", "n", "i")
  var result = toUpper(aList)
  var result2 = functionAsParam(toUpper(aList),  {
    aListString => aListString.toLowerCase()
  })

  "List in result" should "return upper strings" in {
    assertResult(List("D", "A", "N", "I"))(result)
  }

  "List in result" should "return lower strings" in {
    assertResult(List("d", "a", "n", "i"))(result2)
  }

  // filtering

  var filterList = List(1,2,3,4,5,6);
  val newFilteredList =
    filterList
      .filter(num => num % 2 === 0)
      .map(num => num * 2)

  var otherFilteredList =
    filterList
      .filter(num => {
        num % 2 === 0
      })
      .map(num => {
        num * 2
      })

  "new list" should "return list of length 3 and multiply by 2" in {
    assertResult(List(4,8,12))(newFilteredList)
  }

  "other list" should "return list of length 3 and multiply by 2" in {
    assertResult(List(4,8,12))(otherFilteredList)
  }


  // passing params

  var num = 200
  var str = "beers"
  var stringToReplace = "Must buy %d %s"

  "string" should "be equals" in {
    assertResult("Must buy 200 beers")(stringToReplace.format(num, str))
  }

  "string" should "be equals (without dot notation)" in {
    assertResult("Must buy 257 beers")(stringToReplace format (num + 57, str))
  }

  def matchTest( num : Int) : String = num match {
    case 100 => "Cien"
    case 200 => "Docientos"
    case 300 => {
      var str = "aqui va lo que sea"
      str = "Trecientos"
      // no return statement needed
      str
    }
    case 400 => {
      var str = "aqui va lo que sea"
      str = "cuatrocientos"
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

  "match results" should "evaluate" in {
    assertResult("Docientos")(matchResult1)
    assertResult("Trecientos")(matchResult2)
    assertResult("cuatrocientos")(matchResult21)
    assertResult("default")(matchResult3)
    assertResult("default")(matchResult4)
  }

  /*abstract class Term {
    case class Var( str : String) extends Term
    case class Fun( str : String, t : Term) extends Term
    case class App( t1 : Term, t2 : Term) extends Term
  }

  object TermTest extends Term {
    def printTerm( term : Term ): Unit = {
      term match {
        case Var(str) => print(str)
        case Fun(str, t) =>
          print("*" + str + ".")
          printTerm(t)
        case App(t1, t2) =>
          print("(")
          printTerm(t1)
          print(" ")
          printTerm(t2)
          print(")")
      }
    }

    def isIdentityFunc(term : Term) : Boolean = term match {
      case Fun(x, Var(y)) if x == y => true
      case _ => false
    }

    var id = Fun("x", Var("x"))
    var t = Fun("x", Fun("y", App(Var("x"), Var("y"))))

    printTerm(t)
    println
    println(isIdentityFunc(id))
    println(isIdentityFunc(t))
  }*/







}


