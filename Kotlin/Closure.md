## Closure(클로저)

>클로저는 outer scope (상위 함수의 영역)의 변수를 접근할 수 있는 `함수`를 말한다.  
`Closure` 는 `close over` (~을 에워싸다[뒤덮다]) 라는 의미.

클로저라는 함수는 'x' 라는 변수를 "클로즈" 한다고 한다
```
function add(x) {
    return function closure(y) {
        return x + y;
    };
}
```

Ex1)
```
private fun add(x: Int): (Int) -> Int {
    return fun(y: Int): Int {
        return x + y
  }
}

val myClosure = add(1)
val result = myClosure(2)
println("result $result")`  // 3
```

Ex2)  
java 와는 달리 kotlin에서는 아래의 코드가 가능하다.(자바는 final 변수의 '참조'만 가능함. 수정x)  
a 가 2로 변경된 상태에서 클릭하면 2가 출력된다.
```
var a = 1
myView.setOnClickListener {
    println(a)
}
```







