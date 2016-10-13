import scalaz._, Scalaz._

//scalaz.Enum - An scalaz.Order'able with discrete values

'a' to 'e'

'a' |-> 'e'

3 |-> 5

'B'.succ
'B'.succx
'B'.pred
'B'.predx

implicitly[Enum[Char]].min
implicitly[Enum[Long]].min
