## RxJava - 데이터 발행자 (Data source)

### Observable class

>옵서버 패턴을 구현. 데이터 흐름에 맞게 알림을 보내 구독자가 데이터를 처리할 수 있도록 한다. 데이터를 무한하게 발행 할 수 있다.

#### 세 가지 알림을 구독자에게 전달

- onNext : Observable이 데이터의 발생을 알림.

- onComplete : 모든 데이터 발행 완료 알림. 이후 onNext 발생 안함.

- onError : 에러 발생. onError 이벤트 후에 onNext 및 onComplete 발생안함.

```
Observable<Integer> source = Observable.just(1, 2, 3, 4, 5);
source.subscribe(System.out::println);
```

#### Cold Observable vs Hot Observable

- Cold Observable : just(), fromIterable() 함수 등을 호출해도 옵저버가 subscribe() 함수를 호출하여 구독하지 않으면 데이터를 발행하지 않는다.

Ex) 웹 요청, DB 쿼리, 파일 I/O

- Hot Observable : 구독자 존재 여부와 관계 없이 데이터 발행.
구독자가 여러 명이 될 수 있고, 구독자는 구독한 시점부터 Observable이 발행한 값을 수신 (처음부터 발행한 데이터 수신 보장안함)

Ex) 마우스 이벤트, 키보드 이벤트, 센서 데이터, 주식 가격 등

**구독자가 여러 명이다** 의 의미

Ex 1)

구독자가 둘

```
Observable observable = Observable.just(1,2,4,5);
observable.subscribe(data -> System.out.println("subscriber #1  : " + data));
observable.subscribe(data -> System.out.println("subscriber #2  : " + data));
```

Ex 2)

서버에 요청한 결과로 반환된 JSON 문서를 파싱해 원하는 속성을 추출하는 상황에서 날씨 정보, 지역정보, 시간 정보를 반환하는 경우 이 세 가지 정보를 각각 구독하면 구독자가 여러 명이다 라고 할 수 있다.

### Single class

>오직 1개의 데이터만 발행

데이터 하나를 발행과 동시에 종료(onSuccess)됨.

#### 라이프사이클 함수

- onSuccess(T value)

- onError()

```
Single<String> source = Observable.just(1);
source.subscribe(System.out::println);

```

### Maybe class

>Single 클래스에 onComplete 이벤트가 추가된 형태.<br>
최대 데이터 하나를 가질 수 있지만 데이터 발행 없이 바로 데이터 발생을 완료 할 수 있다.

#### 함수

- onSuccess

- onError

- onComplete

### Subject class

> Observable의 속성과 구독자의 속성 모두 가지고 있다.<br>
Cold Observable 을 Hot Observable 로 바꿔준다.<br>
주요 Subject 클래스는 AsyncSubject, BehaviorSubject, PublishSubject, ReplaySubject

1. AsyncSubject

>Observable에서 발행한 마지막 데이터를 얻어온다.

데이터 발행자로 동작하는 예제
```
AsyncSubject<String> subject = AsyncSubject.create();
subject.subscribe(data -> System.out.println("Subscriber #1 =>" + data));	// Subscriber #1 =>5
subject.onNext("1");
subject.onNext("3");
subject.subscribe(data -> System.out.println("Subscriber #2 =>" + data));	// Subscriber #2 =>5
subject.onNext("5");
subject.onComplete();
```

데이터 구독자로 동작하는 예제
```
Float[] temperature = {10.1f, 13.4f, 12.5f};
Observable<Float> source = Observable.fromArray(temperature);

AsyncSubject<Float> subject = AsyncSubject.create();
subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));		// Subscriber #1 => 12.5

source.subscribe(subject);
```

2. BehaviorSubject class

>구독자가 구독을 하면 가장 최근 값 혹은 기본값을 넘겨주는 클래스

```
BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
subject.onNext("1");
subject.onNext("3");
subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
subject.onNext("5");
subject.onComplete();

/* 
Subscriber #1 => 6
Subscriber #1 => 1
Subscriber #1 => 3
Subscriber #2 => 3
Subscriber #1 => 5
Subscriber #2 => 5
*/
```

3. PublishSubject class

>구독자가 subscribe() 함수를 호출하면 값을 발행하기 시작. 구독한 시점부터 발생한 데이터를 구독자에게 전달

```
PublishSubject<String> subject = PublishSubject.create();
subject.subscribe(data -> System.out.println("Subscriber #1 -> " + data));
subject.onNext("1");
subject.onNext("3");
subject.subscribe(data -> System.out.println("Subscriber #2 -> " + data));
subject.onNext("5");
subject.onComplete();

/*
Subscriber #1 -> 1
Subscriber #1 -> 3
Subscriber #1 -> 5
Subscriber #2 -> 5
*/
```

4. ReplaySubject class

>구독자가 새로 생기면 항상 데이터의 처음부터 끝까지 발행하는 것을 보장
 'Replay' 단어대로 동작.

```
ReplaySubject<String> subject = ReplaySubject.create();
subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
subject.onNext("1");
subject.onNext("3");
subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
subject.onNext("5");
subject.onComplete();

/*
Subscriber #1 => 1
Subscriber #1 => 3
Subscriber #2 => 1
Subscriber #2 => 3
Subscriber #1 => 5
Subscriber #2 => 5
*/
```

#### Etc

### ConnectableObservable class

>Subject class 처럼 Cold Observable 을 Hot Observable 로 변환.<br>
connect() 함수를 호출한 시점부터 subscribe() 함수를 호출한 구독자에게 데이터를 발행.

```
String[] dt = {"1", "3", "5"};
Observable<String> balls = Observable.interval(100L, TimeUnit.MILLISECONDS)
        .map(Long::intValue)
        .map(i -> dt[i])
        .take(dt.length);
ConnectableObservable<String> source = balls.publish();
source.subscribe(data -> System.out.println("Subscriber #1 => " + data));
source.subscribe(data -> System.out.println("Subscriber #2 => " + data));
source.connect();

Thread.sleep(250);
source.subscribe(data -> System.out.println("Subscriber #3 =>" + data));
Thread.sleep(100);

/*
Subscriber #1 => 1
Subscriber #2 => 1
Subscriber #1 => 3
Subscriber #2 => 3
Subscriber #1 => 5
Subscriber #2 => 5
Subscriber #3 =>5
*/
```
