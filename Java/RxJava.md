# RxJava
> Java에서 Reactive Programming 을 구현을 위한 라이브러리

#### 구성요소
- Observable
- Observer
- Operator

### Observable

> 데이터 제공자. 지속적으로 흐름을 파악할 수 있는 데이터.
Ex) 현실에서 유튜버를 **Observable** 하다 라고 할 수 있고, 유튜버를 구독하는 사람들을 **Subcriber** 라고 할 수 있다.

Observable 은 두 가지 종류가 있다.

- Hot Observable
Subcriber(구독자)  가 없어도 동작

- Cold Observable
Subcriber(구독자)  가 있을 때만 동작


**just 를 통해 1, 2, 3, 4 를 방출하는 Observable 예시 : **
<pre><code>Observable<String> observable = Observable.just("1", "2", "3", "4");
</code></pre>

아래 마블 다이어그램이 어떻게 Observable과 Observable의 전환을 표현하는지 보여준다.

![](http://reactivex.io/assets/operators/legend.png)

### Observer
>데이터 처리/데이터 수신자. 옵저버는 Observable을 구독한다. 
Ex) 유튜브 시청자

`onNext(T)`

Observable 이 하나의 T 객체를 내보낼 때 호출된다.
여러개의  객체인 경우 각 객체가 처리될 때 마다 호출됨

`onCompleted()`

Observable 이 객체를 다 내보내면 호출. 동작 완료의 의미

`onError(Throwable)`

Observable은 기대하는 데이터가 생성되지 않았거나 다른 이유로 오류가 발생할 경우 오류를 알리기 위해 이 메서드를 호출한다. 이 메서드가 호출되면 onNext나 onCompleted는 더 이상 호출되지 않는다.

#### DisposableObserver
>메모리 누수를 방지하기 위한 Observer 이며, Thread-Safe 하다.

**DisposableObserver 예시**
<pre><code>Observable<String> observable = Observable.just("1", "2", "3", "4");

DisposableObserver<String> disposableObserver = observable.subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
</code></pre>

**안드로이드에서 메모리 릭 방지 처리**
<pre><code>    @Override
    protected void onDestroy() {
        if(disposableObserver != null && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
        super.onDestroy();
    }
</code></pre>

### Operator
> 연산자.
ReactiveX를 지원하는 언어 별 구현체들은 다양한 연산자들을 제공하는데, 이 중에는 공통적으로 제공되는 연산자도 있지만 반대로 특정 구현체에서만 제공하는 연산자들도 존재한다.

**`Just`** : 객체 하나 또는 객채집합을 Observable로 변환한다. 변환된 Observable은 원본 객체들을 발행한다

**just 마블다이어그램 (marble diagram) **
![](http://reactivex.io/documentation/operators/images/just.c.png)

**just 예제**
<pre><code>Observable<String> observable = Observable.just("1", "2", "3", "4");
        DisposableObserver<String> disposableObserver = observable.subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                // "1", "2", "3", "4", "5"
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
</code></pre>

**`filter`** : 테스트 조건을 만족하는 항목들만 배출한다.

아래 예제 코드에서는 `onNext()` 에서 정수 '3' 이 전달되고 이후 `onComplete()` 가 호출된다.

**filter 예제**
<pre><code>Observable<Integer> observable2 = Observable.just(1, 2, 3, 4, 5)
                .filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Integer>() {
                    @Override
                    public boolean test(Integer value) {
                        if(value == 3) {
                            return true;
                        }
                        return false;
                    }
                });

        DisposableObserver<Integer> disposableObserver2 =
                observable2.subscribeWith(new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer value) {
					// 3
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
</code></pre>

**filter 마블다이어그램 (marble diagram)  **
![](./img/rx_filter.PNG)











