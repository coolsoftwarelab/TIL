## Android Unit Test

JUnit 과 Mock 활용

### 기본

프로젝트를 생성하면 addition_isCorrect() 테스트 메소드가 기본적으로 생성됨
Ex) src->test->com.example.unittest.ExampleUnitTest

```
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)    // case of Success
        // assertEquals(4, 2 + 3) // case of Fail
    }
}
```

### 기본 테스트 실행
- Ctrl + Shift + F10
- 프로젝트에서 오른쪽 마우스 키 -> Run 'All Tests'

성공 :  
```
Process finished with exit code 0
```

실패 :  
```
java.lang.AssertionError: 
Expected :4
Actual   :5
```

### 간단한 메소드 테스트

src->Main->...->Calculator.kt
```
class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }
}
```

src->test->...->CalculatorTest.kt
```
class CalculatorTest {
    lateinit var calc: Calculator

    @Before
    fun setUp() {
        calc = Calculator()
    }

    @Test
    fun addTest() {
        val result = calc.add(1, 2)
        assertEquals(3, result)
    }
}
```
