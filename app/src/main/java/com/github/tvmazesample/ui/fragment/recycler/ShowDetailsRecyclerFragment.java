package com.github.tvmazesample.ui.fragment.recycler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.tvmazesample.R;
import com.github.tvmazesample.api.dto.ShowDto;
import com.github.tvmazesample.api.dto.ShowEpisodeDto;
import com.github.tvmazesample.api.dto.ShowSeasonDto;
import com.github.tvmazesample.manager.ShowDetailsManager;
import com.github.tvmazesample.ui.activity.MainActivity;
import com.github.tvmazesample.ui.fragment.BundleKey;
import com.github.tvmazesample.ui.fragment.recycler.data.*;
import com.github.tvmazesample.ui.fragment.recycler.holder.BaseViewHolder;
import com.github.tvmazesample.ui.fragment.recycler.holder.EpisodeViewHolder;
import com.github.tvmazesample.ui.fragment.recycler.holder.SeasonViewHolder;
import com.github.tvmazesample.ui.fragment.recycler.holder.SummaryViewHolder;
import com.github.tvmazesample.util.L;
import com.github.tvmazesample.viewmodel.ShowDetailsViewModel;
import com.github.tvmazesample.viewmodel.response.Response;
import com.github.tvmazesample.viewmodel.response.Status;
import com.squareup.picasso.Picasso;
import io.reactivex.functions.Consumer;
import junit.framework.Assert;
import retrofit2.HttpException;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class ShowDetailsRecyclerFragment extends BaseRecyclerFragment {

    private ShowDetailsViewModel mViewModel;
    private Drawable mParallaxDrawable;

    public static ShowDetailsRecyclerFragment instantiate(ShowDto show, Drawable parallaxImage) {
        Bundle args = new Bundle();
        args.putParcelable(BundleKey.SHOW, show);

        ShowDetailsRecyclerFragment fragment = new ShowDetailsRecyclerFragment();
        fragment.setArguments(args);
        fragment.setParallaxDrawable(parallaxImage);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadParallaxDrawable();
        mViewModel = ViewModelProviders.of(this).get(ShowDetailsViewModel.class);

        setupObservers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getViewHolderObservable()
                .subscribe(new Consumer<BaseViewHolder>() {
                    @Override
                    public void accept(BaseViewHolder baseViewHolder) throws Exception {
                        if (baseViewHolder instanceof SeasonViewHolder) {
                            ((SeasonViewHolder) baseViewHolder).clickStream().subscribe(getClickOnASeasonObserver());
                        } else if (baseViewHolder instanceof EpisodeViewHolder) {
                            ((EpisodeViewHolder) baseViewHolder).clickStream().subscribe(getClickOnAEpisodeObserver());
                        } else if (baseViewHolder instanceof SummaryViewHolder) {
                            ((SummaryViewHolder) baseViewHolder).clickStream().subscribe(getClickOnASummaryObserver());
                        }
                    }
                });
        return view;
    }

    private Consumer<SummaryViewHolder> getClickOnASummaryObserver() {
        return new Consumer<SummaryViewHolder>() {
            @Override
            public void accept(SummaryViewHolder summaryViewHolder) throws Exception {
                String url = summaryViewHolder.getData().getUrl();
                if (!TextUtils.isEmpty(url)) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }
        };
    }

    @NonNull
    private Consumer<EpisodeViewHolder> getClickOnAEpisodeObserver() {
        return new Consumer<EpisodeViewHolder>() {
            @Override
            public void accept(EpisodeViewHolder episodeViewHolder) throws Exception {
                String url = episodeViewHolder.getData().getUrl();
                if (!TextUtils.isEmpty(url)) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }
        };
    }

    @NonNull
    private Consumer<SeasonViewHolder> getClickOnASeasonObserver() {
        return new Consumer<SeasonViewHolder>() {
            @Override
            public void accept(SeasonViewHolder seasonViewHolder) throws Exception {
                String url = seasonViewHolder.getData().getUrl();
                if (!TextUtils.isEmpty(url)) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ShowDto show = getArguments().getParcelable(BundleKey.SHOW);
        List<BaseRecyclerData> content = new ArrayList<>();

        content.add(new ShowDescriptionData(show.getName(), show.getPremiered(), show.getRuntime(), show.getRating()));
        content.add(new SummaryData(show.getSummary(), show.getUrl()));
        handleDataInserted(content);
    }

    @Override
    protected int getMaxSpanCount() {
        return 1;
    }

    @Nullable
    @Override
    protected View getEmptyView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.empty_list, (ViewGroup) getView(), false);
        ((TextView) view.findViewById(R.id.empty_message)).setText(R.string.show_is_empty);
        return view;
    }

    @Override
    protected void provide(int page) {
        ShowDto show = getArguments().getParcelable(BundleKey.SHOW);
        mViewModel.load(show.getId());
    }

    @Override
    protected void setupHeaderTitle() {
        ShowDto show = getArguments().getParcelable(BundleKey.SHOW);
        if (!TextUtils.isEmpty(show.getName())) {
            getArguments().putString(BundleKey.HEADER_TITLE, show.getName());
        }
    }

    @Override
    public boolean hasHomeAsUp() {
        return true;
    }

    @Override
    public Drawable getCollapsingParallaxDrawable() {
        return mParallaxDrawable;
    }

    @Override
    public View.OnClickListener getFabClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDto show = getArguments().getParcelable(BundleKey.SHOW);
                if (!TextUtils.isEmpty(show.getUrl())) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(show.getUrl())));
                }
            }
        };
    }

    private void setParallaxDrawable(Drawable mParallaxDrawable) {
        this.mParallaxDrawable = mParallaxDrawable;
    }

    private void loadParallaxDrawable() {
        ShowDto show = getArguments().getParcelable(BundleKey.SHOW);
        FragmentActivity activity = getActivity();

        if (activity instanceof MainActivity) {
            ImageView headerView = ((MainActivity) activity).getHeaderView();
            if (headerView != null) {
                Picasso.with(getContext())
                        .load(show.getImage().getOriginal())
                        .into(headerView);
            }

        } else {
            Assert.fail();
        }
    }

    private void setupObservers() {
        mViewModel.getLoadingStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loading) {
                if (loading != null) {
                    handleLoading(loading);
                }
            }
        });

        mViewModel.getResponse().observe(this, new Observer<Response<ShowDetailsManager.Aggregator>>() {
            @Override
            public void onChanged(@NonNull Response<ShowDetailsManager.Aggregator> response) {
                if (response.getStatus() == Status.SUCCESS) {
                    List<BaseRecyclerData> content = new ArrayList<>();

                    fillContent(response, content);

                    handleDataInserted(content);
                    handleEndOfList();
                } else {
                    if (response.getError() instanceof HttpException) {
                        if (((HttpException) response.getError()).response().code() == HttpURLConnection.HTTP_NOT_FOUND) {
                            handleEndOfList();
                        } else {
                            handleError(getString(R.string.loading_problem));
                        }
                    } else {
                        handleError(getString(R.string.loading_problem));
                        L.e(getClass(), "Error happened: ", response.getError());
                    }
                }

            }
        });
    }

    private void fillContent(@NonNull Response<ShowDetailsManager.Aggregator> response, List<BaseRecyclerData> content) {
        if (response.getData().getShowSeasonsDto() != null && response.getData().getShowSeasonsDto().size() != 0) {
            content.add(new TitleData(getString(R.string.seasons)));
            for (ShowSeasonDto seasonDto : response.getData().getShowSeasonsDto()) {
                content.add(new SeasonData(seasonDto.getUrl(), seasonDto.getName(), seasonDto.getSummary(),
                        seasonDto.getNumber(), seasonDto.getPremiereDate(), seasonDto.getEndDate()));
            }

            if (response.getData().getShowEpisodesDto() != null && response.getData().getShowEpisodesDto().size() != 0) {
                content.add(new TitleData(getString(R.string.episodes)));
                for (ShowEpisodeDto episode : response.getData().getShowEpisodesDto()) {
                    content.add(new EpisodeData(episode.getUrl(), episode.getName(), episode.getSummary(),
                            episode.getSeason(), episode.getNumber(), episode.getRuntime()));
                }
            }
        }
    }
}
