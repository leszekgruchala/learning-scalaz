import scalaz._, Scalaz._

//Equality testing with scalaz.Equal

1 === 1
//1 === "foo" //compilation error for unrelated types

1 == "foo" //false for unrelated types

//Inequality testing
1.some =/= 2.some
1.some =/= 1.some
1.some =/= None
//1.some =/= true //compilation error for unrelated types

1 assert_=== 2 //throws an excaption for ineaquality

