package com.example.android.myxkcdcomics.ui.comicsfragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.myxkcdcomics.R;
import com.example.android.myxkcdcomics.callbacks.OnComicClickListener;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;
import com.example.android.myxkcdcomics.ui.DetailComicActivity;
import com.example.android.myxkcdcomics.ui.comicsfragment.adapters.ComicsListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicsFragment extends Fragment implements OnComicClickListener {

    private static final String COMIC_PARCEL_KEY = "comic_key";

    @BindView(R.id.comics_rv)
    RecyclerView comicRv;
    @BindView(R.id.error_message)
    TextView errorMessage;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ComicsListAdapter comicsAdapter;
    private ComicsViewModel viewModel;

    public ComicsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comics, container, false);
        ButterKnife.bind(this, rootView);

        // Initialize the ViewModel
        initCurrentComicViewModel();

        return rootView;
    }

    /*
    Method for sating up the RecyclerView
     */
    private void setRecyclerView() {
        int columnCount = getResources().getInteger(R.integer.list_column_count);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);

        comicRv.setLayoutManager(staggeredGridLayoutManager);
        comicsAdapter = new ComicsListAdapter(this);
    }

    /*
    Method for initializing the ViewModel
     */
    private void initCurrentComicViewModel() {
        // Setup the RecyclerView first
        setRecyclerView();

        // Initialize the ViewModel
        viewModel = ViewModelProviders.of(this).get(ComicsViewModel.class);

        viewModel.getCurrentComic().observe(this, new Observer<PagedList<CurrentXkcdComic>>() {
            @Override
            public void onChanged(@Nullable PagedList<CurrentXkcdComic> currentXkcdComics) {
                if (currentXkcdComics != null) {
                    comicsAdapter.submitList(currentXkcdComics);
                }
            }
        });

        // Set the adapter on the Rv
        comicRv.setAdapter(comicsAdapter);
    }

    @Override
    public void onComicClick(CurrentXkcdComic currentXkcdComic) {
        // Create an intent for the detail view
        Bundle bundle = new Bundle();
        bundle.putParcelable(COMIC_PARCEL_KEY, currentXkcdComic);

        Intent detailIntent = new Intent(getContext(), DetailComicActivity.class);
        detailIntent.putExtras(bundle);
        startActivity(detailIntent);
    }
}
