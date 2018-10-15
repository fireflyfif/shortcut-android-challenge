package com.example.android.myxkcdcomics.ui.comicsfragment.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myxkcdcomics.R;
import com.example.android.myxkcdcomics.callbacks.OnComicClickListener;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicsListAdapter extends PagedListAdapter<CurrentXkcdComic, RecyclerView.ViewHolder> {

    private static final String TAG = ComicsListAdapter.class.getSimpleName();
    private List<CurrentXkcdComic> comicsList;
    private OnComicClickListener clickHandler;

    public ComicsListAdapter(OnComicClickListener clickHandler) {
        super(CurrentXkcdComic.DIFF_CALLBACK);
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder called!");

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comic_item, parent, false);

        return new ComicItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called!");

        if (getItem(position) != null) {
            ((ComicItemViewHolder) holder).bindTo(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class ComicItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.comic_title)
        TextView comicTitle;
        @BindView(R.id.comic_number)
        TextView comicNumber;
        @BindView(R.id.comic_image)
        ImageView comicImage;
        @BindView(R.id.comic_year)
        TextView comicYear;

        public ComicItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        private void bindTo(final CurrentXkcdComic currentComic) {

            String comicNimString = String.valueOf(currentComic.getNum());
            comicNumber.setText(comicNimString);

            String comicTitleSting = currentComic.getTitle();
            comicTitle.setText(comicTitleSting);

            String comicYearString = currentComic.getYear();
            comicYear.setText(comicYearString);

            final String comicImgUrl = currentComic.getImg();
            Picasso.get()
                    .load(comicImgUrl)
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(comicImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d(TAG, "Picasso onError called, offline mode.");
                            // Try again online if cache failed
                            // source: https://stackoverflow.com/a/30686992/8132331
                            Picasso.get()
                                    .load(comicImgUrl)
                                    .error(R.drawable.ic_launcher_background)
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .into(comicImage, new Callback() {
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
        public void onClick(View view) {
            CurrentXkcdComic currentComic = getItem(getAdapterPosition());
            clickHandler.onComicClick(currentComic);
        }
    }
}
