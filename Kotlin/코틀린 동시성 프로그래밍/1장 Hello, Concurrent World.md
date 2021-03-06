## 프로세스, 스레드, 코루틴

### 프로세스
>실행 중인 애플리케이션의 인스턴스.  
애플리케이션이 시작될 때마다 프로세스가 시작되고 상태를 가진다.
애플리케이션은 여러 프로세스로 구성될 수 있다.(인터넷 브라우저 등)

### 스레드
>실행 스레드는 프로세스가 실행할 일련의 명령을 포함한다.  
프로세스는 최소 하나의 스레드를 포함하고 보통 진입점은 애플리케이션 main()함수이며, 이를 메인스레드라 한다.  
메인스레드가 끝나면 프로세스의 다른 스레드와 상관없이 프로세스가 종료된다.

메인 스레드를 블록하면 애플리케이션이 UI를 업데이트하거나 사용자로부터 상호작용을 수신하지 못하도록 방해하므로,  
GUI 애플리케이션은 응답성 유지를 위해 UI 스레드를 블록하지 않는다.

### 코루틴
>코루틴을 경량 스레드라고 한다.  
코루틴이 프로세서가 실행할 명령어 집합의 실행을 정의하기 때문이다.

코루틴은 스레드 안에서 실행된다.  
스레드 하나에 많은 코루틴이 있을 수 있지만 주어진 시간에 하나의 스레드에서 하나의 명령만이 실행될 수 있다.  
즉 같은 스레드에 10개의 코루틴이 있다면 해당 시점에는 하나의 코루틴만 실행된다.

***스레드 코루틴의 관계***
- 코루틴은 빠르고 적은 비용으로 생성할 수 있다.
- 코루틴은 고정된 크기의 스레드 풀을 사용하고 코루틴을 스레드들에 배포하기 때문에 실행 시간이 매우 적게 증가한다.
- 코루틴은 특정 스레드안에서 실행되더라도 스레드와 묶이지 않는다. 실행을 중지한 다음 나중에 다른 스레드에서 실행하는 것이 가능하다.
- 스레드는 한 번에 하나의 코루틴만 실행할 수 있기 때문에 프레임워크가 필요에 따라 코루틴을 스레드들 사이에 옮기는 역할을 한다.

### 내용정리
- 코루틴이 기본적으로 스레드안에 존재하지만 스레드에 얽매이지 않은 가벼운 스레드이다  
- 각 코루틴이 스레드를 옮겨다니며 실행될 수 있다
- 동시성은 애플리케이션이 동시에 한 개 이상의 스레드에서 실행될 때 발생한다.  

#### 동시성은 병렬성이 아니다.

<img src="https://user-images.githubusercontent.com/4969393/118619973-077bd980-b800-11eb-836e-20b26f35739f.png" height="300px" width="700px">

#### CPU 바운드 알고리즘에서의 동시성과 병렬성
>단일 코어의 경우 컨텍스트 스위칭으로 인해 오버헤드가 발생하여, 오히려 순차적 구현이 더 빠를 수도 있다.

#### I/O 바운드 알고리즘에서의 동시성 대 병렬성
>I/O 바운드 알고리즘은 끊임없이 무언가를 기다리므로 병렬이거나 단일 코어에 상관없이 늘 동시성으로 실행하는 편이 좋다. (저장장치 I/O, 네트워크 I/O 등)

### 동시성이 어려운 이유
#### 레이스 컨디션(Race condition:경합조건)
>레이스 컨디션은 코드를 동시성으로 작성했지만 순차적 코드처럼 동작할 것이라고 예상할 때 발생한다.(코드가 항상 특정한 순서로 실행될 것이라고 가정하고 오해)  
ex) DB에서 데이터를 가져오고 웹 서비스를 호출하는 기능을 동시에 수행. 이 때 DB작업이 더 빠를 것이로 가정하고 웹서비스 작업이 끝나자마자 DB 작업 결과에 접근할 때 Crash 발생

레이스 컨디션은 동시성 코드 일부가 제대로 작동하기 위해 일정한 순서로 완료돼야 할 때 발생한다. 이것은 동시성 코드를 구현하는 방법이 아니다.

#### 원자성 위반
>원자성 작업이란(atomic operation) 작업이 사용하는 데이터를 간섭 없이 접근할 수 있음을 말한다.

```
// This method will often print values lower than 2100
fun main(args: Array<String>) = runBlocking {
    val workerA = asyncIncrement(2000)
    val workerB = asyncIncrement(100)

    workerA.await()
    workerB.await()

    print("counter [$counter]")
}

var counter = 0

fun asyncIncrement(by: Int) = GlobalScope.async {
    for (i in 0 until by) {
        counter++
    }
}
```
counter++에서 원자성이 없기 때문에 counter 예상값 2,100 보다 작은 수가 나올 수 있다.

#### 교착상태(Dead lock)
>순환적 의존(circular dependencies)으로 인해 전체 애플리케이션이 중단될 수 있다.

```
// This will never complete execution.
fun main(args: Array<String>) = runBlocking {
    jobA = launch {
        delay(1000)
        // wait for JobB to finish
        jobB.join()
    }

    jobB = launch {
        // wait for JobA to finish
        jobA.join()
    }

    // wait for JobA to finish
    jobA.join()
    println("Finished")
}
```

#### 라이브 락(Livelocks)
>교착상태와 비슷하지만 라이브락은 진행될 때 애플리케이션이 지속적으로 변하지만  
애플리케이션이 정상 실행으로 돌아오지 못하게 하는 방향으로 상태가 변하는 것  

Ex) 두 사람이 마주보고 걸어오다가 서로 겹치는 방향으로 길을 비켜주면서 서로 길을 계속 막게되는 상태  
(앞 사람이 오른쪽으로 피하고 나는 왼쪽으로 피하면서 계속 마주보게되어 길이 막히는 형태)

교착상태에서 복구하려는 시도가 라이브 락을 만들어낼 수도 있다.

### 코틀린에서의 동시성

#### 넌 블로킹
>코틀린은 스레드의 실행을 블로킹하지 않으면서 실행을 잠시 중단하는 '중단 가능한 연산(Suspendable computations' 기능 제공

Ex) 스레드 Y에서 작업이 끝나기를 기다리면서 스레드 X를 블로킹하는 대신, 대기해야 하는 코드를 일시 중단하고 그동안 스레드 X를 다른 연산작업에 사용

코틀린은 channels, actors, mutual exclusions와 같은 기본형(primitives)도 제공해 스레드를 블록하지 않고 동시성 코드를 효과적으로 통신하고 동기화하는 메커니즘을 제공한다.

#### 명시적 선언
>연산이 동시에 실행돼야 하는 시점을 명시적으로 만드는 것이 중요하다.

순차실행
```
fun main(args: Array<String>) = runBlocking {
    val time = measureTimeMillis {
        val name = getName()
        val lastName = getLastName()
    }
    println("Excution took $time ms")
}

suspend fun getName(): String {
    delay(1000)
    return "Susan"
}

suspend fun getLastName(): String {
    delay(1000)
    return "Calvin"
}

// 실행결과
// Task :TestKt.main()
// Excution took 2034 ms
```

위 코드에서 getLastName과 getName()간 의존성이 없기 때문에 동시에 수행하는 편이 낫다.
```
fun main(args: Array<String>) = runBlocking {
    val time = measureTimeMillis {
        val name = async { getName() }
        val lastName = async { getLastName() }

        println("Hello, ${name.await()} ${lastName.await()}")
    }

    println("Excution took $time ms")
}

// 실행결과
> Task :TestKt.main()
Hello, Susan Calvin
Excution took 1036 ms
```
async{} 를 호출해 두 함수를 동시에 실행해야 하며 await()를 호출해 두 연산에 모두 결과가 나타낼 까지 main()이 일시중단되도록 요청함

#### 가독성

일시중단연산(Suspending computations)은 해당 스레드를 차단하지않고 실행을 일시 중지할 수 잇는 연산이다.  
일시 중단 연산을 통해 스레드를 다시 시작할 때까지 스레드를 다른 연산에서 사용할 수 있다. 키워드 `suspend`

- async() : 결과가 예상되는 코루틴을 시작하는 데 사용된다. 결과또는예외를 포함하는 `Deferred<T>`를 반환한다
- lauch() : 결과를 반환하지 않는 쿠로틴을 시작한다.  자체 혹은 자식 코루틴의 실행을 취소하기 위해 `Job`을 반환한다
- runBlocking : 블로킹 코드를 일시 중지 가능한 코드로 연결하기 위해 작성됐다. 보통 mian()메소드와 유닛 테스트에 사용된다. runBlocking()은 코루틴의 실행이 끝날 때까지 현재 스레드를 차단한다.

async 예제
```
val result = GlobalScope.async {
    isPalindrome(word = "Sample")
}
result.await()
```
