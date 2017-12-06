package com.github.tvmazesample.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class ShowCountry implements Parcelable{
    private String name;
    private String code;
    private String timezone;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getTimezone() {
        return timezone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.timezone);
    }

    public ShowCountry() {
    }

    protected ShowCountry(Parcel in) {
        this.name = in.readString();
        this.code = in.readString();
        this.timezone = in.readString();
    }

    public static final Creator<ShowCountry> CREATOR = new Creator<ShowCountry>() {
        @Override
        public ShowCountry createFromParcel(Parcel source) {
            return new ShowCountry(source);
        }

        @Override
        public ShowCountry[] newArray(int size) {
            return new ShowCountry[size];
        }
    };
}
