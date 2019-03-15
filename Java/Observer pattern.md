### 옵서버 패턴(observer pattern)
> 객체의 상태 변화를 관찰하는 관찰자들, 즉 옵저버들의 목록을 객체에 등록하여 상태 변화가 있을 때마다 메서드 등을 통해 객체가 직접 목록의 각 옵저버에게 통지하도록 하는 디자인 패턴이다.
주로 분산 이벤트 핸들링 시스템을 구현하는 데 사용된다. 발행/구독 모델로 알려져 있기도 하다.

UML 다이어그램으로는 아래처럼 표현된다. 관찰 대상인 객체는 “이벤트를 발생시키는 주체”라는 의미에서 Subject로 표시되어 있다.

![](https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Observer.svg/854px-Observer.svg.png)

