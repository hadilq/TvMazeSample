package com.github.tvmazesample.di;

import android.content.Context;
import com.github.tvmazesample.BuildConfig;
import com.github.tvmazesample.api.ShowsService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * @author hadi
 */
@Module
public class NetServiceModule {

    private static final long CONNECT_TIMEOUT_SECOND = 30;
    private static final long READ_TIMEOUT_SECOND = 30;

    @Provides
    @Singleton
    ShowsService provideShowsService(Retrofit retrofit) {
        return retrofit.create(ShowsService.class);
    }

    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor();
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggerInterceptor);
        }

        return httpClient;
    }

    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    @Provides
    @Singleton
    Picasso providePicasso(
            Context context,
            OkHttpClient.Builder httpClient
    ) {
        httpClient.cache(new Cache(context.getCacheDir(), Integer.MAX_VALUE));
        return new Picasso.Builder(context)
                .build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(
            final OkHttpClient.Builder httpClient,
            Retrofit.Builder retrofitBuilder,
            Picasso picasso
    ) {
        // First install Picasso
        Picasso.setSingletonInstance(picasso);

        // Then create retrofit
        httpClient
                .connectTimeout(CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECOND, TimeUnit.SECONDS);

        return retrofitBuilder
                .client(httpClient.build())
                .build();
    }
}
