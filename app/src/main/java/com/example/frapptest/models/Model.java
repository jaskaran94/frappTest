package com.example.frapptest.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public class Model implements Parcelable{

    String imageUrl;
    String title;
    String desc;
    String type;
    long viewCount;
    public Model(){
        super();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    protected Model(Parcel in) {
        imageUrl = in.readString();
        title = in.readString();
        desc = in.readString();
        type = in.readString();
        viewCount = in.readLong();

    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(type);
        dest.writeLong(viewCount);
    }
}
