### let

>함수를 호출하는 객체를 이어지는 블록의 인자로 넘기고, 블록의 결과값을 반환합니다.  
스코프 함수는 새로운 기술 기능을 도입하지 않지만 코드를보다 간결하고 읽기 쉽게 만들 수 있습니다.

사용조건

- 지정된 값이 null 이 아닌 경우에 코드를 실행해야 하는 경우.
- Nullable 객체를 다른 Nullable 객체로 변환하는 경우.
- 단일 지역 변수의 범위를 제한 하는 경우.

일반적인 사용

```
val alice = Person("Alice", 20, "Amsterdam")
println(alice)
alice.moveTo("London")
alice.incrementAge()
println(alice)
```

let 사용

```
Person("Alice", 20, "Amsterdam")?.let {
    println(it)
    it.moveTo("London")
    it.incrementAge()
    println(it)
}
```


