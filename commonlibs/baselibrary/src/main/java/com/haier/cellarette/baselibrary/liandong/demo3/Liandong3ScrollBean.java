package com.haier.cellarette.baselibrary.liandong.demo3;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class Liandong3ScrollBean extends SectionEntity<Liandong3ScrollBean.ScrollItemBean> {

    public Liandong3ScrollBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public Liandong3ScrollBean(Liandong3ScrollBean.ScrollItemBean bean) {
        super(bean);
    }

    public static class ScrollItemBean {
        private String text;
        private String type;

        public ScrollItemBean(String text, String type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
