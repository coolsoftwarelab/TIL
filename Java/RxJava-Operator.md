#### fromArray

Array 의 값을 Observable 형태로 변환

```
Integer[] arr = new Integer[]{ 1, 2, 3, 4, 5 };
Observable observable = Observable.fromArray(arr);
```

#### map

입력 값을 어떠한 함수에 넣어서 원하는 값을 반환하는 함수

```
Integer[] arr = new Integer[]{ 1, 2, 3, 4, 5 };
Observable.fromArray(arr).map((Function<Integer, Object>) integer -> integer + 10)
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

#### flatMap

`map` 처럼 입력 값을 처리한 후 반환 값을 `Observable` 형태로 반환한다.

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
