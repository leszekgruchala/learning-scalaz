//ordinary sum function, handles only Int type
def sum(xs: List[Int]): Int = xs.foldLeft(0)(_ + _)
sum(List(1, 2, 3))

//generalized function which handles Monoid type and provides supported types through ad-hoc polymorphism
trait Monoid[A] {
  def mappend(a: A, b: A): A
  def mzero: A
}

object Monoid {

  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {

    override def mappend(a: Int, b: Int): Int = a + b
    override def mzero: Int = 0
  }
}

implicit val StringMonoid: Monoid[String] = new Monoid[String] {

  override def mappend(a: String, b: String): String = a + b
  override def mzero: String = ""
}

import Monoid._

def genericSum[A : Monoid](xs: Traversable[A]): A = {
  val monoid = implicitly[Monoid[A]]
  xs.foldLeft(monoid.mzero)(monoid.mappend)
}

genericSum(List(1, 2, 3))
genericSum(List("a", "b", "c"))

//now generalize also Traversable type, what we need is a type with foldLeft
import scala.language.higherKinds
trait FoldLeft[F[_]] {

  def foldLeft[A, B](xs: F[A], b: B)(f: (B, A) => B): B
}
object FoldLeft {

  implicit val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
    override def foldLeft[A, B](xs: List[A], b: B)(f: (B, A) => B): B =
      xs.foldLeft(b)(f)
  }
}

def fullyGenericSum[M[_]: FoldLeft, A: Monoid](xs: M[A]): A = {
  val monoid = implicitly[Monoid[A]]
  val fl = implicitly[FoldLeft[M]]
  fl.foldLeft(xs, monoid.mzero)(monoid.mappend)
}
import FoldLeft._
fullyGenericSum(List(1, 2, 3))
fullyGenericSum(List("a", "b", "c"))

//see
scalaz.Monoid


//PimpMyLibrary - we want to provide new operator for Monoid type without affecting it directly
trait MonoidOp[A] {
  val F: Monoid[A]
  val value: A
  def |+|(anotherMonoid: A): A = F.mappend(value, anotherMonoid)
}

//provide implicit conversion from our Monoid type to MonoidOp
import scala.language.implicitConversions
implicit def toMonoidOp[A: Monoid](anyTypeWithMonoidSupport: A): MonoidOp[A] = new MonoidOp[A] {
  override val F: Monoid[A] = implicitly[Monoid[A]]
  override val value: A = anyTypeWithMonoidSupport
}

3 |+| 4
"abc" |+| "def"
