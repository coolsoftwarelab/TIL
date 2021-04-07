## 람다식 (Lambda Expressions)

> 익명함수를 표현하는 하나의 표현식 (익명 메소드)  
> 즉, 함수를 따로 만들지 않고 코드 한줄에 함수를 써서 그것을 호출하는 방식  
> 람다 형태는 매개변수를 가진 코드블록이지만, 런타임 시에는 익명 구현 객체를 생성한다  
> 자바는 메소드만 전달할 수 있는 방법은 없었기 기때문에 매번 객체를 생성해서 매개변수로 전달해야 했다. 이런 부분을 해결한 것이 람다표현식이다.


**요약**
(x, y)->{return x+y;}

**예시) 일반적인 버튼 클릭 이벤트**
```
mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JDEBUG", "Click!");
            }
});
```

**예시) 람다식**
```
mTestButton.setOnClickListener(v -> Log.d("JDEBUG", "Click!"));
```

안드로이드 스튜디오에서 사용 시 build.gradle 에 아래 구문 추가
```
android {
	...
	compileOptions {
		sourceCompatibility JavaVersion.VERSION_1_8
		targetCompatibility JavaVersion.VERSION_1_8
	}
	...
}
```
