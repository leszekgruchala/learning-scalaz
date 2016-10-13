import scalaz._, Scalaz._

//Show - A typeclass for conversion to textual representation, done via scalaz.Cord for efficiency

3.show
"abcd".show.split(3)

3.shows

"hello".println
