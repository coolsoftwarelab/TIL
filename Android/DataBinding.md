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

