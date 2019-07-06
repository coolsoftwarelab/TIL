### Scheduler
>데이터를 처리하고자 하는 특정한 스레드

Scheduler | 용도
----------|-----------
Schedulers.computation( ) | 이벤트-루프와 콜백 처리 같은 연산 중심적인 작업을 위해 사용된다. 그렇기 때문에 I/O를 위한 용도로는 사용하지 말아야 한다(대신 Schedulers.io( )를 사용) 기본적으로 스레드의 수는 프로세서의 수와 같다
Schedulers.io( ) | 블러킹 I/O의 비동기 연산 같은 I/O 바운드 작업을 처리한다. 이 스케줄러는 필요한 만큼 증가하는 스레드-풀을 통해 실행된다; 일반적인 연산이 필요한 작업은 Schedulers.computation( )를 사용하면 된다
Schedulers.newThread( )	 | 각각의 단위 작업을 위한 새로운 스레드를 생성한다
AndroidScheduler.mainThread() | RxAndroid 라이브러리에서 사용하는 방식으로 메인 스레드에서 동작해야하는 (UI 처리) 작업을 위해 사용

#### Observable 의 연산자를 활용한 Scheduler 사용

1. subscribeOn()<br>
Observable을 구독할 때 사용할 스케줄러를 명시한다 (특정한 스레드를 지정해서 데이터 처리)
한번만 사용하능하며, 여러 번 중첩해서 선언하는 경우 처음 지정한 스레드로 동작한다<br>
데이터 전달자가 사용하는 스레드를 지정한다.

2. observeOn()<br>
옵저버가 어느 스케줄러 상에서 Observable을 관찰할지 명시한다 (결과를 받는 스레드)
따로 지정하지 않으면 subscribeOn() 에서 지정한 스레드로 동작한다.
subscribeOn() 과 달리 observeOn() 은 여러번 호출하여 각각 연산에 대한 스레드를 달리 할 수 있다.

**observeOn()이 우선된다.**

- subscribeOn() 선언 후 observeOn() 이 선언되면 subscribeOn()은 무시되고,
observeOn() 이 먼저 선언되고 subscribeOn()이 선언되어도 observeOn() 으로 동작한다.

<br>

#### Scheduler를 지정하지 않았을 때
```
Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5)
	.map(t -> {
	    System.out.println("Thread in map : " 
	    	+ Thread.currentThread().getName());  // main
	    return t + 1;
	});

observable.subscribe(t -> {
    System.out.println("Thread in map : " 
    	+ Thread.currentThread().getName()); // main
});
```

#### subscribeOn(Schedulers.io())
```
Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5)
	.map(t -> {
	    System.out.println("Thread in map : " 
	    	+ Thread.currentThread().getName());  // RxCachedThreadScheduler-1
	    return t + 1;
	});

observable.subscribeOn(Schedulers.io())
	.subscribe(t -> {
    System.out.println("Thread in subscribe : " 
    	+ Thread.currentThread().getName()); // RxCachedThreadScheduler-1
});
```

#### subscribeOn(Schedulers.computation())
```
Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5)
	.map(t -> {
	    System.out.println("Thread in map : " 
	    	+ Thread.currentThread().getName());  // RxComputationThreadPool-1
	    return t + 1;
	});

observable.subscribeOn(Schedulers.computation())
	.subscribe(t -> {
	    System.out.println("Thread in subscribe : " 
	    	+ Thread.currentThread().getName()); // RxComputationThreadPool-1
	});
});
```

<br>
<br>

#### subscribeOn(Schedulers.computation()), observeOn(Schedulers.io())
```
Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5)
	.map(t -> {
	    System.out.println("Thread in map : " + Thread.currentThread().getName());  // RxComputationThreadPool-1
	    return t + 1;
	});

observable.subscribeOn(Schedulers.computation())
	.observeOn(Schedulers.io())
	.subscribe(t -> {
	    System.out.println("Thread in subscribe : " + Thread.currentThread().getName()); // RxCachedThreadScheduler-1
	});
```

<br>

### Multiple scheduler test
```
Observable<Integer> observable = Observable.just(0, 1, 2, 3, 4)
	.map(t -> {
	    System.out.println("Thread in map : " + Thread.currentThread().getName());  // RxCachedThreadScheduler-1
	    return t + 1;
	});

observable.subscribeOn(Schedulers.io())
	.observeOn(Schedulers.computation())
	.reduce((a, b) -> {
	    System.out.println("Thread in reduce : " + Thread.currentThread().getName());  // RxComputationThreadPool-1
	    return a + b;
	})
	.observeOn(Schedulers.computation())
	.filter(t -> {
	    System.out.println("Thread in filter : " + Thread.currentThread().getName());  // RxCachedThreadScheduler-2
	    if (t == 15) {
		return true;
	    }
	    return false;
	})
	.observeOn(AndroidSchedulers.mainThread())
	.subscribe(t -> {
	    System.out.println("Thread in subscribe : " + Thread.currentThread().getName()); // main
	    System.out.println("Thread in subscribe t : " + t); // 15
	});
```

