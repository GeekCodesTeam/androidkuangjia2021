package com.example.gsydemo.bt;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {
    private String id;
    private String nickname;
    private String headImgUrl;
    private String sex = "m";
    private double weight = 60;
    private String height;
    private int age = 30;
    private String num;//手环编号
    private String deviceNum;//手环序列号
    private String courseId;
    private String courseType;//团课：grp   私教课：pvt
    private int hr;//瞬时心率
    private double bmi;// BMI
    private double tizhi;// 体脂
    private double cal;//总卡路里
    private long firstTimestamp;//连接上手环获取到心率的时间
    private long lastTimestamp;//上一次获取到心率的时间
    private long currentTimestamp;//当前获取到心率的时间
    private int rank;//排名
    private int maxhr;//最大心率
    private int oldIndex;

    protected Member(Parcel in) {
        id = in.readString();
        nickname = in.readString();
        headImgUrl = in.readString();
        sex = in.readString();
        weight = in.readDouble();
        height = in.readString();
        age = in.readInt();
        num = in.readString();
        deviceNum = in.readString();
        courseId = in.readString();
        courseType = in.readString();
        hr = in.readInt();
        bmi = in.readDouble();
        tizhi = in.readDouble();
        cal = in.readDouble();
        firstTimestamp = in.readLong();
        lastTimestamp = in.readLong();
        currentTimestamp = in.readLong();
        rank = in.readInt();
        maxhr = in.readInt();
        oldIndex = in.readInt();
    }

    public Member(String id, String nickname, String headImgUrl, String sex,
                  double weight, String height, int age, String num, String deviceNum,
                  String courseId, String courseType, int hr, double bmi, double tizhi,
                  double cal, long firstTimestamp, long lastTimestamp,
                  long currentTimestamp, int rank, int maxhr, int oldIndex) {
        this.id = id;
        this.nickname = nickname;
        this.headImgUrl = headImgUrl;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.num = num;
        this.deviceNum = deviceNum;
        this.courseId = courseId;
        this.courseType = courseType;
        this.hr = hr;
        this.bmi = bmi;
        this.tizhi = tizhi;
        this.cal = cal;
        this.firstTimestamp = firstTimestamp;
        this.lastTimestamp = lastTimestamp;
        this.currentTimestamp = currentTimestamp;
        this.rank = rank;
        this.maxhr = maxhr;
        this.oldIndex = oldIndex;
    }

    public Member() {
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nickname);
        parcel.writeString(headImgUrl);
        parcel.writeString(sex);
        parcel.writeDouble(weight);
        parcel.writeString(height);
        parcel.writeInt(age);
        parcel.writeString(num);
        parcel.writeString(deviceNum);
        parcel.writeString(courseId);
        parcel.writeString(courseType);
        parcel.writeInt(hr);
        parcel.writeDouble(bmi);
        parcel.writeDouble(tizhi);
        parcel.writeDouble(cal);
        parcel.writeLong(firstTimestamp);
        parcel.writeLong(lastTimestamp);
        parcel.writeLong(currentTimestamp);
        parcel.writeInt(rank);
        parcel.writeInt(maxhr);
        parcel.writeInt(oldIndex);
    }

    @Override
    public String toString() {
        return "Member{" +
                "nickname='" + nickname + '\'' +
                ", hr=" + hr +
                ", cal=" + cal +
                ", bmi=" + bmi +
                ", tizhi=" + tizhi +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImgUrl() {
        return this.headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDeviceNum() {
        return this.deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseType() {
        return this.courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getHr() {
        return this.hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public double getCal() {
        return this.cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    public long getFirstTimestamp() {
        return this.firstTimestamp;
    }

    public void setFirstTimestamp(long firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public long getLastTimestamp() {
        return this.lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public long getCurrentTimestamp() {
        return this.currentTimestamp;
    }

    public void setCurrentTimestamp(long currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMaxhr() {
        return this.maxhr;
    }

    public void setMaxhr(int maxhr) {
        this.maxhr = maxhr;
    }

    public int getOldIndex() {
        return this.oldIndex;
    }

    public void setOldIndex(int oldIndex) {
        this.oldIndex = oldIndex;
    }

    public double getBmi() {
        return this.bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getTizhi() {
        return this.tizhi;
    }

    public void setTizhi(double tizhi) {
        this.tizhi = tizhi;
    }


}
