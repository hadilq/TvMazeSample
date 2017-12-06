package com.github.tvmazesample.ui.fragment.recycler.data;

import android.os.Parcel;
import com.github.tvmazesample.R;
import com.github.tvmazesample.api.dto.ShowDto;

public class ShowData extends BaseRecyclerData {

    public static final int VIEW_TYPE = R.layout.show;

    private ShowDto showDto;

    public ShowData(ShowDto showDto) {
        this.showDto = showDto;
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    public int getSpan() {
        return 1;
    }

    public ShowDto getShowDto() {
        return showDto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.showDto, flags);
    }

    protected ShowData(Parcel in) {
        this.showDto = in.readParcelable(ShowDto.class.getClassLoader());
    }

    public static final Creator<ShowData> CREATOR = new Creator<ShowData>() {
        @Override
        public ShowData createFromParcel(Parcel source) {
            return new ShowData(source);
        }

        @Override
        public ShowData[] newArray(int size) {
            return new ShowData[size];
        }
    };
}
