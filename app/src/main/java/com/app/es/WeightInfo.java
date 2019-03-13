package com.app.es;

import android.os.Parcel;
import android.os.Parcelable;

public class WeightInfo implements Parcelable {
    private String date;
    private String id;
    private String time;
    private String weight;

    /* renamed from: com.hankang.phone.treadmill.bean.WeightInfo$1 */
   static Creator<WeightInfo> CREATOR = new Creator<WeightInfo>() {

        public WeightInfo createFromParcel(Parcel source) {
            return new WeightInfo(source);
        }

        public WeightInfo[] newArray(int size) {
            return new WeightInfo[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int describeContents() {
        return 0;
    }

    public WeightInfo(Parcel in) {
        this.id = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.weight = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.id);
        out.writeString(this.date);
        out.writeString(this.time);
        out.writeString(this.weight);
    }
}
