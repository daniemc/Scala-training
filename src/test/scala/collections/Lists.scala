import org.scalatest._

class Lists extends FunSuite {

    test("list definition with inferred type") {        
        val myList = List(1, 2, 3)
        assert(List(1, 2, 3) == myList)
    }

    test("list definition with explicit type") {
        val myStrList : List[String] = List("this", "is", "a", "list");
        assert(List("this", "is", "a", "list") == myStrList)
    }

    test("lists can contain multiple types of values") {
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

    test("define a list element by element fail without 'Nil' at the end") {        
        
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

    test("take elements in tail of a empty list") {
        var list = List()

        // must fail with UnsupportedOperationException
        assertThrows[UnsupportedOperationException] {
            list.tail
        }
    }    

    test("split a list") {
        var list = List(1, 2, 3, 4)
        var splitedList = list.splitAt(2);
        
        assert(List(1, 2) == splitedList._1)
        assert(List(3, 4) == splitedList._2)
    }

    test("reverse a list") {
        var list = List(1, 2, 3, 4)
        var reversedList = list.reverse

        assert(List(4, 3, 2, 1) == reversedList)
    }

    test("you can serialize a list as String") {
        var list = List(1, 2, 3, 4)

        // you choose delimiter
        var strList1 = list.mkString("-")

        // or not
        var strList2 = list.mkString

        assert("1-2-3-4" == strList1)  
        assert("1234" == strList2)  
    }

    test("you can make some operations with list") {
        var list = List(1, 2, 3, 4)

        var sum = list.sum
        var max = list.max
        var min = list.min 

        assert(10 == sum)
        assert(4 == max)
        assert(1 == min)
    }

    test("you can take elements from a list") {
        var list = List(1, 4, 2, 6, 3, 5, 0, 7, 3, 9, 8)

        // take elements from left
        var firstFourelements = list.take(4)

        // take elements from wight        
        var lastFourElements = list.takeRight(4)

        // take elements while condition is true
        var takeCondition = list.takeWhile(_ > 0)

        assert(List(1, 4, 2, 6) == firstFourelements)
        assert(List(7, 3, 9, 8) == lastFourElements)
        assert(List(1, 4, 2, 6, 3, 5) == takeCondition)

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

    test("transform list elements with a given function") {
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

    test("filter a list") {
        var list = List(1, 2, 3, 4, 5)
        var filteredList = list.filter(el => el % 2 == 0)

        var strList = List("ac", "dc", "ai", "cd")
        var filteredStrList = strList.filter(str => str.startsWith("a"))

        assert(List(2, 4) == filteredList)
        assert(List("ac", "ai") == filteredStrList)
    }

    test("use fold to operate a list") {
        var list = List(1, 2, 3, 4, 5)

        var listSumResult1 = list.fold(0)((accum, el) => accum + el)

        // you can do it many ways
        var listSumResult2 = list.fold(0) {
                (accum, el) => accum + el
            }

        var listSumResult3 = list.fold(0)(_ + _)

        assert(15 == listSumResult1)
        assert(15 == listSumResult2)
        assert(15 == listSumResult3)
    }

    test("test - get average of pair numbers sum") {
        val list = List(1, 2, 3, 4, 6, 7, 8, 9, 10)
        
        var (sum, resultLength) = list
            .filter(num => num % 2 == 0)
            .foldLeft(0, 0)((accum, num) => (accum._1 + num, accum._2 + 1))

        val avg = sum / resultLength
        
        assert(6 == avg)
    }



}