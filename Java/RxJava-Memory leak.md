### 안드로이드에서 RxJava 사용 중 메모리 릭 방지 처리

#### 1. Disposable 인터페이스 이용

```
private Disposable mDisposable;

public void disposableExample() {
DisposableObserver<Integer> observer = new DisposableObserver<Integer>() {
    @Override
    public void onNext(Integer integer) {
	// TextView 등 Activity 의 자원을 참조한다고 가정
    }

    @Override
    public void onError(Throwable e) { }

    @Override
    public void onComplete() { }
};

Observable<Integer> observable = Observable.just(0, 1, 2, 3, 4);
mDisposable = observable.subscribeWith(observer);
}

@Override
protected void onDestroy() {
if (mDisposable.isDisposed()) {
    mDisposable.dispose();
}
super.onDestroy();
}
```

#### 2. RxLifecycle 라이브러리 이용

```









```
