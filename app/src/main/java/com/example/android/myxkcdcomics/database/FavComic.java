package com.example.android.myxkcdcomics.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

@Entity(tableName = "fav_comics")
public class FavComic {

    public static DiffUtil.ItemCallback<FavComic> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavComic>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavComic oldItem, @NonNull FavComic newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavComic oldItem, @NonNull FavComic newItem) {
            return oldItem.equals(newItem);
        }
    };

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "month")
    private String month;

    @ColumnInfo(name = "number")
    private String num;

    @ColumnInfo(name = "link")
    private String link;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "transcript")
    private String transcript;

    @ColumnInfo(name = "alt")
    private String alt;

    @ColumnInfo(name = "img")
    private String img;

    @ColumnInfo(name = "title")
    private String title;

    public FavComic(String month, String num, String link, String year,
                    String transcript, String alt, String img, String title) {
        this.month = month;
        this.num = num;
        this.link = link;
        this.year = year;
        this.transcript = transcript;
        this.alt = alt;
        this.img = img;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public String getNum() {
        return num;
    }

    public String getLink() {
        return link;
    }

    public String getYear() {
        return year;
    }

    public String getTranscript() {
        return transcript;
    }

    public String getAlt() {
        return alt;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }
}
