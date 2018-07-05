package co.com.s4n.testingscala.collections

import org.scalatest._

class Map extends FunSuite {

  test("create a map") {
    var myMap = Map(1 -> "a", 2 -> "b", 3 -> "c")

    assertResult(Map(1 -> "a", 2 -> "b", 3 -> "c"))(myMap)
  }

  test("you can't use length in Map") {
    var myMap = Map(1 -> "a", 2 -> "b", 3 -> "c")

    assertDoesNotCompile("myMap.length")

    //instead use size method
    assert(3 == myMap.size)
  }

  test("there can't be two values with the same key") {
    var myMap = Map(1 -> "a", 2 -> "b", 2 -> "B2", 3 -> "c", 3 -> "C2")

    assert(3 == myMap.size)
  }

  test("what happen with Map when there's duplicate keys") {
    var myMap = Map(1 -> "a", 2 -> "b", 2 -> "B2", 3 -> "c", 3 -> "C2")

    var position2 = myMap(2)
    var position3 = myMap(3)

    assert("B2" == position2)
    assert("C2" == position3)

    // this keeps the index but changes the value by the last one
  }

  test("you can have same value woth different index") {
    var myMap = Map(1 -> "AA", 2 -> "BB", 3 -> "AA", 4 -> "BB")

    assert(4 == myMap.size)
  }

  test("access element in a map by its key") {
    var myMap = Map("a" -> 1, "b" -> 27, "c" -> 2)

    var num1 = myMap("b")

    assert(27 == num1)

    // i can use get
    var num2 = myMap.get("b")
    // but it returns an option (convenient)
    assert(Some(27) == num2)
  }

  test("if you try to access a non existing element with get, it returns none") {
    var myMap = Map("a" -> 1, "b" -> 27, "c" -> 2)

    var num1 = myMap.get("d")
    assert(None == num1)
  }

  test(
    "if you try to access a non existing element without get, it returns throwable") {
    var myMap = Map("a" -> 1, "b" -> 27, "c" -> 2)

    assertThrows[NoSuchElementException] {
      var num1 = myMap("d")
    }

  }

  test("loop and operate through map values") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)
    var mapResult = myMap.mapValues(value => value * 2)

    assert(Map("a" -> 2, "b" -> 4, "c" -> 6) == mapResult)
  }

  test("get map head") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)
    var mapHead = myMap.head

    // note that it returns a tuple
    assert(("a" -> 1) == mapHead)
    assert(("a", 1) == mapHead)

    // and i can access it
    assert("a" == mapHead._1)
    assert(1 == mapHead._2)
  }

  test("get map tail") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)
    var mapTail = myMap.tail

    assert(Map("b" -> 2, "c" -> 3) == mapTail)
  }

  test("transform a Map using map") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)

    // need to access like a tuple and return two params
    var mapResult = myMap map (keyValue => (keyValue._1, keyValue._2 + 2))
    assertResult(Map("a" -> 3, "b" -> 4, "c" -> 5))(mapResult)
  }

  test("if you only return one parameter in the map, you'll get a list") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)
    var mapResult = myMap map (keyValue => (keyValue._2 + 2))

    assertResult(List(3, 4, 5))(mapResult)
  }

  test("transform a Map with map with condition") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)
    var mapResult = myMap map (keyValue =>
      (keyValue._1, {
        if (keyValue._1 == "b")
          keyValue._2 * 5
        else
          keyValue._2
      }))

    assertResult(Map("a" -> 1, "b" -> 10, "c" -> 3))(mapResult)
  }

  test("split a map an access it like a tuple") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)

    var splitedMap = myMap.splitAt(2);

    assertResult(Map("a" -> 1, "b" -> 2))(splitedMap._1)
    assertResult(Map("c" -> 3))(splitedMap._2)
  }

  test("split a map in two varibles") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)

    var (map1, map2) = myMap.splitAt(2);

    assertResult(Map("a" -> 1, "b" -> 2))(map1)
    assertResult(Map("c" -> 3))(map2)
  }

  test("you can loop a Map with foreach") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)
    var sum = 0;
    myMap.foreach(x => {
      sum += x._2
    })

    assert(6 == sum)
  }

  test("you can drop elements with the position from left") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)

    var mapResult = myMap.drop(1)
    assert(Map("b" -> 2, "c" -> 3) == mapResult)
  }

  test("you can drop elements with the position from right") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)

    var mapResult = myMap.dropRight(1)
    assert(Map("a" -> 1, "b" -> 2) == mapResult)
  }

  test("you can't drop elements with key") {
    var myMap = Map("a" -> 1, "b" -> 2, "c" -> 3)

    assertDoesNotCompile("myMap.drop(\"a\")")

  }

  test("you can filter a Map") {
    var myMap = Map(1 -> "Tailor",
                    2 -> "Chriss",
                    3 -> "Tania",
                    4 -> "Casper",
                    5 -> "John")

    var namesStartsWithT = myMap.filter(keyValue => keyValue._2.startsWith("T"))
    var namesStartsWithC = myMap.filter(keyValue => keyValue._2.startsWith("C"))

    assert(Map(1 -> "Tailor", 3 -> "Tania") == namesStartsWithT)
    assert(Map(2 -> "Chriss", 4 -> "Casper") == namesStartsWithC)

    // also can filter by its key
    var keyFilter = myMap.filter(keyValue => keyValue._1 % 2 != 0)

    assert(Map(1 -> "Tailor", 3 -> "Tania", 5 -> "John") == keyFilter)
  }

  test("you can filter a Map by its key spesific method") {
    var myMap = Map(1 -> "Tailor",
                    2 -> "Chriss",
                    3 -> "Tania",
                    4 -> "Casper",
                    5 -> "John")

    var spesificKeyFilter = myMap.filterKeys(key => key % 2 != 0)

    assert(Map(1 -> "Tailor", 3 -> "Tania", 5 -> "John") == spesificKeyFilter)
  }

}
