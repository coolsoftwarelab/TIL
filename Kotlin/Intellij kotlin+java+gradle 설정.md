IDE 프로젝트 추가

![스크린샷 2021-05-18 오후 3 07 26](https://user-images.githubusercontent.com/4969393/118599617-c927ef80-b7ea-11eb-9e86-1f7ed93b29bb.png)

코드
```
fun main() { 
  pirntln ("test")
}
```

edit config -> Application -> 빌드버전, main() 함수가 있는 클래스 있는 설정

예시
- Test.kt 파일안에 main() 함수 존재
- config에서 메인 클래스를 자동감지 못하는 경우, 'Main class' 란에 main()함수가 있는 클래스파일 직접 타이핑(Test.kt -> TestKt)
