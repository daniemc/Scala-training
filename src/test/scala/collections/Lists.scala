import org.scalatest._

class Lists extends FunSuite {

    test("list definition with inferred type") {        
        val myList = List(1, 2, 3)
        assert(List(1, 2, 3) == myList)
    }

    test("list definition with explicit value") {
        val myStrList : List[String] = List("this", "is", "a", "list");
        assert(List("this", "is", "a", "list") == myStrList)
    }

    test("lists can contain multiple types") {
        val multiList = List(1, "str1", 2, "str2")
        assert(List(1, "str1", 2, "str2") == multiList)
    }

    test("define a list element by element") {
        // note that should end with 'Nil'
        val myList = 1 :: 2 :: 3 :: Nil
        assert(List(1, 2, 3) == myList)

        // or List()
        val myList2 = 1 :: 2 :: 3 :: List()
        assert(List(1, 2, 3) == myList2)
    }

    test("define a list element by element fail (without 'Nil' at the end)") {        
        
        assertDoesNotCompile("val myList = 1 :: 2 :: 3")
    
    }

    test("append elements to a lists") {
        var list = List(1, 2 , 3)
        var list2 = list :+ 4
        assert(List(1, 2, 3, 4) == list2)
    }

    test("prepend alements to a list") {
        var list = List(1, 2, 3)
        var list2 = 4 +: list

        // can do it in this way too 
        var list3 = 4 :: list

        assert(List(4, 1, 2, 3) == list2)
        assert(List(4, 1, 2, 3) == list3)
    }

    test("take first element of a list") {
        var list = List(1, 2, 3)
        var firstElement = list.head
        assert(1 == firstElement)
    }

    test("take first element of a empty list"){
        var list = List()
        
        // head must fail
        assertThrows[NoSuchElementException] {
            var firstElement = list.head
        }

        // secure way 'headOption'
        var firstElement = list.headOption
        assert(None == firstElement)
    }

    test("take elements in tail of a list") {
        var list = List(1, 2 , 3)
        var tailElements = list.tail
        assert(List(2 , 3) == tailElements)
    }

    test("take elements in tal of a empty list") {
        var list = List()

        // must fail with UnsupportedOperationException
        assertThrows[Exception] {
            list.tail
        }
    }

    test("drop elements of a list") {
        var list = List(1, 2, 3, 4, 5)
        var resultList = list.drop(3)

        assert(List(4, 5) == resultList)

        // note that 'drop()' delete elements berfore 
        // the given element inlcuding the element
        // the default droped elements side is left
    }

    test("drop rigth elements of a list starting in a given element") {
        var list = List(1, 2, 3, 4, 5)
        var resultList = list.dropRight(3)

        assert(List(1, 2) == resultList)
    }

    test("transform list elements") {
        var list = List(1, 2, 3, 4, 5)

        // you can transform list's elements with map
        var resultList = list.map(el => el * 2)

        assert(List(2, 4, 6, 8, 10) == resultList)
    }

    test("transform list elements with a given fucntion") {
        def myFunction = (str : String) => str + " added str"
        var list = List("text1", "text2", "text3", "text4")

        var resultList = list.map(str => myFunction(str))

        assert(
            List("text1 added str","text2 added str",
                 "text3 added str","text4 added str") 
            == 
            resultList
            )
    }



}