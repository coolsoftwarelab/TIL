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

`use`

`property`

`data class`

`inline`

`run`
