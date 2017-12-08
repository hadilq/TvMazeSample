package com.github.tvmazesample.di;

import com.github.tvmazesample.ui.activity.BaseContentActivity;
import com.github.tvmazesample.ui.activity.MainActivity;
import com.github.tvmazesample.ui.fragment.recycler.RecyclerListAdapter;
import com.github.tvmazesample.ui.fragment.recycler.ShowsRecyclerFragment;
import com.github.tvmazesample.ui.fragment.recycler.holder.ShowViewHolder;
import com.github.tvmazesample.viewmodel.ShowDetailsViewModel;
import com.github.tvmazesample.viewmodel.ShowsViewModel;
import dagger.Component;

import javax.inject.Singleton;

/**
 * @author hadi
 */
@Singleton
@Component(modules = {AppModule.class, NetServiceModule.class, ServicesModule.class})
public interface AppComponent {

    void inject(BaseContentActivity baseContentActivity);

    void inject(MainActivity mainActivity);

    void inject(ShowsViewModel showsViewModel);

    void inject(RecyclerListAdapter recyclerListAdapter);

    void inject(ShowViewHolder showViewHolder);

    void inject(ShowsRecyclerFragment showsRecyclerFragment);

    void inject(ShowDetailsViewModel showDetailsViewModel);
}
