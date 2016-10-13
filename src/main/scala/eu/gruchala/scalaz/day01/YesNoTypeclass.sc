import scala.language.implicitConversions

trait CanTruthy[A] {

  def truthys(a: A): Boolean
}

object CanTruthy {
  def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    override def truthys(a: A): Boolean = f(a)
  }
}

trait CanTruthyOps[A] {
  def self: A
  def F: CanTruthy[A]
  final def truthy: Boolean = F.truthys(self)
}

object ToCanIsTruthyOps {
  implicit def toCanIsTruthyOps[A](a: A)(implicit ev: CanTruthy[A]): CanTruthyOps[A] = new CanTruthyOps[A] {

    override def F: CanTruthy[A] = ev
    override def self: A = a
  }
}

import ToCanIsTruthyOps._

implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
  case 0 => false
  case _ => true
})

10.truthy
