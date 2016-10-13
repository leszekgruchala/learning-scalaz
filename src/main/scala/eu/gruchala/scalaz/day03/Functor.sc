import scalaz._, Scalaz._

//scalaz.Functor, scalaz.FunctorSyntax
//Functor typeclass, which is basically for things that can be mapped over.

(1, 2, 3) map (_ + 1) //applied only to last element of Tuple

val functionAsFunctor = ((x: Int) => x + 1) map (_ * 2)
functionAsFunctor(3)

//lift a function to a functor (f => F[_])
val fList = Functor[List].lift((_: Int) * 2)
fList(List(1, 2, 3))

//we can also override the values in the data structure
List(1, 2, 3) >| "x"
List(1, 2, 3) as "x"

List(1, 2, 3).fpair

List(1, 2, 3).strengthL("x")
List(1, 2, 3).strengthR("x")

List(1, 2, 3).void
