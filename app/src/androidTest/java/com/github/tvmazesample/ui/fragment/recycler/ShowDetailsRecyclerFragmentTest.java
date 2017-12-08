package com.github.tvmazesample.ui.fragment.recycler;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.github.tvmazesample.ApplicationLauncher;
import com.github.tvmazesample.R;
import com.github.tvmazesample.api.ShowsService;
import com.github.tvmazesample.api.dto.DefaultDtoFactory;
import com.github.tvmazesample.api.dto.ShowDto;
import com.github.tvmazesample.api.dto.ShowEpisodeDto;
import com.github.tvmazesample.api.dto.ShowSeasonDto;
import com.github.tvmazesample.di.AppComponent;
import com.github.tvmazesample.di.AppModule;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.ui.activity.MainActivity;
import com.github.tvmazesample.util.NavigationUtil;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Singleton;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.github.tvmazesample.util.Matchers.withIndex;
import static com.github.tvmazesample.util.Matchers.withRecyclerView;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ShowDetailsRecyclerFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    private MockAppComponent testAppComponent;
    private ShowsService mockShowsService;
    private ApplicationLauncher app;

    @Before
    public void setup() {
        mockShowsService = mock(ShowsService.class);

        app = (ApplicationLauncher) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        assertNotNull(app);
        testAppComponent = DaggerShowDetailsRecyclerFragmentTest_MockAppComponent.builder()
                .appModule(new AppModule(app))
                .mockServiceModule(new MockServiceModule(mockShowsService))
                .build();
        Injector.setTestAppComponent(testAppComponent);
    }

    @Test
    public void testProvide() throws Exception {
        // GIVEN
        when(mockShowsService.loadShows(1)).thenReturn(Observable.create(new ObservableOnSubscribe<List<ShowDto>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<ShowDto>> e) throws Exception {
                e.onNext(DefaultDtoFactory.getInstance().getShowsDto(25));

            }
        }));
        when(mockShowsService.loadSeasons(anyLong())).thenReturn(Observable.create(new ObservableOnSubscribe<List<ShowSeasonDto>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShowSeasonDto>> e) throws Exception {
                e.onNext(DefaultDtoFactory.getInstance().getShowSeasonsDto(5));
            }
        }));
        when(mockShowsService.loadEpisodes(anyLong())).thenReturn(Observable.create(new ObservableOnSubscribe<List<ShowEpisodeDto>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShowEpisodeDto>> e) throws Exception {
                e.onNext(DefaultDtoFactory.getInstance().getShowEpisodesDto(25));
            }
        }));

        // WHEN
        NavigationUtil navigationUtil = testAppComponent.getNavigationUtil();
        activityRule.launchActivity(new Intent());
        navigationUtil.startContentFragment(activityRule.getActivity().getSupportFragmentManager(),
                ShowDetailsRecyclerFragment.instantiate(DefaultDtoFactory.getInstance().getShowDto(),
                        new ColorDrawable(R.color.colorPrimaryLight)));

        // THEN
        onView(withIndex(withId(R.id.list), 1)).check(matches(isDisplayed()));
        onView(withRecyclerView(withId(R.id.list)).atIndexedPositionOnView(1, 0, R.id.title))
                .check(matches(isDisplayed()));
        onView(withRecyclerView(withId(R.id.list)).atIndexedPositionOnView(1, 0, R.id.rating))
                .check(matches(isDisplayed()));
//        onView(withId(R.id.rating)).check(matches(isDisplayed()));

        activityRule.getActivity().finish();
    }


    @Singleton
    @Component(modules = {AppModule.class, MockServiceModule.class})
    public interface MockAppComponent extends AppComponent {
        NavigationUtil getNavigationUtil();
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