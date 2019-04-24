### Droid Knights 2019 Java vs Kotlin 실제 안드로이드 코드 사례. 발표자 박상권

#### 2017년 Google I/O에서 공식 언어로 지정

+ Java Annotation 과 비교
  ```
  var address: String? = null   // @Nullable 과 같음
  var id: String = "ted"        // @NonNull 과 같음
  ```

+ var와 달리 val 은 get() 만 가능

+ 물음표를 이용해서 null이 아니라면 다음 변수를 참조할 수 있도록 체이닝할 수 있습니다. 이러한 개념을 safe call이라고 합니다.
  * ex) 순차적으로 null 체크 후 변수가 null 이라면 null 리턴
      ```
      private fun getSelectedDealerName(car: Car?) : String? {
        return car?.auction?.selectedBid?.dealer?.name
      }
      ```

+ class 생성 (VO)
  * ex) var를 선언해서 setter/getter가 자동생성되었고, 물음표를 통해서 Nullable한 필드임을 알 수 있습니다. 또한 생성자도 만들어 줍니다.
      ```
      data class Dealer(var name: String?, var address: String?)
      ```

+ Log
  * Log.d("ted", "딜러이름: $name, 딜러주소: $address")

+ Lambda
  ```
  button.setOnClickListener {
    // to something.
  }
  ```

+ apply / also / run / let / with
![](https://cdn-images-1.medium.com/max/1200/1*cKwEowUXup3K7LmiMgn3XQ.png)

![](https://cdn-images-1.medium.com/max/800/1*t3hR3BuuWySMGdcN5SNhXg.png)

+ Java 코드와의 호환
  * Kotlin 에서 만들고
      ```
      class ForJava(var name: String, val birth: Int, var isShow: Boolean)
      ```
  * Java 에서 사용
      ```
      String name = forJava.getName();
      forJava.setname("ted");
      ...
      ```

+ Kotlin으로 작성된 코드는 Java 에서 원래의 사용행태처럼 사용할 수 있다.
  * Annotation 이용 : @JvmName, @JvmField, @JvmStatic, @JvmOverloads 등등

+ Extednsion function
  * 원래정의되어있던함수인것처럼 사용 (BaseActivity 를 만들어 Utils처럼 에서 사용하던 것처럼.
      ```
      fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
      }

      showToast("안녕하세요")
      ```

+ 코틀린을 자바처럼 코딩하지 말자
  ```
  val name = bid!!.dealer!!.name

  val intent = Intent(this, TedActivity::class.java)
  intent.putExtra(EXTRA_AAA, aaa);

  Log.d("ted", "딜러이름: " + name + " , 딜러주소:" + address)
  ```
