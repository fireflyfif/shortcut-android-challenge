package com.example.android.myxkcdcomics.ui.favfragment;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myxkcdcomics.R;
import com.example.android.myxkcdcomics.database.FavComic;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavComicsAdapter extends PagedListAdapter<FavComic, RecyclerView.ViewHolder> {

    private static final String TAG = FavComicsAdapter.class.getSimpleName();
    private List<FavComic> favComicList;

    protected FavComicsAdapter() {
        super(FavComic.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fav_item, parent, false);

        return new FavComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItem(position) != null) {
            ((FavComicsViewHolder) viewHolder).bindTo(favComicList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (favComicList != null) {
            Log.d(TAG, "Size of the fav list: " + favComicList.size());
            return favComicList.size();
        }

        return super.getItemCount();
    }

    public class FavComicsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fav_image)
        ImageView favImage;
        @BindView(R.id.fav_title)
        TextView favTitle;

        public FavComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(FavComic favComic) {
            if (favComic != null) {
                String imageString = favComic.getImg();
                String titleString = favComic.getTitle();

                favTitle.setText(titleString);
                Picasso.get()
                        .load(imageString)
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(favImage);
            }
        }
    }
}
