import scalaz._, Scalaz._

type Birds = Int
case class Pole(left: Birds, right: Birds) {
  def landLeft(birds: Birds) =
    if(math.abs((left + birds) - right) < 4) copy(left = left + birds).some
    else none

  def landRight(birds: Birds) =
    if(math.abs(left - (birds + right)) < 4) copy(right = right + birds).some
    else none

  def banana: Option[Pole] = none
}


Pole(1, 2).landLeft(1).flatMap(_.landRight(3)).flatMap(_.landLeft(-4))

// >>= is same as flatMap
Monad[Option].point(Pole(2, 2))
Monad[Option].point(Pole(0, 3)) >>= (_.landLeft(-1))
Monad[Option].point(Pole(0, 3)) >>= (_.landLeft(1)) >>= (_.banana)

//Fails because operator >> has precedence over >>=
//Monad[Option].point(Pole(0, 0)) >>= {_.landLeft(1)} >> (none: Option[Pole]) >>= {_.landRight(1)}

//Added parens
(Monad[Option].point(Pole(0, 0)) >>= {_.landLeft(1)}) >> (none: Option[Pole]) >>= {_.landRight(1)}

//for syntax
3.some >>= (x => "!".some >>= (y => (x.shows + y).some))
for {
  x <- 3.some
  y <- "!".some
} yield x.shows + y
//By using >>=, any part of the calculation can fail
3.some >>= { x => (none: Option[String]) >>= { y => (x.shows + y).some } }
3.some >>= { x => "!".some >>= { y => none: Option[String] } }


for {
  start <- Monad[Option].point(Pole(0, 0))
  first <- start.landLeft(2)
  _ <- none: Option[Pole]
  second <- first.landRight(2)
  third <- second.landLeft(1)
} yield third

for {
  (x :: xs) <- "hello".toList.some
} yield x

for {
  (x :: xs) <- "".toList.some
} yield x

//MonadPlus, Plus, PlusEmpty, and ApplicativePlus
//filtering
for {
  x <- 1 |-> 50 if x.shows contains '7'
} yield x

List(1, 2, 3) <+> List(4, 5, 6)
(1 |-> 50) filter { x => x.shows contains '7' }

