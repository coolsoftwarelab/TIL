## AAC (Android Architecture Components)

>Android 아키텍처 구성 요소는 강력하고 테스트 가능하며 유지 관리가 쉬운 앱을 디자인하는 데 도움이되는 라이브러리 모음.
**Android Jetpack** 의 일부이다.

### Lifecycle-Aware Components
앱의 라이프 사이클을 관리. 
새로운 라이프 사이클 인식 구성 요소로 활동 및 조각 수명주기를 관리 할 수 있다.
생존하는 구성 변경, 메모리 누수를 피하고 UI에 데이터를 쉽게로드 할 수 있다.

### LiveData
LiveData 를 사용 하면 기본 데이터베이스가 변경 될 때 뷰에 알리는 데이터 개체를 작성할 수 있다.

### ViewModel
ViewModel 앱 회전시 파괴되지 않는 UI 관련 데이터를 저장한다.
(작업이 중복되거나 데이터가 소실되지 않도록 한다)

### Room
SQLite 객체 매핑 라이브러리.
이를 통해 상용구 코드를 피하고 SQLite 테이블 데이터를 Java 객체로 쉽게 변환 할 수 있다.<br>
Room은 SQLite 문의 컴파일 타임 검사를 제공하며 RxJava, Flowable 및 LiveData 관찰을 반환 할 수 있다.


#### 구글 AAC 가이드
![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
