## let

```
inline fun <T, R> T.let(block: (T) -> R): R
```

>함수를 호출하는 객체를 이어지는 블록의 인자로 넘기고, 블록의 결과값을 반환합니다.  

- 지정된 값이 null 이 아닌 경우에 코드를 실행해야 하는 경우. ("test"?.let{})
- Nullable 객체를 다른 Nullable 객체로 변환하는 경우.

let 사용
```
Person("Alice", 20, "Amsterdam")?.let {
    println(it)
    it.moveTo("London")
    it.incrementAge()
    println(it)
}
```

let을 쓰지 않는다면
```
val alice = Person("Alice", 20, "Amsterdam")
println(alice)
alice.moveTo("London")
alice.incrementAge()
println(alice)
```


## run

```
// 1) 객체 없이 호출하며 익명함수로 사용할 수 있으며, 블럭내에 처리할 작업들을 넣어주면 된다. 반환값도 가능하다.
fun <R> run(block: () -> R) : R

// 2) run 함수를 호출하는 객체를 블록의 리시버로 전달하고, 블럭의 결과 값을 반환한다.
fun <T, R> T.run(block: T.() -> R) : R
```

>인자가 없는 익명 함수처럼 동작하는 형태, 객체에서 호출하는 형태 두 가지가 있다.

1) 어떤 값을 계산할 필요가 있거나 여러 개의 지역변수 범위를 제한할 때 사용한다.
```
val person = Person("James", 56)
val ageNextYear = person.run {
    ++age
}
println("$ageNextYear")  // 57
```


2) 어떤 객체를 생성하기 위한 명령문을 블럭 안에 묶음으로써 가독성을 높이는 역할을 한다.
```
val person = run {
    val name = "James"
    val age = 56
    Person(name, age)
}
```


## with

```
inline fun <T, R> with(receiver: T, block: T.() -> R): R
```

>인자로 받는 객체를, 블록의 리시버로 전달하고, 블록의 결과값을 반환한다.  
run함수와 기능이 거의 동일. run의 경우, receiver가 없지만 with 에서는 receiver로 전달할 객체를 처리

```
val person = Person("James", 56)
with(person) {
    println(name)   // James
    println(age)    // 56
    //자기자신을 반환해야 하는 경우 it이 아닌 this를 사용한다
}
```


## apply

```
fun <T> T.apply(block: T.() -> Unit) : T
```

>함수를 호출하는 객체(T)를 이어지는 block의 receiver(리시버)로 전달하고, 객체 자체(T)를 반환합니다.

#### 리시버 : block내에서 객체(T)의 메소드 및 속성에 바로 접근할 수 있도록 할 객체

```
val jake = Person()
val stringDescription = jake.apply {
    name = "Jake"
    age = 30
    about = "Android developer"
}
```


## also

```
fun <T> T.also(block: (T) -> Unit) : T
```

>block 안의 코드 수행결과와 상관없이 T객체(this) 를 반환한다.

연산 결과와 상관없이 number 는 1 이다.
```
var number = 1
number = number.also {
    it + 10
}
println("number : $number")  // number : 1
```

![](https://miro.medium.com/max/700/0*wZoYYxqh_7_B8JQ2.png)
