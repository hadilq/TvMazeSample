package com.github.tvmazesample.api;

import com.github.tvmazesample.api.dto.ShowDetailsDto;
import com.github.tvmazesample.api.dto.ShowDto;
import com.github.tvmazesample.api.dto.ShowSeasonsDto;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ShowsService {

    @GET("shows")
    Observable<List<ShowDto>> loadShows(@Query("page") int page);

    @GET("shows/{id}")
    Observable<ShowDetailsDto> loadShowDetail(@Path("id") long id);

    @GET("shows/{id}/seasons")
    Observable<ShowSeasonsDto> loadSeasons(@Path("id") long id);
}
