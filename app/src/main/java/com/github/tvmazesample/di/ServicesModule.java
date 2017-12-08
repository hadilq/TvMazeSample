package com.github.tvmazesample.di;

import com.github.tvmazesample.api.ShowsService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Module
public class ServicesModule {

    @Provides
    @Singleton
    ShowsService provideShowsService(Retrofit retrofit) {
        return retrofit.create(ShowsService.class);
    }

}
