import scalaz._, Scalaz._
//Comparing/ordering with scalaz.Order, scalaz.syntax.OrderOps
//Instead of returning an Int from Order#compare, scalaz returns a  scalaz.Ordering.

1 > 2.0

1.0 ?|? 2.0 //returns scalaz.Ordering
3 cmp 4 //returns scalaz.Ordering

2.some max 4.some
2.some min None

1 gte 2
//1 gte 2.0 //compilation error for unrelated types
