### Android AAC 의 ViewModel 은 MVVM 패턴에서의 ViewModel 이 아니다.

#### AAC의 ViewModel 은 화면 회전에 대한 해결책이다.
>ViewModel 클래스는 수명 주기를 고려하여 UI 관련 데이터를 저장하고 관리하도록 설계되었습니다. ViewModel 클래스를 사용하면 화면 회전과 같이 구성을 변경할 때도 데이터를 유지할 수 있습니다.

#### 기존 화면 회전
- 액티비티 재생성에 대응해서 onSaveInstanceState() 를 통해 상태 정보를 저장하고 onCreate() 에서 로드.
- 직렬화 된 객체가 아니면 저장 할 수 없는 문제점
- 소량의 데이터에만 적합

#### ViewModel 은 Activity 생명주기에 관계 없이 일관된 상태를 유지한다.  
![](https://developer.android.com/images/topic/libraries/architecture/viewmodel-lifecycle.png)

#### 주의사항
- Singleton 객체로 사용된다.
- 일반적인 객체 생성(new 키워드)대신 ViewModelProvider를 통해 객체를 생성하고 관리한다.
- ViewModel에 액티비티, 프래그먼트, 뷰에 대한 컨텍스트를 저장하면 안된다. (메모리 릭)

#### 참조
https://developer.android.com/topic/libraries/architecture/viewmodel#java

https://medium.com/@jungil.han/%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8-viewmodel-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-2e4d136d28d2
