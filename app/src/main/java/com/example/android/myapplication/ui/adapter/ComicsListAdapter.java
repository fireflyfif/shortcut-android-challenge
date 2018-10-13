package com.example.android.myapplication.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.android.myapplication.model.CurrentXkcdComic;

public class ComicsListAdapter extends PagedListAdapter<CurrentXkcdComic, RecyclerView.ViewHolder> {


    protected ComicsListAdapter(@NonNull DiffUtil.ItemCallback<CurrentXkcdComic> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
