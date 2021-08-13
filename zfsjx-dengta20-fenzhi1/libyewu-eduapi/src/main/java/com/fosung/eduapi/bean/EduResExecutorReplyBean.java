package com.fosung.eduapi.bean;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.util.List;
import java.util.Objects;

public class EduResExecutorReplyBean extends ResponseSlbBean1 implements Parcelable {

    public List<DataBean> datalist;

    protected EduResExecutorReplyBean(Parcel in) {
    }

    public static final Creator<EduResExecutorReplyBean> CREATOR =
            new Creator<EduResExecutorReplyBean>() {
        @Override
        public EduResExecutorReplyBean createFromParcel(Parcel in) {
            return new EduResExecutorReplyBean(in);
        }

        @Override
        public EduResExecutorReplyBean[] newArray(int size) {
            return new EduResExecutorReplyBean[size];
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
            "teamName": null,
                "code": "000002000008000004",
                "shotName": null,
                "hasChildren": 1,
                "id": "3443526993289690067",
                "assistUintName": null,
                "teamCode": null,
                "parentId": "3443139788155138777",
                "inTown": null,
                "teamId": null,
                "name": "东营市级工作队"
        },*/

        public boolean checked;
        public boolean enable = true;

        public String code;
        public String name;
        public String parentId;
        public String orgId;
        public boolean hasChildren;
        public String typeFlag;


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
            return Objects.equals(parentId, that.parentId);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public int hashCode() {
            return Objects.hash(parentId);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            checked = in.readByte() != 0;
            code = in.readString();
            hasChildren = in.readByte() != 0;
            name = in.readString();
            parentId = in.readString();
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
            parcel.writeByte((byte) (hasChildren ? 1 : 0));
            parcel.writeString(name);
            parcel.writeString(parentId);
        }
    }
}
