package com.github.tvmazesample.ui.fragment.recycler.data;

import android.os.Parcel;
import com.github.tvmazesample.R;

public class EpisodeData extends BaseRecyclerData {

    public static final int VIEW_TYPE = R.layout.episode;

    private String url;
    private String name;
    private String summary;
    private int season;
    private int number;
    private int runtime;

    public EpisodeData(String url, String name, String summary, int season, int number, int runtime) {
        this.url = url;
        this.name = name;
        this.summary = summary;
        this.season = season;
        this.number = number;
        this.runtime = runtime;
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
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.summary);
        dest.writeInt(this.season);
        dest.writeInt(this.number);
        dest.writeInt(this.runtime);
    }

    protected EpisodeData(Parcel in) {
        this.url = in.readString();
        this.name = in.readString();
        this.summary = in.readString();
        this.season = in.readInt();
        this.number = in.readInt();
        this.runtime = in.readInt();
    }

    public static final Creator<EpisodeData> CREATOR = new Creator<EpisodeData>() {
        @Override
        public EpisodeData createFromParcel(Parcel source) {
            return new EpisodeData(source);
        }

        @Override
        public EpisodeData[] newArray(int size) {
            return new EpisodeData[size];
        }
    };
}
