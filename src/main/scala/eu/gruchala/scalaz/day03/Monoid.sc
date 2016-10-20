import scalaz._, Scalaz._

//Provides an identity element (`zero`)
// to the binary `append` operation from scalaz.Semigroup.

//scalaz.Monoid // `zero`
//scalaz.Semigroup // `append`
//scalaz.SemigroupOps // `|+|`, `mappend` is the binary function. It takes two values of the same type and returns a value of that type as well.

List(1, 2, 3) |+| List(4, 5, 6)

Monoid[List[Int]].zero
Monoid[String].zero

Tags.Multiplication(10) |+| Monoid[Int @@ Tags.Multiplication].zero //10
Tags.Multiplication(10) |+| Tags.Multiplication(2) //20
10 |+| Monoid[Int].zero //10

Monoid[Boolean @@ Tags.Disjunction].zero //false //it's like `or`
Monoid[Boolean @@ Tags.Conjunction].zero //true // it's like `and`

Tags.Disjunction(true) |+| Tags.Disjunction(false)
Monoid[Boolean @@ Tags.Disjunction].zero |+| Tags.Disjunction(true)
Monoid[Boolean @@ Tags.Disjunction].zero |+| Monoid[Boolean @@ Tags.Disjunction].zero


Tags.Conjunction(true) |+| Tags.Conjunction(false)
Monoid[Boolean @@ Tags.Conjunction].zero |+| Tags.Conjunction(true)
Monoid[Boolean @@ Tags.Conjunction].zero |+| Monoid[Boolean @@ Tags.Conjunction].zero

Monoid[Ordering].zero //EQ
(Ordering.LT: Ordering) |+| (Ordering.GT: Ordering)
(Ordering.GT: Ordering) |+| (Ordering.LT: Ordering)
(Ordering.LT: Ordering) |+| (Ordering.LT: Ordering)
(Ordering.GT: Ordering) |+| (Ordering.GT: Ordering)
Monoid[Ordering].zero |+| (Ordering.GT: Ordering)
Monoid[Ordering].zero |+| (Ordering.LT: Ordering)


//compare two Strings in both length and alphabetically
def lengthCompare(lhs: String, rhs: String): Ordering =
  (lhs.length ?|? rhs.length) |+| (lhs ?|? rhs)

lengthCompare("zen", "ants") //LT "zen" is shorter
lengthCompare("zen", "ant") //GT "zen" is shorter, but first alphabetically
lengthCompare("zen", "zen") //EQ
lengthCompare("zen", "zem") //GT lenght is same, but "zen" is first alphabetically
lengthCompare("zem", "zen") //LT
