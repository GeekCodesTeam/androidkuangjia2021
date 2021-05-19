package com.example.slbappcomm.videoplay.bt;

import java.util.List;
import java.util.Map;

public class ListUtil
{
    public static boolean isEmpty(final List<? extends Object> list) {
        return null == list || list.size() == 0;
    }
    public static boolean isEmpty(final Map<? extends Object,? extends Object> map) {
        return null == map || map.size() == 0;
    }
    public static <E> E getIndex(final List<E> list, final int i) {
        if (null == list || i < 0 || i >= list.size()) {
            return null;
        }
        return list.get(i);
    }
}
