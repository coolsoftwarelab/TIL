## 람다식 (Lambda Expressions)
> 식별자 없이 실행 가능한 함수 표현식

**요약**
(x, y)->{return x+y;}

**예시. 일반적인 버튼 클릭 이벤트**
<pre><code>mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JDEBUG", "Click!");
            }
});</code></pre>

**예시. 람다식**

<pre><code>mTestButton.setOnClickListener(v -> Log.d("JDEBUG", "Click!"));</code></pre>

안드로이드 스튜디오에서 사용 시 build.gradle 에 아래 구문 추가
<pre><code>android {
...
		compileOptions {
	       	sourceCompatibility JavaVersion.VERSION_1_8
	       	targetCompatibility JavaVersion.VERSION_1_8
	    }
...
}</code></pre>
