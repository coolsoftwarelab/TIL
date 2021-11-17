## DSL(Domain-Specific Language)

>DSL (도메인 특화 언어)  
특정 분야에 최적화된 프로그래밍 언어다.  
해당 분야 또는 도메인의 개념과 규칙을 사용  
ex) SQL, 정규식

### API 에서 DSL 로

>라이브러리가 외부 사용자에게 프로그래밍 API를 지원하는 것처럼,  
모든 클래스는 다른 클래스에게 자신과 상호작용할 수 있는 가능성을 제공한다.  
이런 상호작용을 이해하기 쉽고 명확하게 표현해야 유지보수가 쉽다.

#### 좋은 API란
- 코드를 읽는 독자가 어떤일이 벌어질지 명확히 이해할 수 있어야 한다
  - 이름 잘붙이기, 적절한 개념 사용 등
- 코드가 간결해야한다

간결한 구문을 지원하는 코틀린

![](https://user-images.githubusercontent.com/4969393/142151194-803336a8-9b72-4d62-ab52-d0c1207b4c85.png)

* 범용 프로그래밍 언어
    * 명령적인 특징을 가지고 있다.
        * ex) 어떤 연산을 완수하기 위해 필요한 각 단계를 순서대로 정확히 기술. C, Java 등등
* 영역 특화 언어
    * SQL과 정규식과 같이 제공하는 기능을 스스로 제한함으로써 오히려 더 효율적으로 목표를 달성할 수 있 도록 하는 특징을 가진 언어
    * 선언적인 특징을 가지고 있다.
        * 원하는 결과를 기술하기만 하고 그 결과를 위한 세부 실행은 언어를 해석하는 엔진에 맡김
    * 특정 영역에 특화되어 자체 문법이 있기 때문에 범용 언어로 만든 애플리케이션과 조합하기가 어렵다.
        * ex) 내부 DB 사용 시 쿼리문 작성 <– 컴파일 시점에 검증 불가

코틀린DSL도 컴파일 시점에 타입이 정해지므로,  
오류 감지, IDE 지원 등 모든 정적 타입 지정 언어의 장점을 누릴 수 있다.

11장 에서는 깔끔한 API에서 한걸음 더 나아가 DSL 구축을 도와주는 코틀린 기능을 살펴본다.

## 내부 DSL

- 내부 DSL은 범용 언어로 작성된 프로그램의 일부며, 범용 언어와 동일한 문법을 사용한다.  
그렇기 때문에 내부 DSL은 다른 언어가 아니라 DSL의 핵심 장점을 유지하면서 주 언어를 특별한 방법으로 사용하는 것.

- 외부 DSL은 주 언어와는 독립적인 문법 구조를 가진다. ex) XML, MakeFile 등

SQL을 이용한 쿼리문 (외부 DSL)
```
SELECT Country.name, COUNT(Customer.id) FROM Country
	JOIN Customer ON Country.id = Customer.country_id
GROUP BY Country.name
ORDER BY COUNT(Customer.id) DESC LIMIT 1
```

코틀린 Exposed 를 이용한 쿼리문 (내부 DSL)
```
(Country join Customer)
	.slice(Coutry.name, Count(Customer.id))
	.selectAll()
	.groupBy(Country.name)
	.orderBy(Count(Customer.id), isAsc = false)
	.limit(1)
```
위 쿼리 코드는 실행하면 내부적으로는 SQL을 실행하지만,  
일반 코틀린 코드로 작성되며 리턴값도 코틀린 객체로, SQL질의 결과를 따로 변환할 필요가 없다.


### DSL의 구조

- DSL은 구조 또는 문법을 독립적으로 가진다.
- DSL은 여러 함수 호출을 조합해서 연산을 만드는 것 또한 내부 DSL 적인 특징이다.

gradle 에서 람다 중첩을 통해 구조를 만듦
```
dependencies {
	compile("junit:junit:4.11")
	compile("com.google.inject:guice:4.1.0")
```

일반 명령-질의 API를 사용. 코드에 중복이 많다
```
project.dependencies.add("compile", "junit:junit:4.11")
project.dependencies.add("compile", "com.google.inject:guice:4.1.0")
```

```
// 코틀린테스트를 이용한 테스트 코드 호출. 메소드 호출 연쇄를 통해 구조를 만듦
str should startWith("kot")

// 일반 JUnit API 사용
assertTrue(str.startsWith("kot"))
```
JUnit을 사용하면 잡음이 더 많고 읽기 쉽지 않다.

### 내부 DSL로 HTML 만들기

kotlinx.html 라이브러리을 이용하여 HTML 페이지를 생성
```
fun createSimpleTable() = createHtml().
	table {
		tr {
			td { +"cell" }
		}
}
```

생성되는 html 결과
```
<table>
	<tr>
		<td>cell</td>
	</tr>
</table>
```
createSimpleTable() 함수는 HTML 조각이 들어있는 문자열을 반환.

동적으로 표의 칸을 생성하는 예제
```
fun createAnotherTable() = createHTML().table {
  val numbers = mapOf(1 to "one", 2 to two)
  for ((num, string) in numbers) {
    tr {
      td { +"$num" }
      td { +string }
    }
  }
}

// 생성되는 html 결과
//<table>
//	<tr>
//		<td>1</td>
//    <td>one</td>
//	</tr>
//	<tr>
//		<td>2</td>
//    <td>two</td>
//	</tr>
//</table>
```

#### 코틀린 코드로 HTML을 만들려는 이유
- 타입 안정성을 보장한다.
  - 위 코드에서 td는 tr 내에서만 사용할 수 있다. 그렇지 않은 경우 컴파일이 되지 않는다.
- 코틀린 코드를 원하는대로 사용할 수 있다.
  - 맵에 들어있는 원소에 따라 동적으로 표의 칸을 생성할 수 있다.

### 구조화된 API 구축 : DSL에서 수신 객체 지정 DSL 사용

#### 수신 객체 지정 람다와 확장 함수 타입

람다를 인자로 받는 buildString() 정의
```
fun buildString(buiderAction: (StringBuilder) -> Unit): String {	// 함수 타입인 파라미터 정의
  val sb = StringBuilder()
  builderAction(sb)
  return sb.toString()
}

val s = buildString { 	
	it.append("Hello, ")	// it 은 Stringbuilder 인스턴스이다.
	it.append("World!")
}
println(s)

>>>> Hello, World!
```
`it`을 계속 사용해야 하므로 불편함

수신 객체 지정 람다를 사용해 buildString() 정의  
람다의 인자 중 하나에게 수신 객체라는 상태를 부여하면 이름과 마침표를 명시하지 않아도 그 인자의 멤버를 바로 사용할 수 있다.
```
fun buildString(builderAction: StringBuilder.() -> Unit): String { // 수신 객체가 있는 함수 타입(확장 함수 타입)의 파라미터 선언
	val sb = StringBuilder()
	sb.builderAction() // 수신 객체로 넘김.
	return sb.toString()
}

val s = buildString {
  this.append("Hello, ")
  append("World!")	// this 생략가능
}
println(s)

>>>> Hello, World
```
위 예제에서 일반 함수 타입 대신 확장 함수 타입 사용. `StringBuilder.() -> Unit`  
`StringBulder`가 수신 객체 타입

![](https://user-images.githubusercontent.com/4969393/142159511-a04e5ae5-9a74-482f-abf6-2ed56123ccc8.png)


apply를 이용한 buildString() 정의
```
fun buildString(builderActon: StringBuilder.() -> Unit): String = 
	StringBuilder().apply(builderAction).toString()
```

apply, with 함수의 구현 참고
```
inline fun <T> T.apply(block: T.() -> Unit): T {
	block()			// this.block()과 같다.
	return this		// 수신 객체 반환
}

inline fun <T, R> with(receiver: T, block: T.() -> R): R = 
	recevier.block()	// 람다를 호출해 얻은 결과를 반환
```
- apply함수
  - 수신 객체 타입에 대한 확장 함수로 선언됐기 때문에 수신 객체의 메소드처럼 불린다.
  - 수신 객체를 묵시적 인자(this)로 받게 된다.
  - 수신 객체를 다시 반환한다.
- with 함수
  - 수신 객체를 첫번째 파라미터로 받는다.
  - 람다를 호출해 얻은 결과를 반환한다.

### 수신 객체 지정 람다를 HTML 빌더 안에서 사용

>HTML 빌더는 HTML을 만들기 위한 코틀린 DSL을 뜻한다.  
빌더를 사용하면 객체 계층 구조를 선언적으로 정의할 수 있다. XML, UI컴포넌트 레이아웃 정의할 때 유용하다

코틀린

```
fun createSimpleTable() = createHTML().
table { 
	tr { // == (this@table).tr
		td { +"cell" } // == (this@tr).td
	}
}
```

코틀린 HTML 빌더를 사용해 간단한 HTML 표 만들기
```
fun createSimpleTable() = createHTML().
	table { 
		tr {
			td { +"cell" }
		}
}

>>>> <table><tr><td></td></tr></table>

```

### invoke 관례를 사용한 더 유연한 블록 중첩

- invoke 관례를 사용하면 객체를 함수처럼 호출할 수 있다.

#### invoke 관례: 함수처럼 호출할 수 있는 객체

operator 변경자가 붙은 invoke 메소드 정의가 들어있는 클래스의 객체는 함수처럼 부를 수 있다.
```
class Greeter(val greeting: String) {
  operator fun invoke(name: String) {
    println("$greeting, $name!")
  }
}

val bavarianGreeter = Greeter("Servus")
bavarianGreeter("Dmitry")	// 컴파일 -> bavarianGreeter.invoke("Dmitry")

>>>>> Servus, Dmitry!
```

#### invoke 관례와 함수형 타입

- 인라인하는 람다를 제외한 모든 람다는 함수형 인터페이스(Function1 등)를 구현하는 클래스로 컴파일된다.
- 람다를 함수처럼 호출하면 이 관례에 따라 invoke 메소드 호출로 변환된다.

![](https://user-images.githubusercontent.com/4969393/142171308-df682190-69f4-4294-a85e-5372322dec17.png)

```
data class Issue(val id: String, val project: String, val type: String, val priority: String, val description: String)

class ImportantIssuesPredicate(val project: String): (Issue) -> Boolean {	// 함수 타입을 부모 클래스로 사용
  override fun invoke(issue: Issue): Boolean {	// invoke 메소드 구현
    return issue.project == project && issue.isImportant()
  }
  
  private fun Issue.isImportant(): Boolean {
    return type == "Bug" &&
            (priority == "Major" || priority == "Critical")
  }
}

val i1 = Issue("IDEA-154446", "IDEA", "Bug", "Major", "Save settings failed")
val i2 = Issue("KT-12183", "Kotlin", "Feature", "Normal", "Intention: convert serveral ~~")

val predicate = ImportantIssuesPredicate("IDEA")
for (issue in listOf(i1, i2).filter(predicate)) { // 술어를 filter에 넘김
  println(issue.id)
}


>>> IDEA-154446
```

- 람다를 함수 타입 인터페이스를 구현하는 클래스로 변환하고 그 클래스의 invoke 메소드를 오버라이드하면 복잡한 람다가 필요한 구문을 리팩토링할 수 있다.
- 위와 같이 리팩토링할 경우 람다 본문에서 따로 분리해낸 메소드가 영향을 끼치는 영역을 최소화할 수 있다는 장점이 있다.

#### DSL의 invoke 관례: 그레이들에서 의존관계 정의

```
// case 1
dependencies.compile("junit:junit:4.11")

// case 2
dependencies {
	compile("junit:junit:4.11")
}
```

유연한 DSL 문법을 제공하기 위해 invoke 사용하기
```
class DependencyHandler {
	// 일반적인 명령형 API 정의
	fun compile(coordinate: String) {
		println("Added dependency on $coordinate")
	}
	
	// invoke를 정의해 DSL 스타일 API를 제공
	operator fun invoke(body: DependencyHandler.() -> Unit) {
		body() // == this.body()
	}
}

-------------------
val dependencies = DependencyHandler()
dependencies.compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.0")

>>>>>Added dependecy on org.jetbrains.kotlin:kotlin-stdlib:1.0.0

dependencies {
  compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.0")
}

>>>>> Added dependecy on org.jetbrains.kotlin:kotlin-stdlib:1.0.0
```

두번째 호출은 다음과 같이 변환
```
dependencies.invoke({
	this.compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.0")
})
```

- dependencies를 함수처럼 호출하면 람다를 인자로 넘기게 된다.
- 람다의 타입은 확장 함수 타입(수신 객체를 지정한 함수 타입) 이다.
- 지정한 수신 객체 타입은 DpendencyHandler이다.
- invoke 메소드는 이 수신 객체 지정 람다를 호출한다.
- invoke 메소드가 DependencyHandler의 메소드이므로 이 메소드 내부에서 this는 DependencyHandler 객체이다.
- 따라서 invoke 안에서 DependencyHandler 타입의 객체를 따로 명시하지 않고 compile() 을 호출할 수 있다.

### 실전 코틀린 DSL

#### 중위 호출 연쇄: 테스트 프레임워크의 should

코틀린테스트 DSL 단언문
```
s should startWith("kot")
```

```
// should 함수 구현
infix fun <T> T.should(matcher: Matcher<T>) = matcher.test(this)

// Matcher 선언
interface Matcher<T> {
  funt test(value: T)
}
class startWith(val prefix: String): Matcher<String> {
  override fun test(value: String) {
    if (!value.startsWith(prefix)) {
      throw AssertionError("String $value does not start with $prefix")
    }
  }
}
```

코틀린테스트 DSL에서 메소드 호출 연쇄
```
"kotlin" should start with("kot")

// 일반 메소드 호출로 변환
"kotlin".should(start).with("kot")
```

중위 호출 연쇄를 지원하기 위한 API 정의
```
object start
infix fun String.should(x: start): StartWrapper = StartWrapper(this)
class StartWrapper(val value: String) {
	infix fun with(prefix: String) = if (!value.startsWith(prefix)) {
		throw AssertionError("String $value does not start with $prefix")
	} else {
		Unit
	}
}
```

원시 타입에 대한 확장 함수 정의: 날짜 처리
```
val Int.days: Period
	get() = Period.ofDays(this) // this는 상수의 값을 가리킴

val Period.ago: LocalDate
	get() = LocalDate.now() - this

val Period.fromNow: LocalDate
	get() = LocalDate.now() + this

println(1.days.ago)
// 결과: 2020-04-18

println(1.days.fromNow)
// 결과: 2020-04-20
```

- Period 클래스는 두 날짜 사이의 시간 간격을 나타내는 JDK 8 타입이다.
- 위에 쓰인 -, + 는 코틀린에서 제공하는 확장함수가 아닌 LocalData라는 JDK 클래스에 있는 관례에 의해 minus, plus 메소드가 호출되는 것이다.
  - 코틀린의 -, + 연산자와 일치할 경우 호출해주는 관례가 들어있다.
