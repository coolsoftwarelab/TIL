`const val`

const 는 컴파일 타임 상수
const val 은 최상위 레벨 또는 객체에서만 허용되며, Java의 public static final 과 동일하다.

```
const val foo = complexFunctionCall()   //Not okay
val fooVal = complexFunctionCall()      //Okay
const val bar = "Hello world"           //Also okay
```

`object`

object 로 선언하면 클래스 선언과 동시에 객체가 생성. (singleton)

`property`

프로피터 = 필드 + 접근자
자바의 필드 + 접근자 메소드 (getter/setter) 와 같다.

`data`

프로퍼티에서 접근자 메소드 없는 VO(value-object) class 를  만들어 주는 것
```
data class A {
    var name: String
}
```

`companion object`

동반 객체의 프로퍼티나 메소드에 접근하려면 그 동반 객체가 정의된 클래스 이름을 사용한다.
이 때 객체의 이름은 따로 지정할 필요가 없다. 이 형태는 자바의 정적 메소드 호출이나 정적 필드 사용과 동일하다.

```
class A {
    companion object {
        fun bar() {
            println("Companion object called")
        }
    }
}

>>> A.bar()
Companion obejct called
```

`internal`

같은 모듈 안에서 접근할 수 있다

`lateinit`

>lateinit은 이 프로퍼티는 절대로 Null이 될 수 없는 프로퍼티인데 초기화를 선언과 동시에 해줄 수 없거나 성능이나 기타 다른 조건들로 인해 최대한 초기화를 미뤄야 할 때

```
- var(mutable) 프로퍼티만 사용 가능
- non-null 프로퍼티만 사용 가능
- 커스텀 getter/setter가 없는 프로퍼티만 사용 가능
- primitive type 프로퍼티는 사용 불가능
- 클래스 생성자에서 사용 불가능
- 로컬 변수로 사용 불가능
```

`lazy`

>호출 시점에 by lazy 로 한번 초기화 후, 이후에는 기억된 결과를 반환

- val 에서 사용

```
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

fun main() {
    println(lazyValue)
    println(lazyValue)
}

/*
    computed!
    Hello
    Hello
*/
```

`use`

`inline`

`run`
