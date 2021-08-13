package com.fosung.eduapi.bean;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.io.Serializable;
import java.util.List;

public class EduResInfoBean extends ResponseSlbBean1 implements Serializable {

    private List<DatalistBean> datalist;

    public List<DatalistBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DatalistBean> datalist) {
        this.datalist = datalist;
    }

    public static class DatalistBean implements Serializable {
        private String label;
        private String type;
        private String value;
        private String key;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
