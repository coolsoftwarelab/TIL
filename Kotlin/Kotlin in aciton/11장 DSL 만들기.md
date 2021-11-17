## DSL(Domain-Specific Language)

- 언어 특화 언어 만들기
- 수신 객체 지정 람다 사용
- invoke 관례 사용
- 기존 코틀린 DSL 예제

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

// 일반 명령-질의 API를 통해 디펜더시 설정
```
project.dependencies.add("compile", "junit:junit:4.11")
project.dependencies.add("compile", "com.google.inject:guice:4.1.0")
```















