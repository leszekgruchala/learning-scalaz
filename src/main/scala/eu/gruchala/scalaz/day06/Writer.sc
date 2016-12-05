import scalaz._, Scalaz._

//To attach a monoid to a value
scalaz.Writer


//scalaz.WriterOps
val w = 3.set("Smalish gang") //attaches value to monoid (3) building a tuple, WriterT[String, Int]
w.run._1 //(String, Int)
w.written //String
w.value //Int

"asd".tell //WriterT[String, Unit]

//MonadTell[Writer, String].point(3).run //WriterT[String, Int] = ("", 3)

def logNumber(x: Int): Writer[List[String], Int] =
  x.set(List("Got number: " + x.shows))

def multiWithLog: Writer[List[String], Int] = for {
  a <- logNumber(3)
  b <- logNumber(5)
} yield a * b

//multiWithLog.run //(List(Got number: 3, Got number: 5),15)


//List uses ++ to mappend (to end of list), it's inefficient and may take log for long lists
//Use Vector instead, it has near constant time for all operations
//Vector is a Tree structure

def vectorFinalCountDown(x: Int): Writer[Vector[String], Unit] = {
  import annotation.tailrec
  @tailrec
  def doFinalCountDown(x: Int, w: Writer[Vector[String], Unit]): Writer[Vector[String], Unit] =
    x match {
      case 0 => w >>= (_ => Vector("0").tell)
      case i => doFinalCountDown(i - 1, w >>= { _ => Vector(i.shows).tell })
    }

  val t0 = System.currentTimeMillis()
  val r = doFinalCountDown(x, Vector.empty[String].tell)
  val t1 = System.currentTimeMillis()
  r >>= { _ => Vector((t1 - t0).shows + " msec").tell }
}

def listFinalCountDown(x: Int): Writer[List[String], Unit] = {
  import annotation.tailrec
  @tailrec
  def doFinalCountDown(x: Int, w: Writer[List[String], Unit]): Writer[List[String], Unit] =
    x match {
      case 0 => w >>= (_ => List("0").tell)
      case i => doFinalCountDown(i - 1, w >>= { _ => List(i.shows).tell })
    }

  val t0 = System.currentTimeMillis()
  val r = doFinalCountDown(x, List.empty[String].tell)
  val t1 = System.currentTimeMillis()
  r >>= { _ => List((t1 - t0).shows + " msec").tell }
}

vectorFinalCountDown(10000).run._1.last //227 msec
listFinalCountDown(10000).run._1.last   //803 msec

//Reader monad lets us pretend the value is already there
scalaz.Reader

//Both (*2) and (+10) get applied to the number 3 in this case.
// return (a+b) does as well, but it ignores it and always
// presents a+b as the result. For this reason,
// the function monad is also called the reader monad.
// All the functions read from a common source.
val addStuff: Int => Int = for {
  a <- (_: Int) * 2 //it's a functor
  b <- (_: Int) + 10
} yield a + b

addStuff(3)

