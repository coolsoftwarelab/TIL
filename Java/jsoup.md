## JSOUP

>jsoup 는 HTML 문서에 저장된 데이터를 구문 분석, 추출 및 조작하도록 설계된 오픈 소스 Java 라이브러리

## 안드로이드에서 사용
구글 메인페이지 버튼 value 값 추출 예시 (Network 작업이므로 Async로 작업 필요)

**build.gradle**
>implementation 'org.jsoup&#58;jsoup&#58;1.11.3'

** source **
<pre>
<code>doc = Jsoup.connect(http://www.google.com).get();
Element btnK = doc.select("input[name=btnK]").first();
String btnKValue = btnK.attr("value");
Log.d(TAG, "btnKValue : " + btnKValue);		// 'Google 검색'</code>
</pre>