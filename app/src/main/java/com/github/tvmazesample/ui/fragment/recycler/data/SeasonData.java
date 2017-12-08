package com.github.tvmazesample.ui.fragment.recycler.data;

import android.os.Parcel;
import com.github.tvmazesample.R;

import java.util.Date;

public class SeasonData extends BaseRecyclerData {

    public static final int VIEW_TYPE = R.layout.season;

    private String url;
    private String name;
    private String summary;
    private int number;
    private Date premiereDate;
    private Date endDate;

    public SeasonData(String url, String name, String summary, int number, Date premiereDate, Date endDate) {
        this.url = url;
        this.name = name;
        this.summary = summary;
        this.number = number;
        this.premiereDate = premiereDate;
        this.endDate = endDate;
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    public int getSpan() {
        return 1;
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

    public Date getPremiereDate() {
        return premiereDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.summary);
        dest.writeInt(this.number);
    }

    protected SeasonData(Parcel in) {
        this.url = in.readString();
        this.name = in.readString();
        this.summary = in.readString();
        this.number = in.readInt();
    }

    public static final Creator<SeasonData> CREATOR = new Creator<SeasonData>() {
        @Override
        public SeasonData createFromParcel(Parcel source) {
            return new SeasonData(source);
        }

        @Override
        public SeasonData[] newArray(int size) {
            return new SeasonData[size];
        }
    };
}
