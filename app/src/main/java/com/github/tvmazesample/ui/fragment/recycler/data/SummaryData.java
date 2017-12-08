package com.github.tvmazesample.ui.fragment.recycler.data;

import android.os.Parcel;
import com.github.tvmazesample.R;

public class SummaryData extends BaseRecyclerData {
    public static final int VIEW_TYPE = R.layout.summary;

    private String summary;
    private String url;

    public SummaryData(String summary, String url) {
        this.summary = summary;
        this.url = url;
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    public int getSpan() {
        return 1;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.summary);
    }

    protected SummaryData(Parcel in) {
        this.summary = in.readString();
    }

    public static final Creator<SummaryData> CREATOR = new Creator<SummaryData>() {
        @Override
        public SummaryData createFromParcel(Parcel source) {
            return new SummaryData(source);
        }

        @Override
        public SummaryData[] newArray(int size) {
            return new SummaryData[size];
        }
    };
}
