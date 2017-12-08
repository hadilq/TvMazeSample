package com.github.tvmazesample.manager;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.github.tvmazesample.ApplicationLauncher;
import com.github.tvmazesample.api.ShowsService;
import com.github.tvmazesample.api.dto.ShowEpisodeDto;
import com.github.tvmazesample.api.dto.ShowSeasonDto;
import com.github.tvmazesample.di.AppComponent;
import com.github.tvmazesample.di.AppModule;
import com.github.tvmazesample.di.Injector;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class ShowDetailsManagerTest {

    private MockAppComponent testAppComponent;
    private ShowsService mockShowsService;
    private ApplicationLauncher app;
    private Random random = new Random();

    @Before
    public void setup() {
        mockShowsService = mock(ShowsService.class);

        app = (ApplicationLauncher)  InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        assertNotNull(app);
        testAppComponent = DaggerShowDetailsManagerTest_MockAppComponent.builder()
                .appModule(new AppModule(app))
                .mockServiceModule(new MockServiceModule(mockShowsService))
                .build();
        Injector.setTestAppComponent(testAppComponent);
    }

    @Test
    public void testLoadEpisodes() {
        // GIVEN
        long id = random.nextInt();
        when(mockShowsService.loadEpisodes(id)).thenReturn(Observable.create(new ObservableOnSubscribe<List<ShowEpisodeDto>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShowEpisodeDto>> e) throws Exception {
            }
        }));
        ShowDetailsManager showDetailsManager = testAppComponent.getShowDetailsManager();
        testAppComponent.inject(showDetailsManager);

        // WHEN
        showDetailsManager.getShowEpisodes(id, new ArrayList<ShowSeasonDto>()).subscribe();

        // THEN
        verify(mockShowsService).loadEpisodes(eq(id));
    }

    @Singleton
    @Component(modules = {AppModule.class, MockServiceModule.class})
    public interface MockAppComponent extends AppComponent {
        void inject(ShowDetailsManager showDetailsManager);

        ShowDetailsManager getShowDetailsManager();
    }

    @Module
    public class MockServiceModule {

        private ShowsService showsService;

        private MockServiceModule(ShowsService showsService) {
            this.showsService = showsService;
        }

        @Provides
        @Singleton
        ShowsService provideShowsService() {
            return showsService;
        }

    }
}