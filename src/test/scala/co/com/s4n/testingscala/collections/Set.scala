package co.com.s4n.testingscala.collections

import org.scalatest._

import scala.collection.immutable.ListSet

class Set extends FunSuite{

  test("create a Set") {
    val mySet = Set(1, 2, 3)

    assert(Set(1, 2, 3) == mySet)
  }

  test("A Set is unordered") {
    val mySet = Set(3, 1, 2, 4)

    assert(Set(1, 2, 3, 4) == mySet)
    assert(Set(4, 3, 2, 1) == mySet)
    assert(Set(2, 4, 1, 3) == mySet)
    assert(Set(3, 1, 4, 2) == mySet)
  }

  test("A Set can't contain duplicate values") {
    val mySet = Set(1, 2, 2, 1)

    assert(Set(1, 2) == mySet)
    assert(2 == mySet.size)
  }

  test("Differences between Set and List") {
    // Differences are in the two lasts tests (1)
    val mySet1 = Set(3, 1, 2, 4)
    var myList1 = List(3, 1, 2, 4)

    // Set is unsorted
    assert(Set(1, 2, 3, 4) == mySet1)
    assert(Set(4, 3, 2, 1) == mySet1)

    // List is sorted
    assert(List(3, 1, 2, 4) == myList1)
    assert(List(1, 2, 3, 4) !== myList1)


    // Differences are in the two lasts tests (2)
    val mySet2 = Set(1, 2, 2, 1)
    val myList2 = List(1, 2, 2, 1)

    // Set can't contain duplicate values
    assert(Set(1, 2) == mySet2)
    assert(2 == mySet2.size)

    // List can contain duplicate values
    assert(List(1, 2, 2, 1) == myList2)
    assert(4 == myList2.length)

  }

  test("A Set can cantain different types of values") {
    val mySet = Set(1, "2", 3, "4")

    assert(Set(1, "2", 3, "4") == mySet)
  }


  test("you can define a set in a range") {
    val mySet = (1 to 5).toSet
    assert(Set(1, 2, 3, 4, 5) == mySet)
  }

  test("you can join Sets") {
    val mySet1 = 1 to 5 toSet
    val mySet2 = 3 to 7 toSet

    val unionSet = mySet1.union(mySet2)

    // note that duplicates were removed
    assertResult(Set(1, 2, 3, 4, 5, 6, 7))(unionSet)
  }

  test("you can intersect Sets") {
    val mySet1 = 1 to 5 toSet
    val mySet2 = 3 to 7 toSet

    val intersectSet = mySet1.intersect(mySet2)

    assertResult(Set(3, 4, 5))(intersectSet)

  }

  test("you can take the difference between sets") {
    val mySet1 = 1 to 5 toSet
    val mySet2 = 3 to 7 toSet

    val intersectSet = mySet1.diff(mySet2)

    assertResult(Set(1, 2))(intersectSet)

    // why 6 & 7 doesn't appear in the result set
  }

  test("Set diff method don't give the same result. it depends on the order") {
    val mySet1 = 1 to 5 toSet
    val mySet2 = 3 to 7 toSet

    val intersectSet1 = mySet1.diff(mySet2)
    val intersectSet2 = mySet2.diff(mySet1)

    assertResult(Set(1, 2))(intersectSet1)
    assertResult(Set(6, 7))(intersectSet2)
  }

  test("sortered sets for access methods") {
    val myListSet = ListSet.empty[Int]
    val otherSet = myListSet + 1 + 4 + 3 + 2

    // the order of insercion only apply for access methods
    assert(otherSet.head==1)
    assert(otherSet.tail.head==4)
    assert(otherSet.tail.tail.head==3)
    assert(otherSet.tail.tail.tail.head==2)

    // if I compare this Set whit another in different order it is equal
    assert(4 != otherSet.head)

    assert(Set(1, 3, 4, 2) == otherSet)

  }



}
