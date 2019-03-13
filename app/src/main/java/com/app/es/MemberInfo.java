package com.app.es;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MemberInfo implements Parcelable {
    private String age;
    private String avatar;
    private String calorie;
    private String distance;
    private String experience;
    private String heat;
    private String height;
    private String hipline;
    private String id;
    private String isAdmin;
    private String lastWeight;
    private String level;
    private String levelNickName;
    private String medals;
    private String nick;
    private String sex;
    private String signature;
    private String target;
    private String time;
    private String waistline;
    private String weight;
    private ArrayList<WeightInfo> weightInfoList = new ArrayList();

    /* renamed from: com.hankang.phone.treadmill.bean.MemberInfo$1 */
  static   Creator<MemberInfo> CREATOR = new Creator<MemberInfo>() {

        public MemberInfo createFromParcel(Parcel source) {
            return new MemberInfo(source);
        }

        public MemberInfo[] newArray(int size) {
            return new MemberInfo[size];
        }
    };

    public String getExperience() {
        return this.experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelNickName() {
        return this.levelNickName;
    }

    public void setLevelNickName(String levelNickName) {
        this.levelNickName = levelNickName;
    }

    public String getMedals() {
        return this.medals;
    }

    public void setMedals(String medals) {
        this.medals = medals;
    }

    public String getCalorie() {
        return this.calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public static Creator<MemberInfo> getCreator() {
        return CREATOR;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getHeat() {
        return this.heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return this.age;
    }

    public void setBirth(String birth) {
        this.age = birth;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLastWeight() {
        return this.lastWeight;
    }

    public void setLastWeight(String lastWeight) {
        this.lastWeight = lastWeight;
    }

    public String getWaistline() {
        return this.waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHipline() {
        return this.hipline;
    }

    public void setHipline(String hipline) {
        this.hipline = hipline;
    }

    public ArrayList<WeightInfo> getWeightInfoList() {
        return this.weightInfoList;
    }

    public void setWeightInfoList(ArrayList<WeightInfo> weightInfoList) {
        this.weightInfoList = weightInfoList;
    }

    public int describeContents() {
        return 0;
    }

    public MemberInfo(Parcel in) {
        this.id = in.readString();
        this.isAdmin = in.readString();
        this.nick = in.readString();
        this.avatar = in.readString();
        this.signature = in.readString();
        this.sex = in.readString();
        this.age = in.readString();
        this.height = in.readString();
        this.weight = in.readString();
        this.lastWeight = in.readString();
        this.waistline = in.readString();
        this.hipline = in.readString();
        this.target = in.readString();
        this.heat = in.readString();
        this.experience = in.readString();
        this.level = in.readString();
        this.levelNickName = in.readString();
        this.medals = in.readString();
        this.calorie = in.readString();
        this.time = in.readString();
        this.distance = in.readString();
        in.readTypedList(this.weightInfoList, WeightInfo.CREATOR);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.id);
        out.writeString(this.isAdmin);
        out.writeString(this.nick);
        out.writeString(this.avatar);
        out.writeString(this.signature);
        out.writeString(this.sex);
        out.writeString(this.age);
        out.writeString(this.height);
        out.writeString(this.weight);
        out.writeString(this.lastWeight);
        out.writeString(this.waistline);
        out.writeString(this.hipline);
        out.writeString(this.target);
        out.writeString(this.heat);
        out.writeString(this.experience);
        out.writeString(this.level);
        out.writeString(this.levelNickName);
        out.writeString(this.medals);
        out.writeString(this.calorie);
        out.writeString(this.time);
        out.writeString(this.distance);
        out.writeTypedList(this.weightInfoList);
    }
}
