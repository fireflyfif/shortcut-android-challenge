package com.example.android.myxkcdcomics.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.myxkcdcomics.R;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;
import com.example.android.myxkcdcomics.ui.adapter.ComicsListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.comics_rv)
    RecyclerView comicRv;
    @BindView(R.id.error_message)
    TextView errorMessage;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ComicsListAdapter comicsAdapter;
    private CurrentComicViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Initialize the ViewModel
        initCurrentComicViewModel();
    }

    private void setRecyclerView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        comicRv.setLayoutManager(staggeredGridLayoutManager);
        comicsAdapter = new ComicsListAdapter();
    }

    /*
    Method for initializing the ViewModel
     */
    private void initCurrentComicViewModel() {
        // Setup the RecyclerView
        setRecyclerView();

        // Initialize the ViewModel
        viewModel = ViewModelProviders.of(this).get(CurrentComicViewModel.class);

        viewModel.getCurrentComic().observe(this, new Observer<PagedList<CurrentXkcdComic>>() {
            @Override
            public void onChanged(@Nullable PagedList<CurrentXkcdComic> currentXkcdComics) {
                if (currentXkcdComics != null) {
                    comicsAdapter.submitList(currentXkcdComics);
                }
            }
        });

        comicRv.setAdapter(comicsAdapter);
    }

    /*
    Method for setting up the UI
     */
    /*private void setupUI(@NonNull CurrentXkcdComic currentXkcdComic) {
        String comicTitleSting = currentXkcdComic.getTitle();
        comicTitle.setText(comicTitleSting);

        String comicImgUrl = currentXkcdComic.getImg();
        Picasso.get()
                .load(comicImgUrl)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(comicImage);
    }*/
}