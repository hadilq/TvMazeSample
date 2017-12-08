package com.github.tvmazesample.ui.fragment.recycler.data;

import android.os.Parcel;
import com.github.tvmazesample.R;

public class TitleData extends BaseRecyclerData {

    public static final int VIEW_TYPE = R.layout.title;

    private String title;

    public TitleData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    public int getSpan() {
        return 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    protected TitleData(Parcel in) {
        this.title = in.readString();
    }

    public static final Creator<TitleData> CREATOR = new Creator<TitleData>() {
        @Override
        public TitleData createFromParcel(Parcel source) {
            return new TitleData(source);
        }

        @Override
        public TitleData[] newArray(int size) {
            return new TitleData[size];
        }
    };
}
