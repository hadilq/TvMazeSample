package com.github.tvmazesample.manager;

import com.github.tvmazesample.api.ShowsService;
import com.github.tvmazesample.api.dto.ShowDto;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ShowsManager {

    @Inject
    ShowsService mShowsService;

    @Inject
    public ShowsManager() {
    }

    public Observable<List<ShowDto>> getShows(int page) {
        return mShowsService.loadShows(page);
    }

}
