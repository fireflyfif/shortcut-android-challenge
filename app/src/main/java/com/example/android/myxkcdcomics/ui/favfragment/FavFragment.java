package com.example.android.myxkcdcomics.ui.favfragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.myxkcdcomics.R;
import com.example.android.myxkcdcomics.database.FavComic;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavFragment extends Fragment {

    private static final String TAG = FavFragment.class.getSimpleName();

    private FavViewModel favViewModel;
    private FavComicsAdapter favAdapter;
    private PagedList<FavComic> favComicPagedList;

    @BindView(R.id.fav_comics_rv)
    RecyclerView favRv;
    @BindView(R.id.fav_progress_bar)
    ProgressBar favProgressBar;
    @BindView(R.id.fav_empty_text)
    TextView emptyMsg;

    public FavFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);
        ButterKnife.bind(this, rootView);

        favViewModel = ViewModelProviders.of(this).get(FavViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        favRv.setLayoutManager(layoutManager);
        favAdapter = new FavComicsAdapter();

        getFavComics();

        favRv.setAdapter(favAdapter);

        return rootView;
    }

    private void getFavComics() {
        favViewModel.getFavComics().observe(this, new Observer<PagedList<FavComic>>() {
            @Override
            public void onChanged(@Nullable PagedList<FavComic> favComics) {
                if (favComics != null && favComics.size() > 0) {
                    // Hide the progress bar and the empty message
                    favProgressBar.setVisibility(View.INVISIBLE);
                    emptyMsg.setVisibility(View.INVISIBLE);

                    Log.d(TAG, "Submit comics to the Adapter " + favComics.size());
                    favAdapter.submitList(favComics);
                } else {
                    // Show the message for empty list
                    emptyMsg.setVisibility(View.VISIBLE);
                    // Hide the progress bar
                    favProgressBar.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
}
