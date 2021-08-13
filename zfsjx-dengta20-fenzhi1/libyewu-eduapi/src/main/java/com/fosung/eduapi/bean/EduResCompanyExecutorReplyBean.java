package com.fosung.eduapi.bean;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.util.List;
import java.util.Objects;

public class EduResCompanyExecutorReplyBean extends ResponseSlbBean1 implements Parcelable {

    public List<DataBean> datalist;

    protected EduResCompanyExecutorReplyBean(Parcel in) {
    }

    public static final Creator<EduResCompanyExecutorReplyBean> CREATOR =
            new Creator<EduResCompanyExecutorReplyBean>() {
        @Override
        public EduResCompanyExecutorReplyBean createFromParcel(Parcel in) {
            return new EduResCompanyExecutorReplyBean(in);
        }

        @Override
        public EduResCompanyExecutorReplyBean[] newArray(int size) {
            return new EduResCompanyExecutorReplyBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static class DataBean implements Parcelable {
        /**{
         "code": "000002000008000002000001",
         "orgName": "中共青岛市市南区委员会",
         "level": 3,
         "outCode": null,
         "num": 1,
         "leaf": false,
         "parentId": "e011ad6a-57f1-11e7-a1a4-0050569a68e4",
         "orgId": "e0118eac-57f1-11e7-a176-0050569a68e4",
         "levelType": null,
         "outId": null,
         "outName": "node17"
        },*/

        public boolean checked;
        public boolean enable = true;

        public String code;
        public String orgName;
        public String parentId;
        public String orgId;
        public boolean leaf;


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            DataBean that = (DataBean) o;
            return Objects.equals(orgId, that.orgId);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public int hashCode() {
            return Objects.hash(orgId);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            checked = in.readByte() != 0;
            code = in.readString();
            leaf = in.readByte() != 0;
            orgName = in.readString();
            parentId = in.readString();
            orgId = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte((byte) (checked ? 1 : 0));
            parcel.writeString(code);
            parcel.writeByte((byte) (leaf ? 1 : 0));
            parcel.writeString(orgName);
            parcel.writeString(parentId);
            parcel.writeString(orgId);
        }
    }
}
