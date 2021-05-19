package com.haier.cellarette.baselibrary.liandong.demo1.contants;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author pengbo
 * @date 2019/1/8 0008
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({Liandong1ItemType.BIG_SORT, Liandong1ItemType.SMALL_SORT})
public @interface Liandong1ItemType {
    int BIG_SORT = 0;
    int SMALL_SORT = 1;
}
