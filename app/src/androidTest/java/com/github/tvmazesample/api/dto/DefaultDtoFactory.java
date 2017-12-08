package com.github.tvmazesample.api.dto;

import com.github.tvmazesample.di.Injector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DefaultDtoFactory {

    private static DefaultDtoFactory sInstance;

    private Random mRandom = new Random();

    private DefaultDtoFactory() {
    }

    public static DefaultDtoFactory getInstance() {
        if (sInstance == null) {
            synchronized (Injector.class) {
                if (sInstance == null) {
                    sInstance = new DefaultDtoFactory();
                }
            }
        }
        return sInstance;
    }

    public List<ShowDto> getShowsDto(int count) {
        List<ShowDto> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(getShowDto());
        }
        return list;
    }

    public ShowDto getShowDto() {
        ShowRating rating = new ShowRating();
        rating.setAverage(mRandom.nextFloat() * 10);

        ShowImage image = new ShowImage();
        image.setMedium("http://static.tvmaze.com/uploads/images/medium_portrait/1/4600.jpg");
        image.setOriginal("http://static.tvmaze.com/uploads/images/original_untouched/1/4600.jpg");

        ShowDto show = new ShowDto();
        show.setId(mRandom.nextInt());
        show.setName("test");
        show.setSummary("test summary: bla bla bla bla bla bla bla blabla blabla blabla blabla blabla bla");
        show.setRating(rating);
        show.setImage(image);

        show.setRuntime(mRandom.nextInt(120) + 10);
        show.setPremiered(new Date(mRandom.nextLong()));
        return show;
    }

    public List<ShowSeasonDto> getShowSeasonsDto(int count) {
        List<ShowSeasonDto> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(getShowSeasonDto(i + 1));
        }
        return list;
    }

    public ShowSeasonDto getShowSeasonDto(int number) {
        ShowSeasonDto season = new ShowSeasonDto();
        season.setId(mRandom.nextInt());
        season.setUrl("http://www.tvmaze.com/seasons/1/under-the-dome-season-1");
        season.setName("");
        season.setSummary("");
        season.setNumber(number);
        season.setPremiereDate(new Date(mRandom.nextLong()));
        season.setEndDate(new Date(mRandom.nextLong()));
        return season;
    }

    public List<ShowEpisodeDto> getShowEpisodesDto(int count) {
        List<ShowEpisodeDto> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(getShowEpisodeDto(1, i));
        }
        return list;
    }

    public ShowEpisodeDto getShowEpisodeDto(int season, int number) {
        ShowEpisodeDto episode = new ShowEpisodeDto();
        episode.setId(mRandom.nextInt());
        episode.setUrl("http://www.tvmaze.com/episodes/1/under-the-dome-1x01-pilot");
        episode.setName("Pilot");
        episode.setSummary("");
        episode.setSeason(season);
        episode.setNumber(number);
        episode.setRuntime(mRandom.nextInt(120) + 10);
        return episode;
    }
}