package com.github.tvmazesample.manager;

import com.github.tvmazesample.api.ShowsService;
import com.github.tvmazesample.api.dto.ShowEpisodeDto;
import com.github.tvmazesample.api.dto.ShowSeasonDto;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ShowDetailsManager {

    @Inject
    ShowsService mShowsService;

    @Inject
    ShowDetailsManager() {
    }

    public Observable<Aggregator> getShowEpisodes(final long id, List<ShowSeasonDto> seasons) {
        final Aggregator aggregator = new Aggregator(seasons);
        return Observable.create(new ObservableOnSubscribe<Aggregator>() {
            @Override
            public void subscribe(final ObservableEmitter<Aggregator> e) throws Exception {
                mShowsService.loadEpisodes(id)
                        .subscribe(new Consumer<List<ShowEpisodeDto>>() {
                            @Override
                            public void accept(List<ShowEpisodeDto> episodes) throws Exception {
                                if (!e.isDisposed()) {
                                    aggregator.setShowEpisodesDto(episodes);
                                    e.onNext(aggregator);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!e.isDisposed()) {
                                    e.onError(throwable);
                                }
                            }
                        });
            }
        });
    }

    public Observable<List<ShowSeasonDto>> getSeasons(final long id) {
        return mShowsService.loadSeasons(id);
    }

    public static class Aggregator {
        private List<ShowSeasonDto> showSeasonsDto;
        private List<ShowEpisodeDto> showEpisodesDto;

        public Aggregator(List<ShowSeasonDto> showSeasonsDto) {
            this.showSeasonsDto = showSeasonsDto;
        }

        public List<ShowSeasonDto> getShowSeasonsDto() {
            return showSeasonsDto;
        }

        public List<ShowEpisodeDto> getShowEpisodesDto() {
            return showEpisodesDto;
        }

        public void setShowEpisodesDto(List<ShowEpisodeDto> showEpisodesDto) {
            this.showEpisodesDto = showEpisodesDto;
        }
    }

}
