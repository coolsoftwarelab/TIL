### build.gradle

```
implementation 'com.squareup.retrofit2:retrofit:2.4.0'
implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
implementation 'com.squareup.okhttp3:okhttp:3.10.0'     // Dependencies okio 1.14.0
implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'
implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
implementation 'io.reactivex.rxjava2:rxjava:2.2.6'
```

### NetworkHandler

```
public class NetworkHandler {
    private static final String TAG = NetworkHandler.class.getSimpleName();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static class LazyHolder {
        static final NetworkHandler INSTANCE = new NetworkHandler();
    }

    private static ServerInterface serverInterface;

    private NetworkHandler() {
        init();
    }

    public static ServerInterface getServerInterface() {
        return serverInterface;
    }

    private void init() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(logging);
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.retryOnConnectionFailure(true);
        OkHttpClient okHttpClient = okHttpBuilder.build();

        serverInterface = new Retrofit.Builder()
                .baseUrl(Config.SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ServerInterface.class);
    }

    public static NetworkHandler getInstance() {
        return LazyHolder.INSTANCE;
    }
}
```

### Usage
```
RequestModel.CheckStatus reqCheckStatus = new RequestModel.CheckStatus();
reqCheckStatus.setId(id);
String param = reqCheckStatus.toJson();
ServerInterface serverInterface = RetrofitHandler.getInstance().getServerInterface();

Observable<ResponseModel.CheckStatus> observable = serverInterface.CheckStatus(param);
CheckStatusDisposable = observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(respCheckStatus -> {
                        // response handling
                    }, 
                    e->{
                        // error handling
                    },
                    ()->{}
                    );
        }
```
