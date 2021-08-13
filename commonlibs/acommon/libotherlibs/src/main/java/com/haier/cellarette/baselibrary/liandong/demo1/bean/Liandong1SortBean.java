package com.haier.cellarette.baselibrary.liandong.demo1.bean;

import java.util.List;

/**
 * @author pengbo
 * @date 2019/1/5 0005
 */
public class Liandong1SortBean {
    public int bigSortId;
    public String bigSortName;

    public List<ListBean> list;

    public static class ListBean {
        public int smallSortId;
        public String smallSortName;
    }

    public boolean isSelected;
}
