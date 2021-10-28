package com.frsarker.medicineApp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Medicine_object implements Parcelable {
    private String name;
    private String type;
    private String content;
    private String uses;
    private String imageUrl;

    protected Medicine_object(Parcel in) {
        name = in.readString();
        type = in.readString();
        content = in.readString();
        uses = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Medicine_object> CREATOR = new Creator<Medicine_object>() {
        @Override
        public Medicine_object createFromParcel(Parcel in) {
            return new Medicine_object(in);
        }

        @Override
        public Medicine_object[] newArray(int size) {
            return new Medicine_object[size];
        }
    };

    public String getName() {
        return name;
    }
    public Medicine_object(String name, String type, String content, String uses, String imageUrl){
        this.name = name;
        this.type = type;
        this.content = content;
        this.uses = uses;
        this.imageUrl = imageUrl;
    }

    public Medicine_object(){

    }
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(content);
        dest.writeString(uses);
        dest.writeString(imageUrl);
    }
}
