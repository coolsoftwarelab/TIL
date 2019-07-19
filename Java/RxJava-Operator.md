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
Integer[] arr = new Integer[]{ 1, 2, 3, 4, 5 };
Observable
  .fromArray(arr)
  .flatMap((Function<Integer, ObservableSource<?>>) integer -> Observable.just(integer + 10))
  .subscribe(new DisposableObserver<Object>() {
    @Override
    public void onNext(Object o) {
        Log.d("TAG", "Object o : " + o);  // 11, 12, 13, 14, 15
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }
});
```




#### fromArray

Array 의 값을 Observable 형태로 변환

```
Integer[] arr = new Integer[]{ 1, 2, 3, 4, 5 };
Observable observable = Observable.fromArray(arr);
```
