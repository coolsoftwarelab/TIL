## Operator

>자바 관점에서는 메서드. 순수함수(Pure functions)<br>
생성, 변환, 필터, 합성, 오류처리, 유틸리티, 조건, 수학과집합형, 배압 등이 있다.

#### map

입력 값을 어떠한 함수에 넣어서 원하는 값을 반환하는 함수

```
String[] balls = {"1", "2", "3", "4", "5"};

// First way
Observable<String> source = Observable.fromArray(balls)
        .map(ball -> ball + "<>");
// Second way
// Function<String, String> getDiamond = ball -> ball + "<>";
// Observable<String> source = Observable.fromArray(balls).map(getDiamond);

source.subscribe(System.out::println);

/*
1<>
2<>
3<>
4<>
5<>
*/
```

#### flatMap

>`map` 함수처럼 입력 값을 처리한 후 반환 값을 `Observable` 형태로 반환한다.<br>
`map` 은 일대일, `flatMap` 은 일대일 혹은 일대다

```
String[] balls = {"1", "3", "5"};

// First way
Observable<String> source = Observable.fromArray(balls)
.flatMap(ball -> Observable.just(ball + "<>", ball + "<>"));

// Second way
//        Function<String, Observable<String>> getDoubleDiamonds =
//                ball -> Observable.just(ball + "<>", ball + "<>");
//        Observable<String> source = Observable.fromArray(balls).flatMap(getDoubleDiamonds);

source.subscribe(System.out::println);

/*
1<>
1<>
3<>
3<>
5<>
5<>
*/
```

#### filter

>Array 의 값을 Observable 형태로 변환

```
String[] objs = {"1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXGON"};

// First way
Observable<String> source = Observable.fromArray(objs)
.filter(obj -> obj.endsWith("CIRCLE"));

// Second way
//        Predicate<String> getFilteredData = data -> data.endsWith("CIRCLE");
//        Observable<String> source = Observable.fromArray(objs).filter(getFilteredData);

source.subscribe(System.out::println);

/*
1 CIRCLE
5 CIRCLE
*/
```

#### filter 와 유사한 함수
- first(default) : Observable의 첫 번째 값을 필터함. 만약 값이 없다면 기본값 리턴.
- last(default) : Observable의 마지막 값을 필터함. 만약 값이 없다면 기본값 리턴.
- take(N) : 최초 N개 값만 가져옴.
- takeLast(N) : 마지막 N개 값을 건너 뜀.
- skip(N) : 최초 N개 값을 건너뜀.
- skipLast(N) : 마지막 N개 값은 건너뜀.

```
Integer[] numbers = new Integer[]{100, 200, 300, 400, 500, 600};
Single<Integer> single;
Observable<Integer> source;

// first
single = Observable.fromArray(numbers).first(-1);
single.subscribe(data -> System.out.println("first() value = " + data));

// last
single = Observable.fromArray(numbers).last(999);
single.subscribe(data -> System.out.println("last() value = " + data));

// take(N)
source = Observable.fromArray(numbers).take(3);
source.subscribe(data -> System.out.println("take(3) value = " + data));

// takeLast(N)
source = Observable.fromArray(numbers).takeLast(3);
source.subscribe(data -> System.out.println("takeLast(3) value = " + data));

// skip(N)
source = Observable.fromArray(numbers).skip(2);
source.subscribe(data -> System.out.println("skip(3) value = " + data));

// skipLast(N)
source = Observable.fromArray(numbers).skipLast(2);
source.subscribe(data -> System.out.println("skipLast(2) value = " + data));

/*
first() value = 100
last() value = 600
take(3) value = 100
take(3) value = 200
take(3) value = 300
takeLast(3) value = 400
takeLast(3) value = 500
takeLast(3) value = 600
skip(3) value = 300
skip(3) value = 400
skip(3) value = 500
skip(3) value = 600
skipLast(2) value = 100
skipLast(2) value = 200
skipLast(2) value = 300
skipLast(2) value = 400
*/
```

#### reduce

>발행한 데이터를 모두 사용하여 최종 결과 데이터를 합성

```
String[] ballas = {"1", "3", "5"};
Maybe<String> source = Observable.fromArray(ballas)
        .reduce((ball1, ball2) -> ball2 + "(" + ball1 + ")");
source.subscribe(System.out::println);

/*
5(3(1))
*/
```

#### interval

>일정 시간 간격으로 데이터 흐름을 생성한다.<br>
일정 시간 지연 후 시작할 수도 있고, 최초 지연 시간(initialDelay)을 조절할 수도 있다.

```
Observable<Long> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
        .map(data -> {
            return (data + 1) * 100;
        })
        .take(5);
source.subscribe(System.out::println);

try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    e.printStackTrace();
}

/*
100
200
300
400
500
*/
```

#### timer

>일정 시간이 지난 후에 한 개의 데이터를 발행하고 onComplete() 이벤트가 발생한다.

```
Observable<String> source = Observable.timer(500L, TimeUnit.MILLISECONDS)
        .map(data -> new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(new Date()));
source.subscribe(System.out::println);

try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    e.printStackTrace();
}

/*
2019/07/30 10:32:01
 */
```

#### range

>주어진 값 n 부터 m 까지의 Integer 객체를 발행한다. (n, m)

```
Observable<Integer> source = Observable.range(1, 3);
source.subscribe(System.out::println);

/*
1
2
3
*/
```

### intervalRange

>일정한 간격으로 시작 숫자(n) 로부터 m 개 값을 생성하고 onComplete 이벤트 발생  

```
Observable<Long> source = Observable.intervalRange(1,   // start
        3,                                              // count
        100L,                                           // initialDelay
        100L,                                           // preriod
        TimeUnit.MILLISECONDS);                         // unit
source.subscribe(System.out::println);

Thread.sleep(1000);

/*
1
2
3
*/
```

#### defer

추가예정

#### repeat

>  반복 실행

```
String[] balls = {"1", "2", "3"};
Observable<String> source = Observable.fromArray(balls)
        .repeat(3);

source.doOnComplete(() -> System.out.println("onComplete"))
        .subscribe(System.out::println);
```

#### concatMap

> flatMap과는 달리 먼저 들어온 데이터 순서대로 처리해서 결과를 낼 수 있도록 보장한다.

```
String[] balls = {"1", "3", "5"};
Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
        .map(Long::intValue)
        .map(idx -> balls[idx])
        .take(balls.length)
        .concatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
        .map(notUsed -> ball + "<>")
        .take(3)
        );
source.subscribe(System.out::println);
Thread.sleep(2000);

/*
1<>
1<>
1<>
3<>
3<>
3<>
5<>
5<>
5<>
 */
```

#### switchMap()

> 순서를 보장하기위해 기존에 진행 중이던 작업을 중단.  
여러 개의 값이 발행되었을 때 마지막으로 들어온 값만 처리하고 싶을 때 사용 (ex. 센서 값)

```
String[] balls = {"1", "3", "5"};
Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
        .map(Long::intValue)
        .map(idx -> balls[idx])
        .take(balls.length)
        .doOnNext(System.out::println)
        .switchMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                .map(notUsed -> ball + "<>")
                .take(3)
        );
source.subscribe(System.out::println);
Thread.sleep(2000);

/*
1
3
5
5<>
5<>
5<>
*/
```

#### zip

> 입력 Observable 에서 데이터를 모두 새로 발행했을 때 그것을 합해준다. (결합)  

```
Observable<Integer> subscribe = Observable.zip(
        Observable.just(1, 2, 3),
        Observable.just(10, 20, 30),
        Observable.just(100, 200, 300),
        (a, b, c) -> a + b + c);
subscribe.subscribe(System.out::println);

/*
111
222
333
 */
```

#### groupBy

>  어떤 기준(keySelector 인자)으로 단일 Observable을 여러 개로 이루어진 Observable 그룹(GroupObservable)으로 만든다.

```
String[] objs = {"6", "4", "2-T", "2", "6-T", "4-T"};
Observable<GroupedObservable<String, String>> source =
        Observable.fromArray(objs).groupBy(d -> getShape(d));
source.subscribe(obj -> {
    obj.subscribe(val ->
            System.out.println("GROUP : " + obj.getKey() + "\t Value : " + val));
});

/*
GROUP : BALL	 Value : 6
GROUP : BALL	 Value : 4
GROUP : TRIANGLE	 Value : 2-T
GROUP : BALL	 Value : 2
GROUP : TRIANGLE	 Value : 6-T
GROUP : TRIANGLE	 Value : 4-T
*/
```

#### scan

> reduce()  함수와 달리 실행할 때마다 입력값에 맞는 중간 결과 및 최종 결과를 구독자에게 발행한다.

```
String[] balls = {"1", "3", "5"};
Observable<String> source = Observable.fromArray(balls)
        .scan((ball1, ball2) -> ball2 + "(" + ball1 + ")");
source.subscribe(System.out::println);

/*
1
3(1)
5(3(1))
 */
```

#### combineLastest

> 2개 이상의 Observable을 기반으로 Observable 각각의 값이 변경되었을 때 갱신해주는 함수이다.

```
String[] data1 = {"6", "7", "4", "2" };
String[] data2 = {"DIAMOND", "STAR", "PENTAGON" };

Observable<String> source = Observable.combineLatest(
        Observable.fromArray(data1)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS),
                        (shape, notUsed) -> Shape.getColor(shape)),
Observable.fromArray(data2)
        .zipWith(Observable.interval(150L, 200L, TimeUnit.MILLISECONDS),
                (shape, notused) -> Shape.getSuffix(shape)), (v1, v2) -> v1 + v2);
source.subscribe(System.out::println);

Thread.sleep(1000);

/*
6<>
7<>
4<>
4-S
2-S
2-P
*/
```

#### merge

> 입력 Observable의 순서와 모든 Observable이 데이터를 발행하는지 등에 관여하지 않고 어느 것이든 업스트림에서 먼저 입력되는 데이터를 그대로 발행한다.

```
String[] data1 = {"1", "3"};
String[] data2 = {"2", "4", "6"};

Observable<String> source1 = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
        .map(Long::intValue)
        .map(idx -> data1[idx])
        .take(data1.length);
Observable<String> source2 = Observable.interval(50L, TimeUnit.MILLISECONDS)
        .map(Long::intValue)
        .map(idx -> data2[idx])
        .take(data2.length);

Observable<String> source = Observable.merge(source1, source2);

source.subscribe(System.out::println);
Thread.sleep(1000);

/*
1
2
3
4
6
 */
```
