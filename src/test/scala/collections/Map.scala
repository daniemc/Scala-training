import org.scalatest._

class Map extends FunSuite {

    test("create a map") {
        var myMap = Map(1 -> "a", 2 -> "b", 3 -> "c")
        
        assertResult(Map(1 -> "a", 2 -> "b", 3 -> "c"))(myMap)
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

    test("if you try to access a non existing element without get, it returns throwable") {
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
        var mapResult = myMap map (keyValue => (keyValue._1 ,{
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

    

}