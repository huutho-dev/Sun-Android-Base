package team.app.base.di

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.app.base.BuildConfig
import team.app.base.data.RequestInterface
import team.app.base.utils.isNetworkConnected
import java.io.File
import java.util.concurrent.TimeUnit


val netModule = module {

    single { createGson() }

    single {
        createOkHttpClient(
            get(),
            createLoggingInterceptor(),
            createCacheInterceptor(get()),
            createHeaderInterceptor()
        )
    }

    single<RequestInterface> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.DOMAIN)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(RequestInterface::class.java)
    }
}


fun createGson(): Gson = Gson()


fun createLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { message -> Log.e("HttpLoggingInterceptor", message) })
    interceptor.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return interceptor
}


fun createCacheInterceptor(context: Context): Interceptor {
    return Interceptor { chain ->
        val request = chain.request()
        chain.proceed(
            when (context.isNetworkConnected()) {
                true -> request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else -> request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()
            }
        )
    }
}


fun createHeaderInterceptor(): Interceptor {
    val map: MutableMap<String, String> = mutableMapOf()
    return Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url().newBuilder().build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .headers(Headers.of(map))
            .method(request.method(), request.body())
            .build()
        chain.proceed(newRequest)
    }
}


fun createOkHttpClient(
    context: Context,
    loggingInterceptor: HttpLoggingInterceptor,
    cacheInterceptor: Interceptor,
    headerInterceptor: Interceptor
): OkHttpClient {
    val timeout = 30L
    val cache = Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024) // 10M cache
    val okHttpBuilder = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(cacheInterceptor)
        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .callTimeout(timeout, TimeUnit.SECONDS)
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)

    return okHttpBuilder.build()
}