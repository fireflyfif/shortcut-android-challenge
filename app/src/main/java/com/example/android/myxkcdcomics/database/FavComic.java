package com.example.android.myxkcdcomics.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "fav_comics")
public class FavComic {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "month")
    private String month;

    @ColumnInfo(name = "number")
    private Integer num;

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

    public FavComic(int id, String month, Integer num, String link, String year,
                    String transcript, String alt, String img, String title) {
        this.id = id;
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

    public String getMonth() {
        return month;
    }

    public Integer getNum() {
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
