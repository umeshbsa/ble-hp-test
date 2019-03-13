package com.app.es;

import android.os.Parcel;
import android.os.Parcelable;

public class PlanStep implements Parcelable {
    private int slope;
    private int speed;
    private int time;

    /* renamed from: com.hankang.phone.treadmill.bean.PlanStep$1 */
    static Creator<PlanStep> CREATOR = new Creator<PlanStep>() {

        public PlanStep createFromParcel(Parcel source) {
            return new PlanStep(source);
        }

        public PlanStep[] newArray(int size) {
            return new PlanStep[size];
        }
    };

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSlope() {
        return this.slope;
    }

    public void setSlope(int slope) {
        this.slope = slope;
    }

    public int describeContents() {
        return 0;
    }

    public PlanStep(Parcel in) {
        this.speed = in.readInt();
        this.time = in.readInt();
        this.slope = in.readInt();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.speed);
        out.writeInt(this.time);
        out.writeInt(this.slope);
    }
}
