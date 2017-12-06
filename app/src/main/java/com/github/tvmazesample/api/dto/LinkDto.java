package com.github.tvmazesample.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class LinkDto implements Parcelable {
    private HrefDto sefl;
    private HrefDto previousepisode;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.sefl, flags);
        dest.writeParcelable(this.previousepisode, flags);
    }

    public LinkDto() {
    }

    protected LinkDto(Parcel in) {
        this.sefl = in.readParcelable(HrefDto.class.getClassLoader());
        this.previousepisode = in.readParcelable(HrefDto.class.getClassLoader());
    }

    public static final Creator<LinkDto> CREATOR = new Creator<LinkDto>() {
        @Override
        public LinkDto createFromParcel(Parcel source) {
            return new LinkDto(source);
        }

        @Override
        public LinkDto[] newArray(int size) {
            return new LinkDto[size];
        }
    };
}
