package com.example.android.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentXkcdComic {

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("num")
    @Expose
    private Integer num;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("news")
    @Expose
    private String news;

    @SerializedName("safe_title")
    @Expose
    private String safeTitle;

    @SerializedName("transcript")
    @Expose
    private String transcript;

    @SerializedName("alt")
    @Expose
    private String alt;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("day")
    @Expose
    private String day;

    // Empty constructor
    public CurrentXkcdComic() {
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

    public String getNews() {
        return news;
    }

    public String getSafeTitle() {
        return safeTitle;
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

    public String getDay() {
        return day;
    }

}
