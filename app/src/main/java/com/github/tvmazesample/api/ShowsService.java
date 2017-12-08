package com.github.tvmazesample.api;

import com.github.tvmazesample.api.dto.ShowDto;
import com.github.tvmazesample.api.dto.ShowEpisodeDto;
import com.github.tvmazesample.api.dto.ShowSeasonDto;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ShowsService {

    @GET("shows")
    Observable<List<ShowDto>> loadShows(@Query("page") int page);

    @GET("shows/{id}/seasons")
    Observable<List<ShowSeasonDto>> loadSeasons(@Path("id") long id);

    @GET("shows/{id}/episodes")
    Observable<List<ShowEpisodeDto>> loadEpisodes(@Path("id") long id);
}
