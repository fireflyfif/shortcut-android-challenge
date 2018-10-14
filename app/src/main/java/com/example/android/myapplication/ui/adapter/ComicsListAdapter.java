package com.example.android.myapplication.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.model.CurrentXkcdComic;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicsListAdapter extends PagedListAdapter<CurrentXkcdComic, RecyclerView.ViewHolder> {

    private static final String TAG = ComicsListAdapter.class.getSimpleName();
    private Context context;

    public ComicsListAdapter(Context context) {
        super(CurrentXkcdComic.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.comic_item, parent, false);

        return new ComicItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItem(position) != null) {
            ((ComicItemViewHolder) holder).bindTo(getItem(position));
        }
    }

    public class ComicItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.comic_title)
        TextView comicTitle;
        @BindView(R.id.comic_image)
        ImageView comicImage;

        public ComicItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindTo(CurrentXkcdComic currentComic) {

            String comicTitleSting = currentComic.getTitle();
            comicTitle.setText(comicTitleSting);

            String comicImgUrl = currentComic.getImg();
            Picasso.get()
                    .load(comicImgUrl)
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(comicImage);

            Log.d(TAG, "Get the title of the comics: " + comicTitleSting);
            Log.d(TAG, "Get the image path of the comics: " + comicTitleSting);
        }
    }
}
