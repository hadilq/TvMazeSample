package com.github.tvmazesample.api.dto;

import java.util.Date;

public class ShowSeasonDto {
    private int id;
    private String url;
    private String name;
    private String summary;
    private int number;
    private int episodeOrder;
    private Date premiereDate;
    private Date endDate;
    private ShowNetwork network;
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

    public int getNumber() {
        return number;
    }

    public int getEpisodeOrder() {
        return episodeOrder;
    }

    public Date getPremiereDate() {
        return premiereDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ShowNetwork getNetwork() {
        return network;
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

    void setNumber(int number) {
        this.number = number;
    }

    void setEpisodeOrder(int episodeOrder) {
        this.episodeOrder = episodeOrder;
    }

    void setPremiereDate(Date premiereDate) {
        this.premiereDate = premiereDate;
    }

    void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    void setNetwork(ShowNetwork network) {
        this.network = network;
    }

    void setImage(ShowImage image) {
        this.image = image;
    }

    void setLink(LinkDto _link) {
        this._link = _link;
    }
}
