package com.example.android.myxkcdcomics.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentXkcdComic implements Parcelable {

    public static DiffUtil.ItemCallback<CurrentXkcdComic> DIFF_CALLBACK = new DiffUtil.ItemCallback<CurrentXkcdComic>() {
        @Override
        public boolean areItemsTheSame(CurrentXkcdComic oldItem, CurrentXkcdComic newItem) {
            return oldItem.num.equals(newItem.num);
        }

        @Override
        public boolean areContentsTheSame(CurrentXkcdComic oldItem, CurrentXkcdComic newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            return true;
        }

        CurrentXkcdComic currentComic = (CurrentXkcdComic) obj;
        return currentComic.num.equals(this.num);
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.month);
        dest.writeInt(this.num);
        dest.writeString(this.link);
        dest.writeString(this.year);
        dest.writeString(this.news);
        dest.writeString(this.safeTitle);
        dest.writeString(this.transcript);
        dest.writeString(this.alt);
        dest.writeString(this.img);
        dest.writeString(this.title);
        dest.writeString(this.day);
    }

    protected CurrentXkcdComic(Parcel in) {
        this.month = in.readString();
        this.num = in.readInt();
        this.link = in.readString();
        this.year = in.readString();
        this.news = in.readString();
        this.safeTitle = in.readString();
        this.transcript = in.readString();
        this.alt = in.readString();
        this.img = in.readString();
        this.title = in.readString();
        this.day = in.readString();
    }

    public static final Parcelable.Creator<CurrentXkcdComic> CREATOR = new Parcelable.Creator<CurrentXkcdComic>() {
        @Override
        public CurrentXkcdComic createFromParcel(Parcel source) {
            return new CurrentXkcdComic(source);
        }

        @Override
        public CurrentXkcdComic[] newArray(int size) {
            return new CurrentXkcdComic[size];
        }
    };
}
