package com.github.tvmazesample.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class ShowDto implements Parcelable {

    private int id;
    private String url;
    private String name;
    private String type;
    private String language;
    private String status;
    private String officialSite;
    private String summary;
    private ShowRating rating;
    private ShowCountry country;
    private ShowImage image;
    private LinkDto _links;
    private int runtime;
    private int weight;
    private Date premiered;
    private long updated;

    private List<String> genres;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLanguage() {
        return language;
    }

    public String getStatus() {
        return status;
    }

    public String getOfficialSite() {
        return officialSite;
    }

    public String getSummary() {
        return summary;
    }

    public ShowRating getRating() {
        return rating;
    }

    public ShowCountry getCountry() {
        return country;
    }

    public ShowImage getImage() {
        return image;
    }

    public LinkDto get_links() {
        return _links;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getWeight() {
        return weight;
    }

    public Date getPremiered() {
        return premiered;
    }

    public long getUpdated() {
        return updated;
    }

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.language);
        dest.writeString(this.status);
        dest.writeString(this.officialSite);
        dest.writeString(this.summary);
        dest.writeParcelable(this.rating, flags);
        dest.writeParcelable(this.country, flags);
        dest.writeParcelable(this.image, flags);
        dest.writeParcelable(this._links, flags);
        dest.writeInt(this.runtime);
        dest.writeInt(this.weight);
        dest.writeLong(this.premiered != null ? this.premiered.getTime() : -1);
        dest.writeLong(this.updated);
        dest.writeStringList(this.genres);
    }

    public ShowDto() {
    }

    protected ShowDto(Parcel in) {
        this.id = in.readInt();
        this.url = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.language = in.readString();
        this.status = in.readString();
        this.officialSite = in.readString();
        this.summary = in.readString();
        this.rating = in.readParcelable(ShowRating.class.getClassLoader());
        this.country = in.readParcelable(ShowCountry.class.getClassLoader());
        this.image = in.readParcelable(ShowImage.class.getClassLoader());
        this._links = in.readParcelable(LinkDto.class.getClassLoader());
        this.runtime = in.readInt();
        this.weight = in.readInt();
        long tmpPremiered = in.readLong();
        this.premiered = tmpPremiered == -1 ? null : new Date(tmpPremiered);
        this.updated = in.readLong();
        this.genres = in.createStringArrayList();
    }

    public static final Creator<ShowDto> CREATOR = new Creator<ShowDto>() {
        @Override
        public ShowDto createFromParcel(Parcel source) {
            return new ShowDto(source);
        }

        @Override
        public ShowDto[] newArray(int size) {
            return new ShowDto[size];
        }
    };
}
