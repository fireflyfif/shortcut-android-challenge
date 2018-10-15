package com.example.android.myxkcdcomics.ui;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myxkcdcomics.R;
import com.example.android.myxkcdcomics.database.FavComic;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;
import com.example.android.myxkcdcomics.repository.XkcdRepository;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailComicActivity extends AppCompatActivity {

    private static final String TAG = DetailComicActivity.class.getSimpleName();
    private static final String COMIC_PARCEL_KEY = "comic_key";

    @BindView(R.id.coordinator_detail_comic)
    CoordinatorLayout coordinatorLayout;
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

    private String titleString;
    private String numberString;
    private String linkString;
    private String monthString;
    private String yearString;
    private String transcriptString;
    private String altString;
    private String imageString;

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
        titleString = currentComic.getTitle();
        numberString = String.valueOf(currentComic.getNum());
        monthString = currentComic.getMonth();
        yearString = currentComic.getYear();
        transcriptString = currentComic.getTranscript();
        altString = currentComic.getAlt();
        linkString = currentComic.getLink();
        imageString = currentComic.getImg();

        Log.d("DetailComicActivity", "Get the transcript: " + transcriptString);
        Log.d("DetailComicActivity", "Get the alt: " + altString);

        title.setText(titleString);
        number.setText(numberString);
        month.setText(monthString);
        year.setText(yearString);
        description.setText(transcriptString);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_comic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                addComicToFavs();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addComicToFavs() {

        FavComic favComic = new FavComic(monthString, numberString, linkString, yearString,
                transcriptString, altString, imageString, titleString);

        XkcdRepository.getInstance(getApplication()).insertItem(favComic);
        Snackbar.make(coordinatorLayout, "Comic starred", Snackbar.LENGTH_SHORT).show();
        Log.d(TAG, "Insert a new item into the db");

    }
}
