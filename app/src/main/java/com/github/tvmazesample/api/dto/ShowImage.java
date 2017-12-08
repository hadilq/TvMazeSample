package com.github.tvmazesample.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class ShowImage implements Parcelable{
    private String medium;
    private String original;

    public String getMedium() {
        return medium;
    }

    public String getOriginal() {
        return original;
    }

    void setMedium(String medium) {
        this.medium = medium;
    }

    void setOriginal(String original) {
        this.original = original;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.medium);
        dest.writeString(this.original);
    }

    public ShowImage() {
    }

    protected ShowImage(Parcel in) {
        this.medium = in.readString();
        this.original = in.readString();
    }

    public static final Creator<ShowImage> CREATOR = new Creator<ShowImage>() {
        @Override
        public ShowImage createFromParcel(Parcel source) {
            return new ShowImage(source);
        }

        @Override
        public ShowImage[] newArray(int size) {
            return new ShowImage[size];
        }
    };
}
