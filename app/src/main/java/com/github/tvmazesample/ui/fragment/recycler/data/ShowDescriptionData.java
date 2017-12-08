package com.github.tvmazesample.ui.fragment.recycler.data;

import android.os.Parcel;
import com.github.tvmazesample.R;
import com.github.tvmazesample.api.dto.ShowRating;

import java.util.Date;

public class ShowDescriptionData extends BaseRecyclerData {

    public static final int VIEW_TYPE = R.layout.show_description;

    private String title;
    private Date premiered;
    private int runtime;
    private ShowRating rating;

    public ShowDescriptionData(String title, Date premiered, int runtime, ShowRating rating) {
        this.title = title;
        this.premiered = premiered;
        this.runtime = runtime;
        this.rating = rating;
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    public int getSpan() {
        return 1;
    }

    public String getTitle() {
        return title;
    }

    public Date getPremiered() {
        return premiered;
    }

    public int getRuntime() {
        return runtime;
    }

    public ShowRating getRating() {
        return rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.premiered != null ? this.premiered.getTime() : -1);
        dest.writeParcelable(this.rating, flags);
    }

    protected ShowDescriptionData(Parcel in) {
        long tmpPremiered = in.readLong();
        this.premiered = tmpPremiered == -1 ? null : new Date(tmpPremiered);
        this.rating = in.readParcelable(ShowRating.class.getClassLoader());
    }

    public static final Creator<ShowDescriptionData> CREATOR = new Creator<ShowDescriptionData>() {
        @Override
        public ShowDescriptionData createFromParcel(Parcel source) {
            return new ShowDescriptionData(source);
        }

        @Override
        public ShowDescriptionData[] newArray(int size) {
            return new ShowDescriptionData[size];
        }
    };
}
