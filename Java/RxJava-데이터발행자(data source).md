## RxJava - 데이터 발행자 (Data source)

### Observable Class

>옵서버 패턴을 구현. 데이터 흐름에 맞게 알림을 보내 구독자가 데이터를 처리할 수 있도록 한다. 데이터를 무한하게 발행 할 수 있다.

#### 세 가지 알림을 구독자에게 전달

- onNext : Observable이 데이터의 발생을 알림.

- onComplete : 모든 데이터 발행 완료 알림. 이후 onNext 발생 안함.

- onError : 에러 발생. onError 이벤트 후에 onNext 및 onComplete 발생안함.

```
Observable<Integer> source = Observable.just(1, 2, 3, 4, 5);
source.subscribe(System.out::println);
```

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


### Maybe

>Single 클래스에 onComplete 이벤트가 추가된 형태.<br>
최대 데이터 하나를 가질 수 있지만 데이터 발행 없이 바로 데이터 발생을 완료 할 수 있다.

#### 함수

- onSuccess

- onError

- onComplete

### Subject

> Cold Observable 을 Hot Observable 로 바꿔준다.






















