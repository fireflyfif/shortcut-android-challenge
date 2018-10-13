package com.example.android.myapplication.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.model.CurrentXkcdComic;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.comic_title)
    TextView comicTitle;
    @BindView(R.id.comic_image)
    ImageView comicImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Initialize the ViewModel
        initCurrentComicViewModel();

    }

    /*
    Method for initializing the ViewModel
     */
    private void initCurrentComicViewModel() {
        // Initialize the ViewModel
        CurrentComicViewModel viewModel = ViewModelProviders.of(this)
                .get(CurrentComicViewModel.class);
        viewModel.initCurrentComic();

        viewModel.getCurrentComic().observe(this, new Observer<CurrentXkcdComic>() {
            @Override
            public void onChanged(@Nullable CurrentXkcdComic currentXkcdComic) {
                if (currentXkcdComic != null) {
                    setupUI(currentXkcdComic);
                }
            }
        });
    }

    /*
    Method for setting up the UI
     */
    private void setupUI(@NonNull CurrentXkcdComic currentXkcdComic) {
        String comicTitleSting = currentXkcdComic.getTitle();
        comicTitle.setText(comicTitleSting);

        String comicImgUrl = currentXkcdComic.getImg();
        Picasso.get()
                .load(comicImgUrl)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(comicImage);
    }
}
