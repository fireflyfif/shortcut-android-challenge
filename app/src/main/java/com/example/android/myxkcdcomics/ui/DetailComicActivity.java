package com.example.android.myxkcdcomics.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myxkcdcomics.R;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailComicActivity extends AppCompatActivity {

    private static final String TAG = DetailComicActivity.class.getSimpleName();
    private static final String COMIC_PARCEL_KEY = "comic_key";

    @BindView(R.id.toolbar_detail)
    Toolbar toolbar;
    @BindView(R.id.comic_detail_title)
    TextView title;
    @BindView(R.id.comic_detail_number)
    TextView number;
    @BindView(R.id.comic_detail_year)
    TextView year;
    @BindView(R.id.comic_detail_month)
    TextView month;
    @BindView(R.id.comic_detail_image)
    ImageView image;
    @BindView(R.id.comic_detail_description)
    TextView description;
    @BindView(R.id.comic_detail_alt)
    TextView alt;

    private CurrentXkcdComic currentXkcdComic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            Bundle receivedArgs = getIntent().getExtras();
            currentXkcdComic = receivedArgs.getParcelable(COMIC_PARCEL_KEY);

            if (currentXkcdComic != null) {
                setupUI(currentXkcdComic);
            }
        }
    }

    private void setupUI(CurrentXkcdComic currentComic) {
        String titleString = currentComic.getTitle();
        String numberString = String.valueOf(currentComic.getNum());
        String monthString = currentComic.getMonth();
        String yearString = currentComic.getYear();
        String descriptionString = currentComic.getTranscript();
        String altString = currentComic.getAlt();
        Log.d("DetailComicActivity", "Get the transcript: " + descriptionString);
        Log.d("DetailComicActivity", "Get the alt: " + altString);
        final String imageString = currentComic.getImg();

        title.setText(titleString);
        number.setText(numberString);
        month.setText(monthString);
        year.setText(yearString);
        description.setText(descriptionString);
        alt.setText(altString);

        // Load the image with Picasso
        Picasso.get()
                .load(imageString)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "Picasso onError called, offline mode.");
                        // Try again online if cache failed
                        // source: https://stackoverflow.com/a/30686992/8132331
                        Picasso.get()
                                .load(imageString)
                                .error(R.drawable.ic_launcher_background)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(image, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.v(TAG, "Picasso could not fetch image.");
                                    }
                                });
                    }
                });
    }
}
