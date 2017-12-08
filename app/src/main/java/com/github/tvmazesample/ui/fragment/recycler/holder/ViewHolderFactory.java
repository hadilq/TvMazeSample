package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.view.View;
import com.github.tvmazesample.ui.fragment.recycler.data.*;
import junit.framework.Assert;

import javax.inject.Inject;

public class ViewHolderFactory {

    @Inject
    ViewHolderFactory() {
    }

    public BaseViewHolder create(int viewType, View itemView) {
        switch (viewType) {
            case TryAgainData.VIEW_TYPE:
                return new TryAgainViewHolder(itemView);
            case ShowData.VIEW_TYPE:
                return new ShowViewHolder(itemView);
            case ShowDescriptionData.VIEW_TYPE:
                return new ShowDescriptionViewHolder(itemView);
            case TitleData.VIEW_TYPE:
                return new TitleViewHolder(itemView);
            case SeasonData.VIEW_TYPE:
                return new SeasonViewHolder(itemView);
            case EpisodeData.VIEW_TYPE:
                return new EpisodeViewHolder(itemView);
            case SummaryData.VIEW_TYPE:
                return new SummaryViewHolder(itemView);
            default:
                Assert.fail("A view holder is not defined");
                return new EmptyViewHolder(new View(itemView.getContext()));
        }
    }
}
