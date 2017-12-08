package com.github.tvmazesample.api.dto;

import java.util.Date;

public class ShowEpisodeDto {
    private int id;
    private String url;
    private String name;
    private String summary;
    private String airdate;
    private String airtime;
    private Date airstamp;
    private int season;
    private int number;
    private int runtime;
    private ShowImage image;
    private LinkDto _link;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public int getSeason() {
        return season;
    }

    public int getNumber() {
        return number;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getAirdate() {
        return airdate;
    }

    public String getAirtime() {
        return airtime;
    }

    public Date getAirstamp() {
        return airstamp;
    }

    public ShowImage getImage() {
        return image;
    }

    public LinkDto getLink() {
        return _link;
    }

    void setId(int id) {
        this.id = id;
    }

    void setUrl(String url) {
        this.url = url;
    }

    void setName(String name) {
        this.name = name;
    }

    void setSummary(String summary) {
        this.summary = summary;
    }

    void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    void setAirstamp(Date airstamp) {
        this.airstamp = airstamp;
    }

    void setSeason(int season) {
        this.season = season;
    }

    void setNumber(int number) {
        this.number = number;
    }

    void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    void setImage(ShowImage image) {
        this.image = image;
    }

    void setLink(LinkDto _link) {
        this._link = _link;
    }
}
