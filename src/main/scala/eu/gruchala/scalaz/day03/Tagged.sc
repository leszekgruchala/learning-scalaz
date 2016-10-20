import scalaz._, Scalaz._


sealed trait KiloGram

def toKiloGram[A](a: A): A @@ KiloGram = Tag[A, KiloGram](a)

val mass = toKiloGram(20.0)
2 * Tag.unwrap[Double, KiloGram](mass)
2 * Tag.unwrap(mass)

//remove the tag T, leaving A
2 * scalaz.Tag.unsubst[Double, Id, KiloGram](mass)
//2 * scalaz.Tag.unsubst(mass) //can't compile

sealed trait JoulePerKiloGram

def JoulePerKilogram[A](a: A): A @@ JoulePerKiloGram = Tag[A, JoulePerKiloGram](a)

def energyR(mass: Double @@ KiloGram): Double @@ JoulePerKiloGram =
  JoulePerKilogram(299792458.0 * 299792458.0 * Tag.unsubst[Double, Id, KiloGram](mass))

energyR(mass) //takes Double @@ KiloGram
//energyR(2.0) //does not compile, can't take just Double
