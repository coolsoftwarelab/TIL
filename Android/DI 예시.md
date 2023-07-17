
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

## 수동 종속 항목 삽입

![](https://developer.android.com/static/images/training/dependency-injection/2-application-graph.png?hl=ko)

흐름의 `Repository` 및 `DataSource` 클래스
```java
class UserRepository(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) { ... }

class UserLocalDataSource { ... }

class UserRemoteDataSource(
    private val loginService: LoginRetrofitService
) { ... }
```

`LoginActivity`
```java
class LoginActivity: Activity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // In order to satisfy the dependencies of LoginViewModel, you have to also
        // satisfy the dependencies of all of its dependencies recursively.
        // First, create retrofit which is the dependency of UserRemoteDataSource
        val retrofit = Retrofit.Builder()
            .baseUrl("https://example.com")
            .build()
            .create(LoginService::class.java)

        // Then, satisfy the dependencies of UserRepository
        val remoteDataSource = UserRemoteDataSource(retrofit)
        val localDataSource = UserLocalDataSource()

        // Now you can create an instance of UserRepository that LoginViewModel needs
        val userRepository = UserRepository(localDataSource, remoteDataSource)

        // Lastly, create an instance of LoginViewModel with userRepository
        loginViewModel = LoginViewModel(userRepository)
    }
}
```
위 코드의 문제지머
- 상용구 코드가 많습니다. 코드의 다른 부분에서 LoginViewModel의 다른 인스턴스를 만들려면 코드가 중복될 수 있습니다.
- 종속 항목은 순서대로 선언해야 합니다. UserRepository를 만들려면 LoginViewModel 전에 인스턴스화해야 합니다.
- 객체를 재사용하기가 어렵습니다. 여러 기능에 걸쳐 UserRepository를 재사용하려면 싱글톤 패턴을 따르게 해야 합니다. 모든 테스트가 동일한 싱글톤 인스턴스를 공유하므로 싱글톤 패턴을 사용하면 테스트가 더 어려워집니다.

### 컨테이너로 종속 항목 관리
```kotlin
// Container of objects shared across the whole app
class AppContainer {

    // Since you want to expose userRepository out of the container, you need to satisfy
    // its dependencies as you did before
    private val retrofit = Retrofit.Builder()
                            .baseUrl("https://example.com")
                            .build()
                            .create(LoginService::class.java)

    private val remoteDataSource = UserRemoteDataSource(retrofit)
    private val localDataSource = UserLocalDataSource()

    // userRepository is not private; it'll be exposed
    val userRepository = UserRepository(localDataSource, remoteDataSource)
}
```
`AppContainer` 인스턴스가 포함된 맞춤 `Application` 클래스 생성
```kotlin
`AppContainer`
class MyApplication : Application() {

    // Instance of AppContainer that will be used by all the Activities of the app
    val appContainer = AppContainer()
}
```
애플리케이션에서 AppContainer의 인스턴스를 가져와서 공유 UserRepository 인스턴스를 획득
```kotlin
class LoginActivity: Activity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Gets userRepository from the instance of AppContainer in Application
        val appContainer = (application as MyApplication).appContainer
        loginViewModel = LoginViewModel(appContainer.userRepository)
    }
}
```
LoginViewModel을 컨테이너로 이동하고 그 유형의 새 객체에 팩토리를 제공하는 경우
```kotlin
// Definition of a Factory interface with a function to create objects of a type
interface Factory<T> {
    fun create(): T
}

// Factory for LoginViewModel.
// Since LoginViewModel depends on UserRepository, in order to create instances of
// LoginViewModel, you need an instance of UserRepository that you pass as a parameter.
class LoginViewModelFactory(private val userRepository: UserRepository) : Factory {
    override fun create(): LoginViewModel {
        return LoginViewModel(userRepository)
    }
}
```

```kotlin
// AppContainer can now provide instances of LoginViewModel with LoginViewModelFactory
class AppContainer {
    ...
    val userRepository = UserRepository(localDataSource, remoteDataSource)

    val loginViewModelFactory = LoginViewModelFactory(userRepository)
}

class LoginActivity: Activity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Gets LoginViewModelFactory from the application instance of AppContainer
        // to create a new LoginViewModel instance
        val appContainer = (application as MyApplication).appContainer
        loginViewModel = appContainer.loginViewModelFactory.create()
    }
}
```
이 접근 방식은 이전 방식보다 좋지만 여전히 다음과 같은 문제를 고려해야 합니다.

- AppContainer를 직접 관리하여 모든 종속 항목의 인스턴스를 수동으로 만들어야 합니다.
- 여전히 상용구 코드가 많습니다. 객체의 재사용 여부에 따라 수동으로 팩토리나 매개변수를 만들어야 합니다.
- 프로젝트에 기능을 더 많이 포함하려 할 때 AppContainer는 복잡해집니다. 

### 애플리케이션 흐름에서 종속 항목 관리

- 활동 하나(LoginActivity)와 여러 프래그먼트(LoginUsernameFragment, LoginPasswordFragment)로 구성된 로그인 흐름을 가정

```kotlin
class LoginContainer(val userRepository: UserRepository) {

    val loginData = LoginUserData()

    val loginViewModelFactory = LoginViewModelFactory(userRepository)
}

// AppContainer contains LoginContainer now
class AppContainer {
    ...
    val userRepository = UserRepository(localDataSource, remoteDataSource)

    // LoginContainer will be null when the user is NOT in the login flow
    var loginContainer: LoginContainer? = null
}
```
```kotlin
class LoginActivity: Activity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginData: LoginUserData
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (application as MyApplication).appContainer

        // Login flow has started. Populate loginContainer in AppContainer
        appContainer.loginContainer = LoginContainer(appContainer.userRepository)

        loginViewModel = appContainer.loginContainer.loginViewModelFactory.create()
        loginData = appContainer.loginContainer.loginData
    }

    override fun onDestroy() {
        // Login flow is finishing
        // Removing the instance of loginContainer in the AppContainer
        appContainer.loginContainer = null
        super.onDestroy()
    }
}
```



## Android Hilt
>Hilt는 Android에서 종속 항목 삽입을 위한 Jetpack의 권장 라이브러리.  
Hilt는 프로젝트의 모든 Android 클래스에 컨테이너를 제공하고 수명 주기를 자동으로 관리함으로써 애플리케이션에서 DI를 실행하는 표준 방법을 정의한다.  
`Dagger` 기반으로 빌드됨

이후 참조 예정
https://developer.android.com/training/dependency-injection/manual?hl=ko


