package com.github.tvmazesample.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class HrefDto implements Parcelable {
    private String href;

    public String getHref() {
        return href;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    public HrefDto() {
    }

    protected HrefDto(Parcel in) {
        this.href = in.readString();
    }

    public static final Creator<HrefDto> CREATOR = new Creator<HrefDto>() {
        @Override
        public HrefDto createFromParcel(Parcel source) {
            return new HrefDto(source);
        }

        @Override
        public HrefDto[] newArray(int size) {
            return new HrefDto[size];
        }
    };
}
