### Retrofit2 Multiple base url

```
public class RetrofitManager {
    private static final String TAG = RetrofitManager.class.getSimpleName();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static class LazyHolder {
        static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    private Server1Interface server1Interface;
    private Server2Interface server2Interface;

    public Server1Interface getServer1Interface(String url) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(logging);
        okHttpBuilder.connectTimeout(15, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(15, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(15, TimeUnit.SECONDS);
        okHttpBuilder.retryOnConnectionFailure(true);
        OkHttpClient okHttpClient = okHttpBuilder.build();

        server1Interface = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(Server1Interface.class);
        return server1Interface;
    }

    public Server2Interface getServer2Interface(String url) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(logging);
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.retryOnConnectionFailure(true);
        OkHttpClient okHttpClient = okHttpBuilder.build();

        server2Interface = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(Server2Interface.class);
        return server2Interface;
    }

    public static RetrofitManager getInstance() {
        return LazyHolder.INSTANCE;
    }
}
```

### Server1Interface

```
    @POST("/a/alpha")
    Observable<ResponseModel.Serv1Model> getData(@Body String body);
}
```

### Server2Interface

```
    @POST("/b/bravo")
    Observable<ResponseModel.Serv2Model> getData(@Body String body);
}
```

### USAGE

```
// Server 1
Server1Interface server1Interface = RetrofitManager.getInstance().getServer1Interface();
Observable<ResponseModel.Data1> observable = server1Interface.sendData(param);
Disposable serv1Disposable = observable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(resp -> {
                    // response handling
                },
                e -> {
                    // error handling
                }
        );
	
// Server 2
Server2Interface server2Interface = RetrofitManager.getInstance().getServer1Interface();
Observable<ResponseModel.Data2> observable = server1Interface.sendMessage(param);
Disposable serv2Disposable = observable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(resp -> {
                    // response handling
                },
                e -> {
                    // error handling
                }
        );
```




