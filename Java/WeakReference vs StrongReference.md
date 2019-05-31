## WeakReference vs StrongReference

우선 가비지 컬렉션에 대해 알아야 한다.

### 자바 가비지 컬렉션(Java Garbage Collection) 이란?
>프로그램이 동적으로 할당했던 메모리 영역 중에서 필요없게 된 영역을 해제하는 기능이다.

자바 가비지 컬렉션(Java Garbage Collection)은 객체가 가비지인지 판별하기 위해 'reachability'라는 개념을 사용한다.<br>
root set으로부터 연결되어 객체에 유효한 참조가 있으면 'reachable'로,<br>
root set으로부터 연결되어 있지 않으면 'unreachable'로 구별하고 가비지로 간주해 GC를 수행한다.<br>
(unreachable 객체들끼리 참조하는 경우도 root set으로부터 연결되어 있지 않으므로 unreachable 로 판단한다.)

### 1. StrongReference
유효한 참조가 있는 한 GC 대상이 되지 않는다.

- `new` 키워드로 생성하는 객체. 
- `null` 처리로 'unreachable' 상태로 만들어 GC 대상으로 만들 수 있다.

```
Test t = new Test(); // 'reachable'
t = null;            // 'unreachable'
```

### 2. WeakReference
GC 가 실행되면 메모리가 회수된다.

```
WeakReference<Test> wr = new WeakReference<Test>(new Test());  
Test t = wr.get();  
...
t = null;  
```

위에서 wr.get() 으로 얻은 객체 't' 는 StrongReference 상태이고 null 처리를 하면
WeakReference 내부에서만 참조하는 weakly reachable 상태가 되어 GC 대상이 된다.


참조 :
https://ko.wikipedia.org/wiki/%EC%93%B0%EB%A0%88%EA%B8%B0_%EC%88%98%EC%A7%91_(%EC%BB%B4%ED%93%A8%ED%84%B0_%EA%B3%BC%ED%95%99)
https://d2.naver.com/helloworld/329631
https://gogorchg.tistory.com/entry/Java-WeakReference-SoftReferernce-StrongReference
