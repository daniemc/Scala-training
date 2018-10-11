package co.com.s4n.testingscala.monads

import org.scalatest._

import java.util.concurrent.Executors
import scala.language.postfixOps
import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

class FutureSuite extends FunSuite {

  def sleep(time: Long) { Thread.sleep(time) }
  def rand(lim: Int): Int = { Random.nextInt(lim) }
  def time = { System.nanoTime() }
  
  test("define a future") {    
    
    println(s"Here it starts $time")
    
    val hi = Future {
      sleep(1000)
    
      println(s"This is the future")
      "Hi from future"
    }
    
    println(s"This is some operation")
    val someOpr = 1 + 1

    // you shouldn't do this, (blocking is bad)
    val result = Await.result(hi, 2 seconds)
    
    assert("Hi from future" == result)

  }

  test("you shouldn't block, use callback instead to get future value") {
      println("-------------------------------------------")
      println("Starts Calc")
      val futureCalc = Future{
        sleep(1000)
        1 + 2
      }

      println("onComplete future")
      futureCalc.onComplete{
        case Success(value) => println(s"Future result: $value")
        case Failure(err) => println("Error on future")
      }

      // let's do some extra things
      println("Job 1"); sleep(400)
      println("Job 2"); sleep(400)
      println("Job 3"); sleep(400)
      println("Job 4"); sleep(400)
      println("Job 5"); sleep(400)      

      assert(futureCalc.isCompleted) 
      
  }

  test("you can validate success future exec") {
    println("-------------------------------------------")
      println("Starts Calc")
      val futureCalc = Future{
        sleep(1000)
        1 + 2
      }

    // validate on success
    // this method will be deprecated
    futureCalc onSuccess {
      case value => println(s"Success!, value : $value")
    }   

    // let's do some extra things
      println("Job 1"); sleep(400)
      println("Job 2"); sleep(400)
      println("Job 3"); sleep(400)
      println("Job 4"); sleep(400)
      println("Job 5"); sleep(400)      

      assert(futureCalc.isCompleted)
  }

  test("you can validate failure future exec") {
    println("-------------------------------------------")
      println("Starts Calc")
      val futureCalc = Future{
        sleep(1000)
        1 / 0
      }

    // validate on failure
    // this method will be deprecated
    futureCalc onFailure {
      case err => println(s"Error!, message: ${ err.getMessage }")
    }

    // let's do some extra things
      println("Job 1"); sleep(400)
      println("Job 2"); sleep(400)
      println("Job 3"); sleep(400)
      println("Job 4"); sleep(400)
      println("Job 5"); sleep(400)      

      assert(futureCalc.isCompleted)

  }

  def calculations(num: Int) = Future{
    println(s"start calc: $num")
    sleep(rand(300))
    println(s"Do calc")
    num + rand(10)
  }

  test("trigger futures with for comp") {
    println("-------------------------------------------")
    println("Start calculations")
    val future1 = calculations(1)
    val future2 = calculations(2)
    val future3 = calculations(3)

    println("start for comph")
    val result = for {
      r1 <- future1 
      r2 <- future2
      r3 <- future3
    } yield (r1 + r2 + r3)

    result onComplete {
      case Success(value) => println(s"result was $value")
      case Failure(err) => println(s"Error: message: ${ err.getMessage }")
    }

    // keep JVM working
    sleep(2000)

    result map(value => assert(0 < value))
    assert(result.isCompleted)

  }

  test("a failed future can be recovered") {

    val badFuture = Future {
      sleep(300)
      2 / 0
    } recover {
      case e: Exception => "Can't divide by 0"
    }

    val result = Await.result(badFuture, 1 second)

    assert("Can't divide by 0" == result)

  }

  test("you can run any code when a future returns") {

    var sum = 10
    val future = calculations(1) andThen {
      case Success(value) => sum += value
      case Failure(err) => err.getMessage
    }

    val result = Await.result(future, 1 second)

    assert(future.isCompleted)
    assert(sum == result + 10)

  }

  test("a future runs in a different thread") {
    println("-------------------------------------------")

    val mainThread = Thread.currentThread().getName
    var futureThread = ""

    val future = Future {
      sleep(100)
      futureThread = Thread.currentThread().getName
      "Future"
    }

    val result = Await.result(future, 1 second)

    println(mainThread) 
    println(futureThread)
    assert("Future" == result)
    assert(mainThread != futureThread)


  }

  test("you can use map in future to operate") {
    val future1 = Future {
      sleep(500)
      "This comes from "
    }

    var resultFuture = future1.map(str1 => str1 + "Future with map")

    val result = Await.result(resultFuture, 1 second)

    assert("This comes from Future with map" == result)
  }

}