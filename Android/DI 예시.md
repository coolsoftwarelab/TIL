
## 수동 종속 항목 삽입

`Car클래스` 자체가 `Engine`을 구성하는 예제
````
class Engine {
    fun start() {...}
}

class Car {
    private val engine = Engine() // 종속

    fun start() {
        engine.start()
    }
}

fun main(args: Array) {
    val car = Car()
    car.start()
}
````
![](https://developer.android.com/images/training/dependency-injection/1-car-engine-no-di.png?hl=ko)
- 위 예제처럼 `Car`가 자체 Engine을 구성했다면 Gas, Electric 유형의 엔진에 동일한 Car를 재사용하는 대신 두 가지 유형의 Car를 생성해야 한다.
- `Engine` 종속 항목 의존성은 테스트를 어렵게 만든다.
- `Car`는 실제 `Engine` 인스턴스를 사용하므로 다양한 테스트 사례에서 테스트 더블을 사용하여 Engine을 수정할 수 없게된다.  
(테스트더블 : 테스트를 진행하기 어려운 경우 이를 대신해 테스트를 진행할 수 있도록 만들어주는 객체를 말한다. stub, mock 등)

DI기반 접근방법. 생성자 매개변수 형태로 종속 항목 삽입코드
```
class Car(private val engine: Engine) {
    fun start() {
        engine.start()
    }
}

fun main(args: Array) {
    val engine = Engine()
    val car = new Car(engine)
    car.start()
}  
```
![](https://developer.android.com/images/training/dependency-injection/1-car-engine-di.png?hl=ko)
- `Car`의 재사용 가능
  - ElectricEngine`이라는 새로운 `Engine` 서브클래스를 정의하고 `ElectricEngine` 인스턴스를 전달하기만 하면되며 Car는 추가 변경 없이도 계속 작동한다. ()
- `Car`의 테스트 편의성
  - `FakeEngine`이라는 `Engine`의 테스트 더블을 생성하여 다양한 테스트에 맞게 구성할 수 있습니다.

#### Android 에서 종속 항목 삽입 방법
- 생성자 삽입, 필드삽입(또는 setter)

## 자동 종속 항목 삽입

### 종속 항목 삽입의 대안

#### 서비스 로케이터 패턴

```
class Engine {
    fun start() { }
}

object ServiceLocator {
    fun getEngine(): Engine = Engine()
}

class Car {
    private val engine = ServiceLocator.getEngine()

    fun start() {
        engine.start()
    }
}

fun main(args: Array) {
    val car = Car()
    car.start()
}
```
#### 종속 항목 삽입과 비교
- 코드를 테스트하기 더 어렵다. 모든 테스트가 동일한 전역 서비스 로케이터와 상호작용해야 하기 때문
-  Car 또는 서비스 로케이터에서 사용 가능한 종속 항목을 변경하면 참조가 실패하여 런타임 오류 또는 테스트 실패가 발생할 수 있습니다.

## Android Hilt
>Hilt는 Android에서 종속 항목 삽입을 위한 Jetpack의 권장 라이브러리.  
Hilt는 프로젝트의 모든 Android 클래스에 컨테이너를 제공하고 수명 주기를 자동으로 관리함으로써 애플리케이션에서 DI를 실행하는 표준 방법을 정의한다.  
`Dagger` 기반으로 빌드됨





