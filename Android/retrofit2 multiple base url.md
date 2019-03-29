### 테스트 필요함 (2019.03.29)

```
public class ApiClient {
	private final static String BASE_URL = "https://simplifiedcoding.net/demos/";
	private final static String BASE_URL2 = "http://freshcamera.herokuapp.com";

	public static ApiClient apiClient;
	private Retrofit retrofit = null;
	private Retrofit retrofit2 = null;

	public static ApiClient getInstance() {
		if (apiClient == null) {
			apiClient = new ApiClient();
		}
		return apiClient;
	}

	//private static Retrofit storeRetrofit = null;

	public Retrofit getClient() {
		return getClient(null);
	}

	public Retrofit getClient2() {
		return getClient2(null);
	}


	private Retrofit getClient(final Context context) {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient.Builder client = new OkHttpClient.Builder();
		client.readTimeout(60, TimeUnit.SECONDS);
		client.writeTimeout(60, TimeUnit.SECONDS);
		client.connectTimeout(60, TimeUnit.SECONDS);
		client.addInterceptor(interceptor);
		client.addInterceptor(new Interceptor() {
			@Override
			public okhttp3.Response intercept(Chain chain) throws IOException {
				Request request = chain.request();

				return chain.proceed(request);
			}
		});

		retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(client.build())
			.addConverterFactory(GsonConverterFactory.create())
			.build();


		return retrofit;
	}
	private Retrofit getClient2(final Context context) {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient.Builder client = new OkHttpClient.Builder();
		client.readTimeout(60, TimeUnit.SECONDS);
		client.writeTimeout(60, TimeUnit.SECONDS);
		client.connectTimeout(60, TimeUnit.SECONDS);
		client.addInterceptor(interceptor);
		client.addInterceptor(new Interceptor() {
			@Override
			public okhttp3.Response intercept(Chain chain) throws IOException {
				Request request = chain.request();

				return chain.proceed(request);
			}
		});

		retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL2)
			.client(client.build())
			.addConverterFactory(GsonConverterFactory.create())
			.build();


		return retrofit;
	}
}
```
