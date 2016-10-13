import scalaz._
import Scalaz._
import scala.language.higherKinds

//scalaz.Applicative, scalaz.ApplicativeSyntax
//Applicative - takes a value and puts it in some sort of context
//With Functor we can map functions with one parameter, what about more?

//Applicative extends Apply
1.pure[List]
1.point[List]
1.point[Option] map (_ + 2)

//Whereas fmap takes a function and a functor and applies the function
// inside the functor value, <*> takes a functor that has a function in it
// and another functor and extracts that function from the first functor
// and then maps it over the second one.
9.some <*> ((_: Int) + 3).some //combines both
9.some *> ((_: Int) + 3).some //returns right
9.some <* ((_: Int) + 3).some //returns left

// ^ extracts values from containers and apply them to a single function:
^(3.some, 5.some)(_ + _)
^(4.some, none[Int])(_ * _)
(3.some |@| 4.some)(_ + _)

List(1, 2, 3) <*> List((_: Int) * 0, (_:Int) + 100, (x:Int) => x * x)
List(3, 4) <*> { List(1, 2) <*> List({(_: Int) + (_: Int)}.curried, {(_: Int) * (_: Int)}.curried) }


val applyOverOption = Apply[Option].lift2((_: Int) :: (_: List[Int]))
applyOverOption(3.some, List(4).some)

//Let’s try implementing a function that takes a list of applicatives and
// returns an applicative that has a list as its result value.
// We’ll call it sequenceA.
def sequenceA[F[_]: Applicative, A](list: List[F[A]]): F[List[A]] = list match {
  case Nil => (Nil: List[A]).point[F]
  case x :: xs => (x |@| sequenceA(xs)) (_ :: _)
}
sequenceA(List(1.some, 2.some, none, 4.some))
sequenceA(List(1.some, 2.some, 4.some))
sequenceA(List(List(1, 2, 3), List(4, 5, 6)))
