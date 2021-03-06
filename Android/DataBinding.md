## DataBinding (데이터바인딩)

데이터 바인딩 라이브러리를 사용하여 선언적 레이아웃을 작성하고 애플리케이션 로직과 레이아웃을 바인딩하는데 필요한 글루 코드를 최소화할 수 있다.

데이터 바인딩 라이브러리는 유연성과 폭넓은 호환성을 모두 제공하는 지원 라이브러리로,
Android 2.1(API 레벨 7 이상)까지 Android 플랫폼의 모든 이전 버전에서 사용할 수 있다.

데이터 바인딩을 사용하려면 Android Plugin for Gradle 1.5.0-alpha1 이상이 필요하다.

[build.gradle]
```
android {
    ....
    dataBinding {
        enabled = true
    }
}
```

[xml]
```
<layout>
...

<data>
    <variable
        name="activity"
        type="com.csl.app.MainActivity" />
</data>

<Button
  android:id="@+id/button"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:onClick="@{activity::onButtonClick}" /> 
  
...
</layout>
```

[src]

- Activity

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.setActivity(this);
}

public void onButtonClick(View view) {
    switch (view.getId()) {
        case R.id.button:
            // hello
            break;
    }
}
```

- Fragment

```
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    FragmentVerifySignBinding binding = 
                    DataBindingUtil.inflate(inflater, R.layout.fragment_verify_sign, container, false);
    View view = binding.getRoot();
    return view;
}
```

- Adapter

```
@Override
public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    MyRecyclerViewRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.my_recycler_view_row, parent, false);
    return new MyListAdapter.ViewHolder(binding);
}

@Override
public void onBindViewHolder(final MyListAdapter.ViewHolder holder, int position) {
    MyRecyclerViewRowBinding binding = holder.binding;
    binding.name.setText("hello");
}

public class ViewHolder extends RecyclerView.ViewHolder {
    private MyRecyclerViewRowBinding binding;

    public ViewHolder(MyRecyclerViewRowBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
```

### UI 자동 업데이트

데이터 바인딩에 임의의 POJO(plain old Java object)를 사용할 수 있지만, POJO를 수정하더라도 UI가 업데이트되지는 않는다.
Observable 객체, Observable 필드, Observable 컬렉션이라는 세 가지 다른 데이터 변경 알림 메커니즘을 사용해야 한다.
이러한 Observable 데이터 객체 중 하나가 UI에 바인딩되어 있고 데이터 객체의 속성이 변경되면 UI가 자동으로 업데이트 된다.

```
<TextView
    android:id="@+id/tv1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{user.nickName}" />
```

```
public class User extends BaseObservable {
    public String nickName;

    @Bindable
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        notifyPropertyChanged(BR.nickName);
    }
}
```

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    User user = new User();
    user.setNickName("hong");
    binding.setUser(user);
   
    // android.os.Handler
    handler.postDelayed(new Runnable() {        
        @Override
        public void run() {
            user.setNickName("gil-dong");
        }
    }, 2000);
}
```

BaseObservable 을 상속받는 대신 ObservableField 등을 사용해도 된다.<br>
ex) public final ObservableField<String> nickName = new ObservableField<>();

### Binding Adapter
>BindingAdapter는 "현재 정의되지 않은 Binding Attribute를 정의하고, 그 내부 로직을 작성" 할 때 쓰인다.
BindingAdapter는 간단하게만 생각하면 Custom View Class를 직접 정의해서 사용하기는 조금 귀찮거나 버거운 일이고, 혹은 꽤 많은 View들에 사용해야 하는데, 그때마다 Custom View Class를 정의하기는 어려울 때 사용한다고 보면 될것 같다.


예제) Binding Adapter 를 이용해서 특정 시점에(2초뒤) 텍스트뷰에 텍스트를 노란색으로 강조(Highlight) 하는 과정<br>
Binding Adapter 를 이용해서 텍스트뷰에 app:highlight 어트리뷰트를 정의하고 이를 user.isTextHighLight 와 연결해서
Model(user) 가 변경됐을 때 동적으로 텍스트 뷰의 텍스트를 하이라이트

```
public class UserBindingAdapter {
    @BindingAdapter(value = {"highlight"})
    public static void setDisappear(TextView tv, boolean highlight) {
        String text = tv.getText().toString();
        if (highlight) {
            SpannableString str = new SpannableString(text);
            str.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, str.length(), 0);
            tv.setText(str);
        } else {
            tv.setText(text);
        }
    }
}
```

```
public class User extends BaseObservable {
    public boolean isTextHighLight;

    @Bindable
    public boolean isTextHighLight() {
        return isTextHighLight;
    }

    public void setTextHighLight(boolean textHighLight) {
        isTextHighLight = textHighLight;
        notifyPropertyChanged(BR.textHighLight);
    }
}
```

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    mUser = new User();
    binding.setUser(mUser);
    
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            mUser.setTextHighLight(true);
        }
    }, 2000);
}
```

```
<TextView
    android:id="@+id/tv1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="test"
    android:textSize="25dp"
    app:highlight="@{user.isTextHighLight}" />
```
<br>
<br>
참조 사이트

https://developer.android.com/topic/libraries/data-binding/?hl=en
http://blog.unsignedusb.com/2017/08/android-databinding-2-bindingadapter.html




