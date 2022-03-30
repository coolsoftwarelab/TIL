내용추가 예정

>LiveData는 식별 가능한 데이터 홀더 클래스입니다. 식별 가능한 일반 클래스와 달리 LiveData는 수명 주기를 인식합니다. 즉 활동, 프래그먼트 또는 서비스와 같은 다른 앱 구성요소의 수명 주기를 고려합니다. 이러한 수명 주기 인식을 통해 LiveData는 활성 수명 주기 상태에 있는 앱 구성요소 관찰자만 업데이트합니다.

데이터의 변화를 감지한다.  
RxJava의 Observable 과 비슷한 듯


참조 : https://developer.android.com/topic/libraries/architecture/livedata?hl=ko

Activity
```kotlin
class LiveDataTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val model: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvNum.text = "init"

        model.getCount().observe(this, Observer {
            binding.tvNum.text = it.toString()
        })

        binding.btnInc.setOnClickListener {
            model.increase()
        }
    }
}
```

ViewModel + LiveData
```kotlin
class MyViewModel: ViewModel() {
    private var count = MutableLiveData<Int>()

    init {
        count.value = 1
    }
    fun getCount(): LiveData<Int> {
        return count
    }

    fun increase() {
        count.value = count.value?.plus(1)
    }
}
```
